package IDAO;

import models.Team;
import models.Tournament;

import java.util.ArrayList;

/**
 * The interface Team idao.
 */
public interface TeamIDAO extends IDAO<Team>{
    Team getOneTeamFromTournament(int idTeam, Tournament t);
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

    ArrayList<Integer> getIdTeamsTournament(Tournament t);

<<<<<<< HEAD
    public ArrayList<Integer> getAllIdTeams();

    public void addTeam(Tournament t, int pos);
=======
    void addTeam(Tournament t, int pos);
>>>>>>> 8d1f87106bff3f2f4f34d09ac4e61bef91987daa

    void deleteTeam(Tournament t, int posTeam);

<<<<<<< HEAD
    public void updatePlayersTeam(int posTeam, String pTeam1, String pTeam2, Tournament t);
=======
    void updatePlayersTeam(int posTeam, String pTeam1, String pTeam2, Tournament t);
>>>>>>> 8d1f87106bff3f2f4f34d09ac4e61bef91987daa

}
