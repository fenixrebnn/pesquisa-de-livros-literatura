package entities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GutendexAPI {

    private static final String API_URL = "https://gutendex.com/books";

    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        try {
            String urlString = API_URL + "?search=" + query.replace(" ", "%20");
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
            JsonArray results = jsonResponse.getAsJsonArray("results");

            for (int i = 0; i < results.size(); i++) {
                JsonObject bookJson = results.get(i).getAsJsonObject();
                String title = bookJson.get("title").getAsString();
                JsonArray authorsArray = bookJson.getAsJsonArray("authors");

                String author = "Autor Desconhecido";
                if (authorsArray != null && authorsArray.size() > 0) {
                    JsonObject firstAuthor = authorsArray.get(0).getAsJsonObject();
                    author = firstAuthor.get("name").getAsString();
                }

                int id = bookJson.get("id").getAsInt();
                books.add(new Book(id, title, author));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar livros: " + e.getMessage());
        }

        return books;
    }

    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> allBooks = searchBooks(authorName);
        List<Book> filteredBooks = new ArrayList<>();

        for (Book book : allBooks) {
            if (book.getAuthor().toLowerCase().contains(authorName.toLowerCase())) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    public List<Book> searchBooksByLanguage(String languageCode) {
        List<Book> books = new ArrayList<>();
        try {
            String urlString = API_URL + "?languages=" + languageCode;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
            JsonArray results = jsonResponse.getAsJsonArray("results");

            for (int i = 0; i < results.size(); i++) {
                JsonObject bookJson = results.get(i).getAsJsonObject();
                String title = bookJson.get("title").getAsString();
                JsonArray authorsArray = bookJson.getAsJsonArray("authors");

                String author = "Autor Desconhecido";
                if (authorsArray != null && authorsArray.size() > 0) {
                    JsonObject firstAuthor = authorsArray.get(0).getAsJsonObject();
                    author = firstAuthor.get("name").getAsString();
                }

                int id = bookJson.get("id").getAsInt();
                books.add(new Book(id, title, author));
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar livros por idioma: " + e.getMessage());
        }
        return books;
    }

    public List<Book> searchBooksBySubject(String subject) {
        return searchBooks(subject);
    }

    public Book getBookById(int id) {
        try {
            String urlString = API_URL + "/" + id;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            con.disconnect();

            JsonObject bookJson = JsonParser.parseString(content.toString()).getAsJsonObject();

            String title = bookJson.get("title").getAsString();
            JsonArray authorsArray = bookJson.getAsJsonArray("authors");

            String author = "Autor Desconhecido";
            if (authorsArray != null && authorsArray.size() > 0) {
                JsonObject firstAuthor = authorsArray.get(0).getAsJsonObject();
                author = firstAuthor.get("name").getAsString();
            }

            return new Book(id, title, author);

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro pelo ID: " + e.getMessage());
            return null;
        }
    }
}
