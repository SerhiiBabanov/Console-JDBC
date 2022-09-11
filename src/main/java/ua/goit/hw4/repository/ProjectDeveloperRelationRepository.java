package ua.goit.hw4.repository;

import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.model.dao.ProjectDeveloperRelationDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectDeveloperRelationRepository implements Repository<ProjectDeveloperRelationDao> {
    private static final String INSERT = "insert into project_developer_relation(project_id, developer_id) values(?,?)";
    private static final String DELETE = "delete from project_developer_relation " +
            "where project_id = ? and developer_id = ?";
    private static final String SELECT_BY_ID = "select id, project_id, developer_id " +
            "from project_developer_relation where id = ?";
    private static final String UPDATE = "update project_developer_relation " +
            "set project_id = ?, developer_id = ? where id = ? " +
            "returning id, project_id, developer_id";
    private static final String SELECT_ALL = "select id, project_id, developer_id from project_developer_relation";
    private static final String SELECT_ALL_WITH_IDS = "select id, project_id, developer_id " +
            "from project_developer_relation" +
            " where id in (?)";
    private final DatabaseManagerConnector manager;

    public ProjectDeveloperRelationRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public ProjectDeveloperRelationDao save(ProjectDeveloperRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getProjectId());
            statement.setLong(2, entity.getDeveloperId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Add developer to project failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer to project not added");
        }
        return entity;
    }

    @Override
    public void delete(ProjectDeveloperRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, entity.getProjectId());
            statement.setLong(2, entity.getDeveloperId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Developer from project not deleted");
        }
    }

    @Override
    public Optional<ProjectDeveloperRelationDao> findById(Long id) {
        ProjectDeveloperRelationDao pdRelationDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pdRelationDao = new ProjectDeveloperRelationDao();
                    getEntity(resultSet, pdRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relation between project and developer by id failed");
        }
        return Optional.ofNullable(pdRelationDao);
    }

    @Override
    public ProjectDeveloperRelationDao update(ProjectDeveloperRelationDao entity) {
        ProjectDeveloperRelationDao pdRelationDao = new ProjectDeveloperRelationDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setLong(1, entity.getProjectId());
            statement.setLong(2, entity.getDeveloperId());
            statement.setLong(3, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, pdRelationDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Relation between project and developer not updated");
        }
        return pdRelationDao;
    }

    @Override
    public List<ProjectDeveloperRelationDao> findAll() {
        List<ProjectDeveloperRelationDao> pdRelationDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProjectDeveloperRelationDao pdRelationDao = new ProjectDeveloperRelationDao();
                getEntity(resultSet, pdRelationDao);
                pdRelationDaoList.add(pdRelationDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all relation between projects and developers failed");
        }
        return pdRelationDaoList;
    }

    @Override
    public List<ProjectDeveloperRelationDao> findByListOfID(List<Long> idList) {
        List<ProjectDeveloperRelationDao> pdRelationDaoList = new ArrayList<>();
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(", "));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_IDS)) {
            statement.setString(1, ids);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProjectDeveloperRelationDao pdRelationDao = new ProjectDeveloperRelationDao();
                    getEntity(resultSet, pdRelationDao);
                    pdRelationDaoList.add(pdRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relations between projects and developers failed");
        }
        return pdRelationDaoList;
    }

    private static void getEntity(ResultSet resultSet, ProjectDeveloperRelationDao pdRelationDao) throws SQLException {
        pdRelationDao.setId(resultSet.getLong("id"));
        pdRelationDao.setProjectId(resultSet.getLong("developer_id"));
        pdRelationDao.setDeveloperId(resultSet.getLong("project_id"));
    }
}
