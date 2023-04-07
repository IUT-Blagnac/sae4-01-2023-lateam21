package IDAO;

import models.CONSTANTS;
import models.Team;
import models.Tournament;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Team idao.
 */
public class TeamIDAOImpl extends AbstractDAO implements TeamIDAO {

    /**
     * Instantiates a new Team idao.
     */
    private TeamIDAOImpl(){super();}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TeamIDAOImpl getInstance() {
        if(CONSTANTS.instance==null){
            synchronized(TeamIDAOImpl.class) {
                if (CONSTANTS.instance == null) {
                    CONSTANTS.instance = new TeamIDAOImpl();
                }
            }
        }
        return CONSTANTS.instance;
    }


    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @Override
    public Team getOne(int id) {return null;}

    /**
     * Gets all.
     *
     * @return the all
     */
    @Override
    public List<Team> getAll() {
        return null;
    }

    @Override
    public Team getOneTeamFromTournament(int idTeam, Tournament t) {
        Team te = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM equipes where id_tournoi = ? and id_equipe = ?");
            ps.setInt(1,t.getIdTournament());
            ps.setInt(2, idTeam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                te = new Team(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return te;
    }

    /**
     * Gets teams tournament.
     *
     * @param t the t
     * @return the teams tournament
     */
    @Override
    public ArrayList<Team> getTeamsTournament(Tournament t) {
        ArrayList<Team> listTeamsTournament = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + t.getIdTournament() + " ORDER BY num_equipe;");
            while(rs.next()){
                listTeamsTournament.add(new Team(rs.getInt(CONSTANTS.BD_ID_EQUIPE),rs.getInt(CONSTANTS.BD_NUM_EQUIPE), rs.getString(CONSTANTS.BD_NOM_J_1), rs.getString(CONSTANTS.BD_NOM_J_2)));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listTeamsTournament;
    }

    /**
     * Gets id teams tournament.
     *
     * @param t the t
     * @return the id teams tournament
     */
    @Override
    public ArrayList<Integer> getIdTeamsTournament(Tournament t) {
        ArrayList<Integer> listIdTeamsTournament = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_equipe FROM equipes WHERE id_tournoi = " + t.getIdTournament() + " ORDER BY num_equipe;");
            while(rs.next()){
<<<<<<< HEAD
                listIdTeamsTournament.add(rs.getInt("id_equipe"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listIdTeamsTournament;
    }

    @Override
    public ArrayList<Integer> getAllIdTeams() {
        ArrayList<Integer> listIdTeamsTournament = new ArrayList<Integer>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_equipe FROM equipes;");
            while(rs.next()){
                listIdTeamsTournament.add(rs.getInt(1));
=======
                listIdTeamsTournament.add(rs.getInt(CONSTANTS.BD_NUM_EQUIPE));
>>>>>>> 8d1f87106bff3f2f4f34d09ac4e61bef91987daa
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listIdTeamsTournament;
    }

    @Override
    public void addTeam(Tournament t, int pos) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES (?, ? , ?,'\"Joueur 1\"', '\"Joueur 2\"');");
            ps.setInt(1, pos);
            ps.setInt(2, pos);
            ps.setInt(3,t.getIdTournament());
            ps.executeUpdate();
            //ICI METTRE UN UPDATE???
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTeam(Tournament t, int posTeam) {
        try {
            int nbTeam;
            Statement st = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT num_equipe FROM equipes WHERE id_equipe = ?");
            ps.setInt(1,posTeam);
            ResultSet rs = ps.executeQuery();
            rs.next();
            nbTeam = rs.getInt(1);
            rs.close();
            st.executeUpdate("DELETE FROM equipes WHERE id_tournoi = " + t.getIdTournament()+ " AND id_equipe = " + posTeam);
//            st.executeUpdate("UPDATE equipes SET num_equipe = num_equipe - 1 WHERE id_tournoi = " + t.getIdTournament() + " AND num_equipe > " + nbTeam);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
<<<<<<< HEAD
    public void updatePlayersTeam(int idTeam, String pTeam1, String pTeam2, Tournament t) {
=======
    public void updatePlayersTeam(int idTeam, String pTeam1, String pTeam2, Tournament t){
>>>>>>> 8d1f87106bff3f2f4f34d09ac4e61bef91987daa
        try {
            Statement st = connection.createStatement();
            String req = "UPDATE equipes SET nom_j1 = '" + pTeam1 + "', nom_j2 = '" + pTeam2 + "' WHERE id_tournoi = " +t.getIdTournament() + "and id_equipe = "+idTeam+";";
            st.executeUpdate(req);
<<<<<<< HEAD

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
=======
>>>>>>> 8d1f87106bff3f2f4f34d09ac4e61bef91987daa

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
