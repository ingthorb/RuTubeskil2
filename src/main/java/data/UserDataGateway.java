package data;

import exceptions.UserAlreadyACloseFriendException;
import exceptions.UserNotFoundException;
import models.CloseFriendsModel;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface UserDataGateway {

    public int addCloseFriend(CloseFriendsModel closeFriend,int userId)  throws UserNotFoundException, UserAlreadyACloseFriendException;

    public void DeleteCloseFriend(int friendId,int userId) throws UserNotFoundException;

}
