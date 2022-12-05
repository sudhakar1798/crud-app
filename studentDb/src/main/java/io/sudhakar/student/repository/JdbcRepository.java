package io.sudhakar.student.repository;

import io.sudhakar.student.dto.Student;
import io.sudhakar.student.dto.Task;
import io.sudhakar.student.dto.User;
import io.sudhakar.student.repository.queryDetails.Queries;
import io.sudhakar.student.util.UserUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;


@Component
public class JdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplateNamed;

    private final Queries queries;
    private final int batchSize;
    private final UserUtil userUtil;


    public JdbcRepository(@Qualifier("applicationDataSource") DataSource dataSource, Queries queries,
                          @Value("${batchSize}") int batchSize, UserUtil userUtil) {
        this.jdbcTemplateNamed = initJdbcTemplate(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.queries = queries;
        this.batchSize = batchSize;
        this.userUtil = userUtil;
    }

    private NamedParameterJdbcTemplate initJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource));
    }

    public List<Student> executeQuery(String queryName) {

        String sqlQuery = getQuery(queryName);

        return jdbcTemplateNamed.query(sqlQuery, new ResultSetExtractor<List<Student>>() {
            @Override
            public List<Student> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {

                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setAge(rs.getInt("age"));
                    student.setPlace(rs.getString("place"));
                    student.setCourse(rs.getString("course"));

                    students.add(student);
                }
                return students;
            }
        });
    }


    public List<Student> executeByIdQuery(int id, String queryName) {
        String sqlQuery = getQuery(queryName);

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id, Types.INTEGER);

        return jdbcTemplateNamed.query(sqlQuery, namedParameters, new ResultSetExtractor<List<Student>>() {
            @Override
            public List<Student> extractData(ResultSet rs) throws SQLException,
                    DataAccessException {

                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getInt("id"));
                    student.setName(rs.getString("name"));
                    student.setAge(rs.getInt("age"));
                    student.setPlace(rs.getString("place"));
                    student.setCourse(rs.getString("course"));

                    students.add(student);
                }
                return students;
            }
        });
    }

    public void executeDeleteQuery(int id, String queryName) {
        String sqlQuery = getQuery(queryName);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id, Types.INTEGER);
        jdbcTemplateNamed.update(sqlQuery, namedParameters);
    }

    public void executeAddQuery(Student student, String queryName) {

        String sqlQuery = getQuery(queryName);

        Map<String, Object> map = new HashMap<>();

        map.put("name", student.getName());
        map.put("course", student.getCourse());
        map.put("age", student.getAge());
        map.put("place", student.getPlace());
        jdbcTemplateNamed.update(sqlQuery, map);

    }


    public void executeUpdateQuery(Student student, String queryName) {

        String sqlQuery = getQuery(queryName);

        Map<String, Object> map = new HashMap<>();

        map.put("id", student.getId());
        map.put("name", student.getName());
        map.put("place", student.getPlace());
        map.put("age", student.getAge());
        map.put("course", student.getCourse());

        jdbcTemplateNamed.update(sqlQuery, map);
    }


    public void executeAddManyQuery(List<Student> students, String queryName) {

        String sqlQuery = getQuery(queryName);
        jdbcTemplate.batchUpdate(sqlQuery,
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {

                        ps.setString(1, students.get(i).getName());
                        ps.setString(2, students.get(i).getPlace());
                        ps.setString(3, students.get(i).getCourse());
                        ps.setInt(4, students.get(i).getAge());
                    }

                    public int getBatchSize() {
                        return batchSize;
                    }
                });
    }

    public void executeDeleteManyQuery(List<Integer> ids, String queryName) {

        String sqlQuery = getQuery(queryName);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ids", ids, Types.INTEGER);
        jdbcTemplateNamed.update(sqlQuery, namedParameters);
    }

    public void executeUpdateManyQuery(List<Integer> ids, String queryName, Student student) {

        String sqlQuery = getQuery(queryName);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ids", ids, Types.INTEGER)
                .addValue("name", student.getName())
                .addValue("age", student.getAge())
                .addValue("course", student.getCourse())
                .addValue("place", student.getPlace());

        jdbcTemplateNamed.update(sqlQuery, namedParameters);
    }


    public void executeAddManyTopicsQuery(List<Task> tasks, String queryName) {

        String sqlQuery = getQuery(queryName);
        User user = userUtil.getLoginUser();

        jdbcTemplate.batchUpdate(sqlQuery,
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {


                        ps.setString(2, tasks.get(i).getName());
                        ps.setString(3, tasks.get(i).getDescription());
                        ps.setString(4, tasks.get(i).getStatus());
                        ps.setLong(5, user.getUserId());
                    }

                    public int getBatchSize() {
                        return batchSize;
                    }
                });
    }


    private String getQuery(String queryName) {

        String query = null;

        if ("findAll".equals(queryName)) {
            query = queries.getFindAll();
        } else if ("add".equals(queryName)) {
            query = queries.getAdd();
        } else if ("delete".equals(queryName)) {
            query = queries.getDelete();
        } else if ("findById".equals(queryName)) {
            query = queries.getFindById();
        } else if ("update".equals(queryName)) {
            query = queries.getUpdate();
        } else if ("insertMany".equals(queryName)) {
            query = queries.getInsertMany();
        } else if ("deleteMany".equals(queryName)) {
            query = queries.getDeleteMany();
        } else if ("updateMany".equals(queryName)) {
            query = queries.getUpdateMany();
        } else if ("addManyTask".equals(queryName)) {
            query = queries.getAddManyTask();
        }

        Assert.isTrue(query != null, "no sql query mapped for queryName " + queryName);
        return query;
    }

}
