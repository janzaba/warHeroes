# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  id                        bigint not null,
  name                      varchar(255),
  start_time                timestamp,
  end_time                  timestamp,
  owner_email               varchar(255),
  winner_email              varchar(255),
  constraint pk_game primary key (id))
;

create table game_players (
  game_id                   bigint,
  user_email                varchar(255))
;

create table shoutbox_message (
  id                        bigint not null,
  game_id                   bigint,
  user_email                varchar(255),
  message                   varchar(255),
  time                      timestamp,
  target                    integer,
  constraint ck_shoutbox_message_target check (target in (0,1)),
  constraint pk_shoutbox_message primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence game_seq;

create sequence shoutbox_message_seq;

create sequence user_seq;

alter table game add constraint fk_game_owner_1 foreign key (owner_email) references user (email) on delete restrict on update restrict;
create index ix_game_owner_1 on game (owner_email);
alter table game add constraint fk_game_winner_2 foreign key (winner_email) references user (email) on delete restrict on update restrict;
create index ix_game_winner_2 on game (winner_email);
alter table game_players add constraint fk_game_players_game_3 foreign key (game_id) references game (id) on delete restrict on update restrict;
create index ix_game_players_game_3 on game_players (game_id);
alter table game_players add constraint fk_game_players_user_4 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_game_players_user_4 on game_players (user_email);
alter table shoutbox_message add constraint fk_shoutbox_message_game_5 foreign key (game_id) references game (id) on delete restrict on update restrict;
create index ix_shoutbox_message_game_5 on shoutbox_message (game_id);
alter table shoutbox_message add constraint fk_shoutbox_message_user_6 foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_shoutbox_message_user_6 on shoutbox_message (user_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists game;

drop table if exists game_players;

drop table if exists shoutbox_message;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists game_seq;

drop sequence if exists shoutbox_message_seq;

drop sequence if exists user_seq;

