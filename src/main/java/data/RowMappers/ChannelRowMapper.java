package data.RowMappers;

import models.ChannelModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Laufey on 01/11/2016.
 */
public class ChannelRowMapper implements RowMapper<ChannelModel>{
    public ChannelModel mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        ChannelModel channel = new ChannelModel (
                rs.getInt (1),       // id
                rs.getString (2));    // channelName
        return channel;
    }
}
