package com.github.ddth.djs.bo.log.jdbc;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.ddth.commons.utils.DateFormatUtils;
import com.github.ddth.dao.jdbc.BaseJdbcDao;
import com.github.ddth.djs.bo.job.IJobDao;
import com.github.ddth.djs.bo.log.ITaskLogDao;
import com.github.ddth.djs.bo.log.TaskLogBo;
import com.github.ddth.djs.utils.DjsUtils;

/**
 * Jdbc-implement of {@link IJobDao}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JdbcTaskLogDao extends BaseJdbcDao implements ITaskLogDao {

	private String tableTemplateName = "djs_tasklog_{0}";

	private String cacheName = "DJS_TASKLOG";

	protected String getTableTemplateName() {
		return tableTemplateName;
	}

	public JdbcTaskLogDao setTableTemplateName(String tableTemplateName) {
		this.tableTemplateName = tableTemplateName;
		return this;
	}

	private String calcTableName(Date timestamp) {
		return MessageFormat.format(tableTemplateName, DateFormatUtils.toString(timestamp, "yyyyMM"));
	}

	private String calcTableName(TaskLogBo taskLog) {
		return calcTableName(DjsUtils.extractTimestamp(taskLog));
	}

	private String calcTableName(String taskLogId) {
		return calcTableName(DjsUtils.extractTimestamp(taskLogId));
	}

	protected String getCacheName() {
		return cacheName;
	}

	public JdbcTaskLogDao setCacheName(String cacheName) {
		this.cacheName = cacheName;
		return this;
	}

	/*----------------------------------------------------------------------*/
	private static String cacheKeyTaskLog(String id) {
		return id;
	}

	private static String cacheKey(TaskLogBo taskLog) {
		return cacheKeyTaskLog(taskLog.getId());
	}

	private void invalidate(TaskLogBo taskLog, boolean update) {
		if (update) {
			putToCache(cacheName, cacheKey(taskLog), taskLog);
		} else {
			removeFromCache(cacheName, cacheKey(taskLog));
		}
	}
	/*----------------------------------------------------------------------*/

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JdbcTaskLogDao init() {
		super.init();

		SQL_CREATE_TASKLOG = MessageFormat.format(SQL_CREATE_TASKLOG, tableTemplateName);
		SQL_DELETE_TASKLOG = MessageFormat.format(SQL_DELETE_TASKLOG, tableTemplateName);
		SQL_GET_TASKLOG = MessageFormat.format(SQL_GET_TASKLOG, tableTemplateName);
		SQL_UPDATE_TASKLOG = MessageFormat.format(SQL_UPDATE_TASKLOG, tableTemplateName);

		return this;
	}

	/*----------------------------------------------------------------------*/

	private final static String[] COLS_TASKLOG_ALL = { TaskLogBoMapper.COL_ID, TaskLogBoMapper.COL_JOB_ID,
			TaskLogBoMapper.COL_STATUS, TaskLogBoMapper.COL_MESSAGE, TaskLogBoMapper.COL_ERROR,
			TaskLogBoMapper.COL_OUTPUT, TaskLogBoMapper.COL_TIMESTAMP_CREATE, TaskLogBoMapper.COL_NODE_CREATE,
			TaskLogBoMapper.COL_TIMESTAMP_PICKUP, TaskLogBoMapper.COL_DURATION_PICKUP, TaskLogBoMapper.COL_NODE_PICKUP,
			TaskLogBoMapper.COL_TIMESTAMP_FINISH, TaskLogBoMapper.COL_TIMESTAMP_FINISH };
	private final static String[] COLS_TASKLOG_CREATE = COLS_TASKLOG_ALL;
	private String SQL_CREATE_TASKLOG = "INSERT INTO {0} (" + StringUtils.join(COLS_TASKLOG_CREATE, ',') + ") VALUES ("
			+ StringUtils.repeat("?", ",", COLS_TASKLOG_CREATE.length) + ")";
	private String SQL_DELETE_TASKLOG = "DELETE FROM {0} WHERE " + TaskLogBoMapper.COL_ID + "=?";
	private String SQL_GET_TASKLOG = "SELECT " + StringUtils.join(COLS_TASKLOG_ALL, ',') + " FROM {0} WHERE "
			+ TaskLogBoMapper.COL_ID + "=?";
	private String SQL_UPDATE_TASKLOG = "UPDATE {0} SET "
			+ StringUtils.join(new String[] { TaskLogBoMapper.COL_STATUS + "=?", TaskLogBoMapper.COL_MESSAGE + "=?",
					TaskLogBoMapper.COL_ERROR + "=?", TaskLogBoMapper.COL_OUTPUT + "=?",
					TaskLogBoMapper.COL_TIMESTAMP_PICKUP + "=?", TaskLogBoMapper.COL_DURATION_PICKUP + "=?",
					TaskLogBoMapper.COL_NODE_PICKUP + "=?", TaskLogBoMapper.COL_TIMESTAMP_FINISH + "=?",
					TaskLogBoMapper.COL_DURATION_FINISH + "=?" }, ',')
			+ " WHERE " + TaskLogBoMapper.COL_ID + "=?";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean create(TaskLogBo _taskLog) {
		TaskLogBo taskLog = TaskLogBo.newInstance(_taskLog);
		final Date now = new Date();
		final Date TIMESTAMP = taskLog.getTimestampCreate() != null ? taskLog.getTimestampCreate() : now;
		final Object[] VALUES = new Object[] { taskLog.getId(), taskLog.getJobId(), taskLog.getStatus(),
				taskLog.getMessage(), taskLog.getError(), taskLog.getOutput(), TIMESTAMP, taskLog.getNodeCreate(),
				taskLog.getTimestampPickup(), taskLog.getDurationPickup(), taskLog.getNodePickup(),
				taskLog.getTimestampFinish(), taskLog.getDurationFinish() };
		try {
			final String SQL = MessageFormat.format(SQL_CREATE_TASKLOG, calcTableName(taskLog));
			int numRows = execute(SQL, VALUES);
			taskLog.setTimestampCreate(TIMESTAMP);
			invalidate(taskLog, true);
			return numRows > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean delete(TaskLogBo taskLog) {
		final Object[] VALUES = new Object[] { taskLog.getId() };
		try {
			final String SQL = MessageFormat.format(SQL_DELETE_TASKLOG, calcTableName(taskLog));
			int numRows = execute(SQL, VALUES);
			invalidate(taskLog, false);
			return numRows > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(TaskLogBo taskLog) {
		final Object[] PARAM_VALUES = new Object[] { taskLog.getStatus(), taskLog.getMessage(), taskLog.getError(),
				taskLog.getOutput(), taskLog.getTimestampPickup(), taskLog.getDurationPickup(), taskLog.getNodePickup(),
				taskLog.getTimestampFinish(), taskLog.getDurationFinish(), taskLog.getId() };
		try {
			final String SQL = MessageFormat.format(SQL_UPDATE_TASKLOG, calcTableName(taskLog));
			int nunRows = execute(SQL, PARAM_VALUES);
			invalidate(taskLog, true);
			return nunRows > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskLogBo getTaskLog(String id) {
		final String cacheKey = cacheKeyTaskLog(id);
		TaskLogBo result = getFromCache(cacheName, cacheKey, TaskLogBo.class);
		if (result == null) {
			final Object[] WHERE_VALUES = new Object[] { id };
			try {
				final String SQL = MessageFormat.format(SQL_GET_TASKLOG, calcTableName(id));
				List<TaskLogBo> dbRows = executeSelect(TaskLogBoMapper.instance, SQL, WHERE_VALUES);
				result = dbRows != null && dbRows.size() > 0 ? dbRows.get(0) : null;
				putToCache(cacheName, cacheKey, result);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

}
