package data;

import is.ruframework.data.RuData;
import models.CloseFriendsModel;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laufey on 01/11/2016.
 */
public class UserData extends RuData implements UserDataGateway {

    public int addCloseFriend(CloseFriendsModel closeFriend , int userId) {

        //TODO check if friendExits

        //TODO check if isAlredyACloseFriend


        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("closeFriends")
                        .usingColumns("userID","friendID")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(5);
        parameters.put("userID", closeFriend.getUserID());
        parameters.put("friendID", closeFriend.getUserID());

        int returnKey = 0;

        returnKey = insertContent.executeAndReturnKey(parameters).intValue();

        return returnKey;
    }
}
