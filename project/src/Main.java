import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

// Classe Livro
class Livro {
    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private int ano;
    private boolean emprestado;

    public Livro(String titulo, String autor, String isbn, String editora, int ano) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.editora = editora;
        this.ano = ano;
        this.emprestado = false;
    }

    @Override
    public String toString() {
        return String.format("Título: %s, Autor: %s, ISBN: %s, Editora: %s, Ano: %d, Emprestado: %b",
                titulo, autor, isbn, editora, ano, emprestado);
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }
}

// Classe Usuario
abstract class Usuario {
    private String nome;
    private String cpf;
    private String endereco;
    private ArrayList<Livro> emprestimos;

    public Usuario(String nome, String cpf, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.emprestimos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Nome: %s, CPF: %s, Endereço: %s",
                nome, cpf, endereco);
    }

    public String getCpf() {
        return cpf;
    }

    public ArrayList<Livro> getEmprestimos() {
        return emprestimos;
    }

    public void adicionarEmprestimo(Livro livro) {
        if (emprestimos.size() < 3) {
            emprestimos.add(livro);
            livro.setEmprestado(true);
        } else {
            System.out.println("Limite de empréstimos atingido!");
        }
    }

    public void removerEmprestimo(Livro livro) {
        emprestimos.remove(livro);
        livro.setEmprestado(false);
    }
}

// Classe UsuarioComum
class UsuarioComum extends Usuario {
    public UsuarioComum(String nome, String cpf, String endereco) {
        super(nome, cpf, endereco);
    }
}

// Classe Bibliotecario
class Bibliotecario extends Usuario {
    public Bibliotecario(String nome, String cpf, String endereco) {
        super(nome, cpf, endereco);
    }

    public void adicionarUsuario(Biblioteca biblioteca, Usuario usuario) {
        biblioteca.adicionarUsuario(usuario);
    }

    public void removerUsuario(Biblioteca biblioteca, Usuario usuario) {
        biblioteca.removerUsuario(usuario);
    }

    public void adicionarLivro(Biblioteca biblioteca, Livro livro) {
        biblioteca.adicionarLivro(livro);
    }

    public void removerLivro(Biblioteca biblioteca, Livro livro) {
        biblioteca.removerLivro(livro);
    }
}

// Classe Biblioteca
class Biblioteca {
    private ArrayList<Livro> livros;
    private ArrayList<Usuario> usuarios;
    private HashMap<Usuario, ArrayList<Livro>> emprestimos;

    public Biblioteca() {
        livros = new ArrayList<>();
        usuarios = new ArrayList<>();
        emprestimos = new HashMap<>();
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void removerLivro(Livro livro) {
        livros.remove(livro);
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        emprestimos.put(usuario, new ArrayList<>());
    }

    public void removerUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        emprestimos.remove(usuario);
    }

    public Livro buscarLivroPorISBN(String isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void emprestarLivro(Usuario usuario, Livro livro) {
        if (livro != null && !livro.isEmprestado()) {
            usuario.adicionarEmprestimo(livro);
            emprestimos.get(usuario).add(livro);
            System.out.println("Livro emprestado com sucesso!");
        } else {
            System.out.println("Livro não disponível ou já emprestado.");
        }
    }

    public void devolverLivro(Usuario usuario, Livro livro) {
        if (emprestimos.get(usuario).contains(livro)) {
            usuario.removerEmprestimo(livro);
            livro.setEmprestado(false);
            emprestimos.get(usuario).remove(livro);
            System.out.println("Livro devolvido com sucesso!");
        } else {
            System.out.println("Livro não encontrado nos empréstimos do usuário.");
        }
    }

    public void gerarRelatorio() {
        System.out.println("Relatório de Livros:");
        for (Livro livro : livros) {
            System.out.println(livro);
        }

        System.out.println("\nRelatório de Usuários:");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
            System.out.println("Empréstimos: ");
            for (Livro livro : usuario.getEmprestimos()) {
                System.out.println(livro);
            }
        }
    }
}

// Classe Principal com interface no terminal
public class Main {
    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Remover Livro");
            System.out.println("3. Adicionar Usuário");
            System.out.println("4. Remover Usuário");
            System.out.println("5. Buscar Livro");
            System.out.println("6. Emprestar Livro");
            System.out.println("7. Devolver Livro");
            System.out.println("8. Gerar Relatório");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    adicionarLivro();
                    break;
                case 2:
                    removerLivro();
                    break;
                case 3:
                    adicionarUsuario();
                    break;
                case 4:
                    removerUsuario();
                    break;
                case 5:
                    buscarLivro();
                    break;
                case 6:
                    emprestarLivro();
                    break;
                case 7:
                    devolverLivro();
                    break;
                case 8:
                    gerarRelatorio();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionarLivro() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Editora: ");
        String editora = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        Livro livro = new Livro(titulo, autor, isbn, editora, ano);
        biblioteca.adicionarLivro(livro);
        System.out.println("Livro adicionado com sucesso!");
    }

    private static void removerLivro() {
        System.out.print("Informe o ISBN do livro a ser removido: ");
        String isbn = scanner.nextLine();
        Livro livroRemover = biblioteca.buscarLivroPorISBN(isbn);
        if (livroRemover != null) {
            biblioteca.removerLivro(livroRemover);
            System.out.println("Livro removido com sucesso!");
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    private static void adicionarUsuario() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Tipo de usuário (1 para Bibliotecário, 2 para Usuário Comum): ");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        Usuario usuario;
        if (tipo == 1) {
            usuario = new Bibliotecario(nome, cpf, endereco);
        } else {
            usuario = new UsuarioComum(nome, cpf, endereco);
        }
        biblioteca.adicionarUsuario(usuario);
        System.out.println("Usuário adicionado com sucesso!");
    }

    private static void removerUsuario() {
        System.out.print("Informe o CPF do usuário a ser removido: ");
        String cpf = scanner.nextLine();
        Usuario usuarioRemover = null;
        for (Usuario usuario : biblioteca.getUsuarios()) {
            if (usuario.getCpf().equals(cpf)) {
                usuarioRemover = usuario;
                break;
            }
        }
        if (usuarioRemover != null) {
            biblioteca.removerUsuario(usuarioRemover);
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }

    private static void buscarLivro() {
        System.out.print("Informe o critério de busca (1 para Título, 2 para Autor, 3 para ISBN, 4 para Editora, 5 para Ano): ");
        int criterio = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        switch (criterio) {
            case 1:
                System.out.print("Título: ");
                String titulo = scanner.nextLine();
                for (Livro livro : biblioteca.getLivros()) {
                    if (livro.toString().contains(titulo)) {
                        System.out.println(livro);
                    }
                }
                break;
            case 2:
                System.out.print("Autor: ");
                String autor = scanner.nextLine();
                for (Livro livro : biblioteca.getLivros()) {
                    if (livro.toString().contains(autor)) {
                        System.out.println(livro);
                    }
                }
                break;
            case 3:
                System.out.print("ISBN: ");
                String isbn = scanner.nextLine();
                Livro livro = biblioteca.buscarLivroPorISBN(isbn);
                if (livro != null) {
                    System.out.println(livro);
                } else {
                    System.out.println("Livro não encontrado!");
                }
                break;
            case 4:
                System.out.print("Editora: ");
                String editora = scanner.nextLine();
                for (Livro livroEditora : biblioteca.getLivros()) {
                    if (livroEditora.toString().contains(editora)) {
                        System.out.println(livroEditora);
                    }
                }
                break;
            case 5:
                System.out.print("Ano: ");
                int ano = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha
                for (Livro livroAno : biblioteca.getLivros()) {
                    if (livroAno.toString().contains(String.valueOf(ano))) {
                        System.out.println(livroAno);
                    }
                }
                break;
            default:
                System.out.println("Critério inválido!");
        }
    }

    private static void emprestarLivro() {
        System.out.print("Informe o CPF do usuário: ");
        String cpf = scanner.nextLine();
        Usuario usuario = null;
        for (Usuario u : biblioteca.getUsuarios()) {
            if (u.getCpf().equals(cpf)) {
                usuario = u;
                break;
            }
        }
        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return;
        }

        System.out.print("Informe o ISBN do livro: ");
        String isbn = scanner.nextLine();
        Livro livro = biblioteca.buscarLivroPorISBN(isbn);
        if (livro != null) {
            biblioteca.emprestarLivro(usuario, livro);
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    private static void devolverLivro() {
        System.out.print("Informe o CPF do usuário: ");
        String cpf = scanner.nextLine();
        Usuario usuario = null;
        for (Usuario u : biblioteca.getUsuarios()) {
            if (u.getCpf().equals(cpf)) {
                usuario = u;
                break;
            }
        }
        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return;
        }

        System.out.print("Informe o ISBN do livro: ");
        String isbn = scanner.nextLine();
        Livro livro = biblioteca.buscarLivroPorISBN(isbn);
        if (livro != null) {
            biblioteca.devolverLivro(usuario, livro);
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

    private static void gerarRelatorio() {
        biblioteca.gerarRelatorio();
    }
}
