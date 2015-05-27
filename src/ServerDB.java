import java.sql.*;

//Tutorial de como usar sqlite http://www.tutorialspoint.com/sqlite/sqlite_java.htm

public class ServerDB {
	public static void main( String args[] )
	  {
		String nombreBaseDeDatos="database";
		createDB(nombreBaseDeDatos);
		createTable(nombreBaseDeDatos);
		int id;
		String nombre, contra;
		id = 1;
		nombre = "jalil";
		contra = "contra123";
		if(registerUser(nombreBaseDeDatos, id, nombre, contra)){
			System.out.println(nombre + " ha sido registrado");
		}
		if(login(nombreBaseDeDatos, nombre, contra)){
			System.out.println(nombre + " inicio sesion");
		}
		viewAllUsers(nombreBaseDeDatos);
	  }
	public static void viewAllUsers(String nameDB){
		System.out.println("viewAllUsers");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+ nameDB +".db");
	      c.setAutoCommit(false);
	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         System.out.println("Id: " + id);
	         System.out.println("Nombre: " + name);
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	public static boolean registerUser(String nameDB, int id,String nombre, String contra){
		System.out.println("registerUser...");
		System.out.println("Buscando usuario");
		if(login(nameDB, nombre, contra)){
			System.out.println("Usuario ya existe");
			return false;
		}else{
			System.out.println("Usuario no existe");
			Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + nameDB + ".db");
		      c.setAutoCommit(false);
		      System.out.println("database.db abierta");
		      
		      System.out.println("Registrando usuario");
		      stmt = c.createStatement();
		      String sql = "INSERT INTO USER (ID,NAME,PASS) " +
		                   "VALUES (" + id + ", '"+ nombre +"', '" + contra+ "');";
		      System.out.println(sql);
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
	public static boolean login(String nameDB, String nombre, String contra ){
		System.out.println("Login...");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + nameDB + ".db");
	      c.setAutoCommit(false);
	      System.out.println("database.db abierta");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
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
	public static void createDB(String nameDB){
		System.out.println("createDB...");
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + nameDB + ".db");
	      System.out.println("Base de datos database.db creda");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	public static void createTable(String nameDB){
		System.out.println("createTable...");
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + nameDB + ".db");
	      System.out.println("database.db abierta");
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE USER " +
	                   "(ID INT PRIMARY KEY     NOT NULL," +
	                   " NAME           TEXT    NOT NULL, " +
	                   " PASS           TEXT    NOT NULL)";
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