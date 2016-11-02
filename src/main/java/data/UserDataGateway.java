package data;

import exceptions.*;
import models.CloseFriendsModel;
import models.FavoriteVideosModel;
import models.UserModel;
import models.VideoModel;

import java.util.List;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface UserDataGateway {

    public int addCloseFriend(CloseFriendsModel closeFriend,int userId)  throws UserNotFoundException, UserAlreadyACloseFriendException;

    public void DeleteCloseFriend(int friendId,int userId) throws UserNotFoundException;

    public boolean doesVideoExist(int videoId);

    public int addFavoriteVideo(FavoriteVideosModel favoriteVideo , int userId) throws videoNotFoundException, videoAlreadyAFavoritVideoxception;

    public void DeleteFavoritVideo(int videoId,int userId) throws videoNotFoundException;

    public UserModel getUser(int userId);

    public List<UserModel> getCloseFriends(int userId);

    public List<VideoModel>  getFavoriteVideos(int userId);

    public UserModel updateUser(UserModel userInfoToChange,int userId) throws UserAlreadyExistsException;
}
