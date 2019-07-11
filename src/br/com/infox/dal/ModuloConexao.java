/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;

// importando
import java.sql.*;

/**
 *
 * @author carlos
 */
public class ModuloConexao {
// metodo responsavel por estabelecer a coneção com o banco de dados  

    public static Connection conector() {
        java.sql.Connection conexao = null;
        //a linha abaixo chama o drive
        String driver = "com.mysql.jdbc.Driver";
        //informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox";
        String user = "root";
        String password = "96132680";
        // estabelecendo conexão com o banco de dados
        try {
            
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
                    
                    
        } catch (Exception  e) {
            // linha comentada a baixo detalha o erro na conexão
            // System.out.println(e);
            return null;
        }

    }
}
