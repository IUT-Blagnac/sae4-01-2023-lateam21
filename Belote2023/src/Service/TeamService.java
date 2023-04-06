package Service;

import IDAO.TeamIDAOImpl;
import models.Team;
import models.Tournament;
import resources.Tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The type Team service.
 */
public class TeamService {
    /**
     * The Data team.
     */
    private ArrayList<Team> dataTeam;
    /**
     * The Id team.
     */
    private ArrayList<Integer> idTeam;
    /**
     * The St.
     */
    Statement st;
    /**
     * The Tournament.
     */
    Tournament tournament;
    private Tools tools;
    /**
     * The Idao team.
     */
    static TeamIDAOImpl idaoTeam = TeamIDAOImpl.getInstance();

    /**
     * Instantiates a new Team service.
     */
    public TeamService(){super();}

    /**
     * Update teams.
     *
     * @param tournament the tournament
     */
    public void updateTeams(Tournament tournament){
        dataTeam = new ArrayList<Team>();
        idTeam = new ArrayList<Integer>();
        dataTeam = idaoTeam.getTeamsTournament(tournament);
        idTeam = idaoTeam.getIdTeamsTournament(tournament);
    }

    /**
     * Update teams.
     *
     * @param index the index
     * @param t     the t
     */
    public void updatePlayersTeams(int index, Tournament t){
        try {
            String req = "UPDATE equipes SET nom_j1 = '" + tools.mysql_real_escape_string(getTeam(index, t).getTeam1()) + "', nom_j2 = '" + tools.mysql_real_escape_string(getTeam(index, t).getTeam2()) + "' WHERE id_equipe = " + getTeam(index, t).getId() + ";";
            System.out.println(req);
            st.executeUpdate(req);
            updateTeams(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * Get team team.
     *
     * @param index the index
     * @param t     the t
     * @return the team
     */
    public Team getTeam(int index, Tournament t){
        updateTeams(t);
        return dataTeam.get(index);

    }

    /**
     * Get nb teams int.
     *
     * @param t the t
     * @return the int
     */
    public int getNbTeams(Tournament t){
        updateTeams(t);
        return dataTeam.size();
    }

<<<<<<< HEAD
    /**
     * Update teams.
     *
     * @param t the tournament
     */
    public void updateTeams(Tournament t){
        dateTeam = new ArrayList<>();
        idTeam = new ArrayList<>();
        dateTeam = idaoTeam.getTeamsTournament(t);
        idTeam = idaoTeam.getIdTeamsTournament(t);
    }
=======
>>>>>>> b7aafcd5b2f9eda817af8194475b4b529cfad6af

    /**
     * Add team.
     *
     * @param t the t
     */
    public void addTeam(Tournament t){
        int posEquipe=0; //initialize position of new team in the database
        for (int i = 1; i <= this.dataTeam.size(); i++){//loop searching and giving position to new team in database
            if(!idTeam.contains(i)){
                posEquipe=i;
                break;
            }
        }
<<<<<<< HEAD
        try {

            st.executeUpdate("INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES (NULL,"+a_aj+", "+ t.getIdTournament() + ",'\"Joueur 1\"', '\"Joueur 2\"');");
            updateTeams(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
=======
        idaoTeam.addTeam(t,posEquipe); //adding team in database
        updateTeams(t); //update teams in tournament
>>>>>>> b7aafcd5b2f9eda817af8194475b4b529cfad6af
    }

    /**
     * Delete team.
     *
     * @param idTeam the idTeam
     * @param t    the t
     */
    public void deleteTeam(int idTeam, Tournament t){
        idaoTeam.deleteTeam(t, idTeam);
        updateTeams(t);
    }

}
