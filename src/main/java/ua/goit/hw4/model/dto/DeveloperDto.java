package ua.goit.hw4.model.dto;

import java.util.Objects;

public class DeveloperDto {
    private Long id;
    private String name;
    private String username;
    private Integer salary;

    public DeveloperDto() {
    }

    public DeveloperDto(String name, String username, Integer salary) {
        this.name = name;
        this.username = username;
        this.salary = salary;
    }

    public DeveloperDto(Long id, String name, String username, Integer salary) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.salary = salary;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperDto that = (DeveloperDto) o;
        return id.equals(that.id) && salary.equals(that.salary) && Objects.equals(name, that.name) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, salary);
    }

    @Override
    public String toString() {
        return "DeveloperDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", salary=" + salary +
                '}';
    }
}
