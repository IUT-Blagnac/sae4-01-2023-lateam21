package models;

/**
 * The type Tournament.
 */
public class Tournament {
	/**
	 * The Status.
	 */
//attributs de la classe tournoi
	private int status;
	/**
	 * The Id tournament.
	 */
	private int idTournament;
	/**
	 * The Name tournament.
	 */
	private String nameTournament;
	/**
	 * The Nb games.
	 */
	private int nbGames;

	/**
	 * The Status name.
	 */
	private String statusName;
	//int    nbtours;

	/**
	 * Instantiates a new Tournament.
	 *
	 * @param name the name
	 */
	public Tournament(String name){
		this.idTournament = 0;
		this.nbGames = 0;
		this.nameTournament = name;
		this.status = 0;

		statusName = CONSTANTS.NULL;
		switch (this.status) {
			//case 0:
			//	statuttnom = "Configuration du tournoi";
			//break;
			case 0 -> statusName = CONSTANTS.PLAYERS_SIGNED;
			case 1 -> statusName = CONSTANTS.GAMES_GENERATED;
			case 2 -> statusName = CONSTANTS.GAMES_IN_PROGRESS;
			case 3 -> statusName = CONSTANTS.ENDED;
		}
		/*st = s;

		try {
			ResultSet rs = s.executeQuery("SELECT * FROM tournois WHERE nom_tournoi = '" + Tournament.mysql_real_escape_string(nt) + "';");
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
		this.NomTournoi = nt;*/
	}

<<<<<<< HEAD
	/**
	 * Instantiates a new Tournament.
	 *
	 * @param name    the name
	 * @param nbGames the nb games
	 * @param satut   the satut
	 */
	public Tournament(String name, int nbGames, int satut){
		this.idTournament = 0;
=======
	public Tournament(int id, String name, int nbGames, int satut){
		this.idTournament = id;
>>>>>>> b7aafcd5b2f9eda817af8194475b4b529cfad6af
		this.nbGames = nbGames;
		this.nameTournament = name;
		this.status = satut;

		switch (this.status) {
			//case 0:
			//	statuttnom = "Configuration du tournoi";
			//break;
			case 0 -> statusName = CONSTANTS.PLAYERS_SIGNED;
			case 1 -> statusName = CONSTANTS.GAMES_GENERATED;
			case 2 -> statusName = CONSTANTS.GAMES_IN_PROGRESS;
			case 3 -> statusName = CONSTANTS.ENDED;
		}
	}


	/**
	 * Get status int.
	 *
	 * @return the int
	 */
	public int getStatus(){
		return status;
	}

	/**
	 * Get status name string.
	 *
	 * @return the string
	 */
	public String getStatusName(){
		return statusName;
	}

	/**
	 * Gets nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nameTournament;
	}

	/**
	 * Gets id tournament.
	 *
	 * @return the id tournament
	 */
	public int getIdTournament() {
		return idTournament;
	}

	/**
	 * Sets id tournament.
	 *
	 * @param idTournament the id tournament
	 */
	public void setIdTournament(int idTournament) {
		this.idTournament = idTournament;
	}

	/**
	 * Sets name tournament.
	 *
	 * @param nameTournament the name tournament
	 */
	public void setNameTournament(String nameTournament) {
		this.nameTournament = nameTournament;
	}

	/**
	 * Sets nb games.
	 *
	 * @param nbGames the nb games
	 */
	public void setNbGames(int nbGames) {
		this.nbGames = nbGames;
	}

	/**
	 * Sets status.
	 *
	 * @param status the status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
