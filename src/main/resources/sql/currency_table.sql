drop table if exists currency;
drop table if exists dates;
drop table if exists exchange;

CREATE TABLE currency(
currency_id Integer PRIMARY KEY NOT NULL,
	base varchar(4) NOT NULL
);

ALTER TABLE currency ADD CONSTRAINT base_unique_key UNIQUE(base);

INSERT INTO currency
VALUES
  (1,'PLN'),
  (2,'EUR');

CREATE TABLE dates(
date_id Integer PRIMARY KEY NOT NULL,
	date_of_update varchar(10) NOT NULL
);

CREATE TABLE exchange(
transaction_id INTEGER PRIMARY KEY NOT NULL,
version_id INTEGER NOT NULL,
base_currency INTEGER NOT NULL,
rate_currency INTEGER NOT NULL,
rate_value FLOAT NOT NULL
);

