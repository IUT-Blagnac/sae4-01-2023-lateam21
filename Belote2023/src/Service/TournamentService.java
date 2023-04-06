package Service;

import IDAO.TournamentIDAOImpl;
import models.Game;
import models.Team;
import models.Tournament;
import resources.Tools;
//import view.TournoiView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

/**
 * The type Tournament service.
 */
public class TournamentService {
    /**
     * The Data teams.
     */
//attibuts classe controller
    private ArrayList<Team> dataTeams = null;
    /**
     * The Ideqs.
     */
    private ArrayList<Integer>ideqs  = null;
    /**
     * The Idao tournoi.
     */
    static TournamentIDAOImpl idaoTournoi = TournamentIDAOImpl.getInstance();
    /**
     * The St.
     */
    Statement st;
    /**
     * The Tournament.
     */
    Tournament tournament;
    static Tools tools;
    /**
     * The Ts.
     */
    private TeamService ts = new TeamService();

    /**
     * Instantiates a new Tournament service.
     */
    public TournamentService(){
        super();
    }

    /**
     * Gets tournament status.
     *
     * @param tournament the tournament
     * @return the tournament status
     */
    public int getTournamentStatus(Tournament tournament) {
        return tournament.getStatus();
    }


    public Vector<String> getTournamentsName(){
        return idaoTournoi.getAllTournamentsNames();
    }

    public Tournament getTournamentFromName(String nt){return idaoTournoi.getOne(nt);}


    /**
     * Delete tournament.
     *
     * @param nom the nom
     */
    public static void deleteTournament(String nom){
        idaoTournoi.deleteTournament(nom);
    }

    /**
     * Create tournament int.
     *
     * @return the int
     */
    public static int createTournament(){
        String nameNewTournament = (String)JOptionPane.showInputDialog(
                null,
                "Entrez le nom du tournoi",
                "Nom du tournoi",
                JOptionPane.PLAIN_MESSAGE);

        if(nameNewTournament == null || nameNewTournament == ""){
            return 1;
        }else{
            try {
                nameNewTournament =  tools.mysql_real_escape_string(nameNewTournament);
                if(nameNewTournament.length() < 3){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Nom trop court.");
                    return 2;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(nameNewTournament == ""){
                JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Ne pas mettre de caract�res sp�ciaux ou accents dans le nom");
                return 2;
            }else{
                Tournament T = idaoTournoi.getOne(nameNewTournament);
                if(T!=null){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Un tournament du m�me nom existe d�j�");
                    return 2;
                }
                System.out.println("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (NULL, 10, '"+nameNewTournament+"', 0)");
                idaoTournoi.createTournament(nameNewTournament);
                //s2.executeUpdate("INSERT INTO tournois (id")
            }
        }
        return 0;
    }


}
