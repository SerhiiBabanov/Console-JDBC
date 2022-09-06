package ua.goit.hw4.model.dao;

import ua.goit.hw4.model.SkillLevel;

import java.util.Objects;

public class SkillDao {
    private Long id;
    private String language;
    private SkillLevel level;

    public SkillDao() {
    }

    public SkillDao(String language, SkillLevel level) {
        this.language = language;
        this.level = level;
    }

    public SkillDao(Long id, String language, SkillLevel level) {
        this.id = id;
        this.language = language;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillDao skillDao = (SkillDao) o;
        return id.equals(skillDao.id) && Objects.equals(language, skillDao.language) && Objects.equals(level, skillDao.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, level);
    }
}
