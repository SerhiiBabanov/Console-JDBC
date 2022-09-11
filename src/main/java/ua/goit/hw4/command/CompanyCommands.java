package ua.goit.hw4.command;

import ua.goit.hw4.model.dto.CompanyDto;
import ua.goit.hw4.service.CompanyService;
import ua.goit.hw4.view.View;

public class CompanyCommands implements Command {
    private static final String COMPANY_COMMANDS = "company";
    private final View view;
    private final CompanyService companyService;

    public CompanyCommands(View view, CompanyService companyService) {
        this.view = view;
        this.companyService = companyService;
    }

    @Override
    public boolean canExecute(String input) {
        return input.split(" ")[0].equals(COMPANY_COMMANDS);
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
            }
        } catch (RuntimeException e) {
            view.write("parameters incorrect");
        }

    }

    private void create(String[] args) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName(args[2]);
        companyDto.setCountry(args[3]);
        companyService.create(companyDto);
        view.write("Company created");
    }

    private void get(String[] args) {
        if (args.length==3) {
            companyService.getById(Long.valueOf(args[2]))
                    .ifPresentOrElse((value) -> view.write(String.valueOf(value)),
                            () -> view.write("Don`t find company"));
        } else {
            companyService.getAll()
                    .forEach((value) -> view.write(value.toString()));
        }

    }

    private void update(String[] args) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(Long.valueOf(args[2]));
        companyDto.setName(args[3]);
        companyDto.setCountry(args[4]);
        companyService.update(companyDto);
        view.write("Company updated");
    }

    private void delete(String[] args) {
        CompanyDto companyDto = companyService.getById(Long.valueOf(args[2])).orElseThrow(RuntimeException::new);
        companyService.delete(companyDto);
        view.write("Company deleted");
    }
}
