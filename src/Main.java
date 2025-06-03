import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        BancoAlunos banco = new BancoAlunos();

        File dbFile = new File("alunos.db");
        if (!dbFile.exists() || dbFile.length() == 0) {
            System.out.println("Iniciando banco de dados...");
            banco.criarTabela();
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SISTEMA DE ALUNOS ---");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Ver todos os alunos");
            System.out.println("3. Editar aluno");
            System.out.println("4. Deletar aluno");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do aluno: ");
                    String nome = scanner.nextLine();

                    System.out.print("Idade: ");
                    int idade = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Curso: ");
                    String curso = scanner.nextLine();

                    Aluno novoAluno = new Aluno(nome, idade, curso);
                    if (banco.salvar(novoAluno)) {
                        System.out.println("Aluno cadastrado com ID: " + novoAluno.getId());
                    } else {
                        System.out.println("Erro ao cadastrar aluno!");
                    }
                    break;

                case 2:
                    List<Aluno> alunos = banco.listar();
                    if (alunos.isEmpty()) {
                        System.out.println("Nenhum aluno cadastrado!");
                    } else {
                        System.out.println("\n--- LISTA DE ALUNOS ---");
                        for (Aluno a : alunos) {
                            System.out.println(a);
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o ID do aluno a editar: ");
                    int idEditar = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();

                    System.out.print("Nova idade: ");
                    int novaIdade = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo curso: ");
                    String novoCurso = scanner.nextLine();

                    Aluno alunoEditado = new Aluno(novoNome, novaIdade, novoCurso);
                    alunoEditado.setId(idEditar);

                    if (banco.atualizar(alunoEditado)) {
                        System.out.println("Aluno atualizado com sucesso!");
                    } else {
                        System.out.println("Erro ao atualizar ou aluno não encontrado!");
                    }
                    break;

                case 4:
                    System.out.print("Digite o ID do aluno a deletar: ");
                    int idDeletar = scanner.nextInt();
                    scanner.nextLine();

                    if (banco.deletar(idDeletar)) {
                        System.out.println("Aluno deletado com sucesso!");
                    } else {
                        System.out.println("Erro ao deletar ou aluno não encontrado!");
                    }
                    break;

                case 5:
                    System.out.println("Encerrando o sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida! Digite de 1 a 5.");
            }
        }
    }
}