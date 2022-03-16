package application;

import org.postgresql.util.PSQLException;
import view.Menus;

import java.sql.Connection;
import java.sql.SQLException;

public class Program {

    public static void main(String[] args) {

        boolean repetir = true;

        while (repetir) {
            try (Connection conn = Menus.conectarBancoDeDados()){
                System.out.println("Conexão Bem Sucedida!");
                Menus.menuCrud(conn);

                repetir = false;
            } catch (SQLException e) {
                System.err.println("Erro ao se conectar ao banco de dados! Causado por: " + e.getMessage());
                e.printStackTrace();
//			conn.rollback();
            } catch (NumberFormatException e) {
                System.err.println("Entrada de dados Inválida! Causado por: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
