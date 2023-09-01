package com.taxi.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Driver {
    private Long id;
    private String name;
    private String login;
    private String password;
    private byte[] hashSeed;
    private String licenseNumber;
    private Set<Role> roles;
    private Boolean isDeleted;

    public Driver() {
        this.roles = new HashSet<>();
    }

    public Driver(String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public Driver(String name, String login, String password, HashSet<Role> roles) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public Driver(String name, String login, String password, byte[] hashSeed, String licenseNumber, HashSet<Role> roles) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.hashSeed = hashSeed;
        this.licenseNumber = licenseNumber;
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getHashSeed() {
        return hashSeed;
    }

    public void setHashSeed(byte[] hashSeed) {
        this.hashSeed = hashSeed;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return "Driver{"
                + "id=" + id
                + ", name='" + name
                + ", licenseNumber='" + licenseNumber
                + ", login= " + login
                + ", roles = " + roles.stream().map(Role::toString).collect(Collectors.joining(", ")) + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        Driver driver = (Driver) o;
        return id.equals(driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
