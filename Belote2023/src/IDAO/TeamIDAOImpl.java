package IDAO;

import models.Team;
import models.Tournament;

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
    private TeamIDAOImpl(){super();};

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
     * Add.
     *
     * @param obj the obj
     */
    @Override
    public void add(Team obj) {

    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(int id) {

    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @Override
    public Team getOne(int id) {
        return null;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @Override
    public List<Team> getAll() {
        return null;
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
}
