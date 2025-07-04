/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jdbctest1;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author USERS
 */
public class ConexionMySQL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String url = "jdbc:mysql://localhost:3307/jdbctest1";
        String usuario = "root";
        String contraseña = "";
        Connection conexion;
        Statement statement;
        ResultSet rs;
        
        try {
            // 1. Cargar el driver JDBC
           // Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Establecer conexión
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            statement = conexion.createStatement();
            rs = statement.executeQuery("SELECT * FROM usuarios");

            // 5. Procesar resultados
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
            
            //insercion de datos
            statement.execute("INSERT INTO `usuarios` (`id`, `nombre`) VALUES (NULL, 'Jluis');");
            System.out.println("");
            rs  =statement.executeQuery("SELECT * FROM usuarios");
            while(rs.next()){
                System.out.println(rs.getString("nombre"));
        }
            
            // Actualización de datos
            statement.execute("UPDATE `usuarios` SET `nombre` = 'jrodriguez' WHERE `usuarios`.`id` = 2;");
            System.out.println("");
            rs = statement.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
            
            // Borrado o eliminación de datos
            statement.execute("DELETE FROM `usuarios` WHERE `usuarios`.`id` = 2");
            System.out.println("");
            rs = statement.executeQuery("SELECT * FROM usuarios");
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
}


        } catch (SQLException ex) {
            Logger.getLogger(ConexionMySQL.class.getName()).log(Level.SEVERE, null, ex);


        }
        
    }
    
}
