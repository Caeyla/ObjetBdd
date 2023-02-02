package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDeliver {

    Connection con;
    String databaseName;

    public  Connection getConnection() {
        
        if (con == null) {

            try {

                Class.forName("org.postgresql.Driver");
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName + "?user=postgres&password=caeyla01");
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
        try {
            if (con.isClosed()) {
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName + "?user=postgres&password=caeyla01");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDeliver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    
}
