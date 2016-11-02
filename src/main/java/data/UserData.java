package data;

import data.RowMappers.CloseFriendsRowMapper;
import data.RowMappers.FavoritVideosRowMapper;
import data.RowMappers.UserRowMapper;
import data.RowMappers.VideoRowMapper;
import exceptions.UserAlreadyACloseFriendException;
import exceptions.UserNotFoundException;
import exceptions.videoAlreadyAFavoritVideoxception;
import exceptions.videoNotFoundException;
import is.ruframework.data.RuData;
import models.CloseFriendsModel;
import models.FavoriteVideosModel;
import models.UserModel;
import models.VideoModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laufey on 01/11/2016.
 */
public class UserData extends RuData implements UserDataGateway {

    public int addCloseFriend(CloseFriendsModel closeFriend , int userId) throws UserNotFoundException, UserAlreadyACloseFriendException {

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<UserModel> friendExists = queryContent.query("select * from users where id ='" + closeFriend.getFriendID() + "'", new UserRowMapper());

        if(friendExists.size() == 0)
        {
            throw new UserNotFoundException("Friend does not exists.");
        }

        List<CloseFriendsModel> isAlreadyACloseFriend = queryContent.query("select * from closeFriends where userID ='" + userId + "' AND friendID = '" + closeFriend.getFriendID() + "'", new CloseFriendsRowMapper());

        if(isAlreadyACloseFriend.size() != 0)
        {
            throw new UserAlreadyACloseFriendException("User is alredy a close friend");
        }

        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("closeFriends")
                        .usingColumns("userID","friendID")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("userID", userId);
        parameters.put("friendID", closeFriend.getFriendID());

        int returnKey = 0;

        returnKey = insertContent.executeAndReturnKey(parameters).intValue();

        return returnKey;
    }

    public void DeleteCloseFriend(int friendId,int userId) throws UserNotFoundException{

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<CloseFriendsModel> isAlreadyACloseFriend = queryContent.query("select * from closeFriends where userID ='" + userId + "' AND friendID = '" + friendId + "'", new CloseFriendsRowMapper());

        if(isAlreadyACloseFriend.size() == 0)
        {
            throw new UserNotFoundException("User is not a close friend");
        }
            queryContent.execute("DELETE FROM closeFriends WHERE friendId='" + friendId + "'");
    }

    public int addFavoriteVideo(FavoriteVideosModel favoriteVideo , int userId) throws videoNotFoundException, videoAlreadyAFavoritVideoxception {

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        if(!doesVideoExist(favoriteVideo.getVideoID())){
            throw new videoNotFoundException("Video not found");
        }

        List<FavoriteVideosModel> isAlreadyAFavoriteVideo = queryContent.query("select * from favoritVideos where userID ='" + userId + "' AND videoID = '" + favoriteVideo.getVideoID() + "'", new FavoritVideosRowMapper());

        if(isAlreadyAFavoriteVideo.size() != 0)
        {
            throw new videoAlreadyAFavoritVideoxception("Video is already in favorite list");
        }

        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("favoritVideos")
                        .usingColumns("userID","videoID")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("userID", userId);
        parameters.put("videoID", favoriteVideo.getVideoID());

        int returnKey = 0;

        returnKey = insertContent.executeAndReturnKey(parameters).intValue();

        return returnKey;
    }

    public boolean doesVideoExist(int videoId){

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<VideoModel> videoExists = queryContent.query("select * from videos where id ='" + videoId + "'", new VideoRowMapper());

        if(videoExists.size() == 0){
            return false;
        }
        return true;
    }

    public void DeleteFavoritVideo(int videoId,int userId) throws videoNotFoundException{

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<FavoriteVideosModel> isAlreadyAFavoriteVideo = queryContent.query("select * from favoritVideos where userID ='" + userId + "' AND videoID = '" + videoId + "'", new FavoritVideosRowMapper());

        if(isAlreadyAFavoriteVideo.size() == 0)
        {
            throw new videoNotFoundException("Video not in favoriteList");
        }
        queryContent.execute("DELETE FROM favoritVideos WHERE videoID='" + videoId + "'");
    }
}
