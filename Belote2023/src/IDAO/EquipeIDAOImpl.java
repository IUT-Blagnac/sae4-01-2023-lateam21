package IDAO;

import models.Equipe;
import models.Tournoi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EquipeIDAOImpl extends AbstractDAO implements EquipeIDAO{
    private static EquipeIDAOImpl instance = null;
    private EquipeIDAOImpl(){super();};
    public final static EquipeIDAOImpl getInstance() {
        if(EquipeIDAOImpl.instance==null){
            synchronized(EquipeIDAOImpl.class) {
                if (EquipeIDAOImpl.instance == null) {
                    EquipeIDAOImpl.instance = new EquipeIDAOImpl();
                }
            }
        }
        return EquipeIDAOImpl.instance;
    }

    @Override
    public void add(Equipe obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Equipe getOne(int id) {
        return null;
    }

    @Override
    public List<Equipe> getAll() {
        return null;
    }

    @Override
    public ArrayList<Equipe> getEquipesTournoi(Tournoi t) {
        ArrayList<Equipe> listEqTournoi = new ArrayList<Equipe>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + t.getId_tournoi() + " ORDER BY num_equipe;");
            while(rs.next()){
                listEqTournoi.add(new Equipe(rs.getInt("id_equipe"),rs.getInt("num_equipe"), rs.getString("nom_j1"), rs.getString("nom_j2")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listEqTournoi;
    }

    @Override
    public ArrayList<Integer> getIdEquipesTournoi(Tournoi t) {
        ArrayList<Integer> listIdEqTournoi = new ArrayList<Integer>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + t.getId_tournoi() + " ORDER BY num_equipe;");
            while(rs.next()){
                listIdEqTournoi.add(rs.getInt("num_equipe"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listIdEqTournoi;
    }
}
