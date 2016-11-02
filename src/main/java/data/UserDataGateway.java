package data;

import exceptions.UserAlreadyACloseFriendException;
import exceptions.UserNotFoundException;
import exceptions.videoAlreadyAFavoritVideoxception;
import exceptions.videoNotFoundException;
import models.CloseFriendsModel;
import models.FavoriteVideosModel;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface UserDataGateway {

    public int addCloseFriend(CloseFriendsModel closeFriend,int userId)  throws UserNotFoundException, UserAlreadyACloseFriendException;

    public void DeleteCloseFriend(int friendId,int userId) throws UserNotFoundException;

    public boolean doesVideoExist(int videoId);

    public int addFavoriteVideo(FavoriteVideosModel favoriteVideo , int userId) throws videoNotFoundException, videoAlreadyAFavoritVideoxception;

    public void DeleteFavoritVideo(int videoId,int userId) throws videoNotFoundException;

}
