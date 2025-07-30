package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // <-- esse import estava faltando

public class UsuarioDAO {

    public static void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "telefone TEXT," +
                "cpf TEXT," +
                "senha TEXT NOT NULL" +
                ");";

        try (Connection conn = Database.conectar(); // <-- corrigido
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela de usuários verificada/criada.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }

    public static boolean cadastrarUsuario(String nome, String email, String telefone, String cpf, String senha) {
        String sql = "INSERT INTO usuarios (nome, email, telefone, cpf, senha) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.conectar(); // <-- corrigido
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, telefone);
            pstmt.setString(4, cpf);
            pstmt.setString(5, senha);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    public static boolean validarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = Database.conectar(); // <-- corrigido
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // se existir, login válido

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
