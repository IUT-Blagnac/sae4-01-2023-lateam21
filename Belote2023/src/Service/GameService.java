package Service;

import IDAO.GameIDAOImpl;
import models.Game;
import models.Tournament;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
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

    Statement st;

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
     * @param index the index
     */
    public void updateGame(int index){
        String hasEnded = (getGame(index).getScore1() > 0 || getGame(index).getScore2() > 0) ? "oui":"non";
        System.out.println(hasEnded);
        String req="UPDATE matchs SET equipe1='" + getGame(index).getTeam1() + "', equipe2='" + getGame(index).getTeam2() + "',  score1='" + getGame(index).getScore1() + "',  score2='" + getGame(index).getScore2() + "', termine='" + hasEnded + "' WHERE id_match = " + getGame(index).getIdMatch() + ";";
        try {
            st.executeUpdate(req);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        updateGame();
    }

    /**
     * Get game game.
     *
     * @param index the index
     * @return the game
     */
    public Game getGame(int index){
        if(dataGames == null) updateGame();
        return dataGames.get(index);
    }


    /**
     * Update game.
     */
    public void updateGame(){
        dataGames = new ArrayList<>();
        try {
            ResultSet rs= st.executeQuery("SELECT * FROM matchs WHERE id_tournoi="+ tournament.getIdTournament() + ";");
            while(rs.next()) dataGames.add(new Game(rs.getInt("id_match"),rs.getInt("equipe1"),rs.getInt("equipe2"), rs.getInt("score1"),rs.getInt("score2"),rs.getInt("num_tour"), Objects.equals(rs.getString("termine"), "oui")));
            //public models.Game(int _idmatch,int _e1,int _e2,int _score1, int _score2, int _num_tour, boolean _termine)
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }



    /**
     * Generate games.
     *
     * @param t the t
     */
    public void generateGames(Tournament t){
        int nbt = 1;

        System.out.println("Nombre d'équipes : " + teS.getNbTeams(t));
        System.out.println("Nombre de tours  : " + nbt);
        StringBuilder req = new StringBuilder("INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n");
        Vector<Vector<Game>> ms;
        ms = getGamesToDo(teS.getNbTeams(t), nbt); //?
        int z = 1;
        char v = ' ';
        for(Vector<Game> vect :ms){
            for(Game m:vect){
                req.append(v).append("(NULL,").append(tournament.getIdTournament()).append(", ").append(z).append(", ").append(m.getTeam1()).append(", ").append(m.getTeam2()).append(", 'non')");
                v = ',';
            }
            req.append("\n");
            z++;
        }
        System.out.println(req);
        try{
            st.executeUpdate(req.toString());
            st.executeUpdate("UPDATE tournois SET statut=2 WHERE id_tournoi=" + tournament.getIdTournament() + ";");
            tournament.setStatus(2);
        }catch(SQLException e){
            System.out.println("Erreur validation �quipes : " + e.getMessage());
        }
    }

    /**
     * Add round boolean.
     *
     * @param t the t
     * @return the boolean
     */
    public boolean addRound(Tournament t){
        // Recherche du nombre de tours actuel
        int nbRoundsBefore;
        if(getNbRounds(tournament) >=  (teS.getNbTeams(t) -1) ) return false;
        System.out.println("Eq:" + teS.getNbTeams(t) + "  tours" + getNbRounds(tournament));
        try {
            ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+ tournament.getIdTournament()+"; ");
            rs.next();
            nbRoundsBefore = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Nombre de tours avant:" + nbRoundsBefore);


        if(nbRoundsBefore == 0){
            Vector<Game> ms;

            ms = getGamesToDo(teS.getNbTeams(t), nbRoundsBefore+1).lastElement();

            StringBuilder req = new StringBuilder("INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n");
            char v = ' ';
            for(Game m:ms){
                req.append(v).append("(NULL,").append(tournament.getIdTournament()).append(", ").append(nbRoundsBefore + 1).append(", ").append(m.getTeam1()).append(", ").append(m.getTeam2()).append(", 'non')");
                v = ',';
            }
            req.append("\n");

            //System.out.println(req);
            try{
                st.executeUpdate(req.toString());
            }catch(SQLException e){
                System.out.println("Erreur ajout tour : " + e.getMessage());
            }
        }else{
            try {
                ResultSet rs;
                //rs = st.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2 AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 AND m.id_tournoi = id_tournoi )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + this.id_tournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + this.id_tournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");

                rs = st.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + tournament.getIdTournament() + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + tournament.getIdTournament() + ") GROUP BY equipe  ORDER BY matchs_gagnes DESC;");


                ArrayList<Integer> ordreeq= new ArrayList<>();
                while(rs.next()){
                    ordreeq.add(rs.getInt("equipe"));
                    System.out.println(rs.getInt(1) +" _ " + rs.getString(2));
                }
                System.out.println("Taille"+ordreeq.size());
                int i;
                boolean fini;
                StringBuilder req = new StringBuilder("INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n");
                char v = ' ';
                while(ordreeq.size() > 1){
                    System.out.println("Taille " + ordreeq.size());
                    int j=0;
                    while(j<ordreeq.size()) {
                        System.out.println(ordreeq.get(j));
                        j++;
                    }
                    i=1;
                    do{
                        rs = st.executeQuery("SELECT COUNT(*) FROM matchs m WHERE ( (m.equipe1 = " + ordreeq.get(0) + " AND m.equipe2 = " + ordreeq.get(i) + ") OR (m.equipe2 = " + ordreeq.get(0) + " AND m.equipe1 = " + ordreeq.get(i) + ")  )");
                        rs.next();
                        if(rs.getInt(1) > 0){
                            // Le match est d�j� jou�
                            i++;
                            fini = false;

                        }else{
                            fini = true;
                            req.append(v).append("(NULL,").append(tournament.getIdTournament()).append(", ").append(nbRoundsBefore + 1).append(", ").append(ordreeq.get(0)).append(", ").append(ordreeq.get(i)).append(", 'non')");
                            System.out.println(ordreeq.get(0) + ", " +  ordreeq.get(i));
                            ordreeq.remove(0);
                            ordreeq.remove(i-1);
                            v = ',';
                        }
                    }while(!fini);
                }
                System.out.println(req);
                st.executeUpdate(req.toString());
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Delete round.
     */
    public void deleteRound(){
        int nbRoundsBefore; //? Ici je pense que on pourrait mettre ca en parametre

        try {
            ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+ tournament.getIdTournament()+"; ");
            rs.next();
            nbRoundsBefore = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return ;
        }
        //if(tour != nbRoundsBefore) return ;

        try {
            st.executeUpdate("DELETE FROM matchs WHERE id_tournoi="+ tournament.getIdTournament()+" AND num_tour=" + nbRoundsBefore);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Erreur del tour : " + e.getMessage());
        }
    }

    /**
     * Get games to do vector.
     *
     * @param nbPlayers the nb players
     * @param nbRounds  the nb rounds
     * @return the vector
     */
    public static Vector<Vector<Game>> getGamesToDo(int nbPlayers, int nbRounds){
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

        int     i, increment  = 1, temp;

        Vector<Vector<Game>> retour = new Vector<>(); //?

        Vector<Game> vg;

        for( int r = 1; r <= nbRounds;r++){
            if(r > 1){
                temp = listPlayers[nbPlayers - 2];
                for(i = (nbPlayers - 2) ; i > 0; i--){
                    listPlayers[i] = listPlayers[i-1];
                }
                listPlayers[0] = temp;
            }
            i       = 0;
            vg = new Vector<>();
            do {
                if (listPlayers[i] != -1 && listPlayers[nbPlayers - 1 - i] != -1) {
                    vg.add(new Game(i, listPlayers[i], listPlayers[nbPlayers - 1 - i], 0, 0, 0, false));
                }

                i += increment;
            } while (i < nbPlayers / 2);
            retour.add(vg);
        }
        return retour;
    }
}
