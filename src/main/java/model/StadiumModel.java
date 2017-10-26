package model;

import common.Stadium;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistance.HibernateUtil;

import java.util.List;

/**
 * This class holds all methods associated with the Stadium class
 */
public class StadiumModel {

    public StadiumModel() {
    }

    /**
     * This get the stadium id and return the stadium name
     * @param stadiumId the id of a stadium
     * @return the stadium name
     */
    public String getStadiumName(Integer stadiumId) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("getStadiumNameById");
            query.setParameter("stadiumId", stadiumId);
            List<Stadium> stadium =  query.list();

            tx.commit();

            Stadium stadiumFirst = stadium.stream().findFirst().get();

            //return stadium.get(0).getName();
            return stadiumFirst.getName();

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
