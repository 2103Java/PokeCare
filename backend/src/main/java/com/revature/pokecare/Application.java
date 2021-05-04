package com.revature.pokecare;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    private static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("autowire.xml");

    public static void main(String[] args) {

    }
}