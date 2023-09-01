package com.taxi.model;

import java.util.Objects;

public class Role {
    private Long id;
    private RoleName roleName;

    private Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public static Role of(String roleName) {
        return new Role(RoleName.valueOf(roleName));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public enum RoleName {
        ADMIN ("ADMIN"),
        USER ("USER") ;

        private String roleDescription;

        public String getRoleDescription() {
            return roleDescription;
        }

        public void setRoleDescription(String roleDescription) {
            this.roleDescription = roleDescription;
        }

        RoleName(String role) {
            this.roleDescription = role;
        }
    }

    @Override
    public String toString() {
        return roleName.getRoleDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return roleName.equals(role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }
}
