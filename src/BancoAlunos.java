import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoAlunos {
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:alunos.db");
    }

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS alunos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "idade INTEGER," +
                "curso TEXT)";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public boolean salvar(Aluno aluno) {
        String sql = "INSERT INTO alunos(nome, idade, curso) VALUES(?,?,?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, aluno.nome);
            pstmt.setInt(2, aluno.idade);
            pstmt.setString(3, aluno.curso);
            pstmt.executeUpdate();


            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    aluno.id = rs.getInt(1);
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
            return false;
        }
    }

    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aluno a = new Aluno(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("curso"));
                a.id = rs.getInt("id");
                alunos.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public boolean atualizar(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, idade = ?, curso = ? WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, aluno.nome);
            pstmt.setInt(2, aluno.idade);
            pstmt.setString(3, aluno.curso);
            pstmt.setInt(4, aluno.id);

            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro de deletar aluno: " + e.getMessage());
            return false;
        }
    }
}