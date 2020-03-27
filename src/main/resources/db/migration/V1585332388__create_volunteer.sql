CREATE TABLE IF NOT EXISTS volunteer (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(40) NOT NULL,
    phone_number char(10)
);

ALTER TABLE volunteer_request
    ADD COLUMN volunteer_id bigint,
    ADD FOREIGN KEY fk_volunteer_id(volunteer_id) REFERENCES volunteer(id);