package ua.goit.hw4.command;

import ua.goit.hw4.view.View;

public class Help implements Command{
    private static final String HELP = "help";
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canExecute(String input) {
        return input.equals(HELP);
    }

    @Override
    public void execute(String input) {
        view.write(String.format("Enter %s to see all command", Help.HELP));
        view.write("Enter: 'company -c name country' to create company");
        view.write("Enter: 'company -d id' to delete company");
        view.write("Enter: 'company -u id name country' to update company");
        view.write("Enter: 'company -g id' to read company with id");
        view.write("Enter: 'company -g' to read all companies");
        view.write("Enter: 'customer -c name email' to create customer");
        view.write("Enter: 'customer -d id' to delete customer");
        view.write("Enter: 'customer -u id name email' to update customer");
        view.write("Enter: 'customer -g id' to read customer");
        view.write("Enter: 'customer -g' to read all customers");
        view.write("Enter: 'developer -c name username salary' to create developer");
        view.write("Enter: 'developer -d id' to delete developer");
        view.write("Enter: 'developer -u id name username salary' to update developer");
        view.write("Enter: 'developer -g id' to read developer");
        view.write("Enter: 'developer -g' to read all developers");
        view.write("Enter: 'developer -as developerId skillId' to add skill to developer");
        view.write("Enter: 'developer -ds developerId skillId' to delete skill from developer");
        view.write("Enter: 'developer -gAp projectId' to get all developers in the project");
        view.write("Enter: 'developer -gAlg SkillLanguage' to get all developers with language");
        view.write("Enter: 'developer -gAlv SkillLevel' to get all developers with level");
        view.write("Enter: 'project -c name git_url cost' to create project");
        view.write("Enter: 'project -d id' to delete project");
        view.write("Enter: 'project -u id name git_url cost date' to update project");
        view.write("Enter: 'project -g id' to read project");
        view.write("Enter: 'project -g' to read all project");
        view.write("Enter: 'project -ad projectId developerId' to add developer to project");
        view.write("Enter: 'project -dd projectId developerId' to delete developer from project");
        view.write("Enter: 'project -da' to get all project with count of developers");
        view.write("Enter: 'skill -c name language level' to create skill");
        view.write("Enter: 'skill -d id' to delete skill");
        view.write("Enter: 'skill -u id name language level' to update skill");
        view.write("Enter: 'skill -g id' to read skill");
        view.write("Enter: 'skill -g' to read all skill");
        view.write("Enter: 'util -p projectID' to get total salary by project");
        view.write(String.format("Enter %s to exit program", Exit.EXIT));
    }
}
