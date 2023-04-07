package models.tables;

import Service.TeamService;
import models.CONSTANTS;
import models.Team;
import models.Tournament;

import javax.swing.table.AbstractTableModel;

public class TeamTable extends AbstractTableModel {
    private Tournament tournament;
    private TeamService teS = new TeamService();

    public TeamTable(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public int getRowCount() {
        if(tournament == null)return 0;
        return teS.getNbTeams(tournament);
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

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

    @Override
    public Object getValueAt(int arg0, int arg1) {
        Object r=null;
        switch(arg1){
            case 0:
                r= teS.getTeam(arg0, tournament).getNum();
                break;
            case 1:
                r= teS.getTeam(arg0, tournament).getTeam1();
                break;
            case 2:
                r= teS.getTeam(arg0, tournament).getTeam2();
                break;
        }
        return r;
    }

    public boolean isCellEditable(int x, int y){
        if(tournament.getStatus() != 0) return false;
        return y > 0;
    }

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
}
