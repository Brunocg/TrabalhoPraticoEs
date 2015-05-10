package br.ufscar.dominio;

import org.apache.commons.collections.list.GrowthList;

public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
        new GrowthList();
        String teste;
        String teste2;
    }

    public String getName() {
        return name;
    }
}
