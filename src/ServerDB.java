import java.sql.*;

//Tutorial de como usar sqlite http://www.tutorialspoint.com/sqlite/sqlite_java.htm

public class ServerDB {
	public static void main( String args[] )
	  {
		crearDB();
	  }
	public static void crearDB(){
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:database.db");
	      System.out.println("Base de datos creda");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
}
