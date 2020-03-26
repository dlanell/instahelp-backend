CREATE TABLE IF NOT EXISTS volunteer_request (
    id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(40) NOT NULL,
    title varchar(100) NOT NULL,
    details varchar(350) NOT NULL,
    date DATE NOT NULL,
    zip char(5) NOT NULL,
    phone_number char(10),
    email varchar(255)
);
