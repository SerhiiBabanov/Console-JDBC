package ua.goit.hw4;

import ua.goit.hw4.command.*;
import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.config.PropertiesConfig;
import ua.goit.hw4.controller.ProjectManagementSystem;
import ua.goit.hw4.repository.*;
import ua.goit.hw4.service.*;
import ua.goit.hw4.service.conventer.*;
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
        CustomerRepository customerRepository = new CustomerRepository(manager);
        DeveloperRepository developerRepository = new DeveloperRepository(manager);
        DeveloperSkillRelationRepository dsRelationRepository = new DeveloperSkillRelationRepository(manager);
        ProjectDeveloperRelationRepository pdRelationRepository = new ProjectDeveloperRelationRepository(manager);
        ProjectRepository projectRepository = new ProjectRepository(manager);
        SkillRepository skillRepository = new SkillRepository(manager);

        CompanyConverter companyConverter = new CompanyConverter();
        CustomersConverter customersConverter = new CustomersConverter();
        DeveloperConverter developerConverter = new DeveloperConverter();
        ProjectConverter projectConverter = new ProjectConverter();
        SkillConverter skillConverter = new SkillConverter();

        CompanyService companyService = new CompanyService(companyRepository, companyConverter);
        CustomerService customerService = new CustomerService(customerRepository, customersConverter);
        DeveloperService developerService = new DeveloperService(developerRepository, dsRelationRepository,
                skillRepository, developerConverter);
        ProjectService projectService = new ProjectService(projectRepository, pdRelationRepository, projectConverter);
        SkillService skillService = new SkillService(skillRepository, skillConverter);

        List<Command> commands = new ArrayList<>();
        commands.add(new CompanyCommands(view, companyService));
        commands.add(new CustomerCommands(view, customerService));
        commands.add(new DeveloperCommands(view, developerService));
        commands.add(new ProjectCommands(view, projectService, developerService));
        commands.add(new SkillComands(view, skillService));
        commands.add(new UtilCommands(view, developerService));
        commands.add(new Help(view));
        commands.add(new Exit(view));

        ProjectManagementSystem projectManagementSystem = new ProjectManagementSystem(view, commands);
        projectManagementSystem.run();
    }
}
