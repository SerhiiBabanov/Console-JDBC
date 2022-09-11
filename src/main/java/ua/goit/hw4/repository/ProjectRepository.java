package ua.goit.hw4.repository;

import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.model.dao.ProjectDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectRepository implements Repository<ProjectDao> {
    private static final String INSERT = "insert into projects(name, git_url, cost, date) values(?,?,?,?)";
    private static final String DELETE = "delete from projects where name = ? and git_url = ? and cost = ?";
    private static final String SELECT_BY_ID = "select id, name, git_url, cost, date from projects where id = ?";
    private static final String UPDATE = "update projects set name = ?, git_url = ?, cost = ?, date = ? where id = ? " +
            "returning name, git_url, cost";
    private static final String SELECT_ALL = "select id, name, git_url, cost, date from projects";
    private static final String SELECT_ALL_WITH_IDS = "select id, name, git_url, cost, date from projects where id in (?)";
    private static final String SELECT_ALL_WITH_DEVELOPER_ID = "select id, project_id, developer_id " +
            "from project_developer_relation" +
            " where developer_id = ?";
    private final DatabaseManagerConnector manager;

    public ProjectRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public ProjectDao save(ProjectDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getGit_url());
            statement.setInt(3, entity.getCost());
            statement.setLong(4, entity.getDate());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating project failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not created");
        }
        return entity;
    }

    @Override
    public void delete(ProjectDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getGit_url());
            statement.setInt(3, entity.getCost());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete project failed");
        }
    }

    @Override
    public Optional<ProjectDao> findById(Long id) {
        ProjectDao projectDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    projectDao = new ProjectDao();
                    getEntity(resultSet, projectDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select project by id failed");
        }
        return Optional.ofNullable(projectDao);
    }

    @Override
    public ProjectDao update(ProjectDao entity) {
        ProjectDao projectDao = new ProjectDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getGit_url());
            statement.setInt(3, entity.getCost());
            statement.setLong(4, entity.getId());
            statement.setLong(5, entity.getDate());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, projectDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not updated");
        }
        return projectDao;
    }

    @Override
    public List<ProjectDao> findAll() {
        List<ProjectDao> projectDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProjectDao projectDao = new ProjectDao();
                getEntity(resultSet, projectDao);
                projectDaoList.add(projectDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all projects failed");
        }
        return projectDaoList;
    }

    @Override
    public List<ProjectDao> findByListOfID(List<Long> idList) {
        List<ProjectDao> projectDaoList = new ArrayList<>();
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(", "));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_IDS)) {
            statement.setString(1, ids);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProjectDao projectDao = new ProjectDao();
                    getEntity(resultSet, projectDao);
                    projectDaoList.add(projectDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select projects failed");
        }
        return projectDaoList;
    }
    public List<ProjectDao> getByDeveloperId(Long developerId){
        List<ProjectDao> projectDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_DEVELOPER_ID)) {
            statement.setLong(1, developerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProjectDao projectDao = new ProjectDao();
                    getEntity(resultSet, projectDao);
                    projectDaoList.add(projectDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select projects with developer failed");
        }
        return projectDaoList;
    }
    private static void getEntity(ResultSet resultSet, ProjectDao projectDao) throws SQLException {
        projectDao.setId(resultSet.getLong("id"));
        projectDao.setName(resultSet.getString("name"));
        projectDao.setGit_url(resultSet.getString("git_url"));
        projectDao.setCost(resultSet.getInt("cost"));
        projectDao.setDate(resultSet.getLong("date"));
    }
}
