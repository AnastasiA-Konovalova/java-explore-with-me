package ru.practicum.stats;

import exeptions.InternalServerException;
import exeptions.NotFoundException;
import exeptions.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.StatsRequestDto;
import ru.practicum.dto.ViewStatsDto;

import java.util.List;

@Service
public class StatsClient {

    protected final RestTemplate rest;
    private final String serverUrl;

    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplate rest) {
        this.rest = rest;
        this.serverUrl = serverUrl;
    }

    public void save(EndpointHitDto endpointHitDto) {
        String uri = UriComponentsBuilder.fromHttpUrl(serverUrl)
                .path("/hit")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EndpointHitDto> entity = new HttpEntity<>(endpointHitDto, headers);
        ResponseEntity<Void> response = rest.exchange(
                uri,
                HttpMethod.POST,
                entity,
                Void.class
        );

        if (response.getStatusCode().is4xxClientError()) {
            throw new ValidationException("Ошибка при записи обращения к событию (путь hit), код " + response.getStatusCode());
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new InternalServerException("Ошибка при записи обращения к событию (путь hit), код " + response.getStatusCode());
        } else if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Другая ошибка при записи обращения к событию (путь hit), код " + response.getStatusCode());
        }
    }

    public List<ViewStatsDto> get(StatsRequestDto statsRequestDto) {
        String uri = UriComponentsBuilder.fromHttpUrl(serverUrl)
                .path("/stats")
                .queryParam("start", statsRequestDto.getStart())
                .queryParam("end", statsRequestDto.getEnd())
                .queryParam("uris", String.join(",", statsRequestDto.getUris()))
                .queryParam("unique", statsRequestDto.getUnique())
                .toUriString();

        ResponseEntity<List<ViewStatsDto>> response = rest.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode().is4xxClientError()) {
            throw new NotFoundException("Ошибка при запросе статистики (путь stats)");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new InternalServerException("Ошибка сервера (путь stats)");
        } else if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Другая ошибка при запросе статистики (путь stats), код " + response.getStatusCode());
        }
        return response.getBody();
    }
}