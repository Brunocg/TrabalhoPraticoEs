package br.ufscar.test.dominio;

import org.junit.Test;

import br.ufscar.dominio.Person;
import static org.junit.Assert.*;

public class PersonTest {
    @Test
    public void canConstructAPersonWithAName() {
        Person person = new Person("Larry");
        assertEquals("Larry", person.getName());
    }
}
