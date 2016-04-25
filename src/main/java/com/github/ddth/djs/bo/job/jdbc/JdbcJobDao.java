package com.github.ddth.djs.bo.job.jdbc;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.ddth.dao.jdbc.BaseJdbcDao;
import com.github.ddth.djs.bo.job.IJobDao;
import com.github.ddth.djs.bo.job.JobInfoBo;

/**
 * Jdbc-implement of {@link IJobDao}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JdbcJobDao extends BaseJdbcDao implements IJobDao {

	private String tableName = "djs_jobinfo";

	private String cacheName = "DJS_JOBINFO";

	protected String getTableName() {
		return tableName;
	}

	public JdbcJobDao setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	protected String getCacleName() {
		return cacheName;
	}

	public JdbcJobDao setCacheName(String cacheName) {
		this.cacheName = cacheName;
		return this;
	}

	/*----------------------------------------------------------------------*/
	private static String cacheKeyJobInfo(String id) {
		return id;
	}

	private static String cacheKey(JobInfoBo jobInfo) {
		return cacheKeyJobInfo(jobInfo.getId());
	}

	private void invalidate(JobInfoBo jobInfo, boolean update) {
		if (update) {
			putToCache(cacheName, cacheKey(jobInfo), jobInfo);
		} else {
			removeFromCache(cacheName, cacheKey(jobInfo));
		}
	}
	/*----------------------------------------------------------------------*/

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JdbcJobDao init() {
		super.init();

		SQL_CREATE_JOBINFO = MessageFormat.format(SQL_CREATE_JOBINFO, tableName);
		SQL_DELETE_JOBINFO = MessageFormat.format(SQL_DELETE_JOBINFO, tableName);
		SQL_GET_JOBINFO = MessageFormat.format(SQL_GET_JOBINFO, tableName);
		SQL_UPDATE_JOBINFO = MessageFormat.format(SQL_UPDATE_JOBINFO, tableName);

		return this;
	}

	/*----------------------------------------------------------------------*/

	private final static String[] COLS_JOBINFO_ALL = { JobInfoBoMapper.COL_ID, JobInfoBoMapper.COL_DESC,
			JobInfoBoMapper.COL_METADATA, JobInfoBoMapper.COL_TAGS, JobInfoBoMapper.COL_UPDATE_TIMESTAMP };
	private final static String[] COLS_JOBINFO_CREATE = COLS_JOBINFO_ALL;
	private String SQL_CREATE_JOBINFO = "INSERT INTO {0} (" + StringUtils.join(COLS_JOBINFO_CREATE, ',') + ") VALUES ("
			+ StringUtils.repeat("?", ",", COLS_JOBINFO_CREATE.length) + ")";
	private String SQL_DELETE_JOBINFO = "DELETE FROM {0} WHERE " + JobInfoBoMapper.COL_ID + "=?";
	private String SQL_GET_JOBINFO = "SELECT " + StringUtils.join(COLS_JOBINFO_ALL, ',') + " FROM {0} WHERE "
			+ JobInfoBoMapper.COL_ID + "=?";
	private String SQL_UPDATE_JOBINFO = "UPDATE {0} SET "
			+ StringUtils.join(new String[] { JobInfoBoMapper.COL_DESC + "=?", JobInfoBoMapper.COL_METADATA + "=?",
					JobInfoBoMapper.COL_TAGS + "=?", JobInfoBoMapper.COL_UPDATE_TIMESTAMP + "=?" }, ',')
			+ " WHERE " + JobInfoBoMapper.COL_ID + "=?";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean create(JobInfoBo _jobInfo) {
		JobInfoBo jobInfo = JobInfoBo.newInstance(_jobInfo);
		final Date now = new Date();
		final Date TIMESTAMP = jobInfo.getUpdateTimestamp() != null ? jobInfo.getUpdateTimestamp() : now;
		final Object[] VALUES = new Object[] { jobInfo.getId(), jobInfo.getDescription(), jobInfo.getMetadata(),
				jobInfo.getTags(), TIMESTAMP };
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
		final Object[] PARAM_VALUES = new Object[] { jobInfo.getDescription(), jobInfo.getMetadata(), jobInfo.getTags(),
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
		JobInfoBo result = getFromCache(cacheName, cacheKey, JobInfoBo.class);
		if (result == null) {
			final Object[] WHERE_VALUES = new Object[] { id };
			try {
				List<JobInfoBo> dbRows = executeSelect(JobInfoBoMapper.instance, SQL_GET_JOBINFO, WHERE_VALUES);
				result = dbRows != null && dbRows.size() > 0 ? dbRows.get(0) : null;
				putToCache(cacheName, cacheKey, result);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

}
