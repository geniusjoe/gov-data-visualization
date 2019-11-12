package cn.banjiaojuhao.lab.se.db.middleware;

import cn.banjiaojuhao.lab.se.db.element.DDepartmentKpi;
import cn.banjiaojuhao.lab.se.db.element.DEvent;
import cn.banjiaojuhao.lab.se.db.element.Transfer;
import cn.banjiaojuhao.lab.se.event.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OPEvent {
    private Middleware md = new Middleware();
    private SessionFactory factory = Middleware.factory;

    public List<Event> queryAppeal(int source) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DEvent> query = builder.createQuery(DEvent.class);
            Root<DEvent> root = query.from(DEvent.class);

            query.select(root).where(builder.equal(root.get("operator"), source));
            query.orderBy(builder.desc(root.get("createTime")));

            Query<DEvent> q = session.createQuery(query);
            List<DEvent> deve = q.getResultList();
            List<Event> res = new ArrayList<>();
            for (DEvent eve : deve) {
                res.add(Transfer.DEvent2Event(eve));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public void addAppeal(@NotNull Event event) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            DEvent deve = Transfer.Event2DEvent(event);
            session.save(deve);

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteAppeal(int rec_id) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DEvent> query = builder.createQuery(DEvent.class);
            Root<DEvent> root = query.from(DEvent.class);

            query.select(root).where(builder.equal(root.get("recId"), rec_id));

            Query<DEvent> q = session.createQuery(query);
            DEvent deve = q.getSingleResult();
            if (deve != null)
                session.delete(deve);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateAppeal(@NotNull Event event) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            DEvent deve = Transfer.Event2DEvent(event);
            session.saveOrUpdate(deve);

            tx.commit();
            session.close();

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Overall queryOverall(long startTime, long endTime) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DEvent> query = builder.createQuery(DEvent.class);
            Root<DEvent> root = query.from(DEvent.class);

            int dps, to_dps, cplt, srv_idx;
            Predicate Bse = builder.between(root.get("create_time"), startTime, endTime);
            Predicate Dps = builder.equal(root.get("event_dispose_state"), 2);
            Predicate To_dps = builder.equal(root.get("event_dispose_state"), 1);
            Predicate Cplt = builder.equal(root.get("event_property_id"), 2);
//            Predicate Srv_idx=builder.equal(root.get("event_property_id"), 2);
            //TODO


            query.select(root).where(builder.and(Bse, Dps));
            Query<DEvent> q = session.createQuery(query);
            dps = q.getResultList().size();

            query = builder.createQuery(DEvent.class);
            query.select(root).where(builder.and(Bse, To_dps));
            q = session.createQuery(query);
            to_dps = q.getResultList().size();

            query = builder.createQuery(DEvent.class);
            query.select(root).where(builder.and(Bse, Cplt));
            q = session.createQuery(query);
            cplt = q.getResultList().size();

            srv_idx = (int) (Math.random() * 6);

            return new Overall(dps, to_dps, cplt, srv_idx);

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<HotCommunity> queryHotCommunity(long startTime, long endTime) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
//            query.select(root).where(builder.between(root.get("createTime"), startTime, endTime);
            query.multiselect(builder.count(root.get("recId")), root.get("communityId"), root.get("createTime"));
            query.groupBy(root.get("communityId"));
            query.having(builder.between(root.get("createTime"), startTime, endTime));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<HotCommunity> res = new ArrayList<>();
            for (Object[] obj : lst) {
                res.add(new HotCommunity((int) obj[1], (int) obj[0]));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<EventProperty> queryProperty(long startTime, long endTime) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
//            query.select(root).where(builder.between(root.get("createTime"), startTime, endTime);
            query.multiselect(builder.count(root.get("recId")), root.get("eventPropertyId"), root.get("createTime"));
            query.groupBy(root.get("eventPropertyId"));
            query.having(builder.between(root.get("createTime"), startTime, endTime));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<EventProperty> res = new ArrayList<>();
            for (Object[] obj : lst) {
                res.add(new EventProperty((int) obj[1], (int) obj[0]));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<ArchiveEvent> queryArchiveEvent(long startTime, long endTime) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
            query.multiselect(builder.count(root.get("recId")), root.get("eventDisposeState"), root.get("eventTypeId"), root.get("createTime"));
            query.groupBy(root.get("eventDisposeState"), root.get("eventTypeId"));
            query.having(builder.between(root.get("createTime"), startTime, endTime));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<ArchiveEvent> res = new ArrayList<>();
            for (Object[] obj : lst) {
                res.add(new ArchiveEvent((int) obj[1], (int) obj[0], new ArchiveEventDetail((int) obj[2], (int) obj[0])));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<WordCloud> queryWordCloud(long startTime, long endTime) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
            query.multiselect(builder.count(root.get("recId")), root.get("mainTypeId"), root.get("createTime"));
            query.groupBy(root.get("mainTypeId"));
            query.having(builder.between(root.get("createTime"), startTime, endTime));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<WordCloud> res = new ArrayList<>();
            for (Object[] obj : lst) {
                res.add(new WordCloud((int) obj[1], (int) obj[0]));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<StreetSubtype> queryStreetSubtype(long startTime, long endTime) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
            query.multiselect(builder.count(root.get("recId")), root.get("streetId"), root.get("subTypeId"), root.get("createTime"));
            query.groupBy(root.get("streetId"), root.get("subTypeId"));
            query.having(builder.between(root.get("createTime"), startTime, endTime));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<StreetSubtype> res = new ArrayList<>();
            for (Object[] obj : lst) {
                res.add(new StreetSubtype((int) obj[1], (int) obj[2], (int) obj[0]));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<EventSrc> queryEventSrc(long startTime, long endTime) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
            query.multiselect(builder.count(root.get("recId")), root.get("eventSrcId"), root.get("createTime"));
            query.groupBy(root.get("eventSrcId"));
            query.having(builder.between(root.get("createTime"), startTime, endTime));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<EventSrc> res = new ArrayList<>();
            for (Object[] obj : lst) {
                res.add(new EventSrc((int) obj[1], (int) obj[0]));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<ShortEvent> queryShortEvent(long timeAfter) {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
            query.multiselect(builder.count(root.get("createTime")), root.get("streetId"), root.get("communityId"),
                    root.get("eventSrcId"), root.get("subTypeId"), root.get("event_property_id"), root.get("eventDisposeState"));
//            query.groupBy(root.get("eventSrcId"));
            query.having(builder.gt(root.get("createTime"), timeAfter));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<ShortEvent> res = new ArrayList<>();
            for (Object[] obj : lst) {
                res.add(new ShortEvent((long) obj[0], (int) obj[1], (int) obj[2],
                        (int) obj[3], (int) obj[4], (int) obj[5], (int) obj[6]));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<Event> queryUndisposedAppeal() {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DEvent> query = builder.createQuery(DEvent.class);
            Root<DEvent> root = query.from(DEvent.class);

            query.select(root).where(builder.equal(root.get("eventDisposeState"), 1));
            query.orderBy(builder.asc(root.get("eventTypeId")), builder.asc(root.get("createTime")));

            Query<DEvent> q = session.createQuery(query);
            List<DEvent> deve = q.getResultList();
            List<Event> res = new ArrayList<>();
            for (DEvent eve : deve) {
                res.add(Transfer.DEvent2Event(eve));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<TimePassed> queryTimePassed() {
        Transaction tx = null;
        Session session = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<DEvent> root = query.from(DEvent.class);
            query.multiselect(builder.sum((root.get("createTime"))), root.get("disposeUnitId"));
            query.groupBy(root.get("disposeUnitId"));

            Query<Object[]> q = session.createQuery(query);
            List<Object[]> lst = q.getResultList();
            List<TimePassed> res = new ArrayList<>();
            for (Object[] obj : lst) {
                //TODO
                res.add(new TimePassed((int) obj[1], (Long) obj[0]));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<DepartmentKpi> queryDepartmentKpi() {
        Transaction tx = null;
        Session session = null;

        HashMap<Integer, DDepartmentKpi> mp = new HashMap<Integer, DDepartmentKpi>();

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            // Create CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // Create CriteriaQuery
            CriteriaQuery<DEvent> query = builder.createQuery(DEvent.class);
            Root<DEvent> root = query.from(DEvent.class);
            query.select(root);

            Query<DEvent> q = session.createQuery(query);
            List<DEvent> lst = q.getResultList();
            for (DEvent deve : lst) {
                int cur_unit = deve.getDisposeUnitId();
                DDepartmentKpi cur;
                if (mp.containsKey(cur_unit)) {
                    cur = mp.get(deve.getDisposeUnitId());
                } else {
                    cur = new DDepartmentKpi();
                    cur.disposeUnitId = cur_unit;
                }
                int cur_prp_id = deve.getEventPropertyId(), cur_dps = deve.getEventDisposeState();
                if (cur_prp_id == 2) cur.complaint++;
                else if (cur_prp_id == 5) cur.thanks++;
                else cur.disposing++;
                if (cur_dps == 0) cur.overtime++;
                else if (cur_dps == 1) cur.disposing++;
                else if (cur_dps == 2) cur.intime++;
                mp.put(cur_unit, cur);
            }

            List<DepartmentKpi> res = new ArrayList<>();
            for (DDepartmentKpi value : mp.values()) {
                res.add(new DepartmentKpi(value.disposeUnitId,
                        value.complaint,value.thanks,value.disposing,
                        value.overtime,value.intime,0));
            }
            return res;

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

}
