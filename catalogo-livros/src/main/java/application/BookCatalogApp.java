package application;

import entities.Book;
import entities.GutendexAPI;

import java.util.List;
import java.util.Scanner;

public class BookCatalogApp {

    private static GutendexAPI api = new GutendexAPI();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Catálogo de Livros ===");
            System.out.println("1 - Buscar livros por título");
            System.out.println("2 - Buscar livros por autor");
            System.out.println("3 - Listar livros populares (busca geral)");
            System.out.println("4 - Buscar livros por idioma");
            System.out.println("5 - Buscar livros por assunto/gênero");
            System.out.println("6 - Detalhes de um livro pelo ID");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opção: ");

            String option = sc.nextLine();

            switch(option) {
                case "1":
                    System.out.print("Digite o título para buscar: ");
                    String title = sc.nextLine();
                    List<Book> booksByTitle = api.searchBooks(title);
                    printBooks(booksByTitle);
                    break;
                case "2":
                    System.out.print("Digite o nome do autor para buscar: ");
                    String author = sc.nextLine();
                    List<Book> booksByAuthor = api.searchBooksByAuthor(author);
                    printBooks(booksByAuthor);
                    break;
                case "3":
                    System.out.println("Buscando livros populares...");
                    List<Book> popularBooks = api.searchBooks("");
                    printBooks(popularBooks);
                    break;
                case "4":
                    System.out.print("Digite o código do idioma (ex: en, pt): ");
                    String lang = sc.nextLine();
                    List<Book> booksByLang = api.searchBooksByLanguage(lang);
                    printBooks(booksByLang);
                    break;
                case "5":
                    System.out.print("Digite o assunto/gênero para buscar: ");
                    String subject = sc.nextLine();
                    List<Book> booksBySubject = api.searchBooksBySubject(subject);
                    printBooks(booksBySubject);
                    break;
                case "6":
                    System.out.print("Digite o ID do livro para ver detalhes: ");
                    String idStr = sc.nextLine();
                    try {
                        int id = Integer.parseInt(idStr);
                        showBookDetails(id);
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido.");
                    }
                    break;
                case "7":
                    running = false;
                    System.out.println("Saindo do catálogo. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
        sc.close();
    }

    private static void printBooks(List<Book> books){
        if (books.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            for(Book b : books) {
                System.out.println(b);
            }
        }
    }

    private static void showBookDetails(int id) {
        Book book = api.getBookById(id);
        if (book != null) {
            System.out.println("\nDetalhes do Livro:");
            System.out.println(book);
        } else {
            System.out.println("Livro com ID " + id + " não encontrado.");
        }
    }
}
