package Service;

import IDAO.TeamIDAOImpl;
import models.Team;
import models.Tournament;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The type Team service.
 */
public class TeamService {
    /**
     * The Date team.
     */
    private ArrayList<Team> dateTeam = null;
    /**
     * The Id team.
     */
    private ArrayList<Integer> idTeam = null;
    /**
     * The St.
     */
    Statement st;
    /**
     * The Tournament.
     */
    Tournament tournament;
    /**
     * The Idao team.
     */
    static TeamIDAOImpl idaoTeam = TeamIDAOImpl.getInstance();

    /**
     * Instantiates a new Team service.
     */
    public TeamService(){super();}

    /**
     * Get team team.
     *
     * @param index the index
     * @param t     the t
     * @return the team
     */
    public Team getTeam(int index, Tournament t){
        if(dateTeam == null)
            updateTeams(t);
        return dateTeam.get(index);

    }

    /**
     * Get nb teams int.
     *
     * @param t the t
     * @return the int
     */
    public int getNbTeams(Tournament t){
        if(dateTeam == null)
            updateTeams(t);
        return dateTeam.size();
    }

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

    /**
     * Add team.
     *
     * @param t the t
     */
    public void addTeam(Tournament t){
        int a_aj= this.dateTeam.size()+1;
        for (int i = 1; i <= this.dateTeam.size(); i++){
            if(!idTeam.contains(i)){
                a_aj=i;
                break;
            }
        }
        try {

            st.executeUpdate("INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES (NULL,"+a_aj+", "+ t.getIdTournament() + ",'\"Joueur 1\"', '\"Joueur 2\"');");
            updateTeams(t);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
