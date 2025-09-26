package com.example.crud;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.crud.model.Student;
import com.example.crud.repository.StudentRepository;

@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ApplicationContext context = SpringApplication.run(CrudApplication.class, args);

        StudentRepository studentRepository = context.getBean(StudentRepository.class);

        int choice, id;
        String name;

        while (true) {
            System.out.println("\n1.Add Student\n2.Get Student By Id\n3.Update Student By Id\n4.Delete Student By Id\n5.Get All Students\n6.Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter id: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter name: ");
                    name = sc.nextLine();

                    Student student = new Student();
                    student.setId(id);
                    student.setName(name);

                    Student savedStudent = studentRepository.save(student);
                    System.out.println("Student created successfully!");
                    System.out.println(savedStudent);
                }

                case 2 -> {
                    System.out.print("Enter id: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    Optional<Student> studentOpt = studentRepository.findById(id);
                    if (studentOpt.isPresent()) {
                        System.out.println(studentOpt.get());
                    } else {
                        System.out.println("Student not found for id: " + id);
                    }
                }

                case 3 -> {
                    System.out.print("Enter id to update: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    Optional<Student> studentOpt = studentRepository.findById(id);
                    if (studentOpt.isPresent()) {
                        Student studentToUpdate = studentOpt.get();

                        System.out.print("Enter new name: ");
                        name = sc.nextLine();
                        studentToUpdate.setName(name);

                        studentRepository.save(studentToUpdate);
                        System.out.println("Student updated successfully!");
                        System.out.println(studentToUpdate);
                    } else {
                        System.out.println("Student not found for id: " + id);
                    }
                }

                case 4 -> {
                    System.out.print("Enter id to delete: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    if (studentRepository.existsById(id)) {
                        studentRepository.deleteById(id);
                        System.out.println("Student deleted successfully!");
                    } else {
                        System.out.println("Student not found for id: " + id);
                    }
                }

                case 5 -> {
                    List<Student> students = studentRepository.findAll();
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        System.out.println("All Students:");
                        students.forEach(System.out::println);
                    }
                }

                case 6 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }
}
