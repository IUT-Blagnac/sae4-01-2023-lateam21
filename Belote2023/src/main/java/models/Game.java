package models;

/**
 * The type Game.
 */
public class Game {
	/**
	 * The Id match.
	 */
	private int idMatch;
	/**
	 * The Team 1.
	 */
	private int team1;
	/**
	 * The Team 2.
	 */
	private int team2;
	/**
	 * The Score 1.
	 */
	private int	score1;
	/**
	 * The Score 2.
	 */
	private int	score2;
	/**
	 * The Num round.
	 */
	private int numRound;
	/**
	 * The Has ended.
	 */
	private boolean hasEnded;

	/**
	 * Instantiates a new Game.
	 *
	 * @param _idGame   the id game
	 * @param _t1       the t 1
	 * @param _t2       the t 2
	 * @param _score1   the score 1
	 * @param _score2   the score 2
	 * @param _numRound the num round
	 * @param _hasEnded the has ended
	 */
	public Game(int _idGame, int _t1, int _t2, int _score1, int _score2, int _numRound, boolean _hasEnded){
			idMatch = _idGame;
			team1 = _t1;
			team2 = _t2;
			score1  = _score1;
			score2  = _score2;
			numRound = _numRound;
			hasEnded = _hasEnded;
	}

	/**
	 * Instantiates a new Game.
	 *
	 * @param _t1 the t 1
	 * @param _t2 the t 2
	 */
	public Game (int _t1, int _t2){
		this.team1 = _t1;
		this.team2 = _t2;
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
		return numRound;
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

	/**
	 * Is ended boolean.
	 *
	 * @return the boolean
	 */
	public boolean isEnded() {return hasEnded;}

}