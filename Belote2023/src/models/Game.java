package models;

public class Game {
	private int idMatch;
    private int team1;
    private int team2;
    private int	score1;
    private int	score2;
    private int numRound;
	private boolean hasEnded;

	public Game(int _idGame, int _t1, int _t2, int _score1, int _score2, int _numRound, boolean _hasEnded){
			idMatch = _idGame;
			team1 = _t1;
			team2 = _t2;
			score1  = _score1;
			score2  = _score2;
			numRound = _numRound;
			hasEnded = _hasEnded;
	}
	public Game (int _t1, int _t2){
		this.team1 = _t1;
		this.team2 = _t2;
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
		return numRound;
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