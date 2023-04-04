import view.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.Statement;  
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * The type Belote.
 */
public class Belote {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 * @throws SQLException the sql exception
	 */
	public static void main(String[] args) throws SQLException {
		//TESSST
        Connection connection = null;  
        Statement statement = null;  		

		// Connection � la base de donn�es
		// et cr�ation des champs 


		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();

			String dos = System.getProperty("user.dir");
			String beloteFile = dos + "\\jBelote";
			String createFile = dos + "\\create.sql";
			System.out.println("Dossier de stockage:" + beloteFile);
			if( !new File(beloteFile).isDirectory() ){
				new File(beloteFile).mkdir();
			}
			connection = DriverManager
					.getConnection("jdbc:hsqldb:file:" + beloteFile + "\\belote","sa","");
			statement = connection.createStatement();

			importSQL(connection, new File(createFile));

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
			
		Window f = new Window(statement);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//statement.execute("SHUTDOWN;");
		//statement.close();  
        //connection.close();  		
	}


	/**
	 * Import sql.
	 *
	 * @param conn the connection
	 * @param in   the file
	 * @throws SQLException          the sql exception
	 * @throws FileNotFoundException the file not found exception
	 */
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


