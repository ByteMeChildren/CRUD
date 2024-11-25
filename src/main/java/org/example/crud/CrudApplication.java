package org.example.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {
    public static void main(String[] args)
    {
        //Serverport ist 9090, da man auf 8080 oft andere Services laufen hat👍
        SpringApplication.run(CrudApplication.class, args);
    }

}
