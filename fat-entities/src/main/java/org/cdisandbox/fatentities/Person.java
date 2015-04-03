package org.cdisandbox.fatentities;

import javax.inject.Inject;
import javax.persistence.Entity;

/**
 *
 */
@Entity
public class Person extends EntityRoot {
    private String firstname;
    private String lastname;

    @Inject
    private PersonService service;


    public Person(String name, String surname) {
        this.firstname = name;
        this.lastname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String surname) {
        this.lastname = surname;
    }

    public void persist() {
        service.persist(this);

    }

    public Integer numberPerson() {
        return service.getNumberPerson();
    }


}
