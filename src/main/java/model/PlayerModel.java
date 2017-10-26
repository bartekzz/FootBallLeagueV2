package model;

import common.Player;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistance.HibernateUtil;

import java.util.List;

/**
 * This class holds all methods associated with the Player class
 */
public class PlayerModel {

    public PlayerModel() {
    }

    /**
     * This get all players from DB with first or last name which matches the name parameter
     * @param name the name of the player to search for
     * @return list of Player-objects
     */
    public List<Player> findPlayerByName(String name) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("findPlayersByName");
            query.setParameter("findName", "%" + name + "%");
            List<Player> player =  query.list();

            tx.commit();

            return player;

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
