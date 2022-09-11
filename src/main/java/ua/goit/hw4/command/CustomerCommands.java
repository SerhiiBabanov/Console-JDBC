package ua.goit.hw4.command;


import ua.goit.hw4.model.dto.CustomerDto;
import ua.goit.hw4.service.CustomerService;
import ua.goit.hw4.view.View;

public class CustomerCommands implements Command {
    private static final String CUSTOMER_COMMANDS = "customer";
    private final View view;
    private final CustomerService customerService;

    public CustomerCommands(View view, CustomerService customerService) {
        this.view = view;
        this.customerService = customerService;
    }

    @Override
    public boolean canExecute(String input) {
        return input.split(" ")[0].equals(CUSTOMER_COMMANDS);
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
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(args[2]);
        customerDto.setEmail(args[3]);
        customerService.create(customerDto);
        view.write("Customer created");
    }

    private void get(String[] args) {
        if (args.length==3) {
            customerService.getById(Long.valueOf(args[2]))
                    .ifPresentOrElse((value) -> view.write(String.valueOf(value)),
                            () -> view.write("Don`t find customer"));
        } else {
            customerService.getAll()
                    .forEach((value) -> view.write(value.toString()));
        }

    }

    private void update(String[] args) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(Long.valueOf(args[2]));
        customerDto.setName(args[3]);
        customerDto.setEmail(args[4]);
        customerService.update(customerDto);
        view.write("Customer updated");
    }

    private void delete(String[] args) {
        CustomerDto customerDto = customerService.getById(Long.valueOf(args[2])).orElseThrow(RuntimeException::new);
        customerService.delete(customerDto);
        view.write("Customer deleted");
    }
}
