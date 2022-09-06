package ua.goit.hw4.repository;

import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.model.dao.DeveloperDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperRepository implements Repository<DeveloperDao> {
    private static final String INSERT = "insert into developers(name, username, salary) values(?,?,?)";
    private static final String DELETE = "delete from developers where name = ? and username = ? and salary = ?";
    private static final String SELECT_BY_ID = "select name, username, salary from developers where id = ?";
    private static final String UPDATE = "update developers set name = ?, username = ?, salary = ? where id = ? " +
            "returning id, name, username, salary";
    private static final String SELECT_ALL = "select id, name, username, salary from developers";
    private final DatabaseManagerConnector manager;

    public DeveloperRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public DeveloperDao save(DeveloperDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getUsername());
            statement.setInt(3, entity.getSalary());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating developer failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer not created");
        }
        return entity;
    }

    @Override
    public void delete(DeveloperDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getUsername());
            statement.setInt(3, entity.getSalary());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete developer failed");
        }
    }

    @Override
    public Optional<DeveloperDao> findById(Long id) {
        DeveloperDao developerDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    developerDao = new DeveloperDao();
                    developerDao.setId(resultSet.getLong("id"));
                    developerDao.setName(resultSet.getString("name"));
                    developerDao.setUsername(resultSet.getString("username"));
                    developerDao.setSalary(resultSet.getInt("salary"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select developer by id failed");
        }
        return Optional.ofNullable(developerDao);
    }

    @Override
    public DeveloperDao update(DeveloperDao entity) {
        DeveloperDao developerDao = new DeveloperDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getUsername());
            statement.setInt(3, entity.getSalary());
            statement.setLong(4, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    developerDao.setId(resultSet.getLong("id"));
                    developerDao.setName(resultSet.getString("name"));
                    developerDao.setUsername(resultSet.getString("username"));
                    developerDao.setSalary(resultSet.getInt("salary"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer not updated");
        }
        return developerDao;
    }

    @Override
    public List<DeveloperDao> findAll() {
        List<DeveloperDao> developerDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DeveloperDao developerDao = new DeveloperDao();
                developerDao.setId(resultSet.getLong("id"));
                developerDao.setName(resultSet.getString("name"));
                developerDao.setUsername(resultSet.getString("username"));
                developerDao.setSalary(resultSet.getInt("salary"));
                developerDaoList.add(developerDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all developers failed");
        }
        return developerDaoList;
    }
}
