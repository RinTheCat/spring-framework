package ru.jdbc.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.jdbc.otus.domain.Author;
import ru.jdbc.otus.domain.Book;
import ru.jdbc.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        Integer amount = jdbc.getJdbcOperations().queryForObject("SELECT COUNT(id) FROM BOOKS", Integer.class);
        return amount == null ? 0: amount;
    }

    @Override
    public void insert(Book book) {
        jdbc.update("INSERT INTO BOOKS(id, title, authorId, genreId) VALUES(:id, :title, :authorId, :genreId)",
                Map.of("id", book.getId(), "title", book.getTitle(),
                        "authorId", book.getAuthor().getId(), "genreId", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("SELECT BOOKS.id, BOOKS.title, BOOKS.authorId, BOOKS.genreId, AUTHORS.fullName, GENRES.name FROM BOOKS " +
                        "INNER JOIN AUTHORS ON BOOKS.authorId= AUTHORS.id " +
                        "INNER JOIN GENRES ON BOOKS.genreId = GENRES.id " +
                        "WHERE BOOKS.id = :id ",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT BOOKS.id, BOOKS.title, BOOKS.authorId, BOOKS.genreId, AUTHORS.fullName, GENRES.name FROM BOOKS " +
                "INNER JOIN AUTHORS ON BOOKS.authorId= AUTHORS.id " +
                "INNER JOIN GENRES ON BOOKS.genreId = GENRES.id", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM BOOKS WHERE id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("BOOKS.id");
            String title = resultSet.getString("BOOKS.title");
            long authorId = resultSet.getLong("BOOKS.authorId");
            long genreId = resultSet.getLong("BOOKS.genreId");
            String authorName = resultSet.getString("AUTHORS.fullName");
            String genreName = resultSet.getString("GENRES.name");
            return new Book(id, title, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}
