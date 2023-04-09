package IDAO;

import models.Tournament;

import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.Vector;


/**
 * The interface Tournament idao.
 */
public interface TournamentIDAO{

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
     * Gets all ids.
     *
     * @return the all ids
     */
    ArrayList<Integer> getAllIds();

    /**
     * Update tournament.
     *
     * @param t the t
     */
    void updateTournament(Tournament t);


    /**
     * Gets all tournaments names.
     *
     * @return the all tournaments names
     */
    Vector<String> getAllTournamentsNames();

    /**
     * Create tournament.
     *
     * @param id  the id
     * @param nom the nom
     */
    void createTournament(int id, String nom);

    /**
     * Gets result tournament.
     *
     * @param tournament the tournament
     * @return the result tournament
     */
    ResultSet getResultTournament(Tournament tournament);

    /**
     * Gets winner tournament.
     *
     * @param tournament the tournament
     * @return the winner tournament
     */
    String getWinnerTournament(Tournament tournament);
}
