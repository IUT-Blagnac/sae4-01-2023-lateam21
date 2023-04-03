package IDAO;

import models.Equipe;
import models.Tournoi;

import java.util.List;


public interface TournoiIDAO extends IDAO<Tournoi> {

    void deleteTournoi(String nomT);

    Tournoi getOne(String nomT);

    public void updateTournoi(Tournoi t);

    public int getNbTours(Tournoi t);

    public int getNbMatchs(Tournoi t);

    public int getNbMatchsFini(Tournoi t);

    public List<String> getAllNames();

    public void creerTournoi(String nom);

    public List<Equipe> getEquipesTournoi(Tournoi t);
}
