package data;

import data.RowMappers.TokenRowMapper;
import data.RowMappers.UserRowMapper;
import data.RowMappers.VideoRowMapper;
import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
import is.ruframework.data.RuData;
import models.ChangePasswordModel;
import models.TokenModel;
import models.UserModel;
import models.VideoModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laufey on 31/10/2016.
 */

public class AccountData extends RuData implements AccountDataGateway{

    public int addUser(UserModel user) throws UserAlreadyExistsException{
        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("users")
                        .usingColumns("fullName","userName","email","password")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(4);
        parameters.put("fullName", user.getFullName());
        parameters.put("userName", user.getUserName());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        int returnKey = 0;

        try
        {
            returnKey = insertContent.executeAndReturnKey(parameters).intValue();
        }
        catch (DataIntegrityViolationException divex)
        {
            log.warning("Duplicate entry");
            throw new UserAlreadyExistsException();
        }
        return returnKey;
    }

    public int addToken(TokenModel token){
        int returnKey = 0;

        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("token")
                        .usingColumns("userID","token");


        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("userID", token.getUserID());
        parameters.put("token", token.getToken());

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<UserModel> userExists = queryContent.query("select * from users where id ='" + token.getUserID() + "'", new UserRowMapper());

        if(userExists.size() != 0)
        {
            List<TokenModel> tokenExists = queryContent.query("select * from token where userID ='" + token.getUserID() + "'", new TokenRowMapper());

            if(tokenExists.size() != 0){
                System.out.println("update token set token ='" + token.getToken() + "' WHERE userID = '" + token.getUserID() +"'");

                queryContent.execute("update token set token ='" + token.getToken() + "' WHERE userID = '" + token.getUserID() +"'");
            }
            else{
                returnKey = insertContent.execute(parameters);
            }
        }
        return returnKey;
    }

    public boolean checkIfPasswordMatches(String userPass,int userId){

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<UserModel> userInDb = queryContent.query("select * from users where id ='" + userId + "'", new UserRowMapper());


        if(userInDb.get(0).getPassword().toString().equals(userPass))
        {
            return true;
        }
        return false;
    }

    public int getUserId(UserModel user) throws UserNotFoundException{ //getUserId
        int returnKey = 0;
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<UserModel> userExists = queryContent.query("select * from users where userName ='" + user.getUserName() + "'", new UserRowMapper());

        if(userExists.size() == 0)
        {
            throw new UserNotFoundException("User does not exists. Please sign up");
        }

        return userExists.get(0).getId();
    }


    public int getUserIdFromToken(String token) throws UnauthorizedException {

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<TokenModel> tokenData = queryContent.query("select * from token where token ='" + token + "'", new TokenRowMapper());

        if(tokenData.size() == 0){
            throw new UnauthorizedException();
        }
        return tokenData.get(0).getUserID();
    }

    public void ChangePassword(ChangePasswordModel passwordModel, int userId) throws InvalidPasswordException {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

//        List<UserModel> userExists = queryContent.query("select * from users where id ='" + userId + "'", new UserRowMapper());

        if(checkIfPasswordMatches(passwordModel.getOldPassword(),userId)){
            queryContent.execute("update users set password ='" + passwordModel.getNewPassword() + "' WHERE id = '" + userId +"'");
        }
        else{
            throw new InvalidPasswordException();
        }
    }

    public void DeleteUser(int id){
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        //TODO klára VIRKAR EKKI

        //TODO remove from close friends
        //TODO remove favorite videos

        List<VideoModel> userVideos = queryContent.query("select * from videos where userId ='" + id+ "'", new VideoRowMapper());

        for(int i = 0; i < userVideos.size();i++){
            queryContent.execute("DELETE FROM VideosInChannel WHERE videoID='" + userVideos.get(i).getId() + "'");
            //queryContent.execute("DELETE FROM favoritVideos WHERE userID='" + id + "'");
        }

        queryContent.execute("DELETE FROM videos WHERE userId='" + id + "'");
        //queryContent.execute("DELETE FROM closeFriends WHERE userID='" + id + "'");
        queryContent.execute("DELETE FROM token WHERE userID='" + id + "'");
        queryContent.execute("DELETE FROM users WHERE id='" + id + "'");
    }

}
