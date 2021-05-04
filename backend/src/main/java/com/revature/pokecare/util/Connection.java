package com.revature.pokecare.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection
{
    private static final SessionFactory sf = new Configuration().configure("hibernate.cfb.xml").buildSessionFactory();
    public static final Session session = getSession();

    public static Session getSession()
    {
        return sf.openSession();
    }
}
