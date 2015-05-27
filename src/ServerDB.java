import java.sql.*;

//Tutorial de como usar sqlite http://www.tutorialspoint.com/sqlite/sqlite_java.htm

public class ServerDB {
	public static void main( String args[] )
	  {
		createDB();
		creatTable();
	  }
	public static void viewAllUsers(){
		System.out.print("viewAllUsers");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:database.db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Users;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         System.out.println("Id: "+id);
	         System.out.println("Nombre: "+name);
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	public static boolean registerUser(int id,String nombre, String contra){
		System.out.println("registerUser...");
		System.out.println("Buscando usuario");
		if(login(nombre,contra)){
			System.out.println("Usuario ya existe");
			return false;
		}else{
			System.out.println("Usuario no existe");
			Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:database.db");
		      c.setAutoCommit(false);
		      System.out.println("database.db abierta");
		      
		      System.out.println("Registrando usuario");
		      stmt = c.createStatement();
		      String sql = "INSERT INTO USERS (ID,NAME,PASS) " +
		                   "VALUES (1, 'Paul', '32' );"; 
		      stmt.executeUpdate(sql);
		      
		      stmt.close();
		      c.commit();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Registro con exito");
		    return true;
		}
	}
	public static boolean login(String nombre, String contra ){
		System.out.println("Login...");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:database.db");
	      c.setAutoCommit(false);
	      System.out.println("database.db abierta");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM Users;" );
	      System.out.println("Buscando Usuario y contraseña");
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         String pass  = rs.getString("pass");
	         if (name == nombre){
	        	 if(pass == contra){
	        		 return true;
	        	 }
	         }
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Login correcto");
		return false;
	}
	public static void createDB(){
		System.out.print("createDB...");
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:database.db");
	      System.out.println("Base de datos database.db creda");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	public static void creatTable(){
		System.out.print("creatTable...");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:database.db");
	      System.out.println("database.db abierta");
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE USERS " +
	                   "(ID INT PRIMARY KEY     NOT NULL," +
	                   " NAME           TEXT    NOT NULL, " +
	                   " PASS           TEXT    NOT NULL, ";
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	      System.out.println("Tabla creada");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
}