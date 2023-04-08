package Service;

import IDAO.GameIDAOImpl;
import models.Game;
import models.Team;
import models.Tournament;
import view.Window;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class GameService {
    //attributes
    TeamService teS = new TeamService();
    static GameIDAOImpl idaoGame = GameIDAOImpl.getInstance();
    /**
     * The Data games.
     */
    private Vector<Game> dataGames = null;
    private ArrayList<Integer> idGames = null;
    public GameService(){super();}
    public ResultSet getNbRoundsByMatchs(Tournament t){
        return idaoGame.getNbRoundsByMatchs(t);
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

//    public Vector<Game> getGames(Tournament to) {
//        return idaoGame.getGamesFromTournament(to);
//    }

    /**
     * Update game.
     */
    public void updateGames(Tournament t){
        dataGames = new Vector<>();
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
        int nbt = 1;
        updateGames(t);
        Vector<Vector<Game>> ms;
        ms = getGamesToDo(teS.getNbTeams(t), nbt);
        idaoGame.createGame(ms, t);

    }

    /**
     * Add round boolean.
     *
     * @param t the t
     * @return the boolean
     */
    public void addRound(Tournament t){
        updateGames(t);
        int nbroundsav;
        if(getNbRounds(t) >= (teS.getNbTeams(t)-1)) return;
        try{
            nbroundsav = idaoGame.getNbRounds(t);
        }catch (Exception e){
            Window.showError("Erreur lors de la récupération du nombre de tours du tournoi.");
            System.out.println(e.getMessage()); // Message développeur
            return;
        }
        if(nbroundsav==0){
            Vector<Game> gvect;
            gvect = getGamesToDo(teS.getNbTeams(t),nbroundsav+1 ).lastElement();
            try{
                for(Game g : gvect){
                    idaoGame.addGame(g,nbroundsav+1,t);
                }
            }catch (Exception e){
                Window.showError("Erreur lors de l'insertion du match.");
                System.out.println(e.getMessage()); // Message développeur
                return;
            }
        }else{
            try{
                idaoGame.addRounds(t);
            }catch (Exception e){
                Window.showError("Erreur lors de la récupération des matchs du tournoi.");
                System.out.println(e.getMessage()); // Message développeur
                return;
            }
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
     * @param nbRounds  the nb rounds
     * @return the vector
     */
    public Vector<Vector<Game>> getGamesToDo(int nbPlayers, int nbRounds){
        if( nbRounds  >= nbPlayers){
            Window.showError("Erreur lors de la récupération des matchs à faire, le nombre de tours est supérieur ou égal au nombre d'équipes.");
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

        int i, increment  = 1, temp;

        Vector<Vector<Game>> retour = new Vector<>(); //?
        Vector<Game> vm = new Vector<>();
        boolean stop;

        for( int r = 1; r <= nbRounds;r++){
            if(r > 1){
                temp = listPlayers[nbPlayers-2];
                for(i = (nbPlayers - 2) ; i > 0; i--){
                    listPlayers[i] = listPlayers[i-1];
                }
                listPlayers[0]=temp;
            }
            i = 0;
            stop = false;
            while(!stop){
                if (!(listPlayers[i]==-1 || listPlayers[nbPlayers - 1 - i]== -1)){
                    vm.add(new Game(listPlayers[i], listPlayers[nbPlayers - 1 - i]));
                }
                i+= increment;
                if(i >= nbPlayers / 2){
                    stop=true;
                }
            }
            retour.add(vm);
        }
        return retour;
    }
}
