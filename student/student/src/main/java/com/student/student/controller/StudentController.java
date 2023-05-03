package com.student.student.controller;

import com.student.student.entity.Student;
import com.student.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @PostMapping("/students")
    public ResponseEntity<?> createStudent(@RequestBody @Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        LocalDate now = LocalDate.now();
        LocalDate dob = student.getDob();
        Period period = Period.between(dob, now);
        int age = period.getYears();
        if (age <= 15 || age > 20) {
            return ResponseEntity.badRequest().body("Age should be greater than 15 years and less than or equal to 20 years.");
        }

        Integer marks1 = student.getMarks1();
        Integer marks2 = student.getMarks2();
        Integer marks3 = student.getMarks3();
        if (marks1 != null && (marks1 < 0 || marks1 > 100)) {
            return ResponseEntity.badRequest().body("Marks 1 should be in the range of 0 to 100.");
        }
        if (marks2 != null && (marks2 < 0 || marks2 > 100)) {
            return ResponseEntity.badRequest().body("Marks 2 should be in the range of 0 to 100.");
        }
        if (marks3 != null && (marks3 < 0 || marks3 > 100)) {
            return ResponseEntity.badRequest().body("Marks 3 should be in the range of 0 to 100.");
        }

        int total = (marks1 != null ? marks1 : 0) + (marks2 != null ? marks2 : 0) + (marks3 != null ? marks3 : 0);
        double average = (double) total / 3;

        String resultText = "Fail";
        if (marks1 != null && marks2 != null && marks3 != null && marks1 >= 35 && marks2 >= 35 && marks3 >= 35 && average >= 50) {
            resultText = "Pass";
        }

        student.setTotal(total);
        student.setAverage(average);
        student.setResult(resultText);

        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.ok(savedStudent);
    }
    @PutMapping("/students/{id}")
    public ResponseEntity<?> updateStudentMarks(@PathVariable("id") Long id, @RequestBody @Valid Student student, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Student existingStudent = optionalStudent.get();
        existingStudent.setMarks1(student.getMarks1());
        existingStudent.setMarks2(student.getMarks2());
        existingStudent.setMarks3(student.getMarks3());

        int total = (existingStudent.getMarks1() != null ? existingStudent.getMarks1() : 0) + (existingStudent.getMarks2() != null ? existingStudent.getMarks2() : 0) + (existingStudent.getMarks3() != null ? existingStudent.getMarks3() : 0);
        double average = (double)(total) / 3;

        String resultText = "Fail";
        if (existingStudent.getMarks1() != null && existingStudent.getMarks2() != null && existingStudent.getMarks3() != null && existingStudent.getMarks1() >= 35 && existingStudent.getMarks2() >= 35 && existingStudent.getMarks3() >= 35 && average >= 50) {
            resultText = "Pass";
        }

        existingStudent.setTotal(total);
        existingStudent.setAverage(average);
        existingStudent.setResult(resultText);

        Student savedStudent = studentRepository.save(existingStudent);
        return ResponseEntity.ok(savedStudent);
    }
}
