package Service;

import IDAO.TournoiIDAOImpl;
import models.Equipe;
import models.MatchM;
import models.Tournoi;
//import view.TournoiView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

public class TournoiService {
    //attibuts classe controller
    private Vector<Equipe> dataeq = null;
    private Vector<MatchM> datam  = null;
    private Vector<Integer>ideqs  = null;
    static TournoiIDAOImpl idao = TournoiIDAOImpl.getInstance();
    Statement st;
    private Tournoi tournoi;

    public TournoiService(){
        super();
    }

    public int getTournoiStatut(Tournoi tournoi) {
        return tournoi.getStatut();
    }

    public int getNbTours(Tournoi tournoi){
        return idao.getNbTours(tournoi);
    }
    public MatchM getMatch(int index){
        if(datam == null) majMatch();
        return datam.get(index);
    }
    public int getNbMatchs(){
        return idao.getNbMatchs(tournoi);
    }
    public Equipe getEquipe(int index){
        if(dataeq == null)
            majEquipes();
        return dataeq.get(index);

    }

    public int getNbEquipes(){
        if(dataeq == null)
            majEquipes();
        return dataeq.size();
    }

    public void majEquipes(){
        dataeq = new Vector<Equipe>();
        ideqs = new Vector<Integer>();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM equipes WHERE id_tournoi = " + tournoi.getId_tournoi() + " ORDER BY num_equipe;");
            while(rs.next()){
                dataeq.add(new Equipe(rs.getInt("id_equipe"),rs.getInt("num_equipe"), rs.getString("nom_j1"), rs.getString("nom_j2")));
                ideqs.add(rs.getInt("num_equipe"));
            }
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }
    public void majMatch(){
        datam = new Vector<MatchM>();
        try {
            ResultSet rs= st.executeQuery("SELECT * FROM matchs WHERE id_tournoi="+ tournoi.getId_tournoi() + ";");
            while(rs.next()) datam.add(new MatchM(rs.getInt("id_match"),rs.getInt("equipe1"),rs.getInt("equipe2"), rs.getInt("score1"),rs.getInt("score2"),rs.getInt("num_tour"),rs.getString("termine") == "oui"));
            //public models.MatchM(int _idmatch,int _e1,int _e2,int _score1, int _score2, int _num_tour, boolean _termine)
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }

    public void genererMatchs(){
        int nbt = 1;

        System.out.println("Nombre d'�quipes : " + getNbEquipes());
        System.out.println("Nombre de tours  : " + nbt);
        String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
        Vector<Vector<MatchM>> ms;
        ms = getMatchsToDo(getNbEquipes(), nbt);
        int z = 1;
        char v = ' ';
        for(Vector<MatchM> vect :ms){
            for(MatchM m:vect){
                req += v + "(NULL," + tournoi.getId_tournoi() + ", " + z + ", "+ m.getEq1() + ", " + m.getEq2() + ", 'non')";
                v = ',';
            }
            req += "\n";
            z++;
        }
        System.out.println(req);
        try{
            st.executeUpdate(req);
            st.executeUpdate("UPDATE tournois SET statut=2 WHERE id_tournoi=" + tournoi.getId_tournoi() + ";");
            tournoi.setStatut(2);
        }catch(SQLException e){
            System.out.println("Erreur validation �quipes : " + e.getMessage());
        }
    }

    public boolean ajouterTour(){
        // Recherche du nombre de tours actuel
        int nbtoursav;
        if(getNbTours(tournoi) >=  (getNbEquipes() -1) ) return false;
        System.out.println("Eq:" + getNbEquipes() + "  tours" + getNbTours(tournoi));
        try {
            ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+tournoi.getId_tournoi()+"; ");
            rs.next();
            nbtoursav = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Nombre de tours avant:" + nbtoursav);


        if(nbtoursav == 0){
            Vector<MatchM> ms;

            ms = getMatchsToDo(getNbEquipes(), nbtoursav+1).lastElement();

            String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
            char v = ' ';
            for(MatchM m:ms){
                req += v + "(NULL," + tournoi.getId_tournoi() + ", " + (nbtoursav + 1) + ", "+ m.getEq1() + ", " + m.getEq2() + ", 'non')";
                v = ',';
            }
            req += "\n";

            //System.out.println(req);
            try{
                st.executeUpdate(req);
            }catch(SQLException e){
                System.out.println("Erreur ajout tour : " + e.getMessage());
            }
        }else{
            try {
                ResultSet rs;
                //rs = st.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2 AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 AND m.id_tournoi = id_tournoi )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + this.id_tournoi + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + this.id_tournoi + ") GROUP BY equipe ORDER BY matchs_gagnes DESC;");

                rs = st.executeQuery("SELECT equipe, (SELECT count(*) FROM matchs m WHERE (m.equipe1 = equipe AND m.score1 > m.score2  AND m.id_tournoi = id_tournoi) OR (m.equipe2 = equipe AND m.score2 > m.score1 )) as matchs_gagnes FROM  (select equipe1 as equipe,score1 as score from matchs where id_tournoi=" + tournoi.getId_tournoi() + " UNION select equipe2 as equipe,score2 as score from matchs where id_tournoi=" + tournoi.getId_tournoi() + ") GROUP BY equipe  ORDER BY matchs_gagnes DESC;");


                ArrayList<Integer> ordreeq= new ArrayList<Integer>();
                while(rs.next()){
                    ordreeq.add(rs.getInt("equipe"));
                    System.out.println(rs.getInt(1) +" _ " + rs.getString(2));
                }
                System.out.println("Taille"+ordreeq.size());
                int i;
                boolean fini;
                String req = "INSERT INTO matchs ( id_match, id_tournoi, num_tour, equipe1, equipe2, termine ) VALUES\n";
                char v = ' ';
                while(ordreeq.size() > 1){
                    System.out.println("Taille " + ordreeq.size());
                    int j=0;
                    while(j<ordreeq.size()) {
                        System.out.println(ordreeq.get(j));
                        j++;
                    }
                    i=1;
                    do{
                        rs = st.executeQuery("SELECT COUNT(*) FROM matchs m WHERE ( (m.equipe1 = " + ordreeq.get(0) + " AND m.equipe2 = " + ordreeq.get(i) + ") OR (m.equipe2 = " + ordreeq.get(0) + " AND m.equipe1 = " + ordreeq.get(i) + ")  )");
                        rs.next();
                        if(rs.getInt(1) > 0){
                            // Le match est d�j� jou�
                            i++;
                            fini = false;

                        }else{
                            fini = true;
                            req += v + "(NULL," + tournoi.getId_tournoi() + ", " + (nbtoursav + 1) + ", "+  ordreeq.get(0) + ", " +  ordreeq.get(i) + ", 'non')";
                            System.out.println(ordreeq.get(0) + ", " +  ordreeq.get(i));
                            ordreeq.remove(0);
                            ordreeq.remove(i-1);
                            v = ',';
                        }
                    }while(!fini);
                }
                System.out.println(req);
                st.executeUpdate(req);
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return true;
    }
    public void supprimerTour(){
        int nbtoursav;
        try {
            ResultSet rs = st.executeQuery("SELECT MAX (num_tour)  FROM matchs WHERE id_tournoi="+tournoi.getId_tournoi()+"; ");
            rs.next();
            nbtoursav = rs.getInt(1);
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return ;
        }
        //if(tour != nbtoursav) return ;

        try {
            st.executeUpdate("DELETE FROM matchs WHERE id_tournoi="+ tournoi.getId_tournoi()+" AND num_tour=" + nbtoursav);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Erreur del tour : " + e.getMessage());
        }
    }

    public static void deleteTournoi(String nom){
        idao.deleteTournoi(nom);
    }
    public static int creerTournoi(){
        String nomNewTournoi = (String)JOptionPane.showInputDialog(
                null,
                "Entrez le nom du tournoi",
                "Nom du tournoi",
                JOptionPane.PLAIN_MESSAGE);

        if(nomNewTournoi == null || nomNewTournoi == ""){
            return 1;
        }else{
            try {
                nomNewTournoi =  mysql_real_escape_string(nomNewTournoi);
                if(nomNewTournoi.length() < 3){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Nom trop court.");
                    return 2;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(nomNewTournoi == ""){
                JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Ne pas mettre de caract�res sp�ciaux ou accents dans le nom");
                return 2;
            }else{
                Tournoi T = idao.getOne(nomNewTournoi);
                if(T!=null){
                    JOptionPane.showMessageDialog(null, "Le tournoi n'a pas �t� cr��. Un tournoi du m�me nom existe d�j�");
                    return 2;
                }
                System.out.println("INSERT INTO tournois (id_tournoi, nb_matchs, nom_tournoi, statut) VALUES (NULL, 10, '"+nomNewTournoi+"', 0)");
                idao.creerTournoi(nomNewTournoi);
                //s2.executeUpdate("INSERT INTO tournois (id")
            }
        }
        return 0;
    }

    public void ajouterEquipe(){
        int a_aj= this.dataeq.size()+1;
        for ( int i=1;i <= this.dataeq.size(); i++){
            if(!ideqs.contains(i)){
                a_aj=i;
                break;
            }
        }
        try {
            st.executeUpdate("INSERT INTO equipes (id_equipe,num_equipe,id_tournoi,nom_j1,nom_j2) VALUES (NULL,"+a_aj+", "+tournoi.getId_tournoi() + ",'\"Joueur 1\"', '\"Joueur 2\"');");
            majEquipes();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void majEquipe(int index){
        try {
            String req = "UPDATE equipes SET nom_j1 = '" + mysql_real_escape_string(getEquipe(index).getEq1()) + "', nom_j2 = '" + mysql_real_escape_string(getEquipe(index).getEq2()) + "' WHERE id_equipe = " + getEquipe(index).getId() + ";";
            System.out.println(req);
            st.executeUpdate(req);
            majEquipes();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void majMatch(int index){
        String termine = (getMatch(index).getScore1() > 0 || getMatch(index).getScore2() > 0) ? "oui":"non";
        System.out.println(termine);
        String req="UPDATE matchs SET equipe1='" + getMatch(index).getEq1() + "', equipe2='" + getMatch(index).getEq2() + "',  score1='" + getMatch(index).getScore1() + "',  score2='" +getMatch(index).getScore2() + "', termine='" + termine + "' WHERE id_match = " + getMatch(index).getIdmatch() + ";";
        try {
            st.executeUpdate(req);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        majMatch();
    }
    public void supprimerEquipe(int ideq){
        try {
            int numeq;
            ResultSet rs = st.executeQuery("SELECT num_equipe FROM equipes WHERE id_equipe = " + ideq);
            rs.next();
            numeq = rs.getInt(1);
            rs.close();
            st.executeUpdate("DELETE FROM equipes WHERE id_tournoi = " + tournoi.getId_tournoi()+ " AND id_equipe = " + ideq);
            st.executeUpdate("UPDATE equipes SET num_equipe = num_equipe - 1 WHERE id_tournoi = " + tournoi.getId_tournoi() + " AND num_equipe > " + numeq);
            majEquipes();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String mysql_real_escape_string( String str)
            throws Exception
    {
        if (str == null) {
            return null;
        }

        if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
            return str;
        }

        String clean_string = str;
        clean_string = clean_string.replaceAll("\\n","\\\\n");
        clean_string = clean_string.replaceAll("\\r", "\\\\r");
        clean_string = clean_string.replaceAll("\\t", "\\\\t");
        clean_string = clean_string.replaceAll("\\00", "\\\\0");
        clean_string = clean_string.replaceAll("'", "''");
        return clean_string;

    }

    public static Vector<Vector<MatchM>> getMatchsToDo(int nbJoueurs, int nbTours){
        if( nbTours  >= nbJoueurs){
            System.out.println("Erreur tours < equipes");
            return null;
        }

        int[]   tabJoueurs;
        if((nbJoueurs % 2) == 1){
            // Nombre impair de joueurs, on rajoute une �quipe fictive
            tabJoueurs   = new int[nbJoueurs+1];
            tabJoueurs[nbJoueurs] = -1;
            for(int z = 0; z < nbJoueurs;z++){
                tabJoueurs[z] = z+1;
            }
            nbJoueurs++;
        }else{
            tabJoueurs   = new int[nbJoueurs];
            for(int z = 0; z < nbJoueurs;z++){
                tabJoueurs[z] = z+1;
            }
        }

        boolean quitter;
        int     i, increment  = 1, temp;

        Vector<Vector<MatchM>> retour = new Vector<Vector<MatchM>>();

        Vector<MatchM> vm;

        for( int r = 1; r <= nbTours;r++){
            if(r > 1){
                temp = tabJoueurs[nbJoueurs - 2];
                for(i = (nbJoueurs - 2) ; i > 0; i--){
                    tabJoueurs[i] = tabJoueurs[i-1];
                }
                tabJoueurs[0] = temp;
            }
            i       = 0;
            quitter = false;
            vm = new Vector<MatchM>();
            while(!quitter){
                if (tabJoueurs[i] == -1 || tabJoueurs[nbJoueurs - 1  - i] == -1){
                    // Nombre impair de joueur, le joueur n'a pas d'adversaire
                }else{
                    vm.add(new MatchM(i,tabJoueurs[i], tabJoueurs[nbJoueurs - 1  - i],0,0,0,false));
                }

                i+= increment;
                if(i >= nbJoueurs / 2){
                    if(increment == 1){
                        quitter = true;
                        break;
                    }else{
                        increment = -2;
                        if( i > nbJoueurs / 2){
                            i = ((i > nbJoueurs / 2) ? i - 3 : --i) ;
                        }
                        if ((i < 1) && (increment == -2)){
                            quitter = true;
                            break;
                        }
                    }
                }
            }
            retour.add(vm);
        }
        return retour;
    }
}
