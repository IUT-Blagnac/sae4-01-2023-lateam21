package IDAO;

import models.CONSTANTS;
import models.Tournament;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The type Tournament idao.
 */
public class TournamentIDAOImpl extends AbstractDAO implements TournamentIDAO {
    /**
     * The constant instance.
     */
    private static TournamentIDAOImpl instance = null;

    /**
     * Instantiates a new Tournament idao.
     */
    private TournamentIDAOImpl(){
        super();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TournamentIDAOImpl getInstance() {
        if (TournamentIDAOImpl.instance == null) {
            synchronized(TournamentIDAOImpl.class) {
                if (TournamentIDAOImpl.instance == null) {
                    TournamentIDAOImpl.instance = new TournamentIDAOImpl();
                }
            }
        }
        return TournamentIDAOImpl.instance;
    }

    /**
     * Add.
     *
     * @param obj the obj
     */
    @Override
    public void add(Tournament obj) {

    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(int id) {

    }

    /**
     * Delete tournament.
     *
     * @param nomT the nom t
     */
    @Override
    public void deleteTournament(String nomT) {
        try {
            int idTournamentDelete;
            Statement st = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT id_tournoi FROM tournois WHERE nom_tournoi = ?");
            ps.setString(1, nomT);
            ResultSet rs = ps.executeQuery();
            rs.next();
            idTournamentDelete = rs.getInt(1);
            rs.close();
            System.out.println(CONSTANTS.TOURNAMENT_DELETE + idTournamentDelete);
            st.executeUpdate("DELETE FROM matchs   WHERE id_tournoi = " + idTournamentDelete);
            st.executeUpdate("DELETE FROM equipes  WHERE id_tournoi = " + idTournamentDelete);
            st.executeUpdate("DELETE FROM tournois WHERE id_tournoi = " + idTournamentDelete);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(CONSTANTS.ERROR_DELETE + e.getMessage());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(CONSTANTS.ERROR_UNKNOWN);
        }
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @Override
    public Tournament getOne(int id) {
        return null;
    }

    /**
     * Gets one.
     *
     * @param nomT the nom t
     * @return the one
     */
    @Override
    public Tournament getOne(String nomT) {
        Tournament t = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tournois where nom_tournoi = ?");
            ps.setString(1, nomT);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t = new Tournament("");
                t.setIdTournament(rs.getInt(CONSTANTS.BD_ID_TOURNOI));
                t.setNbGames(rs.getInt(CONSTANTS.BD_NB_MATCHS));
                t.setNameTournament(nomT);
                t.setStatus(rs.getInt(CONSTANTS.BD_STATUT));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @Override
    public List<Tournament> getAll() {
        List<Tournament> T = new ArrayList<>();
        Tournament t = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tournois");
            while(rs.next()){
                t.setIdTournament(rs.getInt(CONSTANTS.BD_ID_TOURNOI));
                t.setNbGames(rs.getInt(CONSTANTS.BD_NB_MATCHS));
                t.setNameTournament(CONSTANTS.BD_NOM_TOURNOI);
                t.setStatus(rs.getInt(CONSTANTS.BD_STATUT));
                T.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return T;
    }

    /**
     * Update tournament.
     *
     * @param t the t
     */
    @Override
    public void updateTournament(Tournament t) {

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


    /**
     * Gets all names.
     *
     * @return the all names
     */
    @Override
    public ArrayList<String> getAllNames() {
        ArrayList<String> names = new ArrayList<>();
        String nameT;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tournois");
            while(rs.next()){
                nameT = rs.getString(1);
                names.add(nameT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    @Override
    public Vector<String> getAllTournamentsNames() {
        Vector<String> names = new Vector<>();
        String nameT;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tournois");
            while(rs.next()){
                nameT = rs.getString(CONSTANTS.BD_NOM_TOURNOI);
                names.add(nameT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    /**
     * Create tournament.
     *
     * @param nom the nom
     */
    @Override
    public void createTournament(String nom) {
        try {
            PreparedStatement ps = connection.prepareStatement( "INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (NULL, 10, ?, 0)");
            ps.setString(1,nom);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
