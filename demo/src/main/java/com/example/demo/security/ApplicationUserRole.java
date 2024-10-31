package com.example.demo.security;
import com.google.common.collect.Sets;
import java.util.*;
import static com.example.demo.security.ApplicationUserPermission.*;
public enum ApplicationUserRole
{
    ADMIN(Sets.newHashSet()),
    USER(Sets.newHashSet(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE)),
    ADMINISTRATOR(Sets.newHashSet(COURSE_READ,STUDENT_READ));
    private final Set<ApplicationUserPermission> permissions;


    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
