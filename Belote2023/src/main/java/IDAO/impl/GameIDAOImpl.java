package IDAO.impl;

<<<<<<< HEAD:Belote2023/src/main/java/IDAO/GameIDAOImpl.java
import resources.CONSTANTS;
=======
import IDAO.AbstractDAO;
import IDAO.GameIDAO;
import models.CONSTANTS;
>>>>>>> modifByRuben:Belote2023/src/main/java/IDAO/impl/GameIDAOImpl.java
import models.Game;
import models.Tournament;
import view.Window;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

<<<<<<< HEAD:Belote2023/src/main/java/IDAO/GameIDAOImpl.java
/**
 * The type Game idao.
 */
public class GameIDAOImpl extends AbstractDAO implements GameIDAO{
=======
public class GameIDAOImpl extends AbstractDAO implements GameIDAO {
>>>>>>> modifByRuben:Belote2023/src/main/java/IDAO/impl/GameIDAOImpl.java

    /**
     * The constant instance.
     */
    private static GameIDAOImpl instance = null;

    /**
     * Instantiates a new Game idao.
     */
    private GameIDAOImpl(){
        super();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GameIDAOImpl getInstance() {
        if (GameIDAOImpl.instance == null) {
            synchronized(GameIDAOImpl.class) {
                GameIDAOImpl.instance = new GameIDAOImpl();
            }
        }
        return GameIDAOImpl.instance;
    }


    /**
     * Gets games from tournament.
     *
     * @param t the t
     * @return the games from tournament
     */
    @Override
    public Vector<Game> getGamesFromTournament(Tournament t) {
        Vector<Game> listGames = new Vector<>();
        Game game;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM matchs WHERE id_tournoi = ?");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                game = new Game(rs.getInt("id_match"), rs.getInt("equipe1"), rs.getInt("equipe2"),
                        rs.getInt("score1"), rs.getInt("score2"),rs.getInt(CONSTANTS.BD_NUM_TOUR), rs.getString("termine").equals("oui"));
                listGames.add(game);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listGames;
    }

    /**
     * Gets nb rounds by matchs.
     *
     * @param t the t
     * @return the nb rounds by matchs
     */
    @Override
    public ResultSet getNbRoundsByMatchs(Tournament t) {
        try {
            Statement st = connection.createStatement();
            String req = "Select num_tour, count(*) as tmatchs, " +
                    "(Select count(*) from matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour=m.num_tour AND m2.termine='oui' ) as termines " +
                    "from matchs m WHERE m.id_tournoi="+ t.getIdTournament()
                    + " GROUP BY m.num_tour,m.id_tournoi";
            return st.executeQuery(req);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets nb games.
     *
     * @param t the t
     * @return the nb games
     */
    @Override
    public int getNbGames(Tournament t) {
        int result;
        try {
            PreparedStatement ps = connection.prepareStatement("Select count(*) from Matchs m WHERE m.id_tournoi=?;");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Gets nb games ended.
     *
     * @param t the t
     * @return the nb games ended
     */
    @Override
    public int getNbGamesEnded(Tournament t) {
        int result;
        try {
            PreparedStatement ps = connection.prepareStatement("Select count(*) from matchs m  WHERE m.id_tournoi = ?  AND m.termine='oui' ");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Gets nb rounds.
     *
     * @param t the t
     * @return the nb rounds
     */
    @Override
    public int getNbRounds(Tournament t) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi= ? ; ");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Create game.
     *
     * @param Matches the matches
     * @param t       the t
     */
    @Override
    public void createGame(Vector<Vector<Game>> Matches, Tournament t) {
        try {
            String req;
            int tour = 1;
            Statement st = connection.createStatement();
            for(Vector<Game> vect : Matches){
                for (Game m : vect) {
                    req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES (NULL," + t.getIdTournament() + "," + tour + ", " + m.getTeam1() + ", " + m.getTeam2() + ", 'non')";
                    st.executeUpdate(req);
                }
                tour++;
            }
            st.executeUpdate("UPDATE tournois SET statut=2 WHERE id_tournoi=" + t.getIdTournament() + ";"); //maj statut tournoi
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update game.
     *
     * @param gm the gm
     */
    @Override
    public void updateGame(Game gm) {
        String hasEnded = (gm.getScore1() > 0 || gm.getScore2() > 0) ? "oui":"non";
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE matchs SET equipe1 = ?, equipe2 = ?,  score1 = ?,  score2 = ?, termine = ? WHERE id_match = ? ;");
            ps.setInt(1, gm.getTeam1());
            ps.setInt(2, gm.getTeam2());
            ps.setInt(3, gm.getScore1());
            ps.setInt(4, gm.getScore2());
            ps.setString(5, hasEnded);
            ps.setInt(6, gm.getIdMatch());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add rounds.
     *
     * @param t the t
     */
    @Override
    public void addRounds(Tournament t) {
        try {
            Statement st = connection.createStatement();
            int nbroundsav = getNbRounds(t);
            ArrayList<Integer> teamOrder= new ArrayList<>();

            //obtention de l'ordre des matchs
            ResultSet rs;
            rs = st.executeQuery("SELECT equipe, " +
                    "(SELECT count(*) FROM matchs m " +
                    "WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes " +
                    "FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + t.getIdTournament() + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + t.getIdTournament() + ") " +
                    "GROUP BY equipe  ORDER BY matchs_gagnes DESC;");
            while(rs.next()){
                teamOrder.add(rs.getInt("equipe"));
                System.out.println(rs.getInt(1) +" _ " + rs.getString(2)); //message dev
            }
            System.out.println("Taille : "+teamOrder.size());
            int i;
            boolean end;
            while(teamOrder.size() > 1){
                i=1;
                do{
                    rs = getNbGamesByTeams(teamOrder.get(0), teamOrder.get(i-1));
                    rs.next();
                    if(rs.getInt(CONSTANTS.BD_GET_NB_MATCHS)>0){
                        i++;
                        end=false;
                    }else{
                        end=true;
                        Game ga = new Game(teamOrder.get(0), teamOrder.get(i));
                        addGame(ga, nbroundsav+1, t);
                        teamOrder.remove(0);
                        teamOrder.remove(i-1);
                    }
                }while(!end);
            }
        } catch (SQLException e) {
            Window.showError("Erreur lors de la récupération des matchs du tournoi.");
            System.out.println(e.getMessage()); // Message développeur
        }
    }

    /**
     * Gets nb games by teams.
     *
     * @param team1 the team 1
     * @param team2 the team 2
     * @return the nb games by teams
     */
    @Override
    public ResultSet getNbGamesByTeams(int team1, int team2) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) as nb_matchs FROM matchs m " +
                    "WHERE ( (m.equipe1 = ? AND m.equipe2 = ?) OR (m.equipe2 = ? AND m.equipe1 = ?))");
            ps.setInt(1, team1);
            ps.setInt(2, team2);
            ps.setInt(3, team1);
            ps.setInt(4, team2);
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete round.
     *
     * @param to the to
     */
    @Override
    public void deleteRound(Tournament to) {
        int lastRound;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+ to.getIdTournament()+"; ");
            rs.next();
            lastRound = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ;
        }

        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM matchs WHERE id_tournoi="+ to.getIdTournament()+" AND num_tour=" + lastRound);
        } catch (SQLException e) {
            System.out.println("Erreur del tour : " + e.getMessage());
        }
    }

    /**
     * Add game.
     *
     * @param ga       the ga
     * @param numRound the num round
     * @param t        the t
     */
    @Override
    public void addGame(Game ga, int numRound ,Tournament t) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES (NULL,?,?,?,?, 'non')");
            ps.setInt(1, t.getIdTournament());
            ps.setInt(2, numRound);
            ps.setInt(3, ga.getTeam1());
            ps.setInt(4, ga.getTeam2());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
