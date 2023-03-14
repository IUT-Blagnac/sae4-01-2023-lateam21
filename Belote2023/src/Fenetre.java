import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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



public class Fenetre extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JPanel c;
	Statement s;
	
	private JTextArea gt;
	private JPanel ListeTournois;
	private Vector<String> noms_tournois;
	private JList<String> list;
	private JLabel        label;
	private JButton       creerTournoi;
	private JButton       selectTournoi;	
	private JButton       deleteTournoi;
	private JButton       btournois;
	private JButton       bequipes;
	private JButton       btours;
	private JButton       bmatchs;
	private JButton       bresultats;
	private JButton       bparams;
	
	private boolean tournois_trace  = false;
	private boolean details_trace   = false;
	private boolean equipes_trace   = false;
	private boolean tours_trace     = false;
	private boolean match_trace     = false;
	private boolean resultats_trace = false;
	
	private CardLayout fen;
	final static String TOURNOIS = "Tournois";
    final static String DETAIL   = "Paramètres du tournoi";
    final static String EQUIPES  = "Equipes";
    final static String TOURS    = "Tours";
    final static String MATCHS   = "Matchs";
    final static String RESULTATS= "Resultats";
    public Tournoi t = null;
    
    private JLabel statut_slect = null;
    private final String statut_deft = "Gestion de tournois de Belote v1.0 - ";
	public Fenetre(Statement st){
		
		s = st;
		this.setTitle("Gestion de tournoi de Belote");
		setSize(800,400);
		this.setVisible(true);
		this.setLocationRelativeTo(this.getParent());
		
		
		JPanel contenu = new JPanel();
		contenu.setLayout(new BorderLayout());
		this.setContentPane(contenu);
		
		
		JPanel phaut = new JPanel();
		contenu.add(phaut,BorderLayout.NORTH);
		
		phaut.add(statut_slect = new JLabel());
		this.setStatutSelect("Pas de tournoi sélectionné");
		
		JPanel pgauche = new JPanel();
		pgauche.setBackground(Color.RED);
		pgauche.setPreferredSize(new Dimension(130,0));
		contenu.add(pgauche,BorderLayout.WEST);
		
		
		btournois    = new JButton("Tournois");
		bparams      = new JButton("Paramètres");
		bequipes     = new JButton("Equipes");
		btours       = new JButton("Tours");
		bmatchs      = new JButton("Matchs");
		bresultats   = new JButton("Résultats");
		
		
		int taille_boutons = 100;
		int hauteur_boutons = 30;
		
		btournois.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bparams.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bequipes.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		btours.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bmatchs.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		bresultats.setPreferredSize(new Dimension(taille_boutons,hauteur_boutons));
		
		pgauche.add(btournois);
		pgauche.add(bparams);
		pgauche.add(bequipes);
		pgauche.add(btours);
		pgauche.add(bmatchs);
		pgauche.add(bresultats);
		fen = new CardLayout();
		c = new JPanel(fen);
		
		contenu.add(c,BorderLayout.CENTER);
		
		btournois.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_select_tournoi();	
			}
		});
		btours.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_tours_tournoi();	
			}
		});
		bparams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_details_tournoi();
			}
		});
		bequipes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracer_tournoi_equipes();
			}
		});
		bmatchs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracer_tournoi_matchs();
			}
		});
		bresultats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tracer_tournoi_resultats();
			}
		});
		
		tracer_select_tournoi();
	}
	
	public void setStatutSelect(String t){
		statut_slect.setText(statut_deft + "" + t);
	}
	public void majboutons(){
		if( t == null){
			btournois.setEnabled(true);
			bequipes.setEnabled(false);
			bmatchs.setEnabled(false);
			btours.setEnabled(false);
			bresultats.setEnabled(false);
			bparams.setEnabled(false);			
		}else{
			switch(t.getStatut()){
			case 0:
				btournois.setEnabled(true);
				bequipes.setEnabled(true);
				bmatchs.setEnabled(false);
				btours.setEnabled(false);
				bresultats.setEnabled(false);
				bparams.setEnabled(true);	
			break;
			case 2:
				btournois.setEnabled(true);
				bequipes.setEnabled(true);
				bmatchs.setEnabled(t.getNbTours() > 0);
				btours.setEnabled(true);
				
				int total=-1, termines=-1;
				try {
					ResultSet rs = s.executeQuery("Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + this.t.id_tournoi +" GROUP by id_tournoi ;");
					rs.next();
					total    = rs.getInt(1);
					termines = rs.getInt(2);
				} catch (SQLException e) {
					e.printStackTrace();
					return ;
				}
				bresultats.setEnabled(total == termines && total > 0);			
				bparams.setEnabled(true);					
			break;
			}
		}
	}
	public void tracer_select_tournoi(){
		
		t = null;
		majboutons();

		int nbdeLignes = 0;
		noms_tournois = new Vector<String>();
       this.setStatutSelect("sélection d'un tournoi");
		ResultSet rs;
		try {
			rs = s.executeQuery("SELECT * FROM tournois;");

			while( rs.next() ){
				nbdeLignes++;
				noms_tournois.add(rs.getString("nom_tournoi"));
			}
			
			if( nbdeLignes == 0){
				//System.out.println("Pas de résultats");
				//t.add(new JLabel("Aucun tournoi n'a �t� cr��"));
			}else{
				//System.out.println(nbdeLignes + " r�sultats");
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Erreur lors de la requète :" + e.getMessage());
			e.printStackTrace();
		}
		
		if(tournois_trace){
			list.setListData(noms_tournois);

	        if(nbdeLignes == 0){
	        	selectTournoi.setEnabled(false);
	        	deleteTournoi.setEnabled(false);
	        }else{
	        	selectTournoi.setEnabled(true);
	        	deleteTournoi.setEnabled(true);
	        	list.setSelectedIndex(0);
	        }
			fen.show(c, TOURNOIS);


		}else{
		    tournois_trace = true;
			JPanel t = new JPanel();
			
			t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
			c.add(t,TOURNOIS);
			gt = new JTextArea("Gestion des tournois\nXXXXX XXXXXXXX, juillet 2012");
			gt.setAlignmentX(Component.CENTER_ALIGNMENT);
			gt.setEditable(false);
			t.add(gt);
			
			// Recherche de la liste des tournois
			ListeTournois = new JPanel();
			
			t.add(ListeTournois);		
	
			list = new JList<String>(noms_tournois); 
			list.setAlignmentX(Component.LEFT_ALIGNMENT); 
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			//list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		    list.setVisibleRowCount(-1);
		    JScrollPane listScroller = new JScrollPane(list);
	        listScroller.setPreferredSize(new Dimension(250, 180));
	        //listScroller.setAlignmentX(LEFT_ALIGNMENT);
	        
	        label = new JLabel("Liste des tournois");
	        label.setLabelFor(list);
	        label.setAlignmentX(Component.LEFT_ALIGNMENT);
	        t.add(label);
	        //c.add(Box.createRigidArea(new Dimension(0,0)));
	        t.add(listScroller);
	        t.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	        
	        
	
	        Box bh = Box.createHorizontalBox();
	        t.add(bh);
			creerTournoi = new JButton("Créer un nouveau tournoi");
			selectTournoi = new JButton("Sélectionner le tournoi");
			deleteTournoi = new JButton("Supprimer le tournoi");
			bh.add(creerTournoi);
			bh.add(selectTournoi);	
			bh.add(deleteTournoi);
			
			t.updateUI();
	        if(nbdeLignes == 0){
	        	selectTournoi.setEnabled(false);
	        	deleteTournoi.setEnabled(false);
	        }else{
	        	list.setSelectedIndex(0);
	        }
	        
	        creerTournoi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Tournoi.creerTournoi(Fenetre.this.s);
					Fenetre.this.tracer_select_tournoi();
					//String nt = JOptionPane.showInputDialog("Nom du tournoi ?");
					//ResultSet rs = Fenetre.this.s.executeQuery("SELECT)
					//Fenetre.this.s.execute("INSERT INTO TOURNOI (id_tournoi)
				}
			});
	        
	        deleteTournoi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Tournoi.deleteTournoi(Fenetre.this.s, Fenetre.this.list.getSelectedValue());
					Fenetre.this.tracer_select_tournoi();
	
					
				}
			});
	        selectTournoi.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String nt = Fenetre.this.list.getSelectedValue();
					Fenetre.this.t = new Tournoi(nt, Fenetre.this.s);
					//Fenetre.this.detracer_select_tournoi();
					Fenetre.this.tracer_details_tournoi();
					Fenetre.this.setStatutSelect("Tournoi \" " + nt + " \"");
					
				}
			});
	        fen.show(c, TOURNOIS);
		}
        
		
		
	}
    
	JLabel                     detailt_nom;
	JLabel                     detailt_statut;
	JLabel                     detailt_nbtours;
	//JButton                    detailt_enregistrer;
	
	public void tracer_details_tournoi(){
		if(t == null){
			return ;
		}
		majboutons();
		
		if(details_trace){
			detailt_nom.setText(t.getNom());
			detailt_statut.setText(t.getNStatut());
			detailt_nbtours.setText(Integer.toString(t.getNbTours()));
		}else{
			details_trace = false;
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.add(new JLabel("Détail du tournoi"));
			c.add(p, DETAIL);
			
			JPanel tab = new JPanel( new GridLayout(4,2));
			detailt_nom = new JLabel(t.getNom());
			tab.add(new JLabel("Nom du tournoi"));
			tab.add(detailt_nom);

			detailt_statut = new JLabel(t.getNStatut());
			tab.add(new JLabel("Statut"));
			tab.add(detailt_statut);
			
			detailt_nbtours = new JLabel(Integer.toString(t.getNbTours()));
			tab.add(new JLabel("Nombre de tours:"));
			tab.add(detailt_nbtours);
			//detailt_nbtours.setPreferredSize(new Dimension(40,40));

			p.add(tab);
			
			//detailt_enregistrer = new JButton("Enregistrer");
			//p.add(Box.createHorizontalGlue());
			//p.add(detailt_enregistrer);
			p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			//p.add(new JLabel("  e"));
			//tab.setPreferredSize(new Dimension(-1, 200));
			

		}
		fen.show(c, DETAIL);
		
	}

	private AbstractTableModel eq_modele;
    private JButton            eq_ajouter;
    private JButton            eq_supprimer;
    private JButton            eq_valider;
    private JScrollPane        eq_js;
    JTable                     eq_jt;
    JPanel                     eq_p;
    BoxLayout                  eq_layout;
    JLabel                     eq_desc;

	public void tracer_tournoi_equipes(){
		if(t == null){
			return ;
		}
		majboutons();
		if(equipes_trace){
			t.majEquipes();
			eq_modele.fireTableDataChanged();
		}else{
			equipes_trace = true;
			eq_p      = new JPanel();
			eq_layout = new BoxLayout(eq_p, BoxLayout.Y_AXIS);
			eq_p.setLayout(eq_layout);
			eq_desc = new JLabel("Equipes du tournoi");
			eq_p.add(eq_desc);
			eq_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(eq_p, EQUIPES);
	
			eq_modele = new AbstractTableModel() {
				

				private static final long serialVersionUID = 1L;

				@Override
				public Object getValueAt(int arg0, int arg1) {
					Object r=null;
					switch(arg1){
					case 0:
						r= t.getEquipe(arg0).num;
					break;
					case 1:
						r= t.getEquipe(arg0).eq1;
					break;
					case 2:
						r= t.getEquipe(arg0).eq2;
					break;
					}
					return r;
		
				}
				public String getColumnName(int col) {
				        if(col == 0){
				        	return "Numéro d'équipe";
				        }else if(col == 1){
				        	return "Joueur 1";
				        }else if(col == 2){
				        	return "Joueur 2";
				        }else{
				        	return "??";
				        }
				 }
				@Override
				public int getRowCount() {
					if(t == null)return 0;
					return t.getNbEquipes();
				}
				
				@Override
				public int getColumnCount() {
					return 3;
				}
				public boolean isCellEditable(int x, int y){
					if(t.getStatut() != 0) return false;
					return y > 0;
				}
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					Equipe e = t.getEquipe(rowIndex);
					if( columnIndex == 0){
						
					}else if( columnIndex == 1){
						e.eq1 = (String)aValue;
					}else if( columnIndex == 2){
						e.eq2 = (String)aValue;
					}
					t.majEquipe(rowIndex);
					fireTableDataChanged();
				}
			};
			eq_jt = new JTable(eq_modele);
			eq_js = new JScrollPane(eq_jt);
			eq_p.add(eq_js);
			//jt.setPreferredSize(getMaximumSize());

			JPanel bt    = new JPanel();
			eq_ajouter   = new JButton("Ajouter une équipe");
			eq_supprimer = new JButton("Supprimer une équipe");
			eq_valider   = new JButton("Valider les équipes");
			
			eq_ajouter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					t.ajouterEquipe();
					eq_valider.setEnabled(t.getNbEquipes() > 0 && t.getNbEquipes() % 2 == 0) ;
					eq_modele.fireTableDataChanged();
					if(t.getNbEquipes() > 0){
						eq_jt.getSelectionModel().setSelectionInterval(0, 0);
					
					}	
					
					
				}
			});
			eq_supprimer.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(Fenetre.this.eq_jt.getSelectedRow() != -1){
						t.supprimerEquipe(t.getEquipe(Fenetre.this.eq_jt.getSelectedRow()).id);
					}
					eq_valider.setEnabled(t.getNbEquipes() > 0 && t.getNbEquipes() % 2 == 0) ;
					eq_modele.fireTableDataChanged();
					if(t.getNbEquipes() > 0){
						eq_jt.getSelectionModel().setSelectionInterval(0, 0);
					}					
				}
			});
			eq_valider.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					t.genererMatchs();
					Fenetre.this.majboutons();
					Fenetre.this.tracer_tournoi_matchs();
					
				}
			});
			if(t.getNbEquipes() > 0){
				eq_jt.getSelectionModel().setSelectionInterval(0, 0);
			}
			bt.add(eq_ajouter);
			bt.add(eq_supprimer);
			bt.add(eq_valider);
			eq_p.add(bt);
			eq_p.add(new JLabel("Dans le cas de nombre d'équipes impair, créer une équipe virtuelle"));
		}
		if(t.getStatut() != 0){
			eq_ajouter.setEnabled(false);
			eq_supprimer.setEnabled(false);
			eq_valider.setEnabled(t.getStatut() == 1);
		}else{
			eq_ajouter.setEnabled(true);
			eq_supprimer.setEnabled(true);	
			eq_valider.setEnabled(t.getNbEquipes() > 0) ;
		}
		fen.show(c, EQUIPES);
		
	}

	JTable                     tours_t;
	JScrollPane                tours_js;
	JPanel                     tours_p;
	BoxLayout                  tours_layout;
	JLabel                     tours_desc;
	
	JButton                    tours_ajouter;
	JButton                    tours_supprimer;
	JButton                    tours_rentrer;
	
	public void tracer_tours_tournoi(){
		
		
		if(t == null){
			return ;
		}
		majboutons();
		Vector< Vector<Object>> to =new Vector<Vector<Object>>();
		Vector<Object> v;
		boolean peutajouter = true;
		try {
			ResultSet rs = s.executeQuery("Select num_tour,count(*) as tmatchs, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.num_tour=m.num_tour  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + this.t.id_tournoi + " GROUP BY m.num_tour,m.id_tournoi;");
			while(rs.next()){
				v = new Vector<Object>();
				v.add(rs.getInt("num_tour"));
				v.add(rs.getInt("tmatchs"));
				v.add(rs.getString("termines"));
				to.add(v);
				peutajouter = peutajouter && rs.getInt("tmatchs") == rs.getInt("termines");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Numéro du tour");
		columnNames.add("Nombre de matchs");
		columnNames.add("Matchs joués");
		tours_t = new JTable(to,columnNames );
		if(tours_trace){
			tours_js.setViewportView(tours_t);
		}else{
			tours_trace  = true;
			tours_p      = new JPanel();
			tours_layout = new BoxLayout( tours_p, BoxLayout.Y_AXIS);
			tours_p.setLayout( tours_layout);
			tours_desc = new JLabel("Tours");
			tours_p.add(tours_desc);
			tours_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(tours_p, TOURS);


			
			tours_js = new JScrollPane();
			tours_js.setViewportView(tours_t);
			tours_p.add(tours_js);
			
			JPanel bt    = new JPanel();
			tours_ajouter   = new JButton("Ajouter un tour");
			tours_supprimer = new JButton("Supprimer le dernier tour");
			//tours_rentrer   = new JButton("Rentrer les scores du tour s�lectionn�");
			bt.add(tours_ajouter);
			bt.add(tours_supprimer);
			//bt.add(tours_rentrer);
			tours_p.add(bt);	
			tours_p.add(new JLabel("Pour pouvoir ajouter un tour, terminez tous les matchs du précédent."));
			tours_p.add(new JLabel("Le nombre maximum de tours est \"le nombre total d'équipes - 1\""));
			tours_ajouter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					t.ajouterTour();
					Fenetre.this.tracer_tours_tournoi();								
				}
			});
			tours_supprimer.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					t.supprimerTour();
					Fenetre.this.tracer_tours_tournoi();				
				}
			});
		}
		if(to.size() == 0){
			tours_supprimer.setEnabled(false);
			tours_ajouter.setEnabled(true);
		}else{
			
			tours_supprimer.setEnabled( t.getNbTours() > 1);
			
			if(!peutajouter || t.getNbTours()  >= t.getNbEquipes()-1 ){
				tours_ajouter.setEnabled(false);
			}else
				tours_ajouter.setEnabled(true);
		}

		fen.show(c, TOURS);
		//tours_ajouter.setEnabled(peutajouter);
	}

	private AbstractTableModel match_modele;
    private JScrollPane        match_js;
    JTable                     match_jt;
    JPanel                     match_p;
    BoxLayout                  match_layout;
    JLabel                     match_desc;
    JPanel                     match_bas;
    JLabel                     match_statut;
    JButton                    match_valider;

	public void tracer_tournoi_matchs(){
		if(t == null){
			return ;
		}
		majboutons();
		if(match_trace){
			t.majMatch();
			match_modele.fireTableDataChanged();
			majStatutM();
		}else{
			match_trace = true;
			match_p      = new JPanel();
			match_layout = new BoxLayout(match_p, BoxLayout.Y_AXIS);
			match_p.setLayout(match_layout);
			match_desc = new JLabel("Matchs du tournoi");
			match_p.add(match_desc);
			match_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(match_p, MATCHS );
	
			match_modele = new AbstractTableModel() {
				private static final long serialVersionUID = 1L;
				@Override
				public Object getValueAt(int arg0, int arg1) {
					Object r=null;
					switch(arg1){
					case 0:
						r= t.getMatch(arg0).num_tour;
					break;
					case 1:
						r= t.getMatch(arg0).eq1;
					break;
					case 2:
						r= t.getMatch(arg0).eq2;
					break;
					case 3:
						r= t.getMatch(arg0).score1;
					break;
					case 4:
						r= t.getMatch(arg0).score2;
					break;
					}
					return r;
		
				}
				public String getColumnName(int col) {
				        if(col == 0){
				        	return "Tour";
				        }else if(col == 1){
				        	return "Équipe 1";
				        }else if(col == 2){
				        	return "Équipe 2";
				        }else if(col == 3){
				        	return "Score équipe 1";
				        }else if(col == 4){
				        	return "Score équipe 2";
				        }else{
				        	return "??";
				        }
				 }
				@Override
				public int getRowCount() {
					// TODO Auto-generated method stub
					if(t == null)return 0;
					return t.getNbMatchs();
				}
				
				@Override
				public int getColumnCount() {
					// TODO Auto-generated method stub
					return 5;
				}
				public boolean isCellEditable(int x, int y){
					return y > 2 && t.getMatch(x).num_tour == t.getNbTours();
				}
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					MatchM m = t.getMatch(rowIndex);
					if( columnIndex == 0){
						
					}else if( columnIndex == 3){
						try{
							int sco = Integer.parseInt((String)aValue);
							m.score1 = sco;
							t.majMatch(rowIndex);
							
						}catch(Exception e){
							return ;
						}
						
					}else if( columnIndex == 4){
						try{
							int sco = Integer.parseInt((String)aValue);
							m.score2 = sco;
							t.majMatch(rowIndex);
							
						}catch(Exception e){
							return ;
						}
					}

					fireTableDataChanged();
					Fenetre.this.majStatutM();
					Fenetre.this.majboutons();
				}
			};
			match_jt = new JTable(match_modele);

			match_js = new JScrollPane(match_jt);
			match_p.add(match_js);
			//jt.setPreferredSize(getMaximumSize());

			System.out.println("truc2");
			
			match_bas = new JPanel();
			match_bas.add(match_statut = new JLabel("?? Matchs joués"));
			match_bas.add(match_valider = new JButton("Afficher les résultats"));
			match_valider.setEnabled(false);
			
			match_p.add(match_bas);
			majStatutM();

			
		}

		fen.show(c, MATCHS);
		
	}
	
    private JScrollPane        resultats_js;
    JTable                     resultats_jt;
    JPanel                     resultats_p;
    BoxLayout                  resultats_layout;
    JLabel                     resultats_desc;
    JPanel                     resultats_bas;
    JLabel                     resultats_statut;


	public void tracer_tournoi_resultats(){
		if(t == null){
			return ;
		}
		
		Vector< Vector<Object>> to =new Vector<Vector<Object>>();
		Vector<Object> v;		
		try {
			ResultSet rs = s.executeQuery("SELECT equipe,(SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + this.t.id_tournoi + ") as joueur1,(SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = " + this.t.id_tournoi + ") as joueur2, SUM(score) as score, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes, (SELECT COUNT(*) FROM matchs m WHERE m.equipe1 = equipe OR m.equipe2=equipe) as matchs_joues FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + this.t.id_tournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + this.t.id_tournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");
			while(rs.next()){
				v = new Vector<Object>();
				v.add(rs.getInt("equipe"));
				v.add(rs.getString("joueur1"));
				v.add(rs.getString("joueur2"));
				v.add(rs.getInt("score"));
				v.add(rs.getInt("matchs_gagnes"));
				v.add(rs.getInt("matchs_joues"));
				to.add(v);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Numéro d'équipe");
		columnNames.add("Nom joueur 1");
		columnNames.add("Nom joueur 2");
		columnNames.add("Score");
		columnNames.add("Matchs gagnés");
		columnNames.add("Matchs joués");
		resultats_jt = new JTable(to,columnNames );		
		resultats_jt.setAutoCreateRowSorter(true);
		
		if(resultats_trace){
			resultats_js.setViewportView(resultats_jt);
		}else{
			resultats_trace = true;
			resultats_p      = new JPanel();
			resultats_layout = new BoxLayout(resultats_p, BoxLayout.Y_AXIS);
			
			resultats_p.setLayout(resultats_layout);
			resultats_desc = new JLabel("Résultats du tournoi");
			resultats_p.add(resultats_desc);
			resultats_p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			c.add(resultats_p, RESULTATS );
	
			
			
			

			resultats_js = new JScrollPane(resultats_jt);
			resultats_p.add(resultats_js);
			//jt.setPreferredSize(getMaximumSize());

			
			resultats_bas = new JPanel();
			resultats_bas.add(resultats_statut = new JLabel("Gagnant:"));
			
			resultats_p.add(resultats_bas);
		}

		fen.show(c, RESULTATS);
		
	}
	
	private void majStatutM(){
		int total=-1, termines=-1;
		try {
			ResultSet rs = s.executeQuery("Select count(*) as total, (Select count(*) from matchs m2  WHERE m2.id_tournoi = m.id_tournoi  AND m2.termine='oui' ) as termines from matchs m  WHERE m.id_tournoi=" + this.t.id_tournoi +" GROUP by id_tournoi ;");
			rs.next();
			total    = rs.getInt(1);
			termines = rs.getInt(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		match_statut.setText(termines + "/" + total + " matchs terminés");
		match_valider.setEnabled(total == termines);
	}
}
