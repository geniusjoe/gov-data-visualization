package cn.banjiaojuhao.lab.se.db.middleware;

import cn.banjiaojuhao.lab.se.db.element.DEvent;
import cn.banjiaojuhao.lab.se.db.element.DSession;
import cn.banjiaojuhao.lab.se.db.element.DUser;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Middleware {
    public static SessionFactory factory = null;

    public Middleware() {
        if (factory == null) {
            try {
                factory = new Configuration().
                        configure("src/hibernate.cfg.xml").
                        //addPackage("com.xyz") //add package if used.
                                addAnnotatedClass(DEvent.class).
                                addAnnotatedClass(DSession.class).
                                addAnnotatedClass(DUser.class).
                                buildSessionFactory();
            } catch (Throwable ex) {
                System.err.println("Failed to create sessionFactory object." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

}
