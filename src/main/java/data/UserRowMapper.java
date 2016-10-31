package data;

import models.UserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserModel>
{
    public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        UserModel user = new UserModel (
                rs.getInt (1),       // id
                rs.getString (2),    // fullName
                rs.getString (3),    // userName
                rs.getString (4),    // email
                rs.getString (5));    // password
        return user;
    }
}