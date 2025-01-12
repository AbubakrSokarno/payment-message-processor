package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LittlePayApplication implements CommandLineRunner {

    private final LittlePayServer littlePayServer;

    public LittlePayApplication(LittlePayServer littlePayServer) {
        this.littlePayServer = littlePayServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(LittlePayApplication.class, args);
    }

    @Override
    public void run(String... args) {
        littlePayServer.start(8888);
    }
}