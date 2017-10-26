package model;

import common.Game;
import common.Result;
import custom_class.cScore;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistance.HibernateUtil;
import utils.Sort;

import java.util.*;

/**
 * This class holds all methods associated with the Result class
 */
public class ResultModel {

    ResultModel() {}

    /**
     * This deletes all the entries in the Result-table in DB
     */
    public static void deleteAllResults() {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("deleteAllResults");
            int res = query.executeUpdate();
            System.out.println("Number of records deleted: " + res);

            tx.commit();

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
     * This creates and stores Results in DB from every Game-object in the queue
     * @param queue the queue with Game-objects
     */
    public static void createAllResults(Queue<Game> queue) {
        Session session = null;
        Transaction tx = null;

        try {
            while(!queue.isEmpty()) {
                session = HibernateUtil.getSessionFactory().openSession();
                tx = session.beginTransaction();

                Result result = new Result(queue.peek().getId());
                System.out.println("Game id: " + queue.poll().getId());

                session.save(result);
                tx.commit();
            }

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
     * This get a list of Results-objects from DB
     * @return the list with Result-objects
     */
    public static List<Result> getAllResults() {
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query query = session.getNamedQuery("getAllResults");
            List<Result> res = query.list();

            tx.commit();

            return res;

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
     * This analyzes the Result and returns 1 (team1 win), 2 (team2 win) or 0 (draw)
     * @param result the Result-object to analyze
     * @return the integer representation of which winning team or draw
     */
    public static Integer teamWon(Result result) {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Integer res;
            System.out.println("Score 1: " + result.getTeam1Score());
            System.out.println("Score 2: " + result.getTeam2Score());
            if(result.getTeam1Score() > result.getTeam2Score()) {
                res = 1;
            }
            else if(result.getTeam1Score() < result.getTeam2Score()) {
                res = 2;
            }
            else { res = 0; }
            //System.out.println("Analyzed result");
            return res;

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
     * This matches the games and results in DB and return a list with both objects (Game and Result)
     * as we need the team id later on from Game-object
     * @return list of array object holding Game and Result
     */
    public static List<Object[]> getResultTeams() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        //session.beginTransaction();

        Query query = session.getNamedQuery("getResultTeams");
        //Query query = session.createQuery("from Result result, Game game where result.gameId = game.id");
        List<Object[]> results = query.list();

        //session.getTransaction().commit();
        session.close();

        return results;
    }

    /**
     * This create score table with the each team's name and points (3 points for win and 1 point for draw)
     * @param resultList a list with Result and Game objects
     * @return points stored in a hash table with team-id as key and points as value
     */
    public static Map<Integer, Integer> createScoreTable(List<Object[]> resultList) {
        Map<Integer, Integer> scoreTable = new Hashtable<>();

        for(Object[] result : resultList) {
            Result res = (Result) result[0];
            Game game = (Game) result[1];

            int winnerTeam = teamWon(res);
            System.out.println("Winner: " + winnerTeam);
            if(winnerTeam == 1) {
                scoreTable.put(game.getTeam1Id(), scoreTable.getOrDefault(game.getTeam1Id(), 0) + 3);
                scoreTable.put(game.getTeam2Id(), scoreTable.getOrDefault(game.getTeam2Id(), 0) + 0);
            }
            else if(winnerTeam == 2) {
                scoreTable.put(game.getTeam2Id(), scoreTable.getOrDefault(game.getTeam2Id(), 0) + 3);
                scoreTable.put(game.getTeam1Id(), scoreTable.getOrDefault(game.getTeam1Id(), 0) + 0);
            }
            else {
                scoreTable.put(game.getTeam1Id(), scoreTable.getOrDefault(game.getTeam1Id(), 0) + 1);
                scoreTable.put(game.getTeam2Id(), scoreTable.getOrDefault(game.getTeam2Id(), 0) + 1);
            }
        }

        return scoreTable;
    }

    /**
     * This sorts the score table and return a sorted list with a custom class (cScore) which holds
     * the name of team and its points
     * @param scoreTable a hash table with team id and points
     * @return a sorted list with cScore-object
     */
    public static List<cScore> sortScoreTable(Map<Integer, Integer> scoreTable) {

        Map<Integer, Integer> sortedScoreTable = Sort.sortByValue(scoreTable);
        System.out.println("Scoretable: " + sortedScoreTable);

        // Create ScoreList (holding cScore objects)
        List<cScore> scoreList = new ArrayList<>();

        Set<Integer> keySet = sortedScoreTable.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while(iterator.hasNext()) {
            Integer key = iterator.next();
            System.out.println("Team: " + TeamModel.getTeamName(key) + ", score: " + sortedScoreTable.get(key));
            // Add cScore objects to scoreList
            scoreList.add(new cScore(TeamModel.getTeamName(key), sortedScoreTable.get(key)));
        }


        return scoreList;

    }

    /**
     * This updates scores in Result-table in DB
     * @param scoreList score list with a hash-table with game id and scores (input through TextFields)
     */
    public static void updateScores(List<Map<Integer, List<TextField>>> scoreList) {

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            for(Map<Integer, List<TextField>> score : scoreList) {
                Set<Integer> keySet = score.keySet();
                Iterator<Integer> iterator = keySet.iterator();
                Integer gameId = iterator.next();
                System.out.println("Gameid: " + gameId);

                List<TextField> valueList = score.get(gameId);

                String score1Str = valueList.get(0).getText();
                System.out.println("Score1: " + score1Str);
                Integer score1;
                if(StringUtils.isNumeric(score1Str)) {
                    score1 = Integer.parseInt(valueList.get(0).getText());
                    Query query = session.getNamedQuery("updateResultScore1");
                    query.setParameter("score1", score1);
                    query.setParameter("gameId", gameId);
                    int result = query.executeUpdate();
                    System.out.println("Updated score1: " + result);
                }

                String score2Str = valueList.get(1).getText();
                System.out.println("Score2: " + score2Str);
                Integer score2;
                if(StringUtils.isNumeric(score2Str)) {
                    score2 = Integer.parseInt(valueList.get(1).getText());
                    Query query = session.getNamedQuery("updateResultScore2");
                    query.setParameter("score2", score2);
                    query.setParameter("gameId", gameId);
                    int result = query.executeUpdate();
                    System.out.println("Updated score2: " + result);
                }
            }

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
