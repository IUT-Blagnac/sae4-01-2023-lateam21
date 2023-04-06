package IDAO;

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
     * The constant instance.
     */
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
    public final static TeamIDAOImpl getInstance() {
        if(TeamIDAOImpl.instance==null){
            synchronized(TeamIDAOImpl.class) {
                if (TeamIDAOImpl.instance == null) {
                    TeamIDAOImpl.instance = new TeamIDAOImpl();
                }
            }
        }
        return TeamIDAOImpl.instance;
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
        ArrayList<Team> listTeamsTournament = new ArrayList<Team>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + t.getIdTournament() + " ORDER BY num_equipe;");
            while(rs.next()){
                listTeamsTournament.add(new Team(rs.getInt("id_equipe"),rs.getInt("num_equipe"), rs.getString("nom_j1"), rs.getString("nom_j2")));
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
        ArrayList<Integer> listIdTeamsTournament = new ArrayList<Integer>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + t.getIdTournament() + " ORDER BY num_equipe;");
            while(rs.next()){
                listIdTeamsTournament.add(rs.getInt("num_equipe"));
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
            int nbTeam;
            Statement st = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT num_equipe FROM equipes WHERE id_equipe = ?");
            ps.setInt(1,posTeam);
            ResultSet rs = ps.executeQuery();
            rs.next();
            nbTeam = rs.getInt(1);
            rs.close();
            st.executeUpdate("DELETE FROM equipes WHERE id_tournoi = " + t.getIdTournament()+ " AND id_equipe = " + posTeam);
            st.executeUpdate("UPDATE equipes SET num_equipe = num_equipe - 1 WHERE id_tournoi = " + t.getIdTournament() + " AND num_equipe > " + nbTeam);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlayersTeam(int idTeam, String pTeam1, String pTeam2, Tournament t) {
        try {
            Statement st = connection.createStatement();
            String req = "UPDATE equipes SET nom_j1 = '" + pTeam1 + "', nom_j2 = '" + pTeam2 + "' WHERE id_tournoi = " +t.getIdTournament() + "and id_equipe = "+idTeam+";";
            st.executeUpdate(req);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
