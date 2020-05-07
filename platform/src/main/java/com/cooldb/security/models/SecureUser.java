package com.cooldb.security.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class SecureUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    @NotNull
    private String email;

    @Column
    @NotNull
    private String password;

    @OneToOne
    private TokenUser tokenUser;
}
