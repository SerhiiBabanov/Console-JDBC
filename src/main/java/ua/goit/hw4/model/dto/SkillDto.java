package ua.goit.hw4.model.dto;

import ua.goit.hw4.model.SkillLevel;

import java.util.Objects;

public class SkillDto {
    private Long id;
    private String language;
    private SkillLevel level;

    public SkillDto() {
    }

    public SkillDto(String language, SkillLevel level) {
        this.language = language;
        this.level = level;
    }

    public SkillDto(Long id, String language, SkillLevel level) {
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
        SkillDto skillDto = (SkillDto) o;
        return id.equals(skillDto.id) && Objects.equals(language, skillDto.language) && Objects.equals(level, skillDto.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, level);
    }
}
