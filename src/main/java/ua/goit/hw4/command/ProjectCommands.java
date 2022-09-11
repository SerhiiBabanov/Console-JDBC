package ua.goit.hw4.command;

import ua.goit.hw4.model.dto.ProjectDto;
import ua.goit.hw4.service.DeveloperService;
import ua.goit.hw4.service.ProjectService;
import ua.goit.hw4.view.View;


import java.util.List;


public class ProjectCommands implements Command {
    private static final String PROJECT_COMMANDS = "project";
    private final View view;
    private final ProjectService projectService;
    private final DeveloperService developerService;

    public ProjectCommands(View view, ProjectService projectService, DeveloperService developerService) {
        this.view = view;
        this.projectService = projectService;
        this.developerService = developerService;
    }
    @Override
    public boolean canExecute(String input) {
        return input.split(" ")[0].equals(PROJECT_COMMANDS);
    }

    @Override
    public void execute(String input) {
        String[] args = input.split(" ");
        try {
            switch (args[1]) {
                case "-c" -> create(args);
                case "-g" -> get(args);
                case "-u" -> update(args);
                case "-d" -> delete(args);
                case "-ad" -> addDeveloperToProject(args);
                case "-dd" -> deleteDeveloperFromProject(args);
                case "-pA" -> getAllWithCountOfDeveloper();
            }
        } catch (RuntimeException e) {
            view.write("parameters incorrect");
        }

    }
    private void create(String[] args) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(args[2]);
        projectDto.setGit_url(args[3]);
        projectDto.setCost(Integer.valueOf(args[4]));
        projectDto.setDate(System.currentTimeMillis());
        projectService.create(projectDto);
        view.write("Project created");
    }

    private void get(String[] args) {
        if (args.length==3) {
            projectService.getById(Long.valueOf(args[2]))
                    .ifPresentOrElse((value) -> view.write(String.valueOf(value)),
                            () -> view.write("Don`t find project"));
        } else {
            projectService.getAll()
                    .forEach((value) -> view.write(value.toString()));
        }
    }

    private void update(String[] args) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(Long.valueOf(args[2]));
        projectDto.setName(args[3]);
        projectDto.setGit_url(args[4]);
        projectDto.setCost(Integer.valueOf(args[5]));
        projectDto.setDate(Long.valueOf(args[6]));
        projectService.update(projectDto);
        view.write("Project updated");
    }

    private void delete(String[] args) {
        ProjectDto projectDto = projectService.getById(Long.valueOf(args[2])).orElseThrow(RuntimeException::new);
        projectService.delete(projectDto);
        view.write("Project deleted");
    }
    private void addDeveloperToProject(String[] args){
        Long projectId = Long.valueOf(args[2]);
        Long developerId = Long.valueOf(args[3]);
        projectService.addDeveloperToProject(projectId, developerId);
        view.write("Developer to project added");
    }
    private void deleteDeveloperFromProject(String[] args){
        Long projectId = Long.valueOf(args[2]);
        Long developerId = Long.valueOf(args[3]);
        projectService.addDeveloperToProject(projectId, developerId);
        view.write("Developer to project added");
    }
    private void getAllWithCountOfDeveloper(){
        List<ProjectDto> projectDtoList = projectService.getAll();
        for (ProjectDto dto: projectDtoList
             ) {
            int count = developerService.getByProjectId(dto.getId()).size();
            view.write(dto + ", developerCount=" + count);
        }
    }

}
