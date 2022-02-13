package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Connection to the database in order to get the much needed information such as the spawning waves.
 * @author Paul
 *
 */
public class Sql {
	/**
	 * Sql connection for getting the waves from database in order to play.
	 * @param waveNumber -> I need the current wave number to match it with the wave id in the database in order to get
	 * the right data.
	 * @return
	 * @throws Exception
	 */
	public static WaveProperties getWaves(int waveNumber) throws Exception {
		try {
			String url = "jdbc:sqlserver://bd2021.database.windows.net;databaseName=paulfeier";
			String username = "paulfeieruser";
			String password = "X+RB=2r=a=Fh622A";
			Connection connection = DriverManager.getConnection(url, username, password);
			
			//System.out.println("Connected");
			   
		   Statement S = connection.createStatement();
		   ResultSet RS = S.executeQuery("select * from EnemyWaves");
		   int id = 0;
		   while(RS.next() && id <= waveNumber) {
			   id++;
			   if (id == waveNumber) {
				   int enemyNumber = RS.getInt("Enemies");
				   int enemyHealth = RS.getInt("EnemyHealth");
				   String enemyType = RS.getString("EnemyType");
				   
				   //setez wave-ul corespunzator
				   //System.out.println("mere");
				   WaveProperties wp = new WaveProperties(id, enemyNumber, enemyHealth, enemyType);
				   //System.out.println(wp.getId());
				   return wp;
			   }
		   }
			
		   connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
