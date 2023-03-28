package IDAO;

import models.Tournoi;


public interface TournoiIDAO extends IDAO<Tournoi> {

    void deleteTournoi(String nomT);

    Tournoi getOne(String nomT);

    public void updateTournoi(Tournoi t);

    public int getNbTours(Tournoi t);
}
