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

    ArrayList<Integer> getAllIdTeams();

    void addTeam(Tournament t, int pos);

    void deleteTeam(Tournament t, int posTeam);

    void updatePlayersTeam(int posTeam, String pTeam1, String pTeam2, Tournament t);

}
