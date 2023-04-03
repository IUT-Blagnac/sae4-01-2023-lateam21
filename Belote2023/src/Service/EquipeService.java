package Service;

import IDAO.EquipeIDAOImpl;
import models.Equipe;
import models.Tournoi;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EquipeService {
    //Attributs
    private ArrayList<Equipe> dataeq = null;
    private ArrayList<Integer>ideqs  = null;
    Statement st;
    Tournoi tournoi;
    static EquipeIDAOImpl idaoEquipe = EquipeIDAOImpl.getInstance();

    public EquipeService(){super();}

    public Equipe getEquipe(int index, Tournoi t){
        if(dataeq == null)
            majEquipes(t);
        return dataeq.get(index);

    }

    public int getNbEquipes(Tournoi t){
        if(dataeq == null)
            majEquipes(t);
        return dataeq.size();
    }

    public void majEquipes(Tournoi tournoi){
        dataeq = new ArrayList<Equipe>();
        ideqs = new ArrayList<Integer>();
        dataeq = idaoEquipe.getEquipesTournoi(tournoi);
        ideqs = idaoEquipe.getIdEquipesTournoi(tournoi);
    }
    public void ajouterEquipe(Tournoi t){
        int a_aj= this.dataeq.size()+1;
        for ( int i=1;i <= this.dataeq.size(); i++){
            if(!ideqs.contains(i)){
                a_aj=i;
                break;
            }
        }
        try {
            st.executeUpdate("INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES (NULL,"+a_aj+", "+tournoi.getId_tournoi() + ",'\"Joueur 1\"', '\"Joueur 2\"');");
            majEquipes(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
