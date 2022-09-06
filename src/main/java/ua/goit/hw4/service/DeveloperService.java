package ua.goit.hw4.service;

import ua.goit.hw4.model.dao.DeveloperDao;
import ua.goit.hw4.model.dto.DeveloperDto;
import ua.goit.hw4.repository.DeveloperRepository;
import ua.goit.hw4.service.conventer.DeveloperConverter;

import java.util.Optional;

public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final DeveloperConverter developerConverter;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperConverter developerConverter) {
        this.developerRepository = developerRepository;
        this.developerConverter = developerConverter;
    }

    public DeveloperDto create(DeveloperDto developerDto) {
        DeveloperDao developerDao = developerRepository.save(developerConverter.to(developerDto));
        return developerConverter.from(developerDao);
    }

    public Optional<DeveloperDto> getById(Long id) {
        Optional<DeveloperDao> developerDao = developerRepository.findById(id);
        return developerDao.map(developerConverter::from);
    }

    public DeveloperDto update(DeveloperDto developerDto) {
        DeveloperDao developerDao = developerRepository.update(developerConverter.to(developerDto));
        return developerConverter.from(developerDao);
    }

    public void delete(DeveloperDto developerDto) {
        developerRepository.delete(developerConverter.to(developerDto));
    }
}
