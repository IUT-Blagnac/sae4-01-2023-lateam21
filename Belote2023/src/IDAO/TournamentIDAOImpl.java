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
        Tournament t = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tournois where nom_tournoi = ?");
            ps.setString(1, nomT);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t = new Tournament(nomT);
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
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tournois where id_tournoi = ?");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t.setNbGames(rs.getInt(CONSTANTS.BD_NB_MATCHS));
                t.setStatus(rs.getInt(CONSTANTS.BD_STATUT));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public Vector<Object> getResultTournament(Tournament tournament) {
        Vector<Object> resultTournament = new Vector<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = ?) as joueur1, " +
                    "(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = ?) as joueur2, " +
                    "SUM(score) as score, " +
                    "(SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, " +
                    "(SELECT COUNT(*) FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM " +
                    "(select equipe1 as equipe,score1 as score from matchs where id_tournoi=? UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=?) " +
                    "GROUP BY equipe ORDER BY matchs_gagnes DESC;");
            ps.setInt(1, tournament.getIdTournament());
            ps.setInt(2, tournament.getIdTournament());
            ps.setInt(3, tournament.getIdTournament());
            ps.setInt(4, tournament.getIdTournament());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                resultTournament.add(rs.getInt(CONSTANTS.BD_GET_EQUIPE));
                resultTournament.add(rs.getString(CONSTANTS.BD_GET_JOUEUR1));
                resultTournament.add(rs.getString(CONSTANTS.BD_GET_JOUEUR2));
                resultTournament.add(rs.getInt(CONSTANTS.BD_GET_SCORE));
                resultTournament.add(rs.getInt(CONSTANTS.BD_GET_MATCHS_GAGNES));
                resultTournament.add(rs.getInt(CONSTANTS.BD_GET_MATCHS_JOUES));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultTournament;
    }

}
