package com.ironhack.theBestMidtermProject.model.users;

import com.fasterxml.jackson.annotation.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.sun.istack.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "middleName", column = @Column(name = "middle_name", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "salutation", column = @Column(name = "salutation", columnDefinition = "varchar(60)")),

    })
    protected Name name;
    protected int age;
    protected String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    protected Set<Role> roles;

    @Transient
    protected PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    Empty constructor
    public User() {
    }

//    Constructor with all parameters
    public User(Name name, String password, int age, Set<Role> roles) {
        this.name = name;
        this.password = passwordEncoder.encode(password);
        this.age = age;
        this.roles = roles;
    }

//    Getter y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
