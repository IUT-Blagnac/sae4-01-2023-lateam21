package main;

import view.Fenetre;

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


	private String db ="";
	private String url = "jdbc:hsqldb:file:";
	private String user = "sa";
	private String pwd = "";
	private static Connection connect = null;
	private Belote(){
		try{
			String dos = System.getProperty("user.dir");
			String beloteFile = dos + "\\jBelote";
			String createFile = dos + "\\create.sql";
			Class.forName("org.hsqldb.jdbcDriver");
			connect = DriverManager.getConnection(url+ beloteFile + "\\belote",user,pwd);
			System.out.println("Dossier de stockage:" + beloteFile);
			if( !new File(beloteFile).isDirectory() ){
				new File(beloteFile).mkdir();
			}
			importSQL(connect, new File(createFile));
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de donn�e. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getUniqueInstanceBelote(){
		if(connect==null)
			new Belote();
		return connect;
	}

	public static void main(String[] args) {
		Connection connect = getUniqueInstanceBelote();
		Statement statement = null;
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Fenetre f = new Fenetre(statement);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public static void importSQL(Connection conn, File in) throws SQLException, FileNotFoundException
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


