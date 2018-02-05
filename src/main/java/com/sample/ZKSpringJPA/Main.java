package com.sample.ZKSpringJPA;

import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        Persistence.generateSchema("myapp", null);
    }
}
