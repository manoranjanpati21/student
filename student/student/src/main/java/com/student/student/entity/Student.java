package com.student.student.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    @Size(min = 3)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3)
    private String lastName;

    @Column(name = "dob")
    @NotNull
    @Past
    private LocalDate dob;

    @Column(name = "section")
    @Pattern(regexp = "^[A-C]$", message = "Valid values for section are A, B and C.")
    private String section;

    @Column(name = "gender")
    @Pattern(regexp = "^[MF]$", message = "Valid values for gender are M or F.")
    private String gender;

    @Column(name = "marks_1")
    @Min(value = 0)
    @Max(value = 100)
    private Integer marks1;

    @Column(name = "marks_2")
    @Min(value = 0)
    @Max(value = 100)
    private Integer marks2;

    @Column(name = "marks_3")
    @Min(value = 0)
    @Max(value = 100)
    private Integer marks3;

    @Column(name = "total")
    private Integer total;

    @Column(name = "average")
    private Double average;

    @Column(name = "result")
    private String result;

}
