package IDAO;

import models.Team;
import models.Tournament;

import java.util.ArrayList;

/**
 * The interface Team idao.
 */
public interface TeamIDAO extends IDAO<Team>{
    public Team getOneTeamFromTournament(int idTeam, Tournament t);
    /**
     * Gets teams tournament.
     *
     * @param t the t
     * @return the teams tournament
     */
    ArrayList<Team> getTeamsTournament(Tournament t);

    /**
     * Gets id teams tournament.
     *
     * @param t the t
     * @return the id teams tournament
     */

    public ArrayList<Integer> getIdTeamsTournament(Tournament t);

    public ArrayList<Integer> getAllIdTeams();

    public void addTeam(Tournament t, int pos);

    public void deleteTeam(Tournament t, int posTeam);

    public void updatePlayersTeam(int posTeam, String pTeam1, String pTeam2, Tournament t);

}
