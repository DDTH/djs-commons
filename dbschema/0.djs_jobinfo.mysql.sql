-- DJS - MySQL Schema

DROP TABLE IF EXISTS djs_jobtemplate;
CREATE TABLE djs_jobtemplate (
    tpl_id                          VARCHAR(32),
        PRIMARY KEY (tpl_id),
    tpl_desc                        VARCHAR(255),
    tpl_update_timestamp            DATETIME,
    tpl_params                      TEXT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

-- table to store Job's description info
DROP TABLE IF EXISTS djs_jobinfo;
CREATE TABLE djs_jobinfo (
    job_id                          VARCHAR(32),
        PRIMARY KEY (job_id),
    job_desc                        VARCHAR(255),
    job_template_id                 VARCHAR(32),
    job_update_timestamp            DATETIME,
    job_tags                        TEXT,
    job_metadata                    TEXT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

-- table to store Job's last execution info
DROP TABLE IF EXISTS djs_jobexecinfo;
CREATE TABLE djs_jobexecinfo (
    job_id                          VARCHAR(32),
        PRIMARY KEY (job_id),
    job_last_execute_task_id        VARCHAR(32),
    job_last_fireoff_task_id        VARCHAR(32)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
