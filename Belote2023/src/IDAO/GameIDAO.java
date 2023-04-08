package IDAO;

import models.Game;
import models.Tournament;

import java.util.ArrayList;
import java.util.Vector;

public interface GameIDAO extends IDAO<Game>{

    ArrayList<Game> getGamesFromTournament (Tournament t);
    Vector<Object> getSomeVarFromGamesFromTournament(Tournament t);

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

    void createGame(Vector<Game> Matches, Tournament t, int id);

    void updateGame(Game gm);

    ArrayList<Integer> getAllIdGames();

    void addRounds(Tournament t);

    void deleteRound(Tournament t);
}
