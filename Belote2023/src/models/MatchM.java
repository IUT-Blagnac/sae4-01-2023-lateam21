package models;

public class MatchM{
		private int idmatch,eq1,eq2,score1,score2,num_tour;
		private boolean termine;
		 
		public MatchM(int _idmatch,int _e1,int _e2,int _score1, int _score2, int _num_tour, boolean _termine){
			idmatch = _idmatch;
			eq1     = _e1;
			eq2     = _e2;
			score1  = _score1;
			score2  = _score2;
			num_tour= _num_tour;
			termine = _termine;
		}
		public String toString(){
			if(eq1 < eq2){
				return "  " + eq1 + " contre " + eq2;
			}else{
				return "  " + eq2 + " contre " + eq1;
			}
		}

	//getters setters
	public int getEq1() {
		return eq1;
	}
	public int getEq2() {
		return eq2;
	}
	public int getIdmatch() {
		return idmatch;
	}
	public int getNum_tour() {
		return num_tour;
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
}