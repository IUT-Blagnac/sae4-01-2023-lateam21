package service;

import IDAO.impl.TournamentIDAOImpl;
import models.Tournament;
import resources.Tools;

import javax.swing.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

/**
 * The type Tournament service.
 */
public class TournamentService {
    /**
     * The Idao tournoi.
     */
    static TournamentIDAOImpl idaoTournoi = TournamentIDAOImpl.getInstance();

    /**
     * Instantiates a new Tournament service.
     */
    public TournamentService(){
        super();
    }

    /**
     * Get tournaments names vector.
     *
     * @return the vector
     */
    public Vector<String> getTournamentsNames(){
        return idaoTournoi.getAllTournamentsNames();
    }

    /**
     * Get tournament from name tournament.
     *
     * @param nt the nt
     * @return the tournament
     */
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
     */
    public void createTournament(){
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
                    return;
                }
            } catch (Exception e) {
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
                ArrayList<Integer> idTournaments= getAllIDsTournaments();
                for(int id : idTournaments){
                    if(idTournaments.contains(id_tournoi)){
                        id_tournoi++;
                    }
                }
                idaoTournoi.createTournament(id_tournoi, nameNewTournament);
            }
        }
    }

    /**
     * Update tournament.
     *
     * @param t the t
     */
    public void updateTournament(Tournament t){
        idaoTournoi.updateTournament(t);
    }

    /**
     * Get all i ds tournaments array list.
     *
     * @return the array list
     */
    protected ArrayList<Integer> getAllIDsTournaments(){
        return idaoTournoi.getAllIds();
    }

    /**
     * Gets result tournoi.
     *
     * @param tournament the tournament
     * @return the result tournoi
     */
    public ResultSet getResultTournoi(Tournament tournament) {
        return idaoTournoi.getResultTournament(tournament);
    }

    /**
     * Gets winner tournament.
     *
     * @param tournament the tournament
     * @return the winner tournament
     */
    public String getWinnerTournament(Tournament tournament) {
        return idaoTournoi.getWinnerTournament(tournament);
    }
}