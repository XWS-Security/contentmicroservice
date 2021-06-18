
INSERT INTO public.auth_role (id, name) VALUES ('1', 'NISTAGRAM_USER_ROLE');
INSERT INTO public.auth_role (id, name) VALUES ('2', 'ADMINISTRATOR_ROLE');

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '1', true, '2017-10-01 21:58:58.508-07', 'luka',
	'Lorem Ipsum', 'Profile1.jpg', true);

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '2', true, '2017-10-01 21:58:58.508-07', 'vlado',
	'Lorem Ipsum', 'Profile1.jpg', false );

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '3', true, '2017-10-01 21:58:58.508-07', 'vidoje',
	'Lorem Ipsum', 'Profile2.jpg', true);

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '4', true, '2017-10-01 21:58:58.508-07', 'milica',
	'Lorem Ipsum', 'Profile3.jpg', true);

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '5', true, '2017-10-01 21:58:58.508-07', 'duja',
	'Lorem Ipsum', 'Profile2.jpg', false );

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '6', true, '2017-10-01 21:58:58.508-07', 'kobra',
	'Lorem Ipsum', 'Profile3.jpg', true);

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username)
	VALUES ('ADMINISTRATOR', '7', true, '2017-10-01 21:58:58.508-07', 'admin');

INSERT INTO public.user_close_friend(user_id, close_friend_id) VALUES (1, 2);
INSERT INTO public.user_close_friend(user_id, close_friend_id) VALUES (2, 1);

INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (6, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (7, 2);

INSERT INTO public.location(
    id, name)
    VALUES (1, 'Novi Sad');

INSERT INTO public.location(
    id, name)
    VALUES (2, 'Sremska kamenica');

INSERT INTO public.location(
    id, name)
    VALUES (3, 'Bife Jelena');

INSERT INTO public.content(
    content_type, id, date, tags, about, paths, highlights, only_close_friends, path, location_id, deleted, user_id)
    VALUES ('POST', '1', '2017-10-01 21:58:58.508-07', '{#VSCO, #BALKAN}', 'Vscoooo', '{image1.jpg, image2.jpg, image3.jpg}', null, null, null, 1, 1);

INSERT INTO public.content(
    content_type, id, date, tags, about, paths, highlights, only_close_friends, path, location_id, deleted, user_id)
    VALUES ('POST', '2', '2017-10-01 21:58:58.508-07', '{#VSCO, #BALKAN}', 'Vscoooo', '{image4.jpg, image5.jpg}', null, null, null, 1, 1);

INSERT INTO public.content(
    content_type, id, date, tags, about, paths, highlights, only_close_friends, path, location_id, deleted, user_id)
    VALUES ('POST', '3', '2017-10-01 21:58:58.508-07', '{#VSCO, #BALKAN}', 'Vscoooo', '{video1.mp4, image6.jpg}', null, null, null, 1, 1);

INSERT INTO public.content(
    content_type, id, date, tags, about, paths, highlights, only_close_friends, path, location_id, deleted, user_id)
    VALUES ('POST', '4', '2017-10-01 21:58:58.508-07', '{#VSCO, #BALKAN}', 'Vscoooo', '{video2.mp4, image7.jpg}', null, null, null, 1, 1);

INSERT INTO public.content(
    content_type, id, date, tags, about, paths, highlights, only_close_friends, path, location_id, deleted, user_id)
    VALUES ('POST', '5', '2017-10-01 21:58:58.508-07', '{#VSCO, #BALKAN}', 'Vscoooo', '{image8.jpg, image9.jpg}', null, null, null, 1, 1);

INSERT INTO public.content(
    content_type, id, date, tags, about, paths, highlights, only_close_friends, path, location_id, deleted, user_id)
    VALUES ('POST', '6', '2017-10-01 21:58:58.508-07', '{#VSCO, #BALKAN}', 'Vscoooo', '{image10.jpg}', null, null, null, 1, 1);

INSERT INTO public.comment(id, date, text, user_id) VALUES ('1', '2017-10-01 21:58:58.508-07', 'Lepasi', '2');
INSERT INTO public.comment(id, date, text, user_id) VALUES ('2', '2017-10-01 21:58:58.508-07', 'Lepasi', '3');
INSERT INTO public.comment(id, date, text, user_id) VALUES ('3', '2017-10-01 21:58:58.508-07', 'Lepasi', '4');
INSERT INTO public.comment(id, date, text, user_id) VALUES ('4', '2017-10-01 21:58:58.508-07', 'Lepasi', '5');
INSERT INTO public.comment(id, date, text, user_id) VALUES ('5', '2017-10-01 21:58:58.508-07', 'Lepasi', '6');

INSERT INTO public.post_comment(post_id, comment_id) VALUES (1, 1);
INSERT INTO public.post_comment(post_id, comment_id) VALUES (1, 2);
INSERT INTO public.post_comment(post_id, comment_id) VALUES (1, 3);
INSERT INTO public.post_comment(post_id, comment_id) VALUES (1, 4);
INSERT INTO public.post_comment(post_id, comment_id) VALUES (1, 5);

-- INSERT INTO public.user_content(user_id, content_id) VALUES (1, 1);
-- INSERT INTO public.user_content(user_id, content_id) VALUES (1, 2);
-- INSERT INTO public.user_content(user_id, content_id) VALUES (1, 3);
-- INSERT INTO public.user_content(user_id, content_id) VALUES (1, 4);
-- INSERT INTO public.user_content(user_id, content_id) VALUES (1, 5);
-- INSERT INTO public.user_content(user_id, content_id) VALUES (1, 6);

INSERT INTO public.user_subscribed_user(user_id, subscribed_user_id) VALUES (2, 1);
INSERT INTO public.user_subscribed_user(user_id, subscribed_user_id) VALUES (1, 2);
INSERT INTO public.user_subscribed_user(user_id, subscribed_user_id) VALUES (3, 2);
INSERT INTO public.user_subscribed_user(user_id, subscribed_user_id) VALUES (4, 5);
INSERT INTO public.user_subscribed_user(user_id, subscribed_user_id) VALUES (5, 4);
INSERT INTO public.user_subscribed_user(user_id, subscribed_user_id) VALUES (1, 4);
INSERT INTO public.user_subscribed_user(user_id, subscribed_user_id) VALUES (6, 2);