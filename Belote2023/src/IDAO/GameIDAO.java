package IDAO;

import models.Game;
import models.Tournament;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public interface GameIDAO extends IDAO<Game>{

    Vector<Game> getGamesFromTournament (Tournament t);
    ResultSet getNbRoundsByMatchs(Tournament t);

    /**
     * Gets nb games.
     *
     * @param t the t
     * @return the nb games
     */
    int getNbGames(Tournament t);

    /**
     * Gets nb games ended.
     *
     * @param t the t
     * @return the nb games ended
     */

    int getNbRounds(Tournament t);

    int getNbGamesEnded(Tournament t);


    void createGame(Vector<Vector<Game>> Matches, Tournament t);

    void updateGame(Game gm);

    ArrayList<Integer> getAllIdGames();

    void addRounds(Tournament t);

    ResultSet getNbGamesByTeams(int team1, int team2);

    void deleteRound(Tournament t);

    void addGame(Game ga, int numRound , Tournament t);
}
