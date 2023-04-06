package Service;

import IDAO.TournamentIDAOImpl;
import models.Game;
import models.Team;
import models.Tournament;
//import view.TournoiView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class TournamentService {
    //attibuts classe controller
    private final ArrayList<Team> dataTeams = null;
    private ArrayList<Game> dataGames = null;
    private final ArrayList<Integer>ideqs  = null;
    static TournamentIDAOImpl idaoTournoi = TournamentIDAOImpl.getInstance();
    Statement st;
    private Tournament tournament;
    private final TeamService ts = new TeamService();

    public TournamentService(){
        super();
    }

    public int getTournamentStatus(Tournament tournament) {
        return tournament.getStatus();
    }

    public int getNbRounds(Tournament tournament){
        return idaoTournoi.getNbRounds(tournament);
    }

    public Vector<String> getTournamentsName(){
        return idaoTournoi.getAllTournamentsNames();
    }

    public Game getGame(int index){
        if(dataGames == null) updateGame();
        return dataGames.get(index);
    }


    public void updateGame(){
        dataGames = new ArrayList<>();
        try {
            ResultSet rs= st.executeQuery("SELECT * FROM matchs WHERE id_tournoi="+ tournament.getIdTournament() + ";");
            while(rs.next()) dataGames.add(new Game(rs.getInt("id_match"),rs.getInt("equipe1"),rs.getInt("equipe2"), rs.getInt("score1"),rs.getInt("score2"),rs.getInt("num_tour"), rs.getString("termine").equals("oui")));
            //public models.Game(int _idmatch,int _e1,int _e2,int _score1, int _score2, int _num_tour, boolean _termine)
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }

    public void generateGames(Tournament t){
        int nbt = 1;

        System.out.println("Nombre d'équipes : " + ts.getNbTeams(t));
        System.out.println("Nombre de tours  : " + nbt);
        String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
        Vector<Vector<Game>> ms;
        ms = getGamesToDo(ts.getNbTeams(t), nbt); //?
        int z = 1;
        char v = ' ';
        for(Vector<Game> vect :ms){
            for(Game m:vect){
                req += v + "(NULL," + tournament.getIdTournament() + ", " + z + ", "+ m.getTeam1() + ", " + m.getTeam2() + ", 'non')";
                v = ',';
            }
            req += "\n";
            z++;
        }
        System.out.println(req);
        try{
            st.executeUpdate(req);
            st.executeUpdate("UPDATE tournois SET statut=2 WHERE id_tournoi=" + tournament.getIdTournament() + ";");
            tournament.setStatus(2);
        }catch(SQLException e){
            System.out.println("Erreur validation �quipes : " + e.getMessage());
        }
    }

    public boolean addRound(Tournament t){
        // Recherche du nombre de tours actuel
        int nbRoundsBefore;
        if(getNbRounds(tournament) >=  (ts.getNbTeams(t) -1) ) return false;
        System.out.println("Eq:" + ts.getNbTeams(t) + "  tours" + getNbRounds(tournament));
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

            ms = getGamesToDo(ts.getNbTeams(t), nbRoundsBefore+1).lastElement();

            String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
            char v = ' ';
            for(Game m:ms){
                req += v + "(NULL," + tournament.getIdTournament() + ", " + (nbRoundsBefore + 1) + ", "+ m.getTeam1() + ", " + m.getTeam2() + ", 'non')";
                v = ',';
            }
            req += "\n";

            //System.out.println(req);
            try{
                st.executeUpdate(req);
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
                String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
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
                            req += v + "(NULL," + tournament.getIdTournament() + ", " + (nbRoundsBefore + 1) + ", "+  ordreeq.get(0) + ", " +  ordreeq.get(i) + ", 'non')";
                            System.out.println(ordreeq.get(0) + ", " +  ordreeq.get(i));
                            ordreeq.remove(0);
                            ordreeq.remove(i-1);
                            v = ',';
                        }
                    }while(!fini);
                }
                System.out.println(req);
                st.executeUpdate(req);
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return true;
    }

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

    public static void deleteTournament(String nom){
        idaoTournoi.deleteTournament(nom);
    }

    public static int createTournament(){
        String nameNewTournament = JOptionPane.showInputDialog(
                null,
                "Entrez le nom du tournoi",
                "Nom du tournoi",
                JOptionPane.PLAIN_MESSAGE);

        if(nameNewTournament == null || nameNewTournament.equals("")){
            return 1;
        }else{
            try {
                nameNewTournament =  mysqlRealEscapeString(nameNewTournament);
                if(nameNewTournament.length() < 3){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Nom trop court.");
                    return 2;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(Objects.equals(nameNewTournament, "")){
                JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Ne pas mettre de caract�res sp�ciaux ou accents dans le nom");
                return 2;
            }else{
                Tournament T = idaoTournoi.getOne(nameNewTournament);
                if(T!=null){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Un tournament du m�me nom existe d�j�");
                    return 2;
                }
                System.out.println("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (NULL, 10, '"+nameNewTournament+"', 0)");
                idaoTournoi.createTournament(nameNewTournament);
                //s2.executeUpdate("INSERT INTO tournois (id")
            }
        }
        return 0;
    }

    public void updateTeams(int index, Tournament t){
        try {
            String req = "UPDATE equipes SET nom_j1 = '" + mysqlRealEscapeString(ts.getTeam(index, t).getTeam1()) + "', nom_j2 = '" + mysqlRealEscapeString(ts.getTeam(index, t).getTeam2()) + "' WHERE id_equipe = " + ts.getTeam(index, t).getId() + ";";
            System.out.println(req);
            st.executeUpdate(req);
            ts.updateTeams(t);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

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

    public void deleteTeam(int ideq, Tournament t){
        try {
            int nbTeam;
            ResultSet rs = st.executeQuery("SELECT num_equipe FROM equipes WHERE id_equipe = " + ideq);
            rs.next();
            nbTeam = rs.getInt(1);
            rs.close();
            st.executeUpdate("DELETE FROM equipes WHERE id_tournoi = " + tournament.getIdTournament()+ " AND id_equipe = " + ideq);
            st.executeUpdate("UPDATE equipes SET num_equipe = num_equipe - 1 WHERE id_tournoi = " + tournament.getIdTournament() + " AND num_equipe > " + nbTeam);
            ts.updateTeams(t);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String mysqlRealEscapeString(String str) {
        if (str == null) {
            return null;
        }

        if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
            return str;
        }

        String cleanString = str;
        cleanString = cleanString.replaceAll("\\n","\\\\n");
        cleanString = cleanString.replaceAll("\\r", "\\\\r");
        cleanString = cleanString.replaceAll("\\t", "\\\\t");
        cleanString = cleanString.replaceAll("\\00", "\\\\0");
        cleanString = cleanString.replaceAll("'", "''");
        return cleanString;

    }

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
                if (listPlayers[i] == -1 || listPlayers[nbPlayers - 1 - i] == -1) {
                    // Nombre impair de joueur, le joueur n'a pas d'adversaire
                } else {
                    vg.add(new Game(i, listPlayers[i], listPlayers[nbPlayers - 1 - i], 0, 0, 0, false));
                }

                i += increment;
            } while (i < nbPlayers / 2);
            retour.add(vg);
        }
        return retour;
    }
}
