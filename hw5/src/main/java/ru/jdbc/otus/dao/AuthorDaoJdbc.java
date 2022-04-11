package ru.jdbc.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.jdbc.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class AuthorDaoJdbc implements  AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        Integer amount = jdbc.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM AUTHORS", Integer.class);
        return amount == null ? 0: amount;
    }

    @Override
    public void insert(Author author) {
        jdbc.update("INSERT INTO AUTHORS(id, fullName) VALUES(:id, :fullName)",
                Map.of("id", author.getId(), "fullName", author.getFullName()));
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("SELECT id, fullName FROM AUTHORS WHERE id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT id, fullName FROM AUTHORS", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM AUTHORS WHERE id = :id", Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String fullName = resultSet.getString("fullName");
            return new Author(id, fullName);
        }
    }
}
