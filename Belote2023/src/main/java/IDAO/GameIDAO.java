package IDAO;

import models.Game;
import models.Tournament;

import java.sql.ResultSet;
import java.util.Vector;

/**
 * The interface Game idao.
 */
public interface GameIDAO{

    /**
     * Gets games from tournament.
     *
     * @param t the t
     * @return the games from tournament
     */
    Vector<Game> getGamesFromTournament (Tournament t);

    /**
     * Gets nb rounds by matchs.
     *
     * @param t the t
     * @return the nb rounds by matchs
     */
    ResultSet getNbRoundsByMatchs(Tournament t);

    /**
     * Gets nb games.
     *
     * @param t the t
     * @return the nb games
     */
    int getNbGames(Tournament t);

    /**
     * Gets nb rounds.
     *
     * @param t the t
     * @return the nb rounds
     */
    int getNbRounds(Tournament t);

    /**
     * Gets nb games ended.
     *
     * @param t the t
     * @return the nb games ended
     */
    int getNbGamesEnded(Tournament t);


    /**
     * Create game.
     *
     * @param Matches the matches
     * @param t       the t
     */
    void createGame(Vector<Vector<Game>> Matches, Tournament t);

    /**
     * Update game.
     *
     * @param gm the gm
     */
    void updateGame(Game gm);

    /**
     * Add rounds.
     *
     * @param t the t
     */
    void addRounds(Tournament t);

    /**
     * Gets nb games by teams.
     *
     * @param team1 the team 1
     * @param team2 the team 2
     * @return the nb games by teams
     */
    ResultSet getNbGamesByTeams(int team1, int team2);

    /**
     * Delete round.
     *
     * @param t the t
     */
    void deleteRound(Tournament t);

    /**
     * Add game.
     *
     * @param ga       the ga
     * @param numRound the num round
     * @param t        the t
     */
    void addGame(Game ga, int numRound , Tournament t);
}
