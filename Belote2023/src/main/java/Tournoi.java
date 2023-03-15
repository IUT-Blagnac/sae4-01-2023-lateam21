import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

public class Tournoi {
	String statuttnom;
	String nt;
	int    statut;
	int    id_tournoi;
	//int    nbtours;
	private Vector<Equipe> dataeq = null;
	private Vector<MatchM> datam  = null;
	private Vector<Integer>ideqs  = null; 
	Statement st;
		
	public Tournoi(String nt, Statement s){
		st = s;

		try {
			ResultSet rs = s.executeQuery("SELECT * FROM tournois WHERE nom_tournoi = '" + Tournoi.mysql_real_escape_string(nt) + "';");
			if(!rs.next()){
				return ;
			}
			this.statut = rs.getInt("statut");
			
			this.id_tournoi = rs.getInt("id_tournoi");
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur SQL: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		statuttnom = "Inconnu";
		switch(this.statut){
		//case 0:
		//	statuttnom = "Configuration du tournoi";
		//break;
		case 0:
			statuttnom = "Inscription des joueurs";
		break;
		case 1:
			statuttnom = "Génération des matchs";
		break;
		case 2:
			statuttnom = "Matchs en cours";
		break;
		case 3:
			statuttnom = "Terminé";
		break;
			
		}
		this.nt = nt;
		
	}

	public void majEquipes(){
		dataeq = new Vector<Equipe>();
		ideqs = new Vector<Integer>();
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + id_tournoi + " ORDER BY num_equipe;");
			while(rs.next()){
				dataeq.add(new Equipe(rs.getInt("id_equipe"),rs.getInt("num_equipe"), rs.getString("nom_j1"), rs.getString("nom_j2")));
				ideqs.add(rs.getInt("num_equipe"));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	public void majMatch(){
		datam = new Vector<MatchM>();
		try {
			ResultSet rs= st.executeQuery("SELECT * FROM matchs WHERE id_tournoi="+ id_tournoi + ";");
			while(rs.next()) datam.add(new MatchM(rs.getInt("id_match"),rs.getInt("equipe1"),rs.getInt("equipe2"), rs.getInt("score1"),rs.getInt("score2"),rs.getInt("num_tour"),rs.getString("termine") == "oui"));
			//public MatchM(int _idmatch,int _e1,int _e2,int _score1, int _score2, int _num_tour, boolean _termine)
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	public MatchM getMatch(int index){
		if(datam == null) majMatch();
		return datam.get(index);
	}
	public int getNbMatchs(){
		if(datam == null) majMatch();
		return datam.size();
	}
	public Equipe getEquipe(int index){
		if(dataeq == null) 
			majEquipes();
		return dataeq.get(index);
		
	}
	public int getNbEquipes(){
		if(dataeq == null) 
			majEquipes();
		return dataeq.size();
	}
	
	public int    getStatut(){
		return statut;
	}
	public String getNStatut(){
		return statuttnom;
	}
	public String getNom() {
		return nt;
	}
	public int getNbTours(){
		try {
			ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+id_tournoi+"; ");
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return -1;
		}
	}
	public void genererMatchs(){
		int nbt = 1;

		System.out.println("Nombre d'�quipes : " + getNbEquipes());
		System.out.println("Nombre de tours  : " + nbt);
		String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
		Vector<Vector<Match>> ms;
		ms = Tournoi.getMatchsToDo(getNbEquipes(), nbt);
		int z = 1;
		char v = ' ';
		for(Vector<Match> t :ms){
			for(Match m:t){
				req += v + "(NULL," + id_tournoi + ", " + z + ", "+  m.eq1 + ", " +  m.eq2 + ", 'non')";
				v = ',';
			}
			req += "\n";
			z++;
		}
		System.out.println(req);
		try{
			st.executeUpdate(req);
			st.executeUpdate("UPDATE tournois SET statut=2 WHERE id_tournoi=" + id_tournoi + ";");
			this.statut = 2;
		}catch(SQLException e){
			System.out.println("Erreur validation �quipes : " + e.getMessage());
		}
	}
	
	public boolean ajouterTour(){
		// Recherche du nombre de tours actuel
		int nbtoursav;
		if(getNbTours() >=  (getNbEquipes() -1) ) return false;
		System.out.println("Eq:" + getNbEquipes() + "  tours" + getNbTours());
		try {
			ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+id_tournoi+"; ");
			rs.next();
			nbtoursav = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return false;
		}
		System.out.println("Nombre de tours avant:" + nbtoursav);
		
		
		if(nbtoursav == 0){
			Vector<Match> ms;
			
			ms = Tournoi.getMatchsToDo(getNbEquipes(), nbtoursav+1).lastElement();
			
			String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
			char v = ' ';
			for(Match m:ms){
				req += v + "(NULL," + id_tournoi + ", " + (nbtoursav + 1) + ", "+  m.eq1 + ", " +  m.eq2 + ", 'non')";
				v = ',';
			}
			req += "\n";
		
			//System.out.println(req);
			try{
				st.executeUpdate(req);
			}catch(SQLException e){
				System.out.println("Erreur ajout tour : " + e.getMessage());
			}		
		}else{
			try {
				ResultSet rs;
				//rs = st.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2 AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 AND m.id_tournoi = id_tournoi )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + this.id_tournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + this.id_tournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
	
				rs = st.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + this.id_tournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + this.id_tournoi + ") GROUP BY equipe  ORDER BY matchs_gagnes DESC;");

				
				ArrayList<Integer> ordreeq= new ArrayList<Integer>();
				while(rs.next()){
					ordreeq.add(rs.getInt("equipe"));
					System.out.println(rs.getInt(1) +" _ " + rs.getString(2));
				}
				System.out.println("Taille"+ordreeq.size());
				int i;
				boolean fini;
				String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
				char v = ' ';
				while(ordreeq.size() > 1){
					System.out.println("Taille " + ordreeq.size());
					int j=0;
					while(j<ordreeq.size()) {
						System.out.println(ordreeq.get(j));
						j++;
					}
					i=1;
					do{
						rs = st.executeQuery("SELECT COUNT(*) FROM matchs m WHERE ( (m.equipe1 = " + ordreeq.get(0) + " AND m.equipe2 = " + ordreeq.get(i) + ") OR (m.equipe2 = " + ordreeq.get(0) + " AND m.equipe1 = " + ordreeq.get(i) + ")  )");  
						rs.next();
						if(rs.getInt(1) > 0){
							// Le match est d�j� jou�
							i++;
							fini = false;

						}else{ 
							fini = true;
							req += v + "(NULL," + id_tournoi + ", " + (nbtoursav + 1) + ", "+  ordreeq.get(0) + ", " +  ordreeq.get(i) + ", 'non')";
							System.out.println(ordreeq.get(0) + ", " +  ordreeq.get(i));
							ordreeq.remove(0);
							ordreeq.remove(i-1);
							v = ',';
						}
					}while(!fini);
				}
				System.out.println(req);
				st.executeUpdate(req);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return true;
	}
	public void supprimerTour(){
		int nbtoursav;
		try {
			ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+id_tournoi+"; ");
			rs.next();
			nbtoursav = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return ;
		}
		//if(tour != nbtoursav) return ;
		
		try {
			st.executeUpdate("DELETE FROM matchs WHERE id_tournoi="+ id_tournoi+" AND num_tour=" + nbtoursav);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur del tour : " + e.getMessage());
		}
	}
		
	public static int deleteTournoi(Statement s2, String nomtournoi){
		try {
			int idt;
			ResultSet rs = s2.executeQuery("SELECT id_tournoi FROM tournois WHERE nom_tournoi = '" + mysql_real_escape_string(nomtournoi) + "';");
			rs.next();
			idt = rs.getInt(1);
			rs.close();
			System.out.println("ID du tournoi � supprimer:" + idt);
			s2.executeUpdate("DELETE FROM matchs   WHERE id_tournoi = " + idt);
			s2.executeUpdate("DELETE FROM equipes  WHERE id_tournoi = " + idt);
			s2.executeUpdate("DELETE FROM tournois WHERE id_tournoi = " + idt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur suppression" + e.getMessage());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur inconnue");
		} 
		return 0;
	}
	public static int creerTournoi(Statement s2){
		String s = (String)JOptionPane.showInputDialog(
                null,
                "Entrez le nom du tournoi",
                "Nom du tournoi",
                JOptionPane.PLAIN_MESSAGE);
		
		
		if(s == null || s == ""){
			return 1;
		}else{
			try {
				s =  mysql_real_escape_string(s);
				if(s.length() < 3){
					JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Nom trop court.");
					return 2;					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(s == ""){
				JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Ne pas mettre de caract�res sp�ciaux ou accents dans le nom");
				return 2;
			}else{
				
				
				ResultSet rs;
				try {
					rs = s2.executeQuery("SELECT id_tournoi FROM tournois WHERE nom_tournoi = '" + s + "';");
					if(rs.next()){
						JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Un tournoi du m�me nom existe d�j�");
						return 2;							
					}
	
					System.out.println("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (NULL, 10, '"+s+"', 0)");
				s2.executeUpdate("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (NULL, 10, '"+s+"', 0)");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Erreur requete insertion nouveau tournoi:" + e.getMessage());
					
					//e.printStackTrace();
					
					
				}
				//s2.executeUpdate("INSERT INTO tournois (id")
				
			}
		}
		return 0;
	}
	
	public void ajouterEquipe(){
		int a_aj= this.dataeq.size()+1;
		for ( int i=1;i <= this.dataeq.size(); i++){
			if(!ideqs.contains(i)){
				a_aj=i;
				break;
			}
		}
		try {
			st.executeUpdate("INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES (NULL,"+a_aj+", "+id_tournoi + ",'\"Joueur 1\"', '\"Joueur 2\"');");
		    majEquipes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void majEquipe(int index){
		try {
			String req = "UPDATE equipes SET nom_j1 = '" + mysql_real_escape_string(getEquipe(index).eq1) + "', nom_j2 = '" + mysql_real_escape_string(getEquipe(index).eq2) + "' WHERE id_equipe = " + getEquipe(index).id + ";";
			System.out.println(req);
			st.executeUpdate(req);
		    majEquipes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void majMatch(int index){
		String termine = (getMatch(index).score1 > 0 || getMatch(index).score2 > 0) ? "oui":"non";
		System.out.println(termine);
		String req="UPDATE matchs SET equipe1='" + getMatch(index).eq1 + "', equipe2='" + getMatch(index).eq2 + "',  score1='" + getMatch(index).score1 + "',  score2='" +getMatch(index).score2 + "', termine='" + termine + "' WHERE id_match = " + getMatch(index).idmatch + ";";
		try {
			st.executeUpdate(req);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		majMatch();
	}
	public void supprimerEquipe(int ideq){
		try {
			int numeq;
			ResultSet rs = st.executeQuery("SELECT num_equipe FROM equipes WHERE id_equipe = " + ideq);
			rs.next();
			numeq = rs.getInt(1);
			rs.close();
			st.executeUpdate("DELETE FROM equipes WHERE id_tournoi = " + id_tournoi+ " AND id_equipe = " + ideq);
			st.executeUpdate("UPDATE equipes SET num_equipe = num_equipe - 1 WHERE id_tournoi = " + id_tournoi + " AND num_equipe > " + numeq);
		    majEquipes();
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
    public static String mysql_real_escape_string( String str) 
            throws Exception
      {
          if (str == null) {
              return null;
          }
                                      
          if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
              return str;
          }
              
          String clean_string = str;
          clean_string = clean_string.replaceAll("\\n","\\\\n");
          clean_string = clean_string.replaceAll("\\r", "\\\\r");
          clean_string = clean_string.replaceAll("\\t", "\\\\t");
          clean_string = clean_string.replaceAll("\\00", "\\\\0");
          clean_string = clean_string.replaceAll("'", "''");
          return clean_string;

      }
    
	public static Vector<Vector<Match>> getMatchsToDo(int nbJoueurs, int nbTours){
		if( nbTours  >= nbJoueurs){
			System.out.println("Erreur tours < equipes");
			return null;
		}
		
		int[]   tabJoueurs;
		if((nbJoueurs % 2) == 1){
			// Nombre impair de joueurs, on rajoute une �quipe fictive
			tabJoueurs   = new int[nbJoueurs+1];
			tabJoueurs[nbJoueurs] = -1;
			for(int z = 0; z < nbJoueurs;z++){
				tabJoueurs[z] = z+1;
			}
			nbJoueurs++;
		}else{
			tabJoueurs   = new int[nbJoueurs];
			for(int z = 0; z < nbJoueurs;z++){
				tabJoueurs[z] = z+1;
			}
		}
		
		boolean quitter;
		int     i, increment  = 1, temp;

		Vector<Vector<Match>> retour = new Vector<Vector<Match>>();
		
		Vector<Match> vm;
		
		for( int r = 1; r <= nbTours;r++){
			if(r > 1){
				temp = tabJoueurs[nbJoueurs - 2];
				for(i = (nbJoueurs - 2) ; i > 0; i--){
					tabJoueurs[i] = tabJoueurs[i-1];
				}
				tabJoueurs[0] = temp;
			}
			i       = 0;
			quitter = false;
			vm = new Vector<Match>();
			while(!quitter){
				if (tabJoueurs[i] == -1 || tabJoueurs[nbJoueurs - 1  - i] == -1){
					// Nombre impair de joueur, le joueur n'a pas d'adversaire
				}else{
					vm.add(new Match(tabJoueurs[i], tabJoueurs[nbJoueurs - 1  - i]));
				}

		        i+= increment;
				if(i >= nbJoueurs / 2){
					if(increment == 1){
						quitter = true;
						break;
					}else{
						increment = -2;
						if( i > nbJoueurs / 2){
							i = ((i > nbJoueurs / 2) ? i - 3 : --i) ;
						}
						if ((i < 1) && (increment == -2)){
							quitter = true;
							break;
						}
					}
				}
			}
			retour.add(vm);
		}
		return retour;
	}  

	static class Match{
		public int eq1,eq2;
		public Match(int e1,int e2){
			eq1 = e1;
			eq2 = e2;
		}
		public String toString(){
			if(eq1 < eq2){
				return "  " + eq1 + " contre " + eq2;
			}else{
				return "  " + eq2 + " contre " + eq1;
			}
		}
	}


	

}
