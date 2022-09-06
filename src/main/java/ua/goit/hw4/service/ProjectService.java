package ua.goit.hw4.service;

import ua.goit.hw4.model.dao.ProjectDao;
import ua.goit.hw4.model.dto.ProjectDto;
import ua.goit.hw4.repository.ProjectRepository;
import ua.goit.hw4.service.conventer.ProjectConverter;

import java.util.Optional;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectConverter projectConverter;

    public ProjectService(ProjectRepository projectRepository, ProjectConverter projectConverter) {
        this.projectRepository = projectRepository;
        this.projectConverter = projectConverter;
    }

    public ProjectDto create(ProjectDto projectDto) {
        ProjectDao projectDao = projectRepository.save(projectConverter.to(projectDto));
        return projectConverter.from(projectDao);
    }

    public Optional<ProjectDto> getById(Long id) {
        Optional<ProjectDao> projectDao = projectRepository.findById(id);
        return projectDao.map(projectConverter::from);
    }

    public ProjectDto update(ProjectDto projectDto) {
        ProjectDao projectDao = projectRepository.update(projectConverter.to(projectDto));
        return projectConverter.from(projectDao);
    }

    public void delete(ProjectDto projectDto) {
        projectRepository.delete(projectConverter.to(projectDto));
    }

}
