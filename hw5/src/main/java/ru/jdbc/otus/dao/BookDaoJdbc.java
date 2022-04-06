package ru.jdbc.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.jdbc.otus.domain.Book;

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
        Integer amount = jdbc.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM BOOKS", Integer.class);
        return amount == null ? 0: amount;
    }

    @Override
    public void insert(Book book) {
        jdbc.update("INSERT INTO BOOKS(id, title, authorId, genreId) VALUES(:id, :title, :authorId, :genreId)",
                Map.of("id", book.getId(), "title", book.getTitle(),
                        "authorId", book.getAuthorId(), "genreId", book.getGenreId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("SELECT id, title, authorId, genreId FROM BOOKS WHERE id = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT id, title, authorId, genreId FROM BOOKS", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM BOOKS WHERE id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            long authorId = resultSet.getLong("authorId");
            long genreId = resultSet.getLong("genreId");
            return new Book(id, title, authorId, genreId);
        }
    }
}
