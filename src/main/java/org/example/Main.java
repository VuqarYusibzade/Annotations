package org.example;

import az.code.model.Person;
import orm.EntityManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = new EntityManager();
        entityManager.addEntitiesFromExcelFile("C:\\Users\\yusib\\Documents\\people.xlsx");


        Person foundPerson = entityManager.findById(Person.class, 1);

        if (foundPerson != null) {
            System.out.println("Found person: " + foundPerson);
        } else {
            System.out.println("No one was found with this Id.");
        }

        List<Person> allPersons = entityManager.getAll(Person.class);

        System.out.println("All persons: ");
        for (Person person : allPersons) {
            System.out.println(person);
        }
    }
}
