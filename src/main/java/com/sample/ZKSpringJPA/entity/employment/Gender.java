package com.sample.ZKSpringJPA.entity.employment;

import lombok.Getter;
import lombok.Setter;

public enum Gender {
    FEMALE(1, "Female"),
    MALE(2, "Male");

    @Getter @Setter
    private int value;

    @Getter @Setter
    private String name;

    Gender(final int value, final String name) {
        this.value = value;
        this.name = name;
    }
}
