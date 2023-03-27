package IDAO;

import models.Tournoi;


public interface TournoiIDAO extends IDAO<Tournoi> {
    public void updateTournoi(Tournoi t);
}
