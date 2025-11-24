import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDAO {

    public void registrarLog(Connection conn, String usuario, String accion, String resultado, String detalles) {
        String sql = "INSERT INTO logs_auditoria (usuario, accion, resultado, detalles) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, accion);
            stmt.setString(3, resultado);
            stmt.setString(4, detalles);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error crítico escribiendo log: " + e.getMessage());
        }
    }
}