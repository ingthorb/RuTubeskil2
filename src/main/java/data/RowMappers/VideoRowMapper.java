package data.RowMappers;

import models.VideoModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Laufey on 01/11/2016.
 */
public class VideoRowMapper implements RowMapper<VideoModel> {

    public VideoModel mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        VideoModel video = new VideoModel (
                rs.getInt (1),       // id
                rs.getString (2),    // title
                rs.getString (3),    // type
                rs.getString (4),    // description
                rs.getString (5),  // src
                rs.getInt (6));    // userId
        return video;
    }
}
