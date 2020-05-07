package com.cooldb.core.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class DatabaseUser {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;

    @Enumerated
    @NotNull
    private DatabaseUser.PERMISSION permission;

    @ManyToOne
    private Database database;

    public enum PERMISSION{
        READ(0),
        RW(1);

        private final int value;

        PERMISSION(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static DatabaseUser.PERMISSION fromValue(int value) {
            switch (value) {
                case 0:
                    return READ;
                case 1:
                    return RW;
                default:
                    return READ;
            }
        }
    }
}
