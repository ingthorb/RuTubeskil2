package data.RowMappers;

import models.CloseFriendsModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Laufey on 01/11/2016.
 */
public class CloseFriendsRowMapper implements RowMapper<CloseFriendsModel> {
    public CloseFriendsModel mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        CloseFriendsModel closeFriend = new CloseFriendsModel (
                rs.getInt (1),      // id
                rs.getInt (2),      // userID
                rs.getInt (3));     // friendId
        return closeFriend;
    }
}