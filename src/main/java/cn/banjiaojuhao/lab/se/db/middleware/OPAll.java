package cn.banjiaojuhao.lab.se.db.middleware;

import cn.banjiaojuhao.lab.se.account.User;
import cn.banjiaojuhao.lab.se.db.element.Transfer;
import cn.banjiaojuhao.lab.se.event.Event;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OPAll {
    private Middleware md=new Middleware();
    private SessionFactory factory= Middleware.factory;

    /* Method to CREATE an employee in the database */
    public Integer addDSession(Object obj) {
        Transaction tx = null;
        Integer eventID = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            if (obj instanceof User)
                eventID = (Integer) session.save(Transfer.User2DUser((User) obj));
            else if (obj instanceof Event)
                eventID = (Integer) session.save(Transfer.Event2DEvent((Event) obj));
            else if (obj instanceof Session)
                eventID = (Integer) session.save(obj);
            else return null;
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        if (session != null)
            session.close();
        return eventID;
    }
}
