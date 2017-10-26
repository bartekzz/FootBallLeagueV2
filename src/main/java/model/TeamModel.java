package model;

import common.Team;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistance.HibernateUtil;

import java.util.List;

/**
 * This class holds all methods associated with the Team class
 */
public class TeamModel {

    public TeamModel(){}

    /**
     * This get the team id and returns the team name
     * @param teamId the team id
     * @return the team name
     */
    public static String getTeamName(Integer teamId) {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("getTeamNameById");
            query.setParameter("teamId", teamId);
            List<Team> team =  query.list();
            //System.out.println("Team name: " + team.get(0).getName());

            tx.commit();

            return team.get(0).getName();

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
