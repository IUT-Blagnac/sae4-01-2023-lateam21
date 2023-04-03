package IDAO;

import models.Equipe;
import models.Tournoi;

import java.util.ArrayList;

public interface EquipeIDAO extends IDAO<Equipe>{
    public ArrayList<Equipe> getEquipesTournoi(Tournoi t);

    public ArrayList<Integer> getIdEquipesTournoi(Tournoi t);
}
