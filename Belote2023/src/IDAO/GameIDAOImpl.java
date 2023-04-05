package IDAO;

import models.Game;
import models.Tournament;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GameIDAOImpl extends AbstractDAO implements GameIDAO{

    private static GameIDAOImpl instance = null;

    private GameIDAOImpl(){
        super();
    }

    public final static GameIDAOImpl getInstance() {
        if (GameIDAOImpl.instance == null) {
            synchronized(GameIDAOImpl.class) {
                if (GameIDAOImpl.instance == null) {
                    GameIDAOImpl.instance = new GameIDAOImpl();
                }
            }
        }
        return GameIDAOImpl.instance;
    }

    @Override
    public void add(Game obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Game getOne(int id) {
        return null;
    }

    @Override
    public List<Game> getAll() {
        return null;
    }

    /**
     * Gets nb games.
     *
     * @param t the t
     * @return the nb games
     */
    @Override
    public int getNbGames(Tournament t) {
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("Select count(*) from Match m WHERE m.id_tournoi=? GROUP BY id_tournoi ;");
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
        int result = 0;
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
}
