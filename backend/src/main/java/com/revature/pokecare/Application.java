package com.revature.pokecare;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application {
    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("autowire.xml");

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("auth.properties"));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }

            String[] setting = line.split("=");

            if (setting.length > 1) {
                System.setProperty(setting[0], setting[1]);
            }
        }
    }
}