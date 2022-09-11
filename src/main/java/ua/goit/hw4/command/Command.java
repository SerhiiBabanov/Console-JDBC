package ua.goit.hw4.command;

public interface Command {
    boolean canExecute(String input);
    void execute(String input);
}
