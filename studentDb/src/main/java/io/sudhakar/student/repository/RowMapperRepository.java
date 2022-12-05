package io.sudhakar.student.repository;

import io.sudhakar.student.repository.queryDetails.Queries;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Component
public class RowMapperRepository {

    private final NamedParameterJdbcTemplate jdbcTemplateNamed;
//    private final JdbcTemplate jdbcTemplate;
    private final Queries queries;


    public RowMapperRepository(@Qualifier("applicationDataSource") DataSource dataSource,
                               Queries queries) {
        this.jdbcTemplateNamed = initJdbcTemplate(dataSource);
//        jdbcTemplate = new JdbcTemplate(dataSource);
        this.queries = queries;
    }

    private NamedParameterJdbcTemplate initJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
    }

    public <RES> List<RES> executeGetAllQuery(MapSqlParameterSource mapSqlParameterSource, String queryName, RowMapper<RES> rowMapper) {
        String sqlQuery = getQuery(queryName);
        return jdbcTemplateNamed.query(sqlQuery, mapSqlParameterSource, rowMapper);
    }

    public <RES> List<RES> executeGetQuery(int id, String queryName, RowMapper<RES> rowMapper) {

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id, Types.INTEGER);

        String sqlQuery = getQuery(queryName);
        return jdbcTemplateNamed.query(sqlQuery, namedParameters, rowMapper);
    }

    private String getQuery(String queryName) {

        String query = null;

        if ("findAll".equals(queryName)) {
            query = queries.getFindAll();
        } else if ("findById".equals(queryName)) {
            query = queries.getFindById();
        }

        Assert.isTrue(query != null, "no sql query mapped for queryName " + queryName);
        return query;
    }

}
