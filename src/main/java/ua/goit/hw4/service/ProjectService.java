package ua.goit.hw4.service;

import ua.goit.hw4.model.dao.ProjectDao;
import ua.goit.hw4.model.dao.ProjectDeveloperRelationDao;
import ua.goit.hw4.model.dto.ProjectDto;
import ua.goit.hw4.repository.ProjectDeveloperRelationRepository;
import ua.goit.hw4.repository.ProjectRepository;
import ua.goit.hw4.service.conventer.DeveloperConverter;
import ua.goit.hw4.service.conventer.ProjectConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectDeveloperRelationRepository projectDeveloperRelationRepository;
    private final ProjectConverter projectConverter;
    private final DeveloperConverter developerConverter;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectDeveloperRelationRepository projectDeveloperRelationRepository,
                          ProjectConverter projectConverter,
                          DeveloperConverter developerConverter) {
        this.projectRepository = projectRepository;
        this.projectDeveloperRelationRepository = projectDeveloperRelationRepository;
        this.projectConverter = projectConverter;
        this.developerConverter = developerConverter;
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
    public Long addDeveloperToProject(Long projectId, Long developerId){
        ProjectDeveloperRelationDao projectDeveloperRelationDao = new ProjectDeveloperRelationDao();
        projectDeveloperRelationDao.setProjectId(projectId);
        projectDeveloperRelationDao.setDeveloperId(developerId);
        projectDeveloperRelationDao = projectDeveloperRelationRepository.save(projectDeveloperRelationDao);
        return projectDeveloperRelationDao.getId();
    }
    public void deleteDeveloperFromProject(Long projectId, Long developerId){
        ProjectDeveloperRelationDao projectDeveloperRelationDao = new ProjectDeveloperRelationDao();
        projectDeveloperRelationDao.setProjectId(projectId);
        projectDeveloperRelationDao.setDeveloperId(developerId);
        projectDeveloperRelationRepository.delete(projectDeveloperRelationDao);
    }
    public List<ProjectDto> getProjectsByDeveloperId(Long id){

        return projectRepository.getByDeveloperId(id).stream()
                .map(projectConverter::from)
                .collect(Collectors.toList());
    }

}
