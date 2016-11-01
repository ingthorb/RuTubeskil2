package data.RowMappers;

import models.VideosInChannelModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Laufey on 01/11/2016.
 */
public class VideosInChannelRowMapper implements RowMapper<VideosInChannelModel> {

    public VideosInChannelModel mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        VideosInChannelModel videosInCannel = new VideosInChannelModel (
                rs.getInt (1),     //id
                rs.getInt (2),      //videoID
                rs.getInt (3));    //channelID
        return videosInCannel;
    }
}