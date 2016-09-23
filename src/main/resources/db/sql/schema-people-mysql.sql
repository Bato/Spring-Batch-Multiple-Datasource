USE spring_batch;
DROP TABLE IF EXISTS people;

CREATE TABLE people  (
    person_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
