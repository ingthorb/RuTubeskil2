package data;

import models.CloseFriendsModel;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface UserDataGateway {

    public int addCloseFriend(CloseFriendsModel closeFriend,int userId);

}
