package ua.goit.hw4.service.conventer;

import ua.goit.hw4.model.dao.ProjectDao;
import ua.goit.hw4.model.dto.ProjectDto;

public class ProjectConverter implements Converter<ProjectDto, ProjectDao> {
    @Override
    public ProjectDto from(ProjectDao projectDao) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(projectDao.getId());
        projectDto.setName(projectDao.getName());
        projectDto.setGit_url(projectDao.getGit_url());
        projectDto.setCost(projectDao.getCost());
        return projectDto;
    }

    @Override
    public ProjectDao to(ProjectDto projectDto) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setId(projectDto.getId());
        projectDao.setName(projectDto.getName());
        projectDao.setGit_url(projectDto.getGit_url());
        projectDao.setCost(projectDto.getCost());
        return projectDao;
    }
}
