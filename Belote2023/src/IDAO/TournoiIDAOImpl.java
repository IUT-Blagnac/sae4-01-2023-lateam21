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
        if (TournoiIDAOImpl.instance == null) {
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
            PreparedStatement ps = connection.prepareStatement("SELECT id_tournoi FROM tournois WHERE nom_tournoi = ?");
            ps.setString(1, nomT);
            ResultSet rs = ps.executeQuery();
            rs.next();
            idTournoiDelete = rs.getInt(1);
            rs.close();
            System.out.println("ID du tournoi � supprimer:" + idTournoiDelete);
            ps.executeUpdate("DELETE FROM matchs   WHERE id_tournoi = " + idTournoiDelete);
            ps.executeUpdate("DELETE FROM equipes  WHERE id_tournoi = " + idTournoiDelete);
            ps.executeUpdate("DELETE FROM tournois WHERE id_tournoi = " + idTournoiDelete);
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
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tournois where nom_tournoi = ?");
            ps.setString(1, nomT);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                t = new Tournoi("");
                t.setId_tournoi(rs.getInt("id_tournoi"));
                t.setNbMatchs(rs.getInt("nb_matchs"));
                t.setNomTournoi(nomT);
                t.setStatut(rs.getInt("statut"));
            }
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
                t.setId_tournoi(rs.getInt("id_tournoi"));
                t.setNbMatchs(rs.getInt("nb_matchs"));
                t.setNomTournoi("nom_tournoi");
                t.setStatut(rs.getInt("statut"));
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
            PreparedStatement ps = connection.prepareStatement("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi= ? ; ");
            ps.setInt(1, t.getId_tournoi());
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
    public int getNbMatchs(Tournoi t) {
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("Select count(*) from Match m WHERE m.id_tournoi=? GROUP BY id_tournoi ;");
            ps.setInt(1, t.getId_tournoi());
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int getNbMatchsFini(Tournoi t) {
        int result = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("Select count(*) from matchs m  WHERE m.id_tournoi = ?  AND m.termine='oui' ");
            ps.setInt(1, t.getId_tournoi());
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<String> getAllNames() {
        List<String> names = new ArrayList<String>();
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
    public void creerTournoi(String nom) {
        try {
            PreparedStatement ps = connection.prepareStatement( "INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (NULL, 10, ?, 0)");
            ps.setString(1,nom);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
