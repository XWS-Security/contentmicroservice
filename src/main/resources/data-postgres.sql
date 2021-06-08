
INSERT INTO public.auth_role (id, name) VALUES ('1', 'NISTAGRAM_USER_ROLE');

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '1', true, '2017-10-01 21:58:58.508-07', 'luka',
	'Lorem Ipsum is simply dummy text of the printing and typesetting industry.' ||
    ' When an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Profile1.jpg', true);

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '2', true, '2017-10-01 21:58:58.508-07', 'vlado',
	'Lorem Ipsum is simply dummy text of the printing and typesetting industry.' ||
    ' When an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Profile1.jpg', false );

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '3', true, '2017-10-01 21:58:58.508-07', 'vidoje',
	'Lorem Ipsum is simply dummy text of the printing and typesetting industry.' ||
    ' When an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Profile2.jpg', true);

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '4', true, '2017-10-01 21:58:58.508-07', 'milica',
	'Lorem Ipsum is simply dummy text of the printing and typesetting industry.' ||
    ' When an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Profile3.jpg', true);

INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '5', true, '2017-10-01 21:58:58.508-07', 'duja',
	'Lorem Ipsum is simply dummy text of the printing and typesetting industry.' ||
    ' When an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Profile2.jpg', false );


INSERT INTO public.nistagram_user(
	user_type, id, enabled, last_password_reset_date, username, about, profile_picture, private_profile)
	VALUES ('INSTAGRAM_USER', '6', true, '2017-10-01 21:58:58.508-07', 'kobra',
	'Lorem Ipsum is simply dummy text of the printing and typesetting industry.' ||
    ' When an unknown printer took a galley of type and scrambled it to make a type specimen book.', 'Profile3.jpg', true);

INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (4, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (5, 1);
INSERT INTO public.user_role (user_id, role_id) VALUES (6, 1);

INSERT INTO public.location(
    id, name)
    VALUES (1, 'Novi Sad');

INSERT INTO public.location(
    id, name)
    VALUES (2, 'Sremska kamenica');

INSERT INTO public.location(
    id, name)
    VALUES (3, 'Bife Jelena');
