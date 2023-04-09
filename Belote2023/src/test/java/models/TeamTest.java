package models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Team test.
 */
public class TeamTest {

    /**
     * Can create team.
     */
    @Test
    public void canCreateTeam(){
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team != null);
    }

    /**
     * Gets id.
     */
    @Test
    public void getId() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getId()==10);
    }

    /**
     * Gets num.
     */
    @Test
    public void getNum() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getNum()==1);
    }

    /**
     * Gets team 1.
     */
    @Test
    public void getTeam1() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getTeam1().equals("Jean"));
    }

    /**
     * Gets team 2.
     */
    @Test
    public void getTeam2() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getTeam2().equals("Joueur2"));
    }

    /**
     * Sets team 1.
     */
    @Test
    public void setTeam1() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        team.setTeam1("Random");
        assertTrue(!team.getTeam1().equals("Jean") && team.getTeam1().equals("Random"));
    }

    /**
     * Sets team 2.
     */
    @Test
    public void setTeam2() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        team.setTeam2("Random");
        assertTrue(!team.getTeam2().equals("Joueur2") && team.getTeam2().equals("Random"));
    }
}