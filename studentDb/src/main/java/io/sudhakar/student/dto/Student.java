package io.sudhakar.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private int id;
    private String name;
    private String place;
    private String course;
    private int age;

    private Set<Address> addresses;


    public Student(int id, String name, String place, String course, int age) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.course = course;
        this.age = age;
    }


}