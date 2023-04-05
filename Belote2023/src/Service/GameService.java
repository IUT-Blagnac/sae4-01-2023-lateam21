package Service;

import IDAO.GameIDAOImpl;
import models.Tournament;

public class GameService {
    //attributes
    Tournament tournament;
    static GameIDAOImpl idaoGame = GameIDAOImpl.getInstance();

    public GameService(){super();}

    /**
     * Get nb games int.
     *
     * @return the int
     */
    public int getNbGames(Tournament to){
        return idaoGame.getNbGames(to);
    }

    public int getNbEndedGames(Tournament to){return idaoGame.getNbGamesEnded(to);}
}
