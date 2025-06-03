public class Aluno {
    public int id;
    public String nome;
    public int idade;
    public String curso;

    public Aluno() {}

    public Aluno(String nome, int idade, String curso) {
        this.nome = nome;
        this.idade = idade;
        this.curso = curso;
    }

    public String mostrar() {
        return "ID: " + id + " | Nome: " + nome + " | Idade: " + idade + " | Curso: " + curso;
    }
}