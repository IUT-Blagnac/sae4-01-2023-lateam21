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
    private ArrayList<Team> dataTeamTournament;
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
        dataTeamTournament = new ArrayList<Team>();
        idTeam = new ArrayList<Integer>();
        dataTeamTournament = idaoTeam.getTeamsTournament(tournament);
        idTeam = idaoTeam.getAllIdTeams();
    }

    /**
     * Update teams.
     * @param t     the t
     */
    public void updatePlayersTeams(Team te, Tournament t){
        int idTeam = te.getId();
        String pT1 = tools.mysql_real_escape_string(te.getTeam1());
        String pT2 = tools.mysql_real_escape_string(te.getTeam2());
        idaoTeam.updatePlayersTeam(idTeam, pT1, pT2, t);
        updateTeams(t);
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
        return dataTeamTournament.get(index);

    }

    /**
     * Get nb teams int.
     *
     * @param t the t
     * @return the int
     */
    public int getNbTeams(Tournament t){
        updateTeams(t);
        return dataTeamTournament.size();
    }


    /**
     * Add team.
     *
     * @param t the t
     */
    public void addTeam(Tournament t){
        int posEquipe=0; //initialize position of new team in the database
        for(int i : idTeam){
            if(idTeam.contains(posEquipe)){
                posEquipe++;
            }
        }
        idaoTeam.addTeam(t,posEquipe); //adding team in database
        updateTeams(t); //update teams in tournament
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
