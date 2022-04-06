package ru.jdbc.otus.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.jdbc.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        Integer amount = jdbc.getJdbcOperations().queryForObject("SELECT COUNT(*) FROM GENRES", Integer.class);
        return amount == null ? 0: amount;
    }

    @Override
    public void insert(Genre genre) {
        jdbc.update("INSERT INTO GENRES(id, name) VALUES(:id, :name)",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("SELECT id, name FROM GENRES WHERE id = :id",
                Map.of("id", id), new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT id, name FROM GENRES", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM GENRES WHERE id = :id", Map.of("id", id));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
