package com.revature.pokecare.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    private static final SessionFactory sf = new Configuration().configure("hibernate.cfb.xml")
            .setProperty("hibernate.connection.url", System.getProperty("url"))
            .setProperty("hibernate.connection.username", System.getProperty("username"))
            .setProperty("hibernate.connection.password", System.getProperty("password"))
            .buildSessionFactory();

    public static final Session session = getSession();

    public static Session getSession() {
        return sf.openSession();
    }
}