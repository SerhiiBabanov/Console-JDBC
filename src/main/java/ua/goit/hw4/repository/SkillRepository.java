package ua.goit.hw4.repository;

import ua.goit.hw4.config.DatabaseManagerConnector;
import ua.goit.hw4.model.SkillLevel;
import ua.goit.hw4.model.dao.SkillDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkillRepository implements Repository<SkillDao> {
    private static final String INSERT = "insert into skills(language, level) values(?,?)";
    private static final String DELETE = "delete from skills where language = ? and level = ?";
    private static final String SELECT_BY_ID = "select id, language, level from skills where id = ?";
    private static final String UPDATE = "update skills set language = ?, level = ? where id = ? " +
            "returning id, language, level";
    private static final String SELECT_ALL = "select id, language, level from skills";
    private static final String SELECT_ALL_WITH_IDS = "select id, language, level from skills where id in (?)";
    private static final String SELECT_ALL_WITH_LANGUAGE = "select id, language, level from skills where language = ?";
    private static final String SELECT_ALL_WITH_LEVEL = "select id, language, level from skills where level = ?";
    private static final String SELECT_ALL_WITH_DEVELOPER_ID = "select id, developer_id, skill_id " +
            "from developer_skill_relation" +
            " where developer_id = ?";
    private final DatabaseManagerConnector manager;

    public SkillRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public SkillDao save(SkillDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getLanguage());
            statement.setString(2, entity.getLevel().name());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating skill failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Skill not created");
        }
        return entity;
    }

    @Override
    public void delete(SkillDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, entity.getLanguage());
            statement.setString(2, entity.getLevel().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete skill failed");
        }
    }

    @Override
    public Optional<SkillDao> findById(Long id) {
        SkillDao skillDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skill by id failed");
        }
        return Optional.ofNullable(skillDao);
    }

    @Override
    public SkillDao update(SkillDao entity) {
        SkillDao skillDao = new SkillDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getLanguage());
            statement.setString(2, entity.getLevel().name());
            statement.setLong(3, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, skillDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Skill not updated");
        }
        return skillDao;
    }

    @Override
    public List<SkillDao> findAll() {
        List<SkillDao> skillDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                SkillDao skillDao = new SkillDao();
                getEntity(resultSet, skillDao);
                skillDaoList.add(skillDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all skills failed");
        }
        return skillDaoList;
    }

    @Override
    public List<SkillDao> findByListOfID(List<Long> idList) {
        List<SkillDao> skillDaoList = new ArrayList<>();
        String ids = idList.stream().map(String::valueOf).collect(Collectors.joining(", "));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_IDS)) {
            statement.setString(1, ids);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills failed");
        }
        return skillDaoList;
    }
    public List<SkillDao> findByLanguage(String language) {
        List<SkillDao> skillDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_LANGUAGE)) {
            statement.setString(1, language);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills with language failed");
        }
        return skillDaoList;
    }
    public List<SkillDao> findByLevel(SkillLevel level) {
        List<SkillDao> skillDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_LEVEL)) {
            statement.setString(1, level.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills with level failed");
        }
        return skillDaoList;
    }
    public List<SkillDao> getByDeveloperId(Long developerId){
        List<SkillDao> skillDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_WITH_DEVELOPER_ID)) {
            statement.setLong(1, developerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills with developerId failed");
        }
        return skillDaoList;
    }
    private static void getEntity(ResultSet resultSet, SkillDao skillDao) throws SQLException {
        skillDao.setId(resultSet.getLong("id"));
        skillDao.setLanguage(resultSet.getString("language"));
        skillDao.setLevel(SkillLevel.valueOf(resultSet.getString("level")));
    }
}
