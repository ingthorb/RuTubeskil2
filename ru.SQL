CREATE database ru;

CREATE TABLE ru.users
(
  id int auto_increment primary key NOT NULL,
  fullName varchar(512),
  userName varchar(512) unique,
  email varchar(512),
  password varchar(512)
)

CREATE TABLE ru.token
(
  userID int unique,
  token varchar(512),

  FOREIGN KEY (userID)
  references ru.users(id)
)

CREATE TABLE ru.channels
(
 channelID int auto_increment primary key NOT NULL,
 channelName varchar(512)
)

CREATE TABLE ru.closeFriends(
  id int auto_increment primary key NOT NULL,
  userID int,
  friendID int,

  FOREIGN KEY (userID)
  references ru.users(id)
)

CREATE TABLE ru.videos
(
   id int auto_increment primary key NOT NULL,
   title varchar(512),
   type varchar(512) ,
   description varchar(512),
   src varchar(512),
   userId int ,

FOREIGN KEY (userId)
references ru.users(id)
)

CREATE TABLE ru.VideosInChannel
(
  id int auto_increment primary key NOT NULL,
  videoID int,
  channelID int,

  FOREIGN KEY (videoID)
  references ru.videos(id),

  FOREIGN KEY (channelID)
  references ru.channels(channelID)
)

CREATE TABLE ru.favoritVideos(
  id int auto_increment primary key NOT NULL,
  userID int,
  videoID int,

  FOREIGN KEY (userID)
  references ru.users(id),

  FOREIGN KEY (videoID)
  references ru.videos(id)
)
