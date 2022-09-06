package ua.goit.hw4.model.dao;

import java.util.Objects;

public class CompanyDao {
    private Long id;
    private String name;
    private String country;

    public CompanyDao() {
    }

    public CompanyDao(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public CompanyDao(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDao that = (CompanyDao) o;
        return id.equals(that.id) && Objects.equals(name, that.name) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }
}
