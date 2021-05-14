package com.revature.pokecare;

import javax.servlet.ServletContextEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Application extends org.springframework.web.context.ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
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
        } catch (IOException e) {
            System.out.println("No auth.properties file found in current directory: " + new File("").getAbsolutePath());
            e.printStackTrace();
            return;
        }

        super.contextInitialized(event);
    }
}