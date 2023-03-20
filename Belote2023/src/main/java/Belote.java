import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.Statement;  
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Belote {

	public static void main(String[] args) throws SQLException {
		
        Connection connection = null;  
        Statement statement = null;  		

		// Connection � la base de donn�es
		// et cr�ation des champs 

<<<<<<< HEAD:Belote2023/src/main/java/Belote.java
=======

>>>>>>> ba3cd638f6be3addcf60849e501be94d4092c6fc:Belote2023/src/Belote.java
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();

			String dos = System.getProperty("user.dir") + "\\Belote2023\\jBelote";
			System.out.println("Dossier de stockage:" + dos);
			if( !new File(dos).isDirectory() ){
				new File(dos).mkdir();
			}
			connection = DriverManager
					.getConnection("jdbc:hsqldb:file:" + dos + "\\belote","sa","");
			statement = connection.createStatement();

			importSQL(connection, new File("create.sql"));

		}catch(SQLException e){
				JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de donn�e. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.");
				System.out.println(e.getMessage());
				System.exit(0);
			}catch (Exception e) {  
			    JOptionPane.showMessageDialog(null, "Erreur lors de l'initialisation du logiciel. Vérifiez votre installation Java et vos droits d'acc�s sur le dossier AppData.");
			    System.out.println(e.getMessage());
			    System.exit(0);
			} 
	        
		// Interface graphique
			
		Fenetre f = new Fenetre(statement);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//statement.execute("SHUTDOWN;");
		//statement.close();  
        //connection.close();  		
	}

	
	private static void importSQL(Connection conn, File in) throws SQLException, FileNotFoundException
	{
	        Scanner s = new Scanner(in);
	        s.useDelimiter("(;(\r)?\n)|(--\n)");
	        Statement st = null;
	        try
	        {
	                st = conn.createStatement();
	                while (s.hasNext())
	                {
	                        String line = s.next();
	                        if (line.startsWith("/*!") && line.endsWith("*/"))
	                        {
	                                int i = line.indexOf(' ');
	                                line = line.substring(i + 1, line.length() - " */".length());
	                        }

	                        if (line.trim().length() > 0)
	                        {
	                        	//System.out.println("Req:" + line);
	                                st.execute(line);
	                        }
	                }
	        }
	        finally
	        {
	                if (st != null) st.close();
	        }
	}
}

