package IDAO;

import models.Tournament;

import java.util.List;


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
     * Gets nb games.
     *
     * @param t the t
     * @return the nb games
     */
    public int getNbGames(Tournament t);

    /**
     * Gets nb games ended.
     *
     * @param t the t
     * @return the nb games ended
     */
    public int getNbGamesEnded(Tournament t);

    /**
     * Gets all names.
     *
     * @return the all names
     */
    public List<String> getAllNames();

    /**
     * Create tournament.
     *
     * @param nom the nom
     */
    public void createTournament(String nom);

}
