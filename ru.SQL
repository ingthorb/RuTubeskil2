CREATE database ru;

CREATE TABLE ru.token
(
  userName varchar(512) unique,
  token varchar(512),

  FOREIGN KEY (userName)
  references users(userName)
)

CREATE TABLE ru.users
(
  id int auto_increment primary key NOT NULL,
  fullName varchar(512),
  userName varchar(512) unique,
  email varchar(512),
  password varchar(512)
)

CREATE TABLE ru.videos
(
   id int auto_increment primary key NOT NULL,
   title varchar(512),
   type varchar(512) ,
   description varchar(512),
   src varchar(512),
   userName varchar(512) ,

FOREIGN KEY (userName)
references ru.users(userName)
)

CREATE TABLE ru.channels
(
 channelID int auto_increment primary key NOT NULL,
 channelName varchar(512)
)

CREATE TABLE ru.VideosInChannel
(
  videoID int,
  channelID int,

  FOREIGN KEY (videoID)
  references ru.videos(id),

  foreign key (channelID)
  references ru.channels(channelID)
)
--ÞESSAR ERU EKKI KOMNAR INNÍ DB


CREATE TABLE ru.closeFriends(
  userID int,
  friendID int

  FOREIGN KEY (userID)
  references ru.users(id)
)

CREATE TABLE ru.favoritVideos(
  userID int,
  videoID int

  FOREIGN KEY (userID)
  references ru.users(id)

  FOREIGN KEY (videoID)
  references ru.videos(id)
)
