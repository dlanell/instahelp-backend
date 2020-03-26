CREATE DATABASE IF NOT EXISTS instahelp;

CREATE USER 'instahelp_user'@'%' identified by 'helpme';

GRANT ALL ON instahelp.* to 'instahelp_user'@'%';

