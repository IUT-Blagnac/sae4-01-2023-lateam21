package IDAO;

import models.Tournoi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TournoiIDAOImpl extends AbstractDAO implements TournoiIDAO {
    private static TournoiIDAOImpl instance = null;
    private TournoiIDAOImpl(){
        super();
    }
    public final static TournoiIDAOImpl getInstance() {
        //Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet
        //d'éviter un appel coûteux à synchronized,
        //une fois que l'instanciation est faite.
        if (TournoiIDAOImpl.instance == null) {
            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
            // multiple même par différents "threads".
            // Il est TRES important.
            synchronized(TournoiIDAOImpl.class) {
                if (TournoiIDAOImpl.instance == null) {
                    TournoiIDAOImpl.instance = new TournoiIDAOImpl();
                }
            }
        }
        return TournoiIDAOImpl.instance;
    }



    @Override
    public void add(Tournoi obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void deleteTournoi(String nomT) {
        try {
            int idTournoiDelete;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_tournoi FROM tournois WHERE nom_tournoi ="+nomT+";");
            rs.next();
            idTournoiDelete = rs.getInt(1);
            rs.close();
            System.out.println("ID du tournoi � supprimer:" + idTournoiDelete);
            st.executeUpdate("DELETE FROM matchs   WHERE id_tournoi = " + idTournoiDelete);
            st.executeUpdate("DELETE FROM equipes  WHERE id_tournoi = " + idTournoiDelete);
            st.executeUpdate("DELETE FROM tournois WHERE id_tournoi = " + idTournoiDelete);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Erreur suppression : " + e.getMessage());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Erreur inconnue");
        }
    }

    @Override
    public Tournoi getOne(int id) {
        return null;
    }

    @Override
    public Tournoi getOne(String nomT) {
        Tournoi t = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tournois where nom_tounoi = ?");
            ps.setString(1, nomT);
            ResultSet rs = ps.executeQuery();
            rs.next();
            t.setId_tournoi(rs.getInt("id_tournoi"));
            t.setNbMatchs(rs.getInt("nb_matchs"));
            t.setNomTournoi(nomT);
            t.setStatut(rs.getInt("statut"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    @Override
    public List<Tournoi> getAll() {
        List<Tournoi> T = new ArrayList<Tournoi>();
        Tournoi t = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tournois");
            while(rs.next()){
                t = new Tournoi(rs.getString("nom_tounoi"));
                T.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return T;
    }

    @Override
    public void updateTournoi(Tournoi t) {

    }

    @Override
    public int getNbTours(Tournoi t) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+t.getId_tournoi()+"; ");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return -1;
        }
    }


}
