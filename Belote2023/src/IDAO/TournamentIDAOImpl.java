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
    public final static TournamentIDAOImpl getInstance() {
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
            System.out.println("ID du tournoi � supprimer:" + idTournamentDelete);
            st.executeUpdate("DELETE FROM matchs   WHERE id_tournoi = " + idTournamentDelete);
            st.executeUpdate("DELETE FROM equipes  WHERE id_tournoi = " + idTournamentDelete);
            st.executeUpdate("DELETE FROM tournois WHERE id_tournoi = " + idTournamentDelete);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Erreur suppression : " + e.getMessage());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Erreur inconnue");
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
        Tournament t = new Tournament(nomT);
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tournois where nom_tournoi = ?");
            ps.setString(1, nomT);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t.setIdTournament(rs.getInt("id_tournoi"));
                t.setNbGames(rs.getInt("nb_matchs"));
                t.setStatus(rs.getInt("statut"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    @Override
    public ArrayList<Integer> getAllIds() {
        ArrayList<Integer> T = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id_tournoi FROM tournois");
            while(rs.next()){
                T.add(rs.getInt(CONSTANTS.BD_ID_TOURNOI));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return T;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @Override
    public ArrayList<Tournament> getAll() {
        ArrayList<Tournament> T = new ArrayList<>();
        Tournament t = new Tournament("");
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tournois");
            while(rs.next()){
                t.setIdTournament(rs.getInt("id_tournoi"));
                t.setNbGames(rs.getInt("nb_matchs"));
                t.setNameTournament("nom_tournoi");
                t.setStatus(rs.getInt("statut"));
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
     * Gets all names.
     *
     * @return the all names
     */
    @Override
    public ArrayList<String> getAllNames() {
        ArrayList<String> names = new ArrayList<String>();
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
        Vector<String> names = new Vector<String>();
        String nameT;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tournois");
            while(rs.next()){
                nameT = rs.getString("nom_tournoi");
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
    public void createTournament(int id, String nom) {
        try {
            PreparedStatement ps = connection.prepareStatement( "INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (?, 0, ?, 0)");
            ps.setInt(1, id);
            ps.setString(2,nom);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
