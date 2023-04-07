package IDAO;

import models.CONSTANTS;
import models.Game;
import models.Tournament;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GameIDAOImpl extends AbstractDAO implements GameIDAO{

    private static GameIDAOImpl instance = null;

    private GameIDAOImpl(){
        super();
    }

    public static GameIDAOImpl getInstance() {
        if (GameIDAOImpl.instance == null) {
            synchronized(GameIDAOImpl.class) {
                GameIDAOImpl.instance = new GameIDAOImpl();
            }
        }
        return GameIDAOImpl.instance;
    }

    @Override
    public Game getOne(int id) {
        return null;
    }

    @Override
    public List<Game> getAll() {
        return null;
    }

    @Override
    public ArrayList<Game> getGamesFromTournament(Tournament t) {
        ArrayList<Game> listGames = new ArrayList<>();
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

    @Override
    public Vector<Object> getSomeVarFromGamesFromTournament(Tournament t) {
        Vector<Object> listVarGame = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String req = "Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.num_tour=m.num_tour  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + t.getIdTournament() + " GROUP BY m.num_tour,m.id_tournoi;";
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                listVarGame.add(rs.getInt(CONSTANTS.BD_NUM_TOUR));
                listVarGame.add(rs.getInt(CONSTANTS.BD_TMATCHS));
                listVarGame.add(rs.getInt(CONSTANTS.BD_TERMINES));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listVarGame;
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
            PreparedStatement ps = connection.prepareStatement("Select count(*) from Matchs m WHERE m.id_tournoi=? GROUP BY id_tournoi ;");
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
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public void createGame(Vector<Vector<Game>> Matches, Tournament t) {
        try {
            int tour = 1; String req = null;
            Statement st = connection.createStatement();
            for (Vector<Game> vect : Matches) {
                for (Game m : vect) {
                    req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES (NULL," + t.getIdTournament() + "," + tour + ", " + m.getTeam1() + ", " + m.getTeam2() + ", 'non')";
                }
                tour++;
            }
            st.executeUpdate(req);
            st.executeUpdate("UPDATE tournois SET statut=2 WHERE id_tournoi=" + t.getIdTournament() + ";"); //maj statut tournoi
//            t.setStatus(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
