package com.github.ddth.djs.bo.job.jdbc;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.ddth.commons.utils.DPathUtils;
import com.github.ddth.dao.jdbc.BaseJdbcDao;
import com.github.ddth.djs.bo.job.IJobDao;
import com.github.ddth.djs.bo.job.JobExecInfoBo;
import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.bo.job.JobTemplateBo;

/**
 * Jdbc-implement of {@link IJobDao}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JdbcJobDao extends BaseJdbcDao implements IJobDao {

    private String tableNameJobInfo = "djs_jobinfo";
    private String cacheNameJobInfo = "DJS_JOBINFO";

    private String tableNameJobTemplate = "djs_jobtpl";
    private String cacheNameJobTemplate = "DJS_JOBTPL";

    private String tableNameJobExecInfo = "djs_jobexecinfo";
    private String cacheNameJobExecInfo = "DJS_JOBEXECINFO";

    protected String getTableNameJobInfo() {
        return tableNameJobInfo;
    }

    public JdbcJobDao setTableNameJobInfo(String tableNameJobInfo) {
        this.tableNameJobInfo = tableNameJobInfo;
        return this;
    }

    protected String getCacheNameJobInfo() {
        return cacheNameJobInfo;
    }

    public JdbcJobDao setCacheNameJobInfo(String cacheNameJobInfo) {
        this.cacheNameJobInfo = cacheNameJobInfo;
        return this;
    }

    protected String getTableNameJobTemplate() {
        return tableNameJobTemplate;
    }

    public JdbcJobDao setTableNameJobTemplate(String tableNameJobTemplate) {
        this.tableNameJobTemplate = tableNameJobTemplate;
        return this;
    }

    protected String getCacheNameJobTemplate() {
        return cacheNameJobTemplate;
    }

    public JdbcJobDao setCacheNameJobTemplate(String cacheNameJobTemplate) {
        this.cacheNameJobTemplate = cacheNameJobTemplate;
        return this;
    }

    protected String getTableNameJobExecInfo() {
        return tableNameJobExecInfo;
    }

    public JdbcJobDao setTableNameJobExecInfo(String tableNameJobExecInfo) {
        this.tableNameJobExecInfo = tableNameJobExecInfo;
        return this;
    }

    protected String getCacheNameJobExecInfo() {
        return cacheNameJobExecInfo;
    }

    public JdbcJobDao setCacheNameJobExecInfo(String cacheNameJobExecInfo) {
        this.cacheNameJobExecInfo = cacheNameJobExecInfo;
        return this;
    }

    /*----------------------------------------------------------------------*/
    private static String cacheKeyAllJobInfo() {
        return "_ALL_JOBINFO_";
    }

    private static String cacheKeyJobInfo(String id) {
        return id;
    }

    private static String cacheKey(JobInfoBo jobInfo) {
        return cacheKeyJobInfo(jobInfo.getId());
    }

    private void invalidate(JobInfoBo jobInfo, boolean update) {
        if (update) {
            putToCache(cacheNameJobInfo, cacheKey(jobInfo), jobInfo);
        } else {
            removeFromCache(cacheNameJobInfo, cacheKey(jobInfo));
        }
        removeFromCache(cacheNameJobInfo, cacheKeyAllJobInfo());
    }

    private static String cacheKeyAllJobTemplates() {
        return "_ALL_JOBTPL_";
    }

    private static String cacheKeyJobTemplate(String id) {
        return id;
    }

    private static String cacheKey(JobTemplateBo jobTemplate) {
        return cacheKeyJobTemplate(jobTemplate.getId());
    }

    private void invalidate(JobTemplateBo jobTemplate, boolean update) {
        if (update) {
            putToCache(cacheNameJobTemplate, cacheKey(jobTemplate), jobTemplate);
        } else {
            removeFromCache(cacheNameJobTemplate, cacheKey(jobTemplate));
        }
        removeFromCache(cacheNameJobTemplate, cacheKeyAllJobTemplates());
    }

    private static String cacheKeyJobExecInfo(String id) {
        return id;
    }

    private static String cacheKey(JobExecInfoBo jobExecInfo) {
        return cacheKeyJobExecInfo(jobExecInfo.getId());
    }

    private void invalidate(JobExecInfoBo jobExecInfo, boolean update) {
        if (update) {
            putToCache(cacheNameJobExecInfo, cacheKey(jobExecInfo), jobExecInfo);
        } else {
            removeFromCache(cacheNameJobExecInfo, cacheKey(jobExecInfo));
        }
    }
    /*----------------------------------------------------------------------*/

    /**
     * {@inheritDoc}
     */
    @Override
    public JdbcJobDao init() {
        super.init();

        SQL_CREATE_JOBINFO = MessageFormat.format(SQL_CREATE_JOBINFO, tableNameJobInfo);
        SQL_DELETE_JOBINFO = MessageFormat.format(SQL_DELETE_JOBINFO, tableNameJobInfo);
        SQL_GET_JOBINFO = MessageFormat.format(SQL_GET_JOBINFO, tableNameJobInfo);
        SQL_GET_ALL_JOBINFO_IDS = MessageFormat.format(SQL_GET_ALL_JOBINFO_IDS, tableNameJobInfo);
        SQL_UPDATE_JOBINFO = MessageFormat.format(SQL_UPDATE_JOBINFO, tableNameJobInfo);

        SQL_CREATE_OR_UPDATE_JOBEXECINFO = MessageFormat.format(SQL_CREATE_OR_UPDATE_JOBEXECINFO,
                tableNameJobExecInfo, JobExecInfoBoMapper.COL_LAST_EXECUTE_TASKID,
                JobExecInfoBoMapper.COL_LAST_FIREOFF_TASKID);

        SQL_CREATE_JOBTEMPLATE = MessageFormat.format(SQL_CREATE_JOBTEMPLATE, tableNameJobTemplate);
        SQL_DELETE_JOBTEMPLATE = MessageFormat.format(SQL_DELETE_JOBTEMPLATE, tableNameJobTemplate);
        SQL_GET_JOBTEMPLATE = MessageFormat.format(SQL_GET_JOBTEMPLATE, tableNameJobTemplate);
        SQL_GET_ALL_JOBTEMPLATE_IDS = MessageFormat.format(SQL_GET_ALL_JOBTEMPLATE_IDS,
                tableNameJobTemplate);
        SQL_UPDATE_JOBTEMPLATE = MessageFormat.format(SQL_UPDATE_JOBTEMPLATE, tableNameJobTemplate);

        return this;
    }

    /*----------------------------------------------------------------------*/

    private final static String[] COLS_JOBINFO_ALL = { JobInfoBoMapper.COL_ID,
            JobInfoBoMapper.COL_DESC, JobInfoBoMapper.COL_TEMPLATE_ID, JobInfoBoMapper.COL_METADATA,
            JobInfoBoMapper.COL_TAGS, JobInfoBoMapper.COL_UPDATE_TIMESTAMP };
    private final static String[] COLS_JOBINFO_CREATE = COLS_JOBINFO_ALL;
    private String SQL_CREATE_JOBINFO = "INSERT INTO {0} ("
            + StringUtils.join(COLS_JOBINFO_CREATE, ',') + ") VALUES ("
            + StringUtils.repeat("?", ",", COLS_JOBINFO_CREATE.length) + ")";
    private String SQL_DELETE_JOBINFO = "DELETE FROM {0} WHERE " + JobInfoBoMapper.COL_ID + "=?";
    private String SQL_GET_ALL_JOBINFO_IDS = "SELECT " + JobInfoBoMapper.COL_ID
            + " FROM {0} ORDER BY " + JobInfoBoMapper.COL_ID + " DESC";
    private String SQL_GET_JOBINFO = "SELECT " + StringUtils.join(COLS_JOBINFO_ALL, ',')
            + " FROM {0} WHERE " + JobInfoBoMapper.COL_ID + "=?";
    private String SQL_UPDATE_JOBINFO = "UPDATE {0} SET "
            + StringUtils.join(new String[] { JobInfoBoMapper.COL_DESC + "=?",
                    JobInfoBoMapper.COL_TEMPLATE_ID + "=?", JobInfoBoMapper.COL_METADATA + "=?",
                    JobInfoBoMapper.COL_TAGS + "=?", JobInfoBoMapper.COL_UPDATE_TIMESTAMP + "=?" },
                    ',')
            + " WHERE " + JobInfoBoMapper.COL_ID + "=?";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(JobInfoBo _jobInfo) {
        JobInfoBo jobInfo = JobInfoBo.newInstance(_jobInfo);
        final Date now = new Date();
        final Date TIMESTAMP = jobInfo.getUpdateTimestamp() != null ? jobInfo.getUpdateTimestamp()
                : now;
        final Object[] VALUES = new Object[] { jobInfo.getId(), jobInfo.getDescription(),
                jobInfo.getTemplateId(), jobInfo.getMetadata(), jobInfo.getTags(), TIMESTAMP };
        try {
            int numRows = execute(SQL_CREATE_JOBINFO, VALUES);
            jobInfo.setUpdateTimestamp(TIMESTAMP);
            invalidate(jobInfo, true);
            return numRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(JobInfoBo jobInfo) {
        final Object[] VALUES = new Object[] { jobInfo.getId() };
        try {
            int numRows = execute(SQL_DELETE_JOBINFO, VALUES);
            invalidate(jobInfo, false);
            return numRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(JobInfoBo _jobInfo) {
        JobInfoBo jobInfo = JobInfoBo.newInstance(_jobInfo);
        jobInfo.setUpdateTimestamp(new Date());
        final Object[] PARAM_VALUES = new Object[] { jobInfo.getDescription(),
                jobInfo.getTemplateId(), jobInfo.getMetadata(), jobInfo.getTags(),
                jobInfo.getUpdateTimestamp(), jobInfo.getId() };
        try {
            int nunRows = execute(SQL_UPDATE_JOBINFO, PARAM_VALUES);
            invalidate(jobInfo, true);
            return nunRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobInfoBo getJobInfo(String id) {
        final String cacheKey = cacheKeyJobInfo(id);
        JobInfoBo result = getFromCache(cacheNameJobInfo, cacheKey, JobInfoBo.class);
        if (result == null) {
            final Object[] WHERE_VALUES = new Object[] { id };
            try {
                List<JobInfoBo> dbRows = executeSelect(JobInfoBoMapper.instance, SQL_GET_JOBINFO,
                        WHERE_VALUES);
                result = dbRows != null && dbRows.size() > 0 ? dbRows.get(0) : null;
                putToCache(cacheNameJobInfo, cacheKey, result);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public String[] getAllJobInfoIds() {
        final String cacheKey = cacheKeyAllJobInfo();
        List<String> result = getFromCache(cacheNameJobInfo, cacheKey, List.class);
        if (result == null) {
            final Object[] WHERE_VALUES = ArrayUtils.EMPTY_OBJECT_ARRAY;
            try {
                List<Map<String, Object>> dbRows = executeSelect(SQL_GET_ALL_JOBINFO_IDS,
                        WHERE_VALUES);
                result = new ArrayList<>();
                for (Map<String, Object> dbRow : dbRows) {
                    String id = DPathUtils.getValue(dbRow, JobInfoBoMapper.COL_ID, String.class);
                    if (!StringUtils.isBlank(id)) {
                        result.add(id);
                    }
                }
                putToCache(cacheNameJobInfo, cacheKey, result);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /*----------------------------------------------------------------------*/

    private final static String[] COLS_JOBEXECINFO_ALL = { JobExecInfoBoMapper.COL_ID,
            JobExecInfoBoMapper.COL_LAST_EXECUTE_TASKID,
            JobExecInfoBoMapper.COL_LAST_FIREOFF_TASKID };
    private String SQL_GET_JOBEXECINFO = "SELECT " + StringUtils.join(COLS_JOBEXECINFO_ALL, ',')
            + " FROM {0} WHERE " + JobExecInfoBoMapper.COL_ID + "=?";
    private String SQL_CREATE_OR_UPDATE_JOBEXECINFO = "INSERT INTO {0} ("
            + StringUtils.join(COLS_JOBEXECINFO_ALL, ',') + ") VALUES ("
            + StringUtils.repeat("?", ",", COLS_JOBEXECINFO_ALL.length)
            + ") ON DUPLICATE KEY UPDATE {1} = IF({1} > VALUES({1}), VALUES({1}), {1}),"
            + "{2} = IF({2} > VALUES({2}), VALUES({2}), {2})";

    /**
     * {@inheritDoc}
     */
    @Override
    public JobExecInfoBo getJobExecInfo(String id) {
        final String cacheKey = cacheKeyJobExecInfo(id);
        JobExecInfoBo result = getFromCache(cacheNameJobExecInfo, cacheKey, JobExecInfoBo.class);
        if (result == null) {
            final Object[] WHERE_VALUES = new Object[] { id };
            try {
                List<JobExecInfoBo> dbRows = executeSelect(JobExecInfoBoMapper.instance,
                        SQL_GET_JOBEXECINFO, WHERE_VALUES);
                result = dbRows != null && dbRows.size() > 0 ? dbRows.get(0) : null;
                putToCache(cacheNameJobExecInfo, cacheKey, result);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean createOrUpdate(JobExecInfoBo jobExecInfo) {
        try {
            int numRows = execute(SQL_CREATE_OR_UPDATE_JOBEXECINFO,
                    new Object[] { jobExecInfo.getId(), jobExecInfo.getLastExecuteTaskId(),
                            jobExecInfo.getLastFireoffTaskId() });
            if (numRows > 0) {
                invalidate(jobExecInfo, true);
            }
            return numRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*----------------------------------------------------------------------*/

    private final static String[] COLS_JOBTEMPLATE_ALL = { JobTemplateBoMapper.COL_ID,
            JobTemplateBoMapper.COL_DESC, JobTemplateBoMapper.COL_UPDATE_TIMESTAMP,
            JobTemplateBoMapper.COL_PARAMS };
    private final static String[] COLS_JOBTEMPLATE_CREATE = COLS_JOBTEMPLATE_ALL;
    private String SQL_CREATE_JOBTEMPLATE = "INSERT INTO {0} ("
            + StringUtils.join(COLS_JOBTEMPLATE_CREATE, ',') + ") VALUES ("
            + StringUtils.repeat("?", ",", COLS_JOBTEMPLATE_CREATE.length) + ")";
    private String SQL_DELETE_JOBTEMPLATE = "DELETE FROM {0} WHERE " + JobTemplateBoMapper.COL_ID
            + "=?";
    private String SQL_GET_ALL_JOBTEMPLATE_IDS = "SELECT " + JobTemplateBoMapper.COL_ID
            + " FROM {0} ORDER BY " + JobTemplateBoMapper.COL_ID + " DESC";
    private String SQL_GET_JOBTEMPLATE = "SELECT " + StringUtils.join(COLS_JOBTEMPLATE_ALL, ',')
            + " FROM {0} WHERE " + JobTemplateBoMapper.COL_ID + "=?";
    private String SQL_UPDATE_JOBTEMPLATE = "UPDATE {0} SET "
            + StringUtils.join(new String[] { JobTemplateBoMapper.COL_DESC + "=?",
                    JobTemplateBoMapper.COL_UPDATE_TIMESTAMP + "=?",
                    JobTemplateBoMapper.COL_PARAMS + "=?" }, ',')
            + " WHERE " + JobTemplateBoMapper.COL_ID + "=?";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(JobTemplateBo _jobTemplate) {
        JobTemplateBo jobTemplate = JobTemplateBo.newInstance(_jobTemplate);
        final Date now = new Date();
        final Date TIMESTAMP = jobTemplate.getUpdateTimestamp() != null
                ? jobTemplate.getUpdateTimestamp() : now;
        final Object[] VALUES = new Object[] { jobTemplate.getId(), jobTemplate.getDescription(),
                TIMESTAMP, jobTemplate.getParams() };
        try {
            int numRows = execute(SQL_CREATE_JOBTEMPLATE, VALUES);
            jobTemplate.setUpdateTimestamp(TIMESTAMP);
            invalidate(jobTemplate, true);
            return numRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(JobTemplateBo jobTemplate) {
        final Object[] VALUES = new Object[] { jobTemplate.getId() };
        try {
            int numRows = execute(SQL_DELETE_JOBTEMPLATE, VALUES);
            invalidate(jobTemplate, false);
            return numRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(JobTemplateBo _jobTemplate) {
        JobTemplateBo jobTemplate = JobTemplateBo.newInstance(_jobTemplate);
        jobTemplate.setUpdateTimestamp(new Date());
        final Object[] PARAM_VALUES = new Object[] { jobTemplate.getDescription(),
                jobTemplate.getUpdateTimestamp(), jobTemplate.getParams(), jobTemplate.getId() };
        try {
            int nunRows = execute(SQL_UPDATE_JOBTEMPLATE, PARAM_VALUES);
            invalidate(jobTemplate, true);
            return nunRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JobTemplateBo getJobTemplate(String id) {
        final String cacheKey = cacheKeyJobTemplate(id);
        JobTemplateBo result = getFromCache(cacheNameJobTemplate, cacheKey, JobTemplateBo.class);
        if (result == null) {
            final Object[] WHERE_VALUES = new Object[] { id };
            try {
                List<JobTemplateBo> dbRows = executeSelect(JobTemplateBoMapper.instance,
                        SQL_GET_JOBTEMPLATE, WHERE_VALUES);
                result = dbRows != null && dbRows.size() > 0 ? dbRows.get(0) : null;
                putToCache(cacheNameJobTemplate, cacheKey, result);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public String[] getAllJobTemplateIds() {
        final String cacheKey = cacheKeyAllJobTemplates();
        List<String> result = getFromCache(cacheNameJobTemplate, cacheKey, List.class);
        if (result == null) {
            final Object[] WHERE_VALUES = ArrayUtils.EMPTY_OBJECT_ARRAY;
            try {
                List<Map<String, Object>> dbRows = executeSelect(SQL_GET_ALL_JOBTEMPLATE_IDS,
                        WHERE_VALUES);
                result = new ArrayList<>();
                for (Map<String, Object> dbRow : dbRows) {
                    String id = DPathUtils.getValue(dbRow, JobTemplateBoMapper.COL_ID,
                            String.class);
                    if (!StringUtils.isBlank(id)) {
                        result.add(id);
                    }
                }
                putToCache(cacheNameJobTemplate, cacheKey, result);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /*----------------------------------------------------------------------*/
}
