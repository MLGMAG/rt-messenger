package com.webmuffins.rtsx.messenger.constants;

import com.webmuffins.rtsx.messenger.exception.NotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER("User", Set.of(Permission.SEND_MESSAGES)),

    MANAGER("Manager", Set.of(Permission.SEND_MESSAGES)),

    SYSTEM_ADMIN("Manager", Set.of(Permission.SEND_MESSAGES));

    private final Set<Permission> permissions;
    private final String name;

    Role(String name, Set<Permission> permissions) {
        this.permissions = permissions;
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public String getName() {
        return name;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet());
    }

    public static Role getRoleByName(String name) {
        return Arrays.stream(values())
                .filter(role -> role.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Can not find role by name : " + name));
    }

}
