package cn.banjiaojuhao.lab.se.db.middleware;

import cn.banjiaojuhao.lab.se.db.element.DSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Random;

public class OPSession {
    private Middleware md = new Middleware();
    private SessionFactory factory = Middleware.factory;
    private static Random random = new Random(System.currentTimeMillis());

    private static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public String createSession(int uid) {
        Transaction tx = null;
        Session session = null;
        String sid = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            DSession dses = new DSession();
            dses.setUid(uid);
            sid = (String) session.save(dses);

            tx.commit();
            session.close();
            return sid;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public Integer querySession(@NotNull String sid) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DSession> query = builder.createQuery(DSession.class);
            Root<DSession> root = query.from(DSession.class);

            query.select(root).where(builder.equal(root.get("sid"), sid));

            Query<DSession> q = session.createQuery(query);
            try {
                DSession dses = q.getSingleResult();
                return dses.getUid();
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }


    public void deleteSession(@NotNull String sid) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DSession> query = builder.createQuery(DSession.class);
            Root<DSession> root = query.from(DSession.class);

            query.select(root).where(builder.equal(root.get("sid"), sid));

            Query<DSession> q = session.createQuery(query);
            try {
                DSession dses = q.getSingleResult();
                session.delete(dses);
                tx.commit();
            } catch (NoResultException e) {

            }

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
