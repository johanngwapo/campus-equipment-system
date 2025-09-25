package edu.cit.cordova.johannanthony.campusequipmentload.Security;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class StudentDetails implements UserDetails {

    private final StudentEntity student;

    public StudentDetails(StudentEntity student) {
        this.student = student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // right now no roles/authorities, so return empty list
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return student.getPassword(); // comes from StudentEntity
    }

    @Override
    public String getUsername() {
        return student.getEmail(); // we log in using email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // always valid
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // always valid
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // always valid
    }

    @Override
    public boolean isEnabled() {
        return true; // always valid
    }

    // Extra helper if you want access to student
    public StudentEntity getStudent() {
        return student;
    }
}
