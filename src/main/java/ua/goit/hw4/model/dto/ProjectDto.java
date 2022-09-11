package ua.goit.hw4.model.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public class ProjectDto {
    private Long id;
    private String name;
    private String git_url;
    private Integer cost;
    private Long date;

    public ProjectDto() {
    }

    public ProjectDto(String name, String git_url, Integer cost, Long date) {
        this.name = name;
        this.git_url = git_url;
        this.cost = cost;
        this.date = date;
    }

    public ProjectDto(Long id, String name, String git_url, Integer cost, Long date) {
        this.id = id;
        this.name = name;
        this.git_url = git_url;
        this.cost = cost;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDto that = (ProjectDto) o;
        return id.equals(that.id) && cost.equals(that.cost) && Objects.equals(name, that.name) && Objects.equals(git_url, that.git_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, git_url, cost);
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", git_url='" + git_url + '\'' +
                ", cost=" + cost +
                ", date=" + Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate() +
                '}';
    }
}
