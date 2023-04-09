package models;

/**
 * The type Team.
 */
public 	class Team {
	/**
	 * Instantiates a new Team.
	 *
	 * @param _id    the id
	 * @param _num   the num
	 * @param _team1 the team 1
	 * @param _team2 the team 2
	 */
	public Team(int _id, int _num, String _team1, String _team2){
		id = _id;
		num = _num;
		team1 = _team1;
		team2 = _team2;
	}

	/**
	 * The Id.
	 */
	private final int id;
	/**
	 * The Num.
	 */
	private final int num;
	/**
	 * The Team 1.
	 */
	private String team1;
	/**
	 * The Team 2.
	 */
	private String team2;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets num.
	 *
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Gets team 1.
	 *
	 * @return the team 1
	 */
	public String getTeam1() {
		return team1;
	}

	/**
	 * Gets team 2.
	 *
	 * @return the team 2
	 */
	public String getTeam2() {
		return team2;
	}

	/**
	 * Sets team 1.
	 *
	 * @param team1 the team 1
	 */
	public void setTeam1(String team1) {this.team1 = team1;}

	/**
	 * Sets team 2.
	 *
	 * @param team2 the team 2
	 */
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
}
