package model;

import common.Game;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistance.HibernateUtil;
import utils.GameComp;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class holds all methods associated with the Game class
 */
public class GameModel {

    public GameModel() {}

    /**
     * This returns a queue with all Game-objects in DB
     * @return queue with Game-objects
     */
    public Queue<Game> queueAllGames() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("getAllGames");
            List<Game> games = query.list();

            Queue<Game> queue = new PriorityQueue<>(new GameComp());
            for (Game game : games) {
                queue.add(game);
            }

            System.out.println(queue);

            tx.commit();

            return queue;

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

    /**
     * this returns a list with all Game-objects in DB
     * @return list with Game-objects
     */
    public List<Game> getAllGames() {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

        Query query = session.getNamedQuery("getAllGames");
        List<Game> games = query.list();

        tx.commit();

        return games;

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
