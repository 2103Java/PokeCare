package com.revature.pokecare;

import javax.servlet.ServletContextEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application extends org.springframework.web.context.ContextLoaderListener {
    //private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");

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
            e.printStackTrace();
        }

        super.contextInitialized(event);
    }
}