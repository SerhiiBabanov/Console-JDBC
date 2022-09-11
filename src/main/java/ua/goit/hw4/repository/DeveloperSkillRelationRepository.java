package ua.goit.hw4.repository;

import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.model.dao.DeveloperSkillRelationDao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeveloperSkillRelationRepository implements Repository<DeveloperSkillRelationDao> {
    private static final String INSERT = "insert into developer_skill_relation(developer_id, skill_id) values(?,?)";
    private static final String DELETE = "delete from developer_skill_relation " +
            "where developer_id = ? and skill_id = ?";
    private static final String SELECT_BY_ID = "select id, developer_id, skill_id " +
            "from developer_skill_relation where id = ?";
    private static final String UPDATE = "update developer_skill_relation " +
            "set developer_id = ?, skill_id = ? where id = ? " +
            "returning id, developer_id, skill_id";
    private static final String SELECT_ALL = "select id, developer_id, skill_id from developer_skill_relation";
    private static final String SELECT_ALL_WITH_IDS = "select id, developer_id, skill_id from developer_skill_relation" +
            " where id in (?)";
    private static final String SELECT_ALL_WITH_DEVELOPER_ID = "select id, developer_id, skill_id " +
            "from developer_skill_relation" +
            " where developer_id = ?";
    private final DatabaseManagerConnector manager;

    public DeveloperSkillRelationRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public DeveloperSkillRelationDao save(DeveloperSkillRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getDeveloperId());
            statement.setLong(2, entity.getSkillId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Add skill to developer failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Skill to developer not added");
        }
        return entity;
    }

    @Override
    public void delete(DeveloperSkillRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, entity.getDeveloperId());
            statement.setLong(2, entity.getSkillId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Skill from developer not deleted");
        }
    }

    @Override
    public Optional<DeveloperSkillRelationDao> findById(Long id) {
        DeveloperSkillRelationDao dsRelationDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dsRelationDao = new DeveloperSkillRelationDao();
                    getEntity(resultSet, dsRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relation between developer and skill by id failed");
        }
        return Optional.ofNullable(dsRelationDao);
    }

    @Override
    public DeveloperSkillRelationDao update(DeveloperSkillRelationDao entity) {
        DeveloperSkillRelationDao dsRelationDao = new DeveloperSkillRelationDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setLong(1, entity.getDeveloperId());
            statement.setLong(2, entity.getSkillId());
            statement.setLong(3, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, dsRelationDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Relation between developer and skill not updated");
        }
        return dsRelationDao;
    }

    @Override
    public List<DeveloperSkillRelationDao> findAll() {
        List<DeveloperSkillRelationDao> dsRelationDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DeveloperSkillRelationDao dsRelationDao = new DeveloperSkillRelationDao();
                getEntity(resultSet, dsRelationDao);
                dsRelationDaoList.add(dsRelationDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all relation between developers and skills failed");
        }
        return dsRelationDaoList;
    }

    @Override
    public List<DeveloperSkillRelationDao> findByListOfID(List<Long> idList) {
        List<DeveloperSkillRelationDao> dsRelationDaoList = new ArrayList<>();
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(", "));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_IDS)) {
            statement.setString(1, ids);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DeveloperSkillRelationDao dsRelationDao = new DeveloperSkillRelationDao();
                    getEntity(resultSet, dsRelationDao);
                    dsRelationDaoList.add(dsRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relations between developers and skills failed");
        }
        return dsRelationDaoList;
    }

    private static void getEntity(ResultSet resultSet, DeveloperSkillRelationDao dsRelationDao) throws SQLException {
        dsRelationDao.setId(resultSet.getLong("id"));
        dsRelationDao.setDeveloperId(resultSet.getLong("project_id"));
        dsRelationDao.setSkillId(resultSet.getLong("skill_id"));
    }

}
