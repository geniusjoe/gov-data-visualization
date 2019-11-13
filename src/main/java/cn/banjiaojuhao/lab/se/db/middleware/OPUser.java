package cn.banjiaojuhao.lab.se.db.middleware;

import cn.banjiaojuhao.lab.se.account.User;
import cn.banjiaojuhao.lab.se.account.UserProfile;
import cn.banjiaojuhao.lab.se.db.element.DUser;
import cn.banjiaojuhao.lab.se.db.element.Transfer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class OPUser {
    private Middleware md = new Middleware();
    private SessionFactory factory = Middleware.factory;

    public User queryUserbyName(String str) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DUser> query = builder.createQuery(DUser.class);
            Root<DUser> root = query.from(DUser.class);

            query.select(root).where(builder.equal(root.get("username"), str));

            Query<DUser> q = session.createQuery(query);
            try {
                DUser dusr = q.getSingleResult();
                return Transfer.DUser2User(dusr);
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public User queryUserByUid(int uid) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DUser> query = builder.createQuery(DUser.class);
            Root<DUser> root = query.from(DUser.class);

            query.select(root).where(builder.equal(root.get("uid"), uid));

            Query<DUser> q = session.createQuery(query);
            try {
                DUser dusr = q.getSingleResult();
                return Transfer.DUser2User(dusr);
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public void changePassword(int uid, String new_password) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            DUser usr = session.load(DUser.class, uid);
            usr.setPassword(new_password);
            session.update(usr);

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }


    public void updateUserProfile(UserProfile new_profile) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            DUser usr = session.load(DUser.class, new_profile.component1());
            usr.setNickName(new_profile.component2());
            usr.setDepartment(new_profile.component3());
            usr.setPosition(new_profile.component4());
            usr.setPhone(new_profile.component5());
            usr.setEmail(new_profile.component6());
            session.saveOrUpdate(usr);

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public UserProfile queryUserProfile(int uid) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DUser> query = builder.createQuery(DUser.class);
            Root<DUser> root = query.from(DUser.class);

            query.select(root).where(builder.equal(root.get("uid"), uid));

            Query<DUser> q = session.createQuery(query);
            try {
                DUser dusr = q.getSingleResult();
                return new UserProfile(dusr.getUid(), dusr.getNickName(),
                        dusr.getDepartment(), dusr.getPosition(),
                        dusr.getPhone(), dusr.getEmail());
            } catch (NoResultException e) {
                return null;
            }

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public void InsertUser(User usr){
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            session.saveOrUpdate(Transfer.User2DUser(usr));

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }


}
