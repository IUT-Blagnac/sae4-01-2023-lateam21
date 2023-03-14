 public class MatchM{
		public int idmatch,eq1,eq2,score1,score2,num_tour;
		public boolean termine;
		 
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
 }