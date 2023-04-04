package IDAO;

import models.Team;
import models.Tournament;

import java.util.ArrayList;

/**
 * The interface Team idao.
 */
public interface TeamIDAO extends IDAO<Team>{
    /**
     * Gets teams tournament.
     *
     * @param t the t
     * @return the teams tournament
     */
    public ArrayList<Team> getTeamsTournament(Tournament t);

    /**
     * Gets id teams tournament.
     *
     * @param t the t
     * @return the id teams tournament
     */
    public ArrayList<Integer> getIdTeamsTournament(Tournament t);
}
