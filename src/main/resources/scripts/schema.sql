

  CREATE TABLE settingsTable(
  idsettingsTable int(11) NOT NULL AUTO_INCREMENT,
  buildingCorps varchar(45) NOT NULL
  );



  CREATE TABLE flatTable (
  idflatTable int(11) NOT NULL AUTO_INCREMENT,
  buildingCorps varchar(45) NOT NULL,
  flatRooms int(11) NOT NULL,
  flatFloor int(11) NOT NULL,
  flatNumber int(11) NOT NULL,
  flatArea double NOT NULL,
  flatSet varchar(45) NOT NULL,
  PRIMARY KEY (idflatTable),
  UNIQUE KEY idflatTable_UNIQUE (idflatTable)
  );
  

  
  CREATE TABLE flatBuyerTable (
  idflatBuyerTable int(11) NOT NULL AUTO_INCREMENT,
  idflatTable int(11) NOT NULL,
  flatBuyerLastname varchar(45) DEFAULT NULL,
  flatBuyerFirstname varchar(45) DEFAULT NULL,
  flatBuyerSurname varchar(45) DEFAULT NULL,
  flatContractDate datetime DEFAULT NULL,
  flatContractNumber varchar(45) DEFAULT NULL,
  flatCost int(11) DEFAULT NULL,
  flatSellerName varchar(45) DEFAULT NULL,
  flatNotes varchar(100) DEFAULT NULL,
  PRIMARY KEY (idflatBuyerTable),
  UNIQUE KEY id1a_flat_table_UNIQUE (idflatBuyerTable)
  );
  
  CREATE TABLE expensestable (
  idexpensesTable int(10) unsigned NOT NULL AUTO_INCREMENT,
  idflatTable int(11) NOT NULL,
  expensesTableDate datetime NOT NULL,
  expensesTableSum int(11) NOT NULL,
  expensesTableCategory varchar(45) NOT NULL,
  expensesTableValue varchar(100) NOT NULL,
  expensesTableValueTA varchar(100) DEFAULT NULL,
  expensesTableCurrency varchar(45) DEFAULT NULL,
  expensesTableCourse double DEFAULT NULL,
  expensesTableCourseDate date DEFAULT NULL,
  PRIMARY KEY (idexpensesTable),
  UNIQUE KEY idspendTable_UNIQUE (idexpensesTable)
  );
  
  CREATE TABLE usertable (
  iduserTable int(11) NOT NULL AUTO_INCREMENT,
  userFirstName varchar(45) NOT NULL,
  userLastName varchar(45) NOT NULL,
  userName varchar(45) NOT NULL,
  userPassword varchar(45) NOT NULL,
  userMail varchar(45) DEFAULT NULL,
  userPhone int(10) DEFAULT NULL,
  PRIMARY KEY (iduserTable),
  UNIQUE KEY iduserTable_UNIQUE (iduserTable),
  UNIQUE KEY userName_UNIQUE (userName)
  );
  
  INSERT iNTO settingsTable VALUES (1, '1/A');
  