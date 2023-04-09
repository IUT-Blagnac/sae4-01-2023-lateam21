package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class TournamentTest {

    @Test
    public void canCreateTournament(){
        Tournament to = new Tournament("Bonjour");
        assertTrue(to != null);
    }

    @Test
    public void canCreateTournament2(){
        Tournament to = new Tournament(10, "Bonsoir", 15, 2);
        assertTrue(to != null);
    }

    @Test
    public void getStatus() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        assertTrue(to1.getStatus()==0);
        assertTrue(to2.getStatus()==2);
    }

    @Test
    public void getStatusName() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        assertTrue(to1.getStatusName().equals(CONSTANTS.PLAYERS_SIGNED));
        assertTrue(to2.getStatusName().equals(CONSTANTS.GAMES_IN_PROGRESS));
    }

    @Test
    public void getNom() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        assertTrue(to1.getName().equals("Bonjour"));
        assertTrue(to2.getName().equals("Bonsoir"));
    }

    @Test
    public void getIdTournament() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        assertTrue(to1.getIdTournament()==0);
        assertTrue(to2.getIdTournament()==10);
    }

    @Test
    public void setIdTournament() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        to1.setIdTournament(12);
        to2.setIdTournament(20);
        assertTrue(to1.getIdTournament()!=0 && to1.getIdTournament()==12);
        assertTrue(to2.getIdTournament()!=10 && to2.getIdTournament()==20);
    }

    @Test
    public void setNameTournament() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        to1.setNameTournament("Bleu");
        to2.setNameTournament("Rouge");
        assertTrue(!to1.getName().equals("Bonjour") && to1.getName().equals("Bleu"));
        assertTrue(!to2.getName().equals("Bonsoir") && to2.getName().equals("Rouge"));
    }

    @Test
    public void setNbGames() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        to1.setNbGames(10);
        to2.setNbGames(30);
        assertTrue(to1.getNbGames()!=0 && to1.getNbGames()==10);
        assertTrue(to2.getNbGames()!=10 && to2.getNbGames()==30);
    }

    @Test
    public void setStatus() {
        Tournament to1 = new Tournament("Bonjour");
        Tournament to2 = new Tournament(10, "Bonsoir", 15, 2);
        to1.setStatus(1);
        to2.setStatus(3);
        assertTrue(to1.getStatus()!=0 && to1.getStatus()==1);
        assertTrue(!to1.getStatusName().equals(CONSTANTS.PLAYERS_SIGNED) && to1.getStatusName().equals(CONSTANTS.GAMES_GENERATED));
        assertTrue(to2.getStatus()!=2 && to2.getStatus()==3);
        assertTrue((!to2.getStatusName().equals(CONSTANTS.GAMES_IN_PROGRESS) && to2.getStatusName().equals(CONSTANTS.ENDED)));
    }
}