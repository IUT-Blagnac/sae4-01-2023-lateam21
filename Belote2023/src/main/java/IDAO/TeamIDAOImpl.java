package IDAO;

import models.CONSTANTS;
import models.Team;
import models.Tournament;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The type Team idao.
 */
public class TeamIDAOImpl extends AbstractDAO implements TeamIDAO {

    private static TeamIDAOImpl instance = null;

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
        if(TeamIDAOImpl.instance==null){
            TeamIDAOImpl.instance = new TeamIDAOImpl();
        }
        return TeamIDAOImpl.instance;
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

    @Override
    public ArrayList<Integer> getAllIdTeams() {
        ArrayList<Integer> listIdTeamsTournament = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_equipe FROM equipes;");
            while(rs.next()){
                listIdTeamsTournament.add(rs.getInt(CONSTANTS.BD_ID_EQUIPE));
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTeam(Tournament t, int posTeam) {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM equipes WHERE id_tournoi = " + t.getIdTournament()+ " AND id_equipe = " + posTeam);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlayersTeam(int idTeam, String pTeam1, String pTeam2, Tournament t){
        try {
            Statement st = connection.createStatement();
            String req = "UPDATE equipes SET nom_j1 = '" + pTeam1 + "', nom_j2 = '" + pTeam2 + "' WHERE id_tournoi = " +t.getIdTournament() + "and id_equipe = "+idTeam+";";
            st.executeUpdate(req);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
