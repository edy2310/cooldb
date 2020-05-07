package com.cooldb.core.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "DB")
@Data
public class Database {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String host;

    @Column
    @NotNull
    private Double data;

    @Enumerated
    @NotNull
    private Database.PLAN plan;

    @OneToMany
    @NotNull
    private List<DatabaseUser> databaseUsers;

    public enum PLAN{
        FREE(0),
        PREMIUM(1);

        private final int value;

        PLAN(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static Database.PLAN fromValue(int value) {
            switch (value) {
                case 0:
                    return FREE;
                case 1:
                    return PREMIUM;
                default:
                    return FREE;
            }
        }
    }
}
