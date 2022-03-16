package connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class FactoryConnection {

    private String jdbcUrl= "jdbc:postgresql://localhost/market?useTimezone=true&serverTimezone=UTC";
    private String user = "postgres";
    private String password = "root";
    private DataSource dataSource;

    public FactoryConnection() {
        ComboPooledDataSource comboPooled = new ComboPooledDataSource();
        comboPooled.setJdbcUrl(jdbcUrl);
        comboPooled.setUser(user);
        comboPooled.setPassword(password);
        comboPooled.setMaxPoolSize(18);
        this.dataSource = comboPooled;
    }

    public FactoryConnection(String user, String password) {
        ComboPooledDataSource comboPooled = new ComboPooledDataSource();
        comboPooled.setJdbcUrl(jdbcUrl);
        comboPooled.setUser(user);
        comboPooled.setPassword(password);
        comboPooled.setMaxPoolSize(18);
        this.dataSource = comboPooled;
    }

    public Connection openConnection() throws SQLException {
        System.out.println("Preparando para abrir comunicação com o Banco de Dados...");
        return this.dataSource.getConnection();
    }

    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
        System.out.println("Conexão Encerrada!");
    }
}
