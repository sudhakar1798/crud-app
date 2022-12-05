package io.sudhakar.student.repository.mapper;

import io.sudhakar.student.dto.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();

        student.setCourse(rs.getString("course"));
        student.setPlace(rs.getString("place"));
        student.setAge(rs.getInt("age"));
        student.setName(rs.getString("name"));
        student.setId(rs.getInt("id"));

        return student;
    }

}
