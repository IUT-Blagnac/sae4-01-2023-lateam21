package IDAO;

import models.Team;
import models.Tournament;

import java.util.ArrayList;

/**
 * The interface Team idao.
 */
public interface TeamIDAO{
    /**
     * Gets teams tournament.
     *
     * @param t the t
     * @return the teams tournament
     */
    ArrayList<Team> getTeamsTournament(Tournament t);

    /**
     * Gets all id teams.
     *
     * @return the all id teams
     */
    ArrayList<Integer> getAllIdTeams();

    /**
     * Add team.
     *
     * @param t   the t
     * @param pos the pos
     */
    void addTeam(Tournament t, int pos);

    /**
     * Delete team.
     *
     * @param t       the t
     * @param posTeam the pos team
     */
    void deleteTeam(Tournament t, int posTeam);

    /**
     * Update players team.
     *
     * @param posTeam the pos team
     * @param pTeam1  the p team 1
     * @param pTeam2  the p team 2
     * @param t       the t
     */
    void updatePlayersTeam(int posTeam, String pTeam1, String pTeam2, Tournament t);

}
