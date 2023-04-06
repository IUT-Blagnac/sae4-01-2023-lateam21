package view;

import IDAO.TournamentIDAOImpl;
import Service.GameService;
import Service.TeamService;
import Service.TournamentService;
import models.CONSTANTS;
import models.Team;
import models.Game;
import models.Tournament;

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
	 * The C.
	 */
	public JPanel c;
	/**
	 * The S.
	 */
	Statement s;

	/**
	 * The Gt.
	 */
	private JTextArea gt;
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
	private AbstractTableModel modelTeams;
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
	private AbstractTableModel modelGame;
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
	 * The Tournament.
	 */
	private Tournament tournament = null;

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
	 *
	 * @param st the st
	 */
	public Window(Statement st){
		
		s = st;
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
		
		bTournament.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracerSelectTournament();
			}
		});
		bRounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracerRoundsTournament();
			}
		});
		bParams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracerDetailsTournament();
			}
		});
		bTeams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracer_tournoi_equipes();
			}
		});
		bGames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traceTournamentGames();
			}
		});
		bResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				traceTournamentResults();
			}
		});
		
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
			switch(toS.getTournamentStatus(tournament)){
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

//		lt = null;
		updateButtons();
		tournamentNames = new Vector<String>();
       	this.setStatusSelect(CONSTANTS.STATUS_SELECT_TOURNAMENT);
		tournamentNames = toS.getTournamentsName();
		int nbLines = tournamentNames.size();

		if(traceTournament){
			list.setListData(tournamentNames);

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
			gt = new JTextArea(CONSTANTS.SIGNATURE);
			gt.setAlignmentX(Component.CENTER_ALIGNMENT);
			gt.setEditable(false);
			jpanel.add(gt);

			// Recherche de la liste des tournois
			tournamentList = new JPanel();

			jpanel.add(tournamentList);

			list = new JList<String>(tournamentNames);
			list.setAlignmentX(Component.LEFT_ALIGNMENT);
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			//list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		    list.setVisibleRowCount(-1);
		    JScrollPane listScroller = new JScrollPane(list);
	        listScroller.setPreferredSize(new Dimension(250, 180));
	        //listScroller.setAlignmentX(LEFT_ALIGNMENT);

	        label = new JLabel(CONSTANTS.LABEL_TOURNAMENT_LIST);
	        label.setLabelFor(list);
	        label.setAlignmentX(Component.LEFT_ALIGNMENT);
			jpanel.add(label);
	        //c.add(Box.createRigidArea(new Dimension(0,0)));
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

	        createTournament.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					TournamentService.createTournament();
					Window.this.tracerSelectTournament();
					//String nt = JOptionPane.showInputDialog("Nom du tournament ?");
					//ResultSet rs = view.Window.this.s.executeQuery("SELECT)
					//view.Window.this.s.execute("INSERT INTO TOURNOI (id_tournoi)
				}
			});

	        deleteTournament.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					TournamentService.deleteTournament(Window.this.list.getSelectedValue());
					Window.this.tracerSelectTournament();


				}
			});
	        selectTournament.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String nt = Window.this.list.getSelectedValue();
					Window.this.tournament = toS.getTournamentFromName(nt);
					//view.Window.this.detracer_select_tournoi();
					Window.this.tracerDetailsTournament();
					Window.this.setStatusSelect("models.Tournament \" " + nt + " \"");

				}
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
		updateButtons(); //?

		if(traceDetails){
			nameDetails.setText(tournament.getNom());
			stateDetails.setText(tournament.getStatusName());
			roundNbDetails.setText(Integer.toString(gS.getNbRounds(tournament)));
		}else{
			traceDetails = true;
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.add(new JLabel(CONSTANTS.LABEL_TOURNAMENT_DETAILS));
			c.add(p,CONSTANTS.DETAILS);

			JPanel tab = new JPanel( new GridLayout(4,2));
			nameDetails = new JLabel(tournament.getNom());
			tab.add(new JLabel(CONSTANTS.LABEL_TOURNAMENT_NAME));
			tab.add(nameDetails);

			stateDetails = new JLabel(tournament.getStatusName());
			tab.add(new JLabel(CONSTANTS.LABEL_STATUS));
			tab.add(stateDetails);


			roundNbDetails = new JLabel(Integer.toString(gS.getNbRounds(tournament)));
			tab.add(new JLabel("Nombre de tours:"));

			roundNbDetails = new JLabel(Integer.toString(gS.getNbRounds(tournament)));
			tab.add(new JLabel(CONSTANTS.LABEL_ROUNDS_NUM));

			tab.add(roundNbDetails);
			//detailt_nbtours.setPreferredSize(new Dimension(40,40));

			p.add(tab);

			//detailt_enregistrer = new JButton("Enregistrer");
			//p.add(Box.createHorizontalGlue());
			//p.add(detailt_enregistrer);
			p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			//p.add(new JLabel("  e"));
			//tab.setPreferredSize(new Dimension(-1, 200));
		}
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

			modelTeams = new AbstractTableModel() {


				//private static final long serialVersionUID = 1L;

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
				public int getRowCount() {
					if(tournament == null)return 0;
					return teS.getNbTeams(tournament);
				}

				@Override
				public int getColumnCount() {
					return 3;
				}
				public boolean isCellEditable(int x, int y){
					if(tournament.getStatus() != 0) return false;
					return y > 0;
				}
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					Team e = teS.getTeam(rowIndex, tournament);
					switch (columnIndex){
						case 1:
							e.setTeam1((String) aValue);
							break;
						case 2:
							e.setTeam2((String) aValue);
						default:
							break;
					}
					teS.updatePlayersTeams(rowIndex, tournament);
					fireTableDataChanged();
				}
			};
			jtTeams = new JTable(modelTeams);
			jsTeams = new JScrollPane(jtTeams);
			pTeams.add(jsTeams);
			//jt.setPreferredSize(getMaximumSize());

			JPanel bt    = new JPanel();
			addTeams = new JButton(CONSTANTS.B_TEAM_ADD);
			delTeams = new JButton(CONSTANTS.B_TEAM_DEL);
			confirmTeams = new JButton(CONSTANTS.B_TEAM_CONFIRM);

			addTeams.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					teS.addTeam(tournament);
					confirmTeams.setEnabled(teS.getNbTeams(tournament) > 0 && teS.getNbTeams(tournament) % 2 == 0) ;
					modelTeams.fireTableDataChanged();
					if(teS.getNbTeams(tournament) > 0){
						jtTeams.getSelectionModel().setSelectionInterval(0, 0);

					}


				}
			});
			delTeams.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(Window.this.jtTeams.getSelectedRow() != -1){
						teS.deleteTeam(teS.getTeam(Window.this.jtTeams.getSelectedRow(), tournament).getId(), tournament);
					}
					confirmTeams.setEnabled(teS.getNbTeams(tournament) > 0 && teS.getNbTeams(tournament) % 2 == 0) ;
					modelTeams.fireTableDataChanged();
					if(teS.getNbTeams(tournament) > 0){
						jtTeams.getSelectionModel().setSelectionInterval(0, 0);
					}
				}
			});
			confirmTeams.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					gS.generateGames(tournament);
					Window.this.updateButtons();
					Window.this.traceTournamentGames();

				}
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
		ArrayList< Object> to =new ArrayList<Object>();
		ArrayList<Object> v;
		boolean canAdd = true;
		try {
			ResultSet rs = s.executeQuery("Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.num_tour=m.num_tour  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + this.tournament.getIdTournament() + " GROUP BY m.num_tour,m.id_tournoi;");
			while(rs.next()){
				v = new ArrayList<Object>();
				v.add(rs.getInt(CONSTANTS.BD_NUM_TOUR));
				v.add(rs.getInt(CONSTANTS.BD_TMATCHS));
				v.add(rs.getString(CONSTANTS.BD_TERMINES));
				to.add(v);
				canAdd = canAdd && rs.getInt(CONSTANTS.BD_TMATCHS) == rs.getInt(CONSTANTS.BD_TERMINES);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add(CONSTANTS.COLUMN_ADD_ROUND_NUMBER);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_NUMBER);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_PLAYED);
		tRounds = new JTable((TableModel) to, (TableColumnModel) columnNames);
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

			JPanel bt    = new JPanel();
			addRounds = new JButton(CONSTANTS.B_ROUND_ADD);
			delRounds = new JButton(CONSTANTS.B_ROUND_DEL);
			//tours_rentrer   = new JButton("Rentrer les scores du tour s�lectionn�");
			bt.add(addRounds);
			bt.add(delRounds);
			//bt.add(tours_rentrer);
			pRounds.add(bt);
			pRounds.add(new JLabel(CONSTANTS.LABEL_ROUNDS_END_PLEASE));
			pRounds.add(new JLabel(CONSTANTS.LABEL_ROUNDS_MAX));
			addRounds.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					gS.addRound(tournament);
					Window.this.tracerRoundsTournament();
				}
			});
			delRounds.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					gS.deleteRound();
					Window.this.tracerRoundsTournament();
				}
			});
		}
		if(to.size() == 0){
			delRounds.setEnabled(false);
			addRounds.setEnabled(true);
		}else{

			delRounds.setEnabled( gS.getNbRounds(tournament) > 1);


			if(!canAdd || gS.getNbRounds(tournament)  >= teS.getNbTeams(tournament)-1 ){
				addRounds.setEnabled(false);
			}else
				addRounds.setEnabled(true);

			addRounds.setEnabled(canAdd && gS.getNbRounds(tournament) < teS.getNbTeams(tournament) - 1);

		}

		window.show(c,CONSTANTS.LABEL_ROUNDS);
		//tours_ajouter.setEnabled(peutajouter);
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
			gS.updateGame();
			modelGame.fireTableDataChanged();
			updateGameStatus();
		}else{
			traceGames = true;
			pGame = new JPanel();
			layoutGame = new BoxLayout(pGame, BoxLayout.Y_AXIS);
			pGame.setLayout(layoutGame);
			descGame = new JLabel(CONSTANTS.LABEL_TOURNAMENT_GAMES);
			pGame.add(descGame);
			pGame.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(pGame,CONSTANTS.B_GAMES);

			modelGame = new AbstractTableModel() {
//				private static final long serialVersionUID = 1L;
				@Override
				public Object getValueAt(int arg0, int arg1) {
					Object r=null;
					switch(arg1){
					case 0:
						r= gS.getGame(arg0).getNumRounds();
					break;
					case 1:
						r= gS.getGame(arg0).getTeam1();
					break;
					case 2:
						r= gS.getGame(arg0).getTeam2();
					break;
					case 3:
						r= gS.getGame(arg0).getScore1();
					break;
					case 4:
						r= gS.getGame(arg0).getScore2();
					break;
					}
					return r;

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
				public int getRowCount() {
					// TODO Auto-generated method stub
					if(tournament == null)return 0;
					return gS.getNbGames(tournament);
				}

				@Override
				public int getColumnCount() {
					// TODO Auto-generated method stub
					return 5;
				}
				public boolean isCellEditable(int x, int y){
					return y > 2 && gS.getGame(x).getNumRounds() == gS.getNbRounds(tournament);
				}
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					Game m = gS.getGame(rowIndex);
					if( columnIndex == 0){

					}else if( columnIndex == 3){
						try{
							int sco = Integer.parseInt((String)aValue);
							m.setScore1(sco);
							gS.updateGame(rowIndex);

						}catch(Exception e){
							return ;
						}

					}else if( columnIndex == 4){
						try{
							int sco = Integer.parseInt((String)aValue);
							m.setScore2(sco);
							gS.updateGame(rowIndex);

						}catch(Exception e){
							return ;
						}
					}

					fireTableDataChanged();
					Window.this.updateGameStatus();
					Window.this.updateButtons();
				}
			};
			jtGame = new JTable(modelGame);

			jsGame = new JScrollPane(jtGame);
			pGame.add(jsGame);
			//jt.setPreferredSize(getMaximumSize());

			System.out.println(CONSTANTS.TRUC_2);

			bottomGame = new JPanel();
			bottomGame.add(stateGame = new JLabel(CONSTANTS.LABEL_GAMES_PLAYED));
			bottomGame.add(confirmGame = new JButton(CONSTANTS.B_RESULTS_SHOW));
			confirmGame.setEnabled(false);

			pGame.add(bottomGame);
			updateGameStatus();
		}

		window.show(c, CONSTANTS.B_GAMES);


		window.show(c,CONSTANTS.B_GAMES);


	}


	/**
	 * Trace tournament results.
	 */
	public void traceTournamentResults(){
		if(tournament == null){
			return ;
		}
		ArrayList< Object> to =new ArrayList<Object>();
		ArrayList<Object> v;
		try {
			ResultSet rs = s.executeQuery("SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + this.tournament.getIdTournament() + ") as joueur1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + this.tournament.getIdTournament() + ") as joueur2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + this.tournament.getIdTournament() + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + this.tournament.getIdTournament() + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
			while(rs.next()){
				v = new ArrayList<Object>();
				v.add(rs.getInt(CONSTANTS.BD_GET_EQUIPE));
				v.add(rs.getString(CONSTANTS.BD_GET_JOUEUR1));
				v.add(rs.getString(CONSTANTS.BD_GET_JOUEUR2));
				v.add(rs.getInt(CONSTANTS.BD_GET_SCORE));
				v.add(rs.getInt(CONSTANTS.BD_GET_MATCHS_GAGNES));
				v.add(rs.getInt(CONSTANTS.BD_GET_MATCHS_JOUES));
				to.add(v);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add(CONSTANTS.COLUMN_ADD_TEAM_NUMBER);
		columnNames.add(CONSTANTS.COLUMN_ADD_NAME_PLAYER_1);
		columnNames.add(CONSTANTS.COLUMN_ADD_NAME_PLAYER_2);
		columnNames.add(CONSTANTS.COLUMN_ADD_SCORE);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_WON);
		columnNames.add(CONSTANTS.COLUMN_ADD_GAMES_PLAYED);
		jtResults = new JTable((TableModel) to, (TableColumnModel) columnNames);
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
			//jt.setPreferredSize(getMaximumSize());

			bottomResults = new JPanel();
			bottomResults.add(stateResults = new JLabel(CONSTANTS.LABEL_WINNER));

			pResults.add(bottomResults);
		}

		window.show(c, CONSTANTS.RESULTS);


		window.show(c,CONSTANTS.RESULTS);

	}

	/**
	 * Update game status.
	 */
	private void updateGameStatus(){
		int total=-1, ended=-1;
		try {
			ResultSet rs = s.executeQuery("Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + this.tournament.getIdTournament() +" GROUP by id_tournoi ;");
			rs.next();
			total    = rs.getInt(1);
			ended = rs.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		stateGame.setText(ended + "/" + total + CONSTANTS.GAMES_ENDED);
		confirmGame.setEnabled(total == ended);
	}
}
