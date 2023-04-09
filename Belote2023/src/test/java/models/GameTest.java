package models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The type Game test.
 */
public class GameTest {

    /**
     * Can create game.
     */
    @Test
    public void canCreateGame(){
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        assertTrue(game != null);
    }

    /**
     * Can create game 2.
     */
    @Test
    public void canCreateGame2(){
        Game game2 = new Game(1,2);
        assertTrue(game2 != null);
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertTrue(game.toString().equals("  2 contre 6"));
        assertTrue(game2.toString().equals("  1 contre 2"));
    }

    /**
     * Gets team 1.
     */
    @Test
    public void getTeam1() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertTrue(game.getTeam1()==6);
        assertTrue(game2.getTeam1()==1);
    }

    /**
     * Gets team 2.
     */
    @Test
    public void getTeam2() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertTrue(game.getTeam2()==2);
        assertTrue(game2.getTeam2()==2);
    }

    /**
     * Gets id match.
     */
    @Test
    public void getIdMatch() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertTrue(game.getIdMatch()==10);
        assertTrue(game2.getIdMatch()==0);
    }

    /**
     * Gets num rounds.
     */
    @Test
    public void getNumRounds() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertTrue(game.getNumRounds()==3);
        assertTrue(game2.getNumRounds()==0);
    }

    /**
     * Is ended.
     */
    @Test
    public void isEnded() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertFalse(game.isEnded());
        assertTrue(game2.getIdMatch()==0);
    }

    /**
     * Gets score 1.
     */
    @Test
    public void getScore1() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertTrue(game.getScore1()==120);
        assertTrue(game2.getScore1()==0);
    }

    /**
     * Gets score 2.
     */
    @Test
    public void getScore2() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        assertTrue(game.getScore2()==115);
        assertTrue(game2.getScore2()==0);
    }

    /**
     * Sets score 1.
     */
    @Test
    public void setScore1() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        game.setScore1(250);
        game2.setScore1(100);
        assertTrue(game.getScore1()!=120 && game.getScore1()==250);
        assertTrue(game2.getScore1()!=0 && game2.getScore1()==100);
    }

    /**
     * Sets score 2.
     */
    @Test
    public void setScore2() {
        Game game = new Game(10, 6,2, 120, 115, 3, false);
        Game game2 = new Game(1,2);
        game.setScore2(250);
        game2.setScore2(100);
        assertTrue(game.getScore2()!=115 && game.getScore2()==250);
        assertTrue(game2.getScore2()!=0 && game2.getScore2()==100);
    }
}