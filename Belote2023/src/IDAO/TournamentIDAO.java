package IDAO;

import models.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * The interface Tournament idao.
 */
public interface TournamentIDAO extends IDAO<Tournament> {

    /**
     * Delete tournament.
     *
     * @param nomT the nom t
     */
    void deleteTournament(String nomT);

    /**
     * Gets one.
     *
     * @param nomT the nom t
     * @return the one
     */
    Tournament getOne(String nomT);

    /**
     * Update tournament.
     *
     * @param t the t
     */
    public void updateTournament(Tournament t);

    /**
     * Gets nb rounds.
     *
     * @param t the t
     * @return the nb rounds
     */
    public int getNbRounds(Tournament t);


    /**
     * Gets all names.
     *
     * @return the all names
     */
    public ArrayList<String> getAllNames();

    public Vector<String> getAllTournamentsNames();

    /**
     * Create tournament.
     *
     * @param nom the nom
     */
    public void createTournament(String nom);

}
