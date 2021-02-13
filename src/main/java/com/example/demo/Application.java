package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository studentIdCardRepository) {
        return args -> {

            /*Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            int age = faker.number().numberBetween(17, 55);

            Student student = new Student(firstName, lastName, email, age);

            student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(4)));
            student.addBook(new Book("God's Dead", LocalDateTime.now().minusDays(2)));
            student.addBook(new Book("My Low Self Esteem", LocalDateTime.now().minusYears(1)));

            StudentIdCard studentIdCard = new StudentIdCard("910111213", student);

            studentIdCardRepository.save(studentIdCard);*/

            studentRepository.findById(47L).
                    ifPresent(s -> {
                        List<Book> books = s.getBooks();
                        books.forEach(book -> System.out.println(book.getName()));
                    });

            /*generateFakerStudents(studentRepository);*/
            //sorting(studentRepository);

            // Example of Paging and Sorting
            // Used to limit the number of results we get back from the database
            /*PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
            Page<Student> page = studentRepository.findAll(pageRequest);
            page.stream().forEach(s -> {
                System.out.println(s.toString());
            });*/
        };
    }

    private void sorting(StudentRepository studentRepository) {

        // first type of sort using slightly different syntax
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");

        // This is another way we can concat values
        // for the sort. In this case sorting also by age.
        /*Sort anotherSort = Sort.by("firstName").ascending().and(Sort.by("age")).descending();*/

        // We then pass any sort we create to the findAll method of the repository.
        studentRepository.findAll(sort)
                .forEach(student -> {
                    System.out.println(student.getFirstName());
                });
    }

    private void generateFakerStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();

        for (int i = 0; i <= 20; i++) {

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName, lastName);
            int age = faker.number().numberBetween(17, 55);

            Student student = new Student(firstName, lastName, email, age);

            studentRepository.save(student);
        }
    }
}
