package view;

import IDAO.TournamentIDAOImpl;
import Service.GameService;
import Service.TeamService;
import Service.TournamentService;
import models.CONSTANTS;
import models.Team;
import models.Game;
import models.Tournament;
import models.tables.GameTable;
import models.tables.TeamTable;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


/**
 * The type Window.
 */
public class Window extends JFrame {
	/**
	 * The constant serialVersionUID.
	 */
	@Serial
	private static final long serialVersionUID = 1L;
	/**
	 * The Tournament.
	 */
	private Tournament tournament;

	/**
	 * The C.
	 */
	public JPanel c;
	/**
	 * The S.
	 */
	Statement s;

	/**
	 * The Tournament list.
	 */
	private JPanel tournamentList;
	/**
	 * The Tournament names.
	 */
	private Vector<String> tournamentNames;
	/**
	 * The List.
	 */
	private JList<String> list;
	/**
	 * The Label.
	 */
	private JLabel        label;
	/**
	 * The Create tournament.
	 */
	private JButton createTournament;
	/**
	 * The Select tournament.
	 */
	private JButton 	selectTournament;
	/**
	 * The Delete tournament.
	 */
	private JButton 	deleteTournament;
	/**
	 * The B tournament.
	 */
	private final JButton 	bTournament;
	/**
	 * The B teams.
	 */
	private final JButton bTeams;
	/**
	 * The B rounds.
	 */
	private final JButton bRounds;
	/**
	 * The B games.
	 */
	private final JButton bGames;
	/**
	 * The B results.
	 */
	private final JButton bResults;
	/**
	 * The B params.
	 */
	private final JButton bParams;

	/**
	 * The T rounds.
	 */
	private JTable tRounds;
	/**
	 * The Js rounds.
	 */
	private JScrollPane jsRounds;
	/**
	 * The P rounds.
	 */
	private JPanel pRounds;
	/**
	 * The Layout rounds.
	 */
	private BoxLayout layoutRounds;
	/**
	 * The Desc rounds.
	 */
	private JLabel descRounds;

	/**
	 * The Add rounds.
	 */
	private JButton addRounds;
	/**
	 * The Del rounds.
	 */
	private JButton delRounds;
	/**
	 * The Rentrer rounds.
	 */
	private JButton rentrerRounds;

	/**
	 * The Name details.
	 */
	private JLabel nameDetails;
	/**
	 * The State details.
	 */
	private JLabel stateDetails;
	/**
	 * The Round nb details.
	 */
	private JLabel roundNbDetails;
	//JButton                    saveDetails;

	/**
	 * The Model teams.
	 */
	private TeamTable modelTeams;
	/**
	 * The Add teams.
	 */
	private JButton addTeams;
	/**
	 * The Del teams.
	 */
	private JButton delTeams;
	/**
	 * The Confirm teams.
	 */
	private JButton confirmTeams;
	/**
	 * The Js teams.
	 */
	private JScrollPane jsTeams;
	/**
	 * The Jt teams.
	 */
	private JTable jtTeams;
	/**
	 * The P teams.
	 */
	private JPanel pTeams;
	/**
	 * The Layout teams.
	 */
	private BoxLayout layoutTeams;
	/**
	 * The Desc teams.
	 */
	private JLabel descTeams;

	/**
	 * The Model game.
	 */
	private GameTable modelGame;
	/**
	 * The Js game.
	 */
	private JScrollPane jsGame;
	/**
	 * The Jt game.
	 */
	private JTable jtGame;
	/**
	 * The P game.
	 */
	private JPanel pGame;
	/**
	 * The Layout game.
	 */
	private BoxLayout layoutGame;
	/**
	 * The Desc game.
	 */
	private JLabel descGame;
	/**
	 * The Bottom game.
	 */
	private JPanel bottomGame; //?
	/**
	 * The State game.
	 */
	private JLabel stateGame;
	/**
	 * The Confirm game.
	 */
	private JButton confirmGame;

	/**
	 * The Js results.
	 */
	private JScrollPane jsResults;
	/**
	 * The Jt results.
	 */
	private JTable jtResults;
	/**
	 * The P results.
	 */
	private JPanel pResults;
	/**
	 * The Layout results.
	 */
	private BoxLayout layoutResults;
	/**
	 * The Desc results.
	 */
	private JLabel descResults;
	/**
	 * The Bottom results.
	 */
	private JPanel bottomResults;
	/**
	 * The State results.
	 */
	private JLabel stateResults;


	/**
	 * The Trace tournament.
	 */
	private boolean traceTournament = false;
	/**
	 * The Trace details.
	 */
	private boolean traceDetails = false;
	/**
	 * The Trace teams.
	 */
	private boolean traceTeams = false;
	/**
	 * The Trace rounds.
	 */
	private boolean traceRounds = false;
	/**
	 * The Trace games.
	 */
	private boolean traceGames = false;
	/**
	 * The Trace results.
	 */
	private boolean traceResults = false;

	/**
	 * The Window.
	 */
	private final CardLayout window;

	/**
	 * The Select state.
	 */
	private JLabel selectState = null;
	/**
	 * The Idao.
	 */
	private final TournamentIDAOImpl idaoTournament = TournamentIDAOImpl.getInstance();

	/**
	 * The Ts.
	 */
	public TournamentService toS = new TournamentService();
	/**
	 * The Gs.
	 */
	private final TeamService teS = new TeamService();

	private final GameService gS = new GameService();

	/**
	 * Instantiates a new Window.
	 */
	public Window(){
		this.setTitle(CONSTANTS.TITLE);
		setSize(800,400);
		this.setVisible(true);
		this.setLocationRelativeTo(this.getParent());
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		this.setContentPane(content);
		JPanel pTop = new JPanel();
		content.add(pTop,BorderLayout.NORTH);
		pTop.add(selectState = new JLabel());
		this.setStatusSelect(CONSTANTS.STATUS_SELECT_NULL);
		JPanel pLeft = new JPanel();
		pLeft.setBackground(Color.RED);
		pLeft.setPreferredSize(new Dimension(130,0));
		content.add(pLeft,BorderLayout.WEST);
		bTournament = new JButton(CONSTANTS.B_TOURNAMENT);
		bParams = new JButton(CONSTANTS.B_PARAMS);
		bTeams = new JButton(CONSTANTS.B_TEAMS);
		bRounds = new JButton(CONSTANTS.LABEL_ROUNDS);
		bGames = new JButton(CONSTANTS.B_GAMES);
		bResults = new JButton(CONSTANTS.B_RESULTS);
		int buttonWidth = 100;
		int buttonHeight = 30;
		bTournament.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		bParams.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		bTeams.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		bRounds.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		bGames.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		bResults.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		pLeft.add(bTournament);
		pLeft.add(bParams);
		pLeft.add(bTeams);
		pLeft.add(bRounds);
		pLeft.add(bGames);
		pLeft.add(bResults);
		window = new CardLayout(); //? fen? and c?
		c = new JPanel(window);
		content.add(c,BorderLayout.CENTER);
		bTournament.addActionListener(arg0 -> tracerSelectTournament());
//		bTournament.addActionListener(e-> tournament=null);
		bRounds.addActionListener(arg0 -> tracerRoundsTournament());
		bParams.addActionListener(arg0 -> tracerDetailsTournament());
		bTeams.addActionListener(e -> tracer_tournoi_equipes());
		bGames.addActionListener(e -> traceTournamentGames());
		bResults.addActionListener(arg0 -> traceTournamentResults());
		tracerSelectTournament();
	}

	/**
	 * Set status select.
	 *
	 * @param t the t
	 */
	public void setStatusSelect(String t){
		selectState.setText(CONSTANTS.deftState + "" + t);
	}

	/**
	 * Update buttons.
	 */
	public void updateButtons(){
		if( tournament == null){
			bTournament.setEnabled(true);
			bTeams.setEnabled(false);
			bGames.setEnabled(false);
			bRounds.setEnabled(false);
			bResults.setEnabled(false);
			bParams.setEnabled(false);
		}else{
			switch(tournament.getStatus()){
				case 0:
					bTournament.setEnabled(true);
					bTeams.setEnabled(true);
					bGames.setEnabled(false);
					bRounds.setEnabled(false);
					bResults.setEnabled(false);
					bParams.setEnabled(true);
					break;
				case 2:
					bTournament.setEnabled(true);
					bTeams.setEnabled(true);
					bGames.setEnabled(gS.getNbRounds(tournament) > 0);
					bRounds.setEnabled(true);
					int total=gS.getNbGames(tournament), ended=gS.getNbEndedGames(tournament);
					bResults.setEnabled(total == ended && total > 0);
					bParams.setEnabled(true);
					break;
			}
		}
	}

	/**
	 * Tracer select tournament.
	 */
	public void tracerSelectTournament(){
		tournament = null;
		updateButtons();
		tournamentNames = new Vector<>();
       	this.setStatusSelect(CONSTANTS.STATUS_SELECT_TOURNAMENT);
		tournamentNames = toS.getTournamentsName();
		int nbLines = tournamentNames.size();
		if(traceTournament){
			list.setListData(tournamentNames);
			toS.updateTournaments();
	        if(nbLines == 0){
	        	selectTournament.setEnabled(false);
	        	deleteTournament.setEnabled(false);
	        }else{
	        	selectTournament.setEnabled(true);
	        	deleteTournament.setEnabled(true);
	        	list.setSelectedIndex(0);
	        }
			window.show(c,CONSTANTS.B_TOURNAMENT);
		}else{
		    traceTournament = true;
			JPanel jpanel = new JPanel();
			jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
			c.add(jpanel,CONSTANTS.B_TOURNAMENT);
			JTextArea gt = new JTextArea(CONSTANTS.SIGNATURE);
			gt.setAlignmentX(Component.CENTER_ALIGNMENT);
			gt.setEditable(false);
			jpanel.add(gt);
			// Recherche de la liste des tournois
			tournamentList = new JPanel();

			jpanel.add(tournamentList);

			list = new JList<>(tournamentNames);
			list.setAlignmentX(Component.LEFT_ALIGNMENT);
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		    list.setVisibleRowCount(-1);
		    JScrollPane listScroller = new JScrollPane(list);
	        listScroller.setPreferredSize(new Dimension(250, 180));

	        label = new JLabel(CONSTANTS.LABEL_TOURNAMENT_LIST);
	        label.setLabelFor(list);
	        label.setAlignmentX(Component.LEFT_ALIGNMENT);
			jpanel.add(label);
			jpanel.add(listScroller);
			jpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

	        Box bh = Box.createHorizontalBox();
			jpanel.add(bh);
				createTournament = new JButton(CONSTANTS.B_TOURNAMENT_NEW);
				selectTournament = new JButton(CONSTANTS.B_TOURNAMENT_SELECT);
				deleteTournament = new JButton(CONSTANTS.B_TOURNAMENT_DEL);
				bh.add(createTournament);
				bh.add(selectTournament);
				bh.add(deleteTournament);
				jpanel.updateUI();
	        if(nbLines == 0){
	        	selectTournament.setEnabled(false);
	        	deleteTournament.setEnabled(false);
	        }else{
	        	list.setSelectedIndex(0);
	        }
	        createTournament.addActionListener(e -> {
				toS.createTournament();
				Window.this.tracerSelectTournament();
			});
	        deleteTournament.addActionListener(e -> {
				toS.deleteTournament(Window.this.list.getSelectedValue());
				Window.this.tracerSelectTournament();
			});
	        selectTournament.addActionListener(arg0 -> {
				String nt = Window.this.list.getSelectedValue();
				Window.this.tournament = toS.getTournamentFromName(nt);
				Window.this.tracerDetailsTournament();
				Window.this.setStatusSelect("models.Tournament \" " + nt + " \"");

			});
	        window.show(c,CONSTANTS.B_TOURNAMENT);
		}
	}

	/**
	 * Tracer details tournament.
	 */
	public void tracerDetailsTournament(){
		if(tournament == null){
			return ;
		}
		updateButtons();
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JLabel("Détail du tournoi"));
		c.add(p, CONSTANTS.DETAILS);
		JPanel tab = new JPanel( new GridLayout(4,2));
		nameDetails = new JLabel(tournament.getNom());
		tab.add(new JLabel("Nom du tournoi"));
		tab.add(nameDetails);
		stateDetails = new JLabel(tournament.getStatusName());
		tab.add(new JLabel(CONSTANTS.LABEL_STATUS));
		tab.add(stateDetails);
		roundNbDetails = new JLabel(Integer.toString(gS.getNbRounds(tournament)));
		tab.add(new JLabel("Nombre de tours:"));
		roundNbDetails = new JLabel(Integer.toString(gS.getNbRounds(tournament)));
		tab.add(new JLabel(CONSTANTS.LABEL_ROUNDS_NUM));
		tab.add(roundNbDetails);
		p.add(tab);
		p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		window.show(c,CONSTANTS.DETAILS);
	}

	/**
	 * Tracer tournoi equipes.
	 */
	public void tracer_tournoi_equipes(){
		if(tournament == null){
			return ;
		}
		updateButtons();
		if(traceTeams){
			teS.updateTeams(tournament);
			modelTeams.setTournament(tournament);
			modelTeams.fireTableDataChanged();
		}else{
			traceTeams = true;
			pTeams = new JPanel();
			layoutTeams = new BoxLayout(pTeams, BoxLayout.Y_AXIS);
			pTeams.setLayout(layoutTeams);
			descTeams = new JLabel(CONSTANTS.LABEL_TOURNAMENT_TEAMS);
			pTeams.add(descTeams);
			pTeams.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(pTeams,CONSTANTS.B_TEAMS);
			modelTeams = new TeamTable(tournament); //Teams AbstractTableModel
			jtTeams = new JTable(modelTeams);
			jsTeams = new JScrollPane(jtTeams);
			pTeams.add(jsTeams);
			JPanel bt    = new JPanel();
			addTeams = new JButton(CONSTANTS.B_TEAM_ADD);
			delTeams = new JButton(CONSTANTS.B_TEAM_DEL);
			confirmTeams = new JButton(CONSTANTS.B_TEAM_CONFIRM);
			addTeams.addActionListener(arg0 -> {
				teS.addTeam(tournament);
				confirmTeams.setEnabled(teS.getNbTeams(tournament) > 0 && teS.getNbTeams(tournament) % 2 == 0);
				modelTeams.fireTableDataChanged();
				if(teS.getNbTeams(tournament) > 0){
					jtTeams.getSelectionModel().setSelectionInterval(0, 0);
				}
			});
			delTeams.addActionListener(e -> {
				if(Window.this.jtTeams.getSelectedRow() != -1){
					teS.deleteTeam(teS.getTeam(Window.this.jtTeams.getSelectedRow(), tournament).getId(), tournament);
				}
				confirmTeams.setEnabled(teS.getNbTeams(tournament) > 0 && teS.getNbTeams(tournament) % 2 == 0) ;
				modelTeams.fireTableDataChanged();
				if(teS.getNbTeams(tournament) > 0){
					jtTeams.getSelectionModel().setSelectionInterval(0, 0);
				}
			});
			confirmTeams.addActionListener(e -> {
				gS.updateGames(tournament);
				gS.generateGames(tournament);
				toS.updateTournament(tournament);
				Window.this.updateButtons();
				Window.this.traceTournamentGames();
			});
			if(teS.getNbTeams(tournament) > 0){
				jtTeams.getSelectionModel().setSelectionInterval(0, 0);
			}
			bt.add(addTeams);
			bt.add(delTeams);
			bt.add(confirmTeams);
			pTeams.add(bt);
			pTeams.add(new JLabel(CONSTANTS.LABEL_TEAMS_ODD));
		}
		if(tournament.getStatus() != 0){
			addTeams.setEnabled(false);
			delTeams.setEnabled(false);
			confirmTeams.setEnabled(tournament.getStatus() == 1);
		}else{
			addTeams.setEnabled(true);
			delTeams.setEnabled(true);
			confirmTeams.setEnabled(teS.getNbTeams(tournament) > 0) ;
		}
		window.show(c,CONSTANTS.B_TEAMS);
	}

	/**
	 * Tracer rounds tournament.
	 */
	public void tracerRoundsTournament(){
		if(tournament == null){
			return ;
		}
		updateButtons();
		Vector< Vector<Object>> to =new Vector<>();
		Vector<Object> v;
		boolean canAdd;
		v = gS.getSomeVarFromGamesFromTournament(tournament);
		to.add(v);
		canAdd = v.get(1) == v.get(2);
		Vector<String> columnNames = new Vector<>();
		columnNames.add(CONSTANTS.COLUMN_ADD_ROUND_NUMBER);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_NUMBER);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_PLAYED);
		tRounds = new JTable(to,columnNames);
		if(traceRounds){
			jsRounds.setViewportView(tRounds);
		}else{
			traceRounds = true;
			pRounds = new JPanel();
			layoutRounds = new BoxLayout(pRounds, BoxLayout.Y_AXIS);
			pRounds.setLayout(layoutRounds);
			descRounds = new JLabel(CONSTANTS.LABEL_ROUNDS);
			pRounds.add(descRounds);
			pRounds.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(pRounds,CONSTANTS.LABEL_ROUNDS);
			jsRounds = new JScrollPane();
			jsRounds.setViewportView(tRounds);
			pRounds.add(jsRounds);
			JPanel bt = new JPanel();
			addRounds = new JButton(CONSTANTS.B_ROUND_ADD);
			delRounds = new JButton(CONSTANTS.B_ROUND_DEL);
			bt.add(addRounds);
			bt.add(delRounds);
			pRounds.add(bt);
			pRounds.add(new JLabel(CONSTANTS.LABEL_ROUNDS_END_PLEASE));
			pRounds.add(new JLabel(CONSTANTS.LABEL_ROUNDS_MAX));
			addRounds.addActionListener(e -> {
				// TODO Auto-generated method stub
				gS.addRound(tournament);
				Window.this.tracerRoundsTournament();
			});
			delRounds.addActionListener(e -> {
				gS.deleteRound(tournament);
				Window.this.tracerRoundsTournament();
			});
		}
		if(to.size() == 0){
			delRounds.setEnabled(false);
			addRounds.setEnabled(true);
		}else{
			delRounds.setEnabled(gS.getNbRounds(tournament)>1);
			addRounds.setEnabled(canAdd && gS.getNbRounds(tournament) < teS.getNbTeams(tournament) - 1 );
		}
		window.show(c,CONSTANTS.LABEL_ROUNDS);
	}

	/**
	 * Trace tournament games.
	 */
	public void traceTournamentGames(){
		if(tournament == null){
			return ;
		}
		updateButtons();
		if(traceGames){
			modelGame.setTournament(tournament);
			updateGameStatus(tournament);
			updateButtons();
		}else{
			traceGames = true;
			pGame = new JPanel();
			layoutGame = new BoxLayout(pGame, BoxLayout.Y_AXIS);
			pGame.setLayout(layoutGame);
			descGame = new JLabel(CONSTANTS.LABEL_TOURNAMENT_GAMES);
			pGame.add(descGame);
			pGame.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(pGame,CONSTANTS.B_GAMES);

			modelGame = new GameTable(tournament){
				@Override
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					super.setValueAt(aValue, rowIndex, columnIndex);
					Window.this.updateGameStatus(tournament);
					Window.this.updateButtons();
				}
			};
			jtGame = new JTable(modelGame);
			jsGame = new JScrollPane(jtGame);
			pGame.add(jsGame);
			bottomGame = new JPanel();
			bottomGame.add(stateGame = new JLabel(CONSTANTS.LABEL_GAMES_PLAYED));
			bottomGame.add(confirmGame = new JButton(CONSTANTS.B_RESULTS_SHOW));
			confirmGame.setEnabled(false);
			pGame.add(bottomGame);
			updateGameStatus(tournament);
			confirmGame.addActionListener(e->traceTournamentResults());
		}
		window.show(c, CONSTANTS.B_GAMES);
	}


	/**
	 * Trace tournament results.
	 */
	public void traceTournamentResults(){
		if(tournament == null){
			return ;
		}
		Vector<Vector<Object>> to =new Vector<>();
		Vector<Object> listInfoGame;
		listInfoGame = toS.getInfoTournoi(tournament);
		to.add(listInfoGame);
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(CONSTANTS.COLUMN_ADD_TEAM_NUMBER);
		columnNames.add(CONSTANTS.COLUMN_ADD_NAME_PLAYER_1);
		columnNames.add(CONSTANTS.COLUMN_ADD_NAME_PLAYER_2);
		columnNames.add(CONSTANTS.COLUMN_ADD_SCORE);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_WON);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_PLAYED);
		jtResults = new JTable(to,columnNames);
		jtResults.setAutoCreateRowSorter(true);
		if(traceResults){
			jsResults.setViewportView(jtResults);
		}else{
			traceResults = true;
			pResults = new JPanel();
			layoutResults = new BoxLayout(pResults, BoxLayout.Y_AXIS);
			pResults.setLayout(layoutResults);
			descResults = new JLabel(CONSTANTS.LABEL_TOURNAMENT_RESULTS);
			pResults.add(descResults);
			pResults.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(pResults,CONSTANTS.RESULTS);
			jsResults = new JScrollPane(jtResults);
			pResults.add(jsResults);
			bottomResults = new JPanel();
			bottomResults.add(stateResults = new JLabel(CONSTANTS.LABEL_WINNER));
			pResults.add(bottomResults);
		}
		window.show(c,CONSTANTS.RESULTS);
	}

	/**
	 * Update game status.
	 */
	private void updateGameStatus(Tournament t){
		int total=gS.getNbGames(t), ended=gS.getNbEndedGames(t);
		stateGame.setText(ended + "/" + total + CONSTANTS.GAMES_ENDED);
		confirmGame.setEnabled(total == ended);
	}
}
