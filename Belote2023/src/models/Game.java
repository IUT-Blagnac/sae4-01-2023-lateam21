package models;

public class Game {
	private final int idMatch;
    private final int
			team1;
    private final int
			team2;
    private int
			score1;
    private int
			score2;
    private final int
			numRounds;
	private final boolean hasEnded;

	public Game(int _idGame, int _t1, int _t2, int _score1, int _score2, int _numRounds, boolean _hasEnded){
			idMatch = _idGame;
			team1 = _t1;
			team2 = _t2;
			score1  = _score1;
			score2  = _score2;
			numRounds = _numRounds;
			hasEnded = _hasEnded;
		}

	public String toString(){
			if(team1 < team2){
				return "  " + team1 + " contre " + team2;
			}else{
				return "  " + team2 + " contre " + team1;
			}
		}

	//getters setters
	public int getTeam1() {
		return team1;
	}

	public int getTeam2() {
		return team2;
	}

	public int getIdMatch() {
		return idMatch;
	}

	public int getNumRounds() {
		return numRounds;
	}

	public int getScore1() {
		return score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore1(int sco) {
		this.score1 = sco;
	}

	public void setScore2(int sco) {
		this.score2 = sco;
	}

	public boolean isEnded() {
		return hasEnded;
	}
}