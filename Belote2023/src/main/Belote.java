package main;

import models.CONSTANTS;
import view.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;  
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Belote {


	private final String db ="";
	private final String url = "jdbc:hsqldb:file:";
	private final String user = "sa";
	private final String pwd = "";
	private static Connection connect = null;
	private Properties properties = new Properties();

	private Belote(){
		String beloteFile = System.getProperty("user.dir") + "\\jBelote";
		String createFile = System.getProperty("user.dir") + "\\create.sql";
		String dataBaseConfigFile = "/database.properties";
		try{
			Class.forName("org.hsqldb.jdbcDriver");
			this.loadConfig(dataBaseConfigFile);
			this.connect = DriverManager.getConnection(properties.getProperty("DBURL")+ beloteFile + "\\" + properties.getProperty("DBName"),
					properties.getProperty("DBUser"),
					properties.getProperty("DBPassword"));
//			System.out.println("Dossier de stockage:" + beloteFile);
			if( !new File(beloteFile).isDirectory() ){
				new File(beloteFile).mkdir();
			}
			importSQL(connect, new File(createFile));
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, CONSTANTS.SQL_CONNECTION_ERROR);
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
		Statement statement;
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Window f = new Window();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public static void importSQL(Connection conn, File in) throws SQLException, FileNotFoundException
	{
		Scanner s = new Scanner(in);
		s.useDelimiter("(;(\r)?\n)|(--\n)");
		Statement st = null;
		try{
			st = conn.createStatement();
			while (s.hasNext()){
				String line = s.next();
				if (line.startsWith("/*!") && line.endsWith("*/")){
					int i = line.indexOf(' ');
					line = line.substring(i + 1, line.length() - " */".length());
				}
				if (line.trim().length() > 0){
					//System.out.println("Req:" + line);
					st.execute(line);
				}
			}
		}finally{
			if (st != null) st.close();
		}
	}

	/**
	 * Importe un fichier SQL dans la base de données.
	 * @param dataBaseConfigFile : chemin du fichier SQL.
	 * @throws FileNotFoundException si le fichier SQL n'est pas trouvé.
	 */
	private void loadConfig(String dataBaseConfigFile) {
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(dataBaseConfigFile);
			properties.load(inputStream);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de donn�e. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.");
		}
	}
}


