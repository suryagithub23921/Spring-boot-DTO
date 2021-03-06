CREATE TABLE CHARACTERTYPE (
    ID                  bigint                   NOT NULL,
    CHARACTERNAME       varchar(10)
);

CREATE TABLE CLIENT (
    ID                  bigint                   NOT NULL,
    NAME                varchar(50)
);

CREATE TABLE FILECONTENT (
	ID                  bigint                   NOT NULL,
	CONTENT             bytea,
	VERSION             bigint
);

CREATE TABLE ENTRY (
    ID                  bigint                   NOT NULL,
    CLIENT_ID           bigint,
    DISCRIMINATOR       varchar(6), 
    KEY                 varchar(50),
    CHARTYPE_ID         bigint,
    VALID               boolean,
    CONTENT_ID          bigint,
    SIMPLEVALUE         varchar(75),
    FILEEXTENSION       varchar(15),
    FILELOCATION        varchar(250),
	VERSION             bigint
);

-- Create index
CREATE UNIQUE INDEX PK_ENTRY_ID ON ENTRY (ID);
CREATE UNIQUE INDEX BK_ENTRY ON ENTRY (CLIENT_ID, KEY);
CREATE UNIQUE INDEX PK_CHARACTERTYPE_ID ON CHARACTERTYPE (ID);
CREATE UNIQUE INDEX PK_FILECONTENT_ID ON FILECONTENT (ID);
CREATE UNIQUE INDEX PK_CLIENT_ID ON CLIENT (ID);
CREATE UNIQUE INDEX BK_CHARACTERTYPE ON CHARACTERTYPE (CHARACTERNAME);
CREATE INDEX IDX_ENTRY_KEY ON ENTRY (KEY);

-- Constraints
ALTER TABLE CHARACTERTYPE ADD CONSTRAINT NULL_CHARACTERTYPE_NAME CHECK(CHARACTERNAME IS NOT NULL);
ALTER TABLE CLIENT ADD CONSTRAINT NULL_CLIENT_NAME CHECK(NAME IS NOT NULL);
ALTER TABLE ENTRY ADD CONSTRAINT NULL_ENTRY_CLIENT CHECK(CLIENT_ID IS NOT NULL);
ALTER TABLE ENTRY ADD CONSTRAINT NULL_ENTRY_DISCRIMINATOR CHECK(DISCRIMINATOR IS NOT NULL);
ALTER TABLE ENTRY ADD CONSTRAINT NULL_ENTRY_KEY CHECK(KEY IS NOT NULL);
ALTER TABLE ENTRY ADD CONSTRAINT NULL_ENTRY_CHARTYPE CHECK(CHARTYPE_ID IS NOT NULL);
ALTER TABLE ENTRY ADD CONSTRAINT NULL_ENTRY_VALID CHECK(VALID IS NOT NULL);

-- PK
ALTER TABLE ENTRY ADD CONSTRAINT PK_ENTRY PRIMARY KEY (ID);
ALTER TABLE CHARACTERTYPE ADD CONSTRAINT PK_CHARACTERTYPE PRIMARY KEY (ID);
ALTER TABLE FILECONTENT ADD CONSTRAINT PK_FILECONTENT PRIMARY KEY (ID);
ALTER TABLE CLIENT ADD CONSTRAINT PK_CLIENT PRIMARY KEY (ID);

-- Create FK Indexes
CREATE INDEX FK_ENTRY_APP ON ENTRY (CLIENT_ID);
CREATE INDEX FK_ENTRY_CHA ON ENTRY (CHARTYPE_ID);
CREATE INDEX FK_ENTRY_CON ON ENTRY (CONTENT_ID);


ALTER TABLE ENTRY ADD CONSTRAINT FK_ENTRY_01 FOREIGN KEY (CHARTYPE_ID) REFERENCES CHARACTERTYPE(ID);
ALTER TABLE ENTRY ADD CONSTRAINT FK_ENTRY_02 FOREIGN KEY (CONTENT_ID) REFERENCES FILECONTENT(ID);
ALTER TABLE ENTRY ADD CONSTRAINT FK_CLIENT FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT(ID);



-- Config needs access to Account (select)
CREATE OR REPLACE VIEW V_ENTRIES AS
SELECT ENT.ID ENTRY_ID,
       CLI.NAME,
       ENT.KEY,
       ENT.SIMPLEVALUE,
       (
           CASE
               WHEN ENT.DISCRIMINATOR LIKE 'SIMPLE' THEN 'PROPERTY'
               ELSE 'FILE'
           END
       ) ENTRY_TYPE,
       ( 
           CASE 
               WHEN ENT.VALID = TRUE THEN 'TRUE'
               ELSE 'FALSE'
           END 
       ) AS VALID,
       CHR.CHARACTERNAME AS CHARACTER,
       (
           CASE
               WHEN CHR.CHARACTERNAME LIKE 'Volatil' THEN 'FALSE'
               ELSE 'TRUE'
           END
       ) AS CACHEABLE,
       (
           CASE
               WHEN CHR.CHARACTERNAME LIKE 'Fixed' THEN 'TRUE'
               ELSE 'FALSE'
           END
       ) AS CACHEPERSISTENT,
       (
           CASE
               WHEN CON.CONTENT IS NOT NULL THEN 'TEXT'
               ELSE 'N/A'
           END
       ) AS FILECONTENT,
       ENT.FILEEXTENSION,
       ENT.FILELOCATION
FROM   CLIENT CLI
       JOIN ENTRY                  ENT ON CLI.ID = ENT.CLIENT_ID
       JOIN CHARACTERTYPE          CHR ON CHR.ID = ENT.CHARTYPE_ID
       LEFT OUTER JOIN FILECONTENT CON ON CON.ID = ENT.CONTENT_ID
ORDER BY  
       CLI.NAME  ASC,
       ENT.KEY   ASC;
