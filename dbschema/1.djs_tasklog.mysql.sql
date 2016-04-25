-- DJS - MySQL Schema

-- template table to store task logs
DROP TABLE IF EXISTS djs_tasklog_base;
CREATE TABLE djs_tasklog_base (
    task_id                 VARCHAR(32),
        PRIMARY KEY (task_id),
    job_id                  VARCHAR(32),
        INDEX (job_id),
    task_status             INT                     NOT NULL DEFAULT (0),
    task_message            VARCHAR(255),
    task_error              TEXT,
    task_output             BLOB,
    timestamp_create        DATETIME,
        INDEX (timestamp_create),
    node_create             VARCHAR(255),
    timestamp_pickup        DATETIME,
        INDEX (timestamp_pickup),
    duration_pickup         INT,
    node_pickup             VARCHAR(255),
    timestamp_finish        DATETIME
        INDEX (timestamp_finish),
    duration_finish         INT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
