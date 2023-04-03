package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import IDAO.TournoiIDAOImpl;

public class Tournoi {
	//attributs de la classe tournoi
	private int    statut;
	private int    id_tournoi;
	private String NomTournoi;
	private int nbMatchs;

	private String statuttnom;
	//int    nbtours;
		
	public Tournoi(String nt){
		this.id_tournoi = 0;
		this.nbMatchs = 0;
		this.NomTournoi = nt;
		this.statut = 0;

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
		/*st = s;

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
		this.NomTournoi = nt;*/
	}


	public int    getStatut(){
		return statut;
	}
	public String getNStatut(){
		return statuttnom;
	}
	public String getNom() {
		return NomTournoi;
	}
	public int getId_tournoi() {
		return id_tournoi;
	}
	public void setId_tournoi(int id_tournoi) {
		this.id_tournoi = id_tournoi;
	}
	public void setNomTournoi(String nomTournoi) {
		NomTournoi = nomTournoi;
	}
	public void setNbMatchs(int nbMatchs) {
		this.nbMatchs = nbMatchs;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}

}
