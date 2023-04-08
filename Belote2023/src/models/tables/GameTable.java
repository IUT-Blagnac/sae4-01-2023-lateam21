package models.tables;

import Service.GameService;
import models.CONSTANTS;
import models.Game;
import models.Tournament;

import javax.swing.table.AbstractTableModel;

public class GameTable extends AbstractTableModel {

    private Tournament tournament;
    private GameService gS = new GameService();

    public GameTable(Tournament to){
        this.tournament = to;
    }

    @Override
    public int getRowCount() {
        if(tournament == null)return 0;
        gS.updateGames(tournament);
        return gS.getNbGames(tournament);
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    public String getColumnName(int col) {
        if(col == 0){
            return CONSTANTS.ROUND;
        }else if(col == 1){
            return CONSTANTS.TEAM_1;
        }else if(col == 2){
            return CONSTANTS.TEAM_2;
        }else if(col == 3){
            return CONSTANTS.SCORE_TEAM_1;
        }else if(col == 4){
            return CONSTANTS.SCORE_TEAM_2;
        }else{
            return CONSTANTS.MISSING;
        }
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        return switch (arg1) {
            case 0 -> gS.getGame(arg0, tournament).getNumRounds();
            case 1 -> gS.getGame(arg0, tournament).getTeam1();
            case 2 -> gS.getGame(arg0, tournament).getTeam2();
            case 3 -> gS.getGame(arg0, tournament).getScore1();
            case 4 -> gS.getGame(arg0, tournament).getScore2();
            default -> null;
        };
    }

    public boolean isCellEditable(int x, int y){
        return y > 2 && gS.getGame(x, tournament).getNumRounds() == gS.getNbRounds(tournament);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        gS.updateGames(tournament);
        Game m = gS.getGame(rowIndex, tournament);
        switch (columnIndex) {
            case 3:
                try {
                    int sco = Integer.parseInt((String) aValue);
                    m.setScore1(sco);
                    gS.updateGame(m, tournament);
                    break;
                } catch (Exception e) {
                    return;
                }
            case 4:
                try {
                    int sco = Integer.parseInt((String) aValue);
                    m.setScore2(sco);
                    gS.updateGame(m, tournament);
                    break;
                } catch (Exception e) {
                    return;
                }
            default:
                break;
        }
        fireTableDataChanged();
    }

    public void setTournament(Tournament to) {
        this.tournament = to;
        fireTableDataChanged();
    }
}
