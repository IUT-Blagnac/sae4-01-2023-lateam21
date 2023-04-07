package Service;

import IDAO.TournamentIDAOImpl;
import models.Team;
import models.Tournament;
import resources.Tools;

import javax.swing.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

/**
 * The type Tournament service.
 */
public class TournamentService {
//attibuts classe controller
    /**
     * The Data tournaments.
     */
    private static ArrayList<Tournament> dataTournaments;

    private static ArrayList<Integer> idTournaments;

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
    /**
     * The Ts.
     */
    private final TeamService ts = new TeamService();

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
        updateTournaments();
    }

    /**
     * Create tournament int.
     *
     * @return the int
     */
    public void createTournament(){
        this.updateTournaments();
        String nameNewTournament = JOptionPane.showInputDialog(
                null,
                "Entrez le nom du tournoi",
                "Nom du tournoi",
                JOptionPane.PLAIN_MESSAGE);

        if(nameNewTournament == null || nameNewTournament.equals("")){
            JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Nom vide.");
        }else{
            try {
                nameNewTournament =  Tools.mysql_real_escape_string(nameNewTournament);
                if(nameNewTournament.length() < 3){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Nom trop court.");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(Objects.equals(nameNewTournament, "")){
                JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Ne pas mettre de caract�res sp�ciaux ou accents dans le nom");
            }else{
                Tournament T = idaoTournoi.getOne(nameNewTournament);
                if(T!=null){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Un tournament du m�me nom existe d�j�");
                }
                int id_tournoi=0;
                for(int id : idTournaments){
                    if(idTournaments.contains(id)){
                        id_tournoi++;
                    }
                }
                idaoTournoi.createTournament(id_tournoi, nameNewTournament);
            }
        }
    }

    public static void updateTournaments(){
        dataTournaments = new ArrayList<>();
        idTournaments = new ArrayList<>();
        dataTournaments = idaoTournoi.getAll();
        idTournaments = idaoTournoi.getAllIds();
    }

}