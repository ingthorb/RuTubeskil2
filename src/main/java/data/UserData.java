package data;

import data.RowMappers.CloseFriendsRowMapper;
import data.RowMappers.UserRowMapper;
import exceptions.UserAlreadyACloseFriendException;
import exceptions.UserNotFoundException;
import is.ruframework.data.RuData;
import models.CloseFriendsModel;
import models.UserModel;
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



}
