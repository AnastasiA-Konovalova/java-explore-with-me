package ru.practicum.ewmservice.compilation.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.compilation.dto.CompilationDto;
import ru.practicum.ewmservice.compilation.dto.EventInCompilationDto;
import ru.practicum.ewmservice.compilation.mapper.CompilationMapper;
import ru.practicum.ewmservice.compilation.model.Compilation;
import ru.practicum.ewmservice.compilation.repository.CompilationsRepository;
import ru.practicum.ewmservice.exception.NotFoundException;

import java.awt.print.Pageable;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicCompilationsServiceImpl implements PublicCompilationsService{

    private final CompilationsRepository compilationsRepository;

    @Override
    public List<CompilationDto> getWithConditions(Boolean pinned, Long from, Long size) {
//        List<EventInCompilationDto> compilations = compilationsRepository.getListCompilationsByPinnedWithFromAndOrSize(pinned, from, size);
//        if (compilations.isEmpty()) {
//            return List.of();
//        }
//
//        return compilations.stream()
//                .map(CompilationMapper::toDto)
//                .toList();
        PageRequest pageRequest = PageRequest.of((int) (from / size), size.intValue());
        List<Compilation> compilations = compilationsRepository.findAllByPinned(pinned, pageRequest).getContent();
        return compilations.stream()
                .map(CompilationMapper::toDto)
                .toList();
    }

    @Override
    public CompilationDto getById(Long compId) {
        return CompilationMapper.toDto(compilationsRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка с id " + compId + " не существует.")));
    }
}
