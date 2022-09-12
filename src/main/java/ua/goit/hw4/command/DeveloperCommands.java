package ua.goit.hw4.command;

import ua.goit.hw4.model.SkillLevel;
import ua.goit.hw4.model.dto.DeveloperDto;
import ua.goit.hw4.service.DeveloperService;
import ua.goit.hw4.view.View;

import java.util.List;

public class DeveloperCommands implements Command {
    private static final String DEVELOPER_COMMANDS = "developer";
    private final View view;
    private final DeveloperService developerService;

    public DeveloperCommands(View view, DeveloperService developerService) {
        this.view = view;
        this.developerService = developerService;
    }
    @Override
    public boolean canExecute(String input) {
        return input.split(" ")[0].equals(DEVELOPER_COMMANDS);
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
                case "-as" -> addSkillToDeveloper(args);
                case "-ds" -> deleteSkillFromDeveloper(args);
                case "-gAp" -> getAllDevelopersByProject(args);
                case "-gAlg" -> getAllDevelopersBySkillLanguage(args);
                case "-gAlv" -> getAllDeveloperBySkillLevel(args);
            }
        } catch (RuntimeException e) {
            view.write("parameters incorrect");
        }

    }
    private void create(String[] args) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setName(args[2]);
        developerDto.setUsername(args[3]);
        developerDto.setSalary(Integer.valueOf(args[4]));
        developerService.create(developerDto);
        view.write("Developer created");
    }

    private void get(String[] args) {
        if (args.length==3) {
            developerService.getById(Long.valueOf(args[2]))
                    .ifPresentOrElse((value) -> view.write(String.valueOf(value)),
                            () -> view.write("Don`t find developer"));
        } else {
            developerService.getAll()
                    .forEach((value) -> view.write(value.toString()));
        }
    }

    private void update(String[] args) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(Long.valueOf(args[2]));
        developerDto.setName(args[3]);
        developerDto.setUsername(args[4]);
        developerDto.setSalary(Integer.valueOf(args[5]));
        developerService.create(developerDto);
        view.write("Developer updated");
    }

    private void delete(String[] args) {
        DeveloperDto developerDto = developerService.getById(Long.valueOf(args[2])).orElseThrow(RuntimeException::new);
        developerService.delete(developerDto);
        view.write("Developer deleted");
    }
    private void addSkillToDeveloper(String[] args){
        Long developerId = Long.valueOf(args[2]);
        Long skillId = Long.valueOf(args[3]);
        developerService.addSkill(developerId, skillId);
        view.write("Skill to developer added");
    }
    private void deleteSkillFromDeveloper(String[] args){
        Long developerId = Long.valueOf(args[2]);
        Long skillId = Long.valueOf(args[3]);
        developerService.deleteSkill(developerId, skillId);
        view.write("Skill from developer deleted");
    }
    private void getAllDevelopersByProject(String[] args){
        List<DeveloperDto> developerDtoList = developerService.getByProjectId(Long.valueOf(args[2]));
        for (DeveloperDto dto: developerDtoList
             ) {
            System.out.println(dto);
        }
    }
    private void getAllDevelopersBySkillLanguage(String[] args){
        List<DeveloperDto> developerDtoList = developerService.getBySkillLanguage(args[2]);
        for (DeveloperDto dto: developerDtoList
        ) {
            System.out.println(dto);
        }
    }
    private void getAllDeveloperBySkillLevel(String[] args){
        List<DeveloperDto> developerDtoList = developerService.getBySkillLevel(SkillLevel.valueOf(args[2]));
        for (DeveloperDto dto: developerDtoList
        ) {
            System.out.println(dto);
        }
    }

}
