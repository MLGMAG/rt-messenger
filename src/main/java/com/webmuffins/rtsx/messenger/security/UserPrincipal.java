package com.webmuffins.rtsx.messenger.security;

import com.webmuffins.rtsx.messenger.constants.Role;

import java.util.Objects;
import java.util.StringJoiner;

public class UserPrincipal {

    private String email;
    private Role role;

    public UserPrincipal(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPrincipal)) {
            return false;
        }
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(getEmail(), that.getEmail()) && getRole() == that.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getRole());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserPrincipal.class.getSimpleName() + "[", "]").add("email='" + email + "'").add("role=" + role).toString();
    }
}
