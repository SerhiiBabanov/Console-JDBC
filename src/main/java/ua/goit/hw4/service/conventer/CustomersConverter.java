package ua.goit.hw4.service.conventer;

import ua.goit.hw4.model.dao.CustomerDao;
import ua.goit.hw4.model.dto.CustomerDto;

public class CustomersConverter implements Converter<CustomerDto, CustomerDao> {
    @Override
    public CustomerDto from(CustomerDao customerDao) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerDao.getId());
        customerDto.setName(customerDao.getName());
        customerDto.setEmail(customerDao.getEmail());
        return customerDto;
    }

    @Override
    public CustomerDao to(CustomerDto customerDto) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setId(customerDto.getId());
        customerDao.setName(customerDto.getName());
        customerDao.setEmail(customerDto.getEmail());
        return customerDao;
    }
}
