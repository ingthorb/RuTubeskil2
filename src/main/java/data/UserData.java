package data;

import data.RowMappers.CloseFriendsRowMapper;
import data.RowMappers.FavoritVideosRowMapper;
import data.RowMappers.UserRowMapper;
import data.RowMappers.VideoRowMapper;
import exceptions.*;
import is.ruframework.data.RuData;
import models.CloseFriendsModel;
import models.FavoriteVideosModel;
import models.UserModel;
import models.VideoModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
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

    public UserModel updateUser(UserModel userInfoToChange,int userId) throws UserAlreadyExistsException{

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        if(userInfoToChange.getFullName() != null)
        {
            queryContent.execute("update users set fullName ='" + userInfoToChange.getFullName() + "' WHERE id = '" + userId +"'");
        }
        if(userInfoToChange.getUserName() != null)
        {
            //TODO check if userName exists
            try{
                queryContent.execute("update users set userName ='" + userInfoToChange.getUserName() + "' WHERE id = '" + userId +"'");
            }
            catch (DataIntegrityViolationException divex)
            {
                throw new UserAlreadyExistsException("User with this User name already Exists");
            }

        }
        if(userInfoToChange.getEmail() != null)
        {
            queryContent.execute("update users set email ='" + userInfoToChange.getEmail() + "' WHERE id = '" + userId +"'");
        }

        return getUser(userId);
    }

    public UserModel getUser(int userId)
    {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<UserModel> user = queryContent.query("select * from users  where id ='" + userId + "'", new UserRowMapper());
        UserModel temp = new UserModel(user.get(0).getId(),user.get(0).getFullName(),user.get(0).getUserName(),user.get(0).getEmail(), "*****");
        return temp;
    }

    public List<UserModel> getCloseFriends(int userId)
    {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<CloseFriendsModel> users = queryContent.query("select * from closeFriends  where userID ='" + userId + "'", new CloseFriendsRowMapper());
        List<UserModel> closeFriendsInfo = new ArrayList<UserModel>();
        for(int i = 0; i < users.size(); i++){
            List<UserModel> user = queryContent.query("select * from users where id ='" + users.get(i).getUserID() + "'", new UserRowMapper());

            UserModel temp = new UserModel(user.get(0).getId(),user.get(0).getFullName(),user.get(0).getUserName(),user.get(0).getEmail());
            closeFriendsInfo.add(temp);
        }
        return closeFriendsInfo;
    }

    public List<VideoModel>  getFavoriteVideos(int userId)
    {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<FavoriteVideosModel> favoriteVideos = queryContent.query("select * from favoritVideos  where userID ='" + userId + "'", new FavoritVideosRowMapper());
        List<VideoModel> videos = new ArrayList<VideoModel>();

        for(int i = 0; i < favoriteVideos.size(); i++){
            List<VideoModel> video = queryContent.query("select * from videos where id ='" + favoriteVideos.get(i).getVideoID() + "'", new VideoRowMapper());
            videos.add(video.get(0));
        }
        return videos;
    }
}
