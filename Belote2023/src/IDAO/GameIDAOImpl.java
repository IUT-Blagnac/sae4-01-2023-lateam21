package IDAO;

import Service.GameService;
import models.CONSTANTS;
import models.Game;
import models.Tournament;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GameIDAOImpl extends AbstractDAO implements GameIDAO{

    private static GameIDAOImpl instance = null;

    private GameIDAOImpl(){
        super();
    }

    public static GameIDAOImpl getInstance() {
        if (GameIDAOImpl.instance == null) {
            synchronized(GameIDAOImpl.class) {
                GameIDAOImpl.instance = new GameIDAOImpl();
            }
        }
        return GameIDAOImpl.instance;
    }

    @Override
    public Game getOne(int id) {
        return null;
    }

    @Override
    public List<Game> getAll() {
        return null;
    }

    @Override
    public ArrayList<Game> getGamesFromTournament(Tournament t) {
        ArrayList<Game> listGames = new ArrayList<>();
        Game game;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM matchs WHERE id_tournoi = ?");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                game = new Game(rs.getInt("id_match"), rs.getInt("equipe1"), rs.getInt("equipe2"),
                        rs.getInt("score1"), rs.getInt("score2"),rs.getInt(CONSTANTS.BD_NUM_TOUR), rs.getString("termine").equals("oui"));
                listGames.add(game);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listGames;
    }

    @Override
    public Vector<Object> getSomeVarFromGamesFromTournament(Tournament t) {
        Vector<Object> listVarGame = new Vector<>();
        try {
            Statement st = connection.createStatement();
            String req = "Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.num_tour=m.num_tour  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + t.getIdTournament() + " GROUP BY m.num_tour,m.id_tournoi;";
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                listVarGame.add(rs.getInt(CONSTANTS.BD_NUM_TOUR));
                listVarGame.add(rs.getInt(CONSTANTS.BD_TMATCHS));
                listVarGame.add(rs.getInt(CONSTANTS.BD_TERMINES));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listVarGame;
    }

    /**
     * Gets nb games.
     *
     * @param t the t
     * @return the nb games
     */
    @Override
    public int getNbGames(Tournament t) {
        int result;
        try {
            PreparedStatement ps = connection.prepareStatement("Select count(*) from Matchs m WHERE m.id_tournoi=?;");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Gets nb games ended.
     *
     * @param t the t
     * @return the nb games ended
     */
    @Override
    public int getNbGamesEnded(Tournament t) {
        int result;
        try {
            PreparedStatement ps = connection.prepareStatement("Select count(*) from matchs m  WHERE m.id_tournoi = ?  AND m.termine='oui' ");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            rs.next();
            result = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Gets nb rounds.
     *
     * @param t the t
     * @return the nb rounds
     */
    @Override
    public int getNbRounds(Tournament t) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi= ? ; ");
            ps.setInt(1, t.getIdTournament());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public void createGame(Vector<Game> Matches, Tournament t, int id) {
        try {
            int tour = 1; String req = null;
            Statement st = connection.createStatement();
            for (Game m : Matches) {
                req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES ("+id+"," + t.getIdTournament() + "," + tour + ", " + m.getTeam1() + ", " + m.getTeam2() + ", 'non')";
                tour++;
            }
            st.executeUpdate(req);
            st.executeUpdate("UPDATE tournois SET statut=2 WHERE id_tournoi=" + t.getIdTournament() + ";"); //maj statut tournoi
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGame(Game gm) {
        String hasEnded = (gm.getScore1() > 0 || gm.getScore2() > 0) ? "oui":"non";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE matchs SET equipe1 = ?, equipe2 = ?,  score1 = ?,  score2 = ?, termine = ? WHERE id_match = ? ;");
            ps.setInt(1, gm.getTeam1());
            ps.setInt(2, gm.getTeam2());
            ps.setInt(3, gm.getScore1());
            ps.setInt(4, gm.getScore2());
            ps.setString(5, hasEnded);
            ps.setInt(6, gm.getIdMatch());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Integer> getAllIdGames() {
        ArrayList<Integer> listIdTeamsTournament = new ArrayList<Integer>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_match FROM matchs;");
            while(rs.next()){
                listIdTeamsTournament.add(rs.getInt(CONSTANTS.BD_ID_MATCH));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listIdTeamsTournament;
    }

    @Override
    public void addRounds(Tournament t) {
        try {
            Statement st = connection.createStatement();
            GameService gS = new GameService();
            int nbRounds = gS.getNbRounds(t);
            
//            ResultSet rs;
//            rs = st.executeQuery("SELECT equipe, " +
//                    "(SELECT count(*) FROM matchs m " +
//                    "WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes " +
//                    "FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + t.getIdTournament() + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + t.getIdTournament() + ") " +
//                    "GROUP BY equipe  ORDER BY matchs_gagnes DESC;");
//            ArrayList<Integer> ordreeq= new ArrayList<Integer>();
//            while(rs.next()){
//                ordreeq.add(rs.getInt("equipe"));
//                System.out.println(rs.getInt(1) +" _ " + rs.getString(2));
//            }
//            System.out.println("Taille : "+ordreeq.size());
//            int i;
//            boolean fini;
//            String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
//            char v = ' ';
//            while(ordreeq.size() > 1){
//                System.out.println("Taille " + ordreeq.size());
//                int j=0;
//                while(j<ordreeq.size()) {
//                    System.out.println(ordreeq.get(j));
//                    j++;
//                }
//                i=1;
//                do{
//                    rs = st.executeQuery("SELECT COUNT(*) FROM matchs m WHERE ( (m.equipe1 = " + ordreeq.get(0) + " AND m.equipe2 = " + ordreeq.get(i) + ") OR (m.equipe2 = " + ordreeq.get(0) + " AND m.equipe1 = " + ordreeq.get(i) + ")  )");
//                    rs.next();
//                    if(rs.getInt(1) > 0){
//                        // Le match est d�j� jou�
//                        i++;
//                        fini = false;
//                    }else{
//                        fini = true;
//                        req += v + "(NULL," + t.getIdTournament() + ", " + (getNbRounds(t) + 1) + ", "+  ordreeq.get(0) + ", " +  ordreeq.get(i) + ", 'non')";
//                        System.out.println(ordreeq.get(0) + ", " +  ordreeq.get(i));
//                        ordreeq.remove(0);
//                        ordreeq.remove(i-1);
//                        v = ',';
//                    }
//                }while(!fini);
//            }
//            System.out.println(req);
//            st.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRound(Tournament to) {
        int lastRound;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+ to.getIdTournament()+"; ");
            rs.next();
            lastRound = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return ;
        }

        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM matchs WHERE id_tournoi="+ to.getIdTournament()+" AND num_tour=" + lastRound);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Erreur del tour : " + e.getMessage());
        }
    }


}
