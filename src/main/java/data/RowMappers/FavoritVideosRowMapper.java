package data.RowMappers;

import models.FavoriteVideosModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Laufey on 01/11/2016.
 */
public class FavoritVideosRowMapper implements RowMapper<FavoriteVideosModel> {
    public FavoriteVideosModel mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        FavoriteVideosModel favoriteVideos = new FavoriteVideosModel (
                rs.getInt (1),      // id
                rs.getInt (2),      // userID
                rs.getInt (3));     // videoID
        return favoriteVideos;
    }
}
