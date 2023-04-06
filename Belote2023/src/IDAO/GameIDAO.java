package IDAO;

import models.Game;
import models.Tournament;

public interface GameIDAO extends IDAO<Game>{
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

}
