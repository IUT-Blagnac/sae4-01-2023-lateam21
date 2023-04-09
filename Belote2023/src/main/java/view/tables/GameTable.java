package view.tables;

import service.GameService;
import resources.CONSTANTS;
import models.Game;
import models.Tournament;

import javax.swing.table.AbstractTableModel;

/**
 * The type Game table.
 */
public class GameTable extends AbstractTableModel {

    /**
     * The Tournament.
     */
    private Tournament tournament;
    /**
     * The GameService object.
     */
    private GameService gS = new GameService();

    /**
     * Instantiates a new Game table.
     *
     * @param to the to
     */
    public GameTable(Tournament to){
        this.tournament = to;
    }

    /**
     * Gets row count.
     *
     * @return the row count
     */
    @Override
    public int getRowCount() {
        if(tournament == null)return 0;
        gS.updateGames(tournament);
        return gS.getNbGames(tournament);
    }

    /**
     * Gets column count.
     *
     * @return the column count
     */
    @Override
    public int getColumnCount() {
        return 5;
    }

    /**
     * Gets column name.
     *
     * @param col the col
     * @return the column name
     */
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

    /**
     * Gets value at.
     *
     * @param arg0 the arg 0
     * @param arg1 the arg 1
     * @return the value at
     */
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

    /**
     * Is cell editable boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public boolean isCellEditable(int x, int y){
        return y > 2 && gS.getGame(x, tournament).getNumRounds() == gS.getNbRounds(tournament);
    }

    /**
     * Sets value at.
     *
     * @param aValue      the a value
     * @param rowIndex    the row index
     * @param columnIndex the column index
     */
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

    /**
     * Sets tournament.
     *
     * @param to the to
     */
    public void setTournament(Tournament to) {
        this.tournament = to;
        fireTableDataChanged();
    }
}
