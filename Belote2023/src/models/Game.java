package models;

/**
 * The type Game.
 */
public class Game {
	/**
	 * The Id match.
	 */
	private final int idMatch;
    private final int /**
	 * The Team 1.
	 */
	team1;
    private final int /**
	 * The Team 2.
	 */
	team2;
    private int /**
	 * The Score 1.
	 */
	score1;
    private int /**
	 * The Score 2.
	 */
	score2;
    private final int /**
	 * The Num rounds.
	 */
	numRounds;
	/**
	 * The Has ended.
	 */
	private final boolean hasEnded;

	/**
	 * Instantiates a new Game.
	 *
	 * @param _idGame    the id game
	 * @param _t1        the t 1
	 * @param _t2        the t 2
	 * @param _score1    the score 1
	 * @param _score2    the score 2
	 * @param _numRounds the num rounds
	 * @param _hasEnded  the has ended
	 */
	public Game(int _idGame, int _t1, int _t2, int _score1, int _score2, int _numRounds, boolean _hasEnded){
			idMatch = _idGame;
			team1 = _t1;
			team2 = _t2;
			score1  = _score1;
			score2  = _score2;
			numRounds = _numRounds;
			hasEnded = _hasEnded;
		}

	/**
	 * To string string.
	 *
	 * @return the string
	 */
	public String toString(){
			if(team1 < team2){
				return "  " + team1 + " contre " + team2;
			}else{
				return "  " + team2 + " contre " + team1;
			}
		}

	/**
	 * Gets team 1.
	 *
	 * @return the team 1
	 */
//getters setters
	public int getTeam1() {
		return team1;
	}

	/**
	 * Gets team 2.
	 *
	 * @return the team 2
	 */
	public int getTeam2() {
		return team2;
	}

	/**
	 * Gets id match.
	 *
	 * @return the id match
	 */
	public int getIdMatch() {
		return idMatch;
	}

	/**
	 * Gets num rounds.
	 *
	 * @return the num rounds
	 */
	public int getNumRounds() {
		return numRounds;
	}

	/**
	 * Gets score 1.
	 *
	 * @return the score 1
	 */
	public int getScore1() {
		return score1;
	}

	/**
	 * Gets score 2.
	 *
	 * @return the score 2
	 */
	public int getScore2() {
		return score2;
	}

	/**
	 * Sets score 1.
	 *
	 * @param sco the sco
	 */
	public void setScore1(int sco) {
		this.score1 = sco;
	}

	/**
	 * Sets score 2.
	 *
	 * @param sco the sco
	 */
	public void setScore2(int sco) {
		this.score2 = sco;
	}
}