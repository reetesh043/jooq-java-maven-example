CREATE TABLE Bank (
  bank_id INT NOT NULL AUTO_INCREMENT,
  bank_name VARCHAR(50) NOT NULL,
  address VARCHAR(255) NOT NULL,
  PRIMARY KEY (bank_id)
);

CREATE TABLE BankAccount (
  account_id INT NOT NULL AUTO_INCREMENT,
  bank_id INT NOT NULL,
  account_number VARCHAR(20) NOT NULL,
  account_type VARCHAR(20) NOT NULL,
  balance DOUBLE NOT NULL,
  PRIMARY KEY (account_id),
  FOREIGN KEY (bank_id) REFERENCES Bank (bank_id)
);