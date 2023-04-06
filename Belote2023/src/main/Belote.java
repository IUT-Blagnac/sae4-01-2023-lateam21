package main;

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


/**
 * The type Belote.
 */
public class Belote {


	/**
	 * The Db.
	 */
	private final String db ="";
	/**
	 * The Url.
	 */
	private final String url = "jdbc:hsqldb:file:";
	/**
	 * The User.
	 */
	private final String user = "sa";
	/**
	 * The Pwd.
	 */
	private final String pwd = "";
	/**
	 * The constant connect.
	 */
	private static Connection connect = null;
	private Properties properties = new Properties();

	/**
	 * Instantiates a new Belote.
	 */
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
			JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de donn�e. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.");
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get unique instance belote connection.
	 *
	 * @return the connection
	 */
	public static Connection getUniqueInstanceBelote(){
		if(connect==null)
			new Belote();
		return connect;
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		Connection connect = getUniqueInstanceBelote();
		Statement statement = null;
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Window f = new Window();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/**
	 * Import sql.
	 *
	 * @param conn the connection
	 * @param in   the in
	 * @throws SQLException          the sql exception
	 * @throws FileNotFoundException the file not found exception
	 */
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


