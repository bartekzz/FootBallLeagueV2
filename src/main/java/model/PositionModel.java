package model;

import common.Position;
import common.Team;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistance.HibernateUtil;

import java.util.List;

public class PositionModel {

    public PositionModel() {
    }

    public String getPositionType(Integer positionId) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

        Query query = session.getNamedQuery("getPositionTypeById");
        query.setParameter("positionId", positionId);
        List<Position> position =  query.list();

        tx.commit();

        Position positionFirst = position.stream().findFirst().get();

        //return position.get(0).getType();
        return positionFirst.getType();

        } catch(RuntimeException e){
            try {
                tx.rollback();
            } catch(RuntimeException rbe){
                throw rbe;
            }
            throw e;
        } finally {
            if(session!=null){
                session.close();
            }
        }
    }
}
