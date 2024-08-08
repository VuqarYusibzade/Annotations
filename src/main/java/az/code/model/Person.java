package az.code.model;

import az.code.annotations.ExcelColumn;
import az.code.annotations.ExcelSheet;

@ExcelSheet(sheet = "people")
public class Person {
    @ExcelColumn(column = "id")
    private int id;
    @ExcelColumn(column = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
