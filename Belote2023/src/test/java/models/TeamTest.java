package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class TeamTest {

    @Test
    public void canCreateTeam(){
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team != null);
    }

    @Test
    public void getId() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getId()==10);
    }

    @Test
    public void getNum() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getNum()==1);
    }

    @Test
    public void getTeam1() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getTeam1().equals("Jean"));
    }

    @Test
    public void getTeam2() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        assertTrue(team.getTeam2().equals("Joueur2"));
    }

    @Test
    public void setTeam1() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        team.setTeam1("Random");
        assertTrue(!team.getTeam1().equals("Jean") && team.getTeam1().equals("Random"));
    }

    @Test
    public void setTeam2() {
        Team team = new Team(10, 1, "Jean", "Joueur2");
        team.setTeam2("Random");
        assertTrue(!team.getTeam2().equals("Joueur2") && team.getTeam2().equals("Random"));
    }
}