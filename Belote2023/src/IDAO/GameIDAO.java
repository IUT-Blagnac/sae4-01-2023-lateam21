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
<<<<<<< HEAD

    void createGame(Vector<Vector<Game>> Matches, Tournament t);
=======
>>>>>>> 8d1f87106bff3f2f4f34d09ac4e61bef91987daa

}
