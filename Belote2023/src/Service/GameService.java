package Service;

import IDAO.GameIDAOImpl;
import models.Game;
import models.Tournament;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class GameService {
    //attributes
    Tournament tournament;
    TeamService teS = new TeamService();
    static GameIDAOImpl idaoGame = GameIDAOImpl.getInstance();
    /**
     * The Data games.
     */
    private ArrayList<Game> dataGames = null;

    private ArrayList<Integer> idGames = null;

    Statement st;

    public GameService(){super();}

    public Vector<Object> getSomeVarFromGamesFromTournament(Tournament t){
        return idaoGame.getSomeVarFromGamesFromTournament(t);
    }

    /**
     * Get nb games int.
     *
     * @return the int
     */
    public int getNbGames(Tournament to){
        return idaoGame.getNbGames(to);
    }

    public int getNbEndedGames(Tournament to){return idaoGame.getNbGamesEnded(to);}

    /**
     * Get nb rounds int.
     *
     * @param tournament the tournament
     * @return the int
     */
    public int getNbRounds(Tournament tournament){
        return idaoGame.getNbRounds(tournament);
    }

    /**
     * Update game.
     *
     * @param gm the game
     */
    public void updateGame(Game gm, Tournament t){
        idaoGame.updateGame(gm);
        updateGames(t);
    }

    /**
     * Get game game.
     *
     * @param index the index
     * @return the game
     */
    public Game getGame(int index, Tournament t){
        if(dataGames == null) updateGames(t);
        return dataGames.get(index);
    }


    /**
     * Update game.
     */
    public void updateGames(Tournament t){
        dataGames = new ArrayList<>();
        idGames = new ArrayList<>();
        dataGames = idaoGame.getGamesFromTournament(t);
        idGames = idaoGame.getAllIdGames();
    }


    private int setRandomID(){
        Random rand = new Random();
        int limite = 1000;
        int id = rand.nextInt(limite);
        if(idGames.contains(id)){
            id = rand.nextInt(limite);
        }
        return id;
    }


    /**
     * Generate games.
     *
     * @param t the t
     */
    public void generateGames(Tournament t){
        int id=setRandomID(), nbt = 1;
        for(int i : idGames){
            if(idGames.contains(id)){
                id++;
            }
        }
        Vector<Game> ms;
        ms = getGamesToDo(teS.getNbTeams(t), nbt);
        idaoGame.createGame(ms, t, id);
    }

    /**
     * Add round boolean.
     *
     * @param t the t
     * @return the boolean
     */
    public void addRound(Tournament t){
        //si nbtours == 0 alors créaton d'un tour
        if(getNbRounds(t) == 0){
            Vector<Game> games = getGamesToDo(teS.getNbTeams(t), getNbRounds(t)+1);
//            Game lastGame = games.lastElement();
            int id=setRandomID();
            idaoGame.createGame(games,t,id);
        }else{
            idaoGame.addRounds(t);
        }
    }

    /**
     * Delete round.
     */
    public void deleteRound(Tournament to){
        idaoGame.deleteRound(to);
    }

    /**
     * Get games to do vector.
     *
     * @param nbPlayers the nb players
     * @param nbRounds  the nb rounds
     * @return the vector
     */
    public static Vector<Game> getGamesToDo(int nbPlayers, int nbRounds){
        if( nbRounds  >= nbPlayers){
            System.out.println("Erreur tours < equipes");
            return null;
        }

        int[]   listPlayers;
        if((nbPlayers % 2) == 1){
            // Nombre impair de joueurs, on rajoute une �quipe fictive
            listPlayers   = new int[nbPlayers+1];
            listPlayers[nbPlayers] = -1;
            for(int z = 0; z < nbPlayers;z++){
                listPlayers[z] = z+1;
            }
            nbPlayers++;
        }else{
            listPlayers   = new int[nbPlayers];
            for(int z = 0; z < nbPlayers;z++){
                listPlayers[z] = z+1;
            }
        }

        boolean stop;
        int     i, increment  = 1, temp;

//        Vector<Vector<Game>> retour = new Vector<>(); //?

        Vector<Game> vq = new Vector<>();;

        for( int r = 1; r <= nbRounds;r++){
            if(r > 1){
                temp = listPlayers[nbPlayers - 2];
                for(i = (nbPlayers - 2) ; i > 0; i--){
                    listPlayers[i] = listPlayers[i-1];
                }
                listPlayers[0] = temp;
            }
            i       = 0;
//            stop = false; //? On a un break apres - donc je pense que on pourait simplement faire while(true)
            while(true){
                if (listPlayers[i] == -1 || listPlayers[nbPlayers - 1  - i] == -1){
                    // Nombre impair de joueur, le joueur n'a pas d'adversaire
                }else{
                    vq.add(new Game(i,listPlayers[i], listPlayers[nbPlayers - 1  - i],0,0,0,false));
                }
                i+= increment;
                if(i >= nbPlayers / 2){
                    if(increment == 1){
                        break;
                    }else{
                        increment = -2;
                        if( i > nbPlayers / 2){
                            i = ((i > nbPlayers / 2) ? i - 3 : --i) ;
                        }
                        if ((i < 1) && (increment == -2)){
                            break;
                        }
                    }
                }
            }
        }
        return vq;
    }
}
