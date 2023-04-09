package view.tables;


import resources.CONSTANTS;
import models.Team;
import models.Tournament;
import service.TeamService;

import javax.swing.table.AbstractTableModel;

/**
 * The type Team table.
 */
public class TeamTable extends AbstractTableModel {
    /**
     * The Tournament.
     */
    private Tournament tournament;
    /**
     * The TeamService object.
     */
    private final TeamService teS = new TeamService();

    /**
     * Instantiates a new Team table.
     *
     * @param to the to
     */
    public TeamTable(Tournament to) {
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
        return teS.getNbTeams(tournament);
    }

    /**
     * Gets column count.
     *
     * @return the column count
     */
    @Override
    public int getColumnCount() {
        return 3;
    }

    /**
     * Gets column name.
     *
     * @param col the col
     * @return the column name
     */
    public String getColumnName(int col) {
        if(col == 0){
            return CONSTANTS.TEAM_NUMBER;
        }else if(col == 1){
            return CONSTANTS.PLAYER_1;
        }else if(col == 2){
            return CONSTANTS.PLAYER_2;
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
            case 0 -> teS.getTeam(arg0, tournament).getNum();
            case 1 -> teS.getTeam(arg0, tournament).getTeam1();
            case 2 -> teS.getTeam(arg0, tournament).getTeam2();
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
        if(tournament.getStatus() != 0) return false;
        return y > 0;
    }

    /**
     * Sets value at.
     *
     * @param aValue      the a value
     * @param rowIndex    the row index
     * @param columnIndex the column index
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Team te = teS.getTeam(rowIndex, tournament);
        switch (columnIndex){
            case 1:
                te.setTeam1((String) aValue);
                break;
            case 2:
                te.setTeam2((String) aValue);
            default:
                break;
        }
        teS.updatePlayersTeams(te, tournament);
        fireTableDataChanged();
    }

    /**
     * Sets tournament.
     *
     * @param to the to
     */
    public void setTournament(Tournament to) {
        this.tournament = to;
    }
}
