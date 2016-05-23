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

/**
 * Jdbc-implement of {@link IJobDao}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JdbcJobDao extends BaseJdbcDao implements IJobDao {

    private String tableNameJobInfo = "djs_jobinfo";
    private String cacheNameJobInfo = "DJS_JOBINFO";

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

        return this;
    }

    /*----------------------------------------------------------------------*/

    private final static String[] COLS_JOBINFO_ALL = { JobInfoBoMapper.COL_ID,
            JobInfoBoMapper.COL_DESC, JobInfoBoMapper.COL_METADATA, JobInfoBoMapper.COL_TAGS,
            JobInfoBoMapper.COL_UPDATE_TIMESTAMP };
    private final static String[] COLS_JOBINFO_CREATE = COLS_JOBINFO_ALL;
    private String SQL_CREATE_JOBINFO = "INSERT INTO {0} ("
            + StringUtils.join(COLS_JOBINFO_CREATE, ',') + ") VALUES ("
            + StringUtils.repeat("?", ",", COLS_JOBINFO_CREATE.length) + ")";
    private String SQL_DELETE_JOBINFO = "DELETE FROM {0} WHERE " + JobInfoBoMapper.COL_ID + "=?";
    private String SQL_GET_ALL_JOBINFO_IDS = "SELECT " + JobInfoBoMapper.COL_ID
            + " FROM {0} ORDER BY " + JobInfoBoMapper.COL_ID + " DESC";
    private String SQL_GET_JOBINFO = "SELECT " + StringUtils.join(COLS_JOBINFO_ALL, ',')
            + " FROM {0} WHERE " + JobInfoBoMapper.COL_ID + "=?";
    private String SQL_UPDATE_JOBINFO = "UPDATE {0} SET " + StringUtils.join(
            new String[] { JobInfoBoMapper.COL_DESC + "=?", JobInfoBoMapper.COL_METADATA + "=?",
                    JobInfoBoMapper.COL_TAGS + "=?", JobInfoBoMapper.COL_UPDATE_TIMESTAMP + "=?" },
            ',') + " WHERE " + JobInfoBoMapper.COL_ID + "=?";

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
                jobInfo.getMetadata(), jobInfo.getTags(), TIMESTAMP };
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
                jobInfo.getMetadata(), jobInfo.getTags(), jobInfo.getUpdateTimestamp(),
                jobInfo.getId() };
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
    private String tableNameJobExecInfo = "djs_jobexecinfo";
    private String cacheNameJobExecInfo = "DJS_JOBEXECINFO";

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
}
