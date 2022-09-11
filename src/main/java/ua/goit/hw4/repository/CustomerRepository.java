package ua.goit.hw4.repository;

import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.model.dao.CustomerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerRepository implements Repository<CustomerDao> {
    private static final String INSERT = "insert into customers(name, email) values(?,?)";
    private static final String DELETE = "delete from customers where name = ? and email = ?";
    private static final String SELECT_BY_ID = "select id, name, email from customers where id = ?";
    private static final String UPDATE = "update customers set name = ?, email = ? where id = ? " +
            "returning id, name, email";
    private static final String SELECT_ALL = "select id, name, email from customers";
    private static final String SELECT_ALL_WITH_IDS = "select id, name, email from customers where id in (?)";
    private final DatabaseManagerConnector manager;

    public CustomerRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public CustomerDao save(CustomerDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Customer not created");
        }
        return entity;
    }

    @Override
    public void delete(CustomerDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete customer failed");
        }
    }

    @Override
    public Optional<CustomerDao> findById(Long id) {
        CustomerDao customerDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    customerDao = new CustomerDao();
                    getEntity(resultSet, customerDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select customer by id failed");
        }
        return Optional.ofNullable(customerDao);
    }

    @Override
    public CustomerDao update(CustomerDao entity) {
        CustomerDao customerDao = new CustomerDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setLong(3, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, customerDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Customer not updated");
        }
        return customerDao;
    }

    @Override
    public List<CustomerDao> findAll() {
        List<CustomerDao> customerDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CustomerDao customerDao = new CustomerDao();
                getEntity(resultSet, customerDao);
                customerDaoList.add(customerDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all customers failed");
        }
        return customerDaoList;
    }
    @Override
    public List<CustomerDao> findByListOfID(List<Long> idList) {
        List<CustomerDao> customerDaoList = new ArrayList<>();
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(", "));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_IDS)) {
            statement.setString(1, ids);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CustomerDao customerDao = new CustomerDao();
                    getEntity(resultSet, customerDao);
                    customerDaoList.add(customerDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select customers failed");
        }
        return customerDaoList;
    }

    private static void getEntity(ResultSet resultSet, CustomerDao customerDao) throws SQLException {
        customerDao.setId(resultSet.getLong("id"));
        customerDao.setName(resultSet.getString("name"));
        customerDao.setEmail(resultSet.getString("email"));
    }
}
