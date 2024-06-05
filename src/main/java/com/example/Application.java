package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main method of the application. It starts the Spring Boot application by running the
 * SpringApplication.run() method with the Application class and the command line arguments.
 *
 * @param  args  the command line arguments passed to the application
*/
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}