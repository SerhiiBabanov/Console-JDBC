package ua.goit.hw4;

import ua.goit.hw4.command.Command;
import ua.goit.hw4.command.Exit;
import ua.goit.hw4.command.Help;
import ua.goit.hw4.command.CompanyCommands;
import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.config.PropertiesConfig;
import ua.goit.hw4.controller.ProjectManagementSystem;
import ua.goit.hw4.repository.CompanyRepository;
import ua.goit.hw4.service.CompanyService;
import ua.goit.hw4.service.conventer.CompanyConverter;
import ua.goit.hw4.view.Console;
import ua.goit.hw4.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbPassword = System.getenv("dbPassword");
        String dbUsername = System.getenv("dbUsername");
        PropertiesConfig propertiesConfig = new PropertiesConfig();
        Properties properties = propertiesConfig.loadProperties("application.properties");

        DatabaseManagerConnector manager = new DatabaseManagerConnector(properties, dbUsername, dbPassword);
        Scanner scanner = new Scanner(System.in);
        View view = new Console(scanner);

        CompanyRepository companyRepository = new CompanyRepository(manager);
        CompanyConverter companyConverter = new CompanyConverter();
        CompanyService companyService = new CompanyService(companyRepository, companyConverter);

        List<Command> commands = new ArrayList<>();
        commands.add(new CompanyCommands(view, companyService));
        commands.add(new Help(view));
        commands.add(new Exit(view));

        ProjectManagementSystem projectManagementSystem = new ProjectManagementSystem(view, commands);
        projectManagementSystem.run();
    }
}
