-- DJS - MySQL Schema

-- table to store Job description info
DROP TABLE IF EXISTS djs_jobinfo;
CREATE TABLE djs_jobinfo (
    job_id                  VARCHAR(32),
        PRIMARY KEY (job_id),
    job_desc                VARCHAR(255),
    job_update_timestamp    DATETIME,
    job_tags                TEXT,
    job_metadata            TEXT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
