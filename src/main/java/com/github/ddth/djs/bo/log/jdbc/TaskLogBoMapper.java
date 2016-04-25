package com.github.ddth.djs.bo.log.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.ddth.djs.bo.log.TaskLogBo;

public class TaskLogBoMapper implements RowMapper<TaskLogBo> {

	public final static TaskLogBoMapper instance = new TaskLogBoMapper();

	public final static String COL_ID = "task_id";
	public final static String COL_JOB_ID = "job_id";
	public final static String COL_STATUS = "task_status";
	public final static String COL_MESSAGE = "task_message";
	public final static String COL_ERROR = "task_error";
	public final static String COL_OUTPUT = "task_output";
	public final static String COL_TIMESTAMP_CREATE = "timestamp_create";
	public final static String COL_NODE_CREATE = "node_create";
	public final static String COL_TIMESTAMP_PICKUP = "timestamp_pickup";
	public final static String COL_DURATION_PICKUP = "duration_pickup";
	public final static String COL_NODE_PICKUP = "node_pickup";
	public final static String COL_TIMESTAMP_FINISH = "timestamp_finish";
	public final static String COL_DURATION_FINISH = "duration_finish";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskLogBo mapRow(ResultSet rs, int rowNum) throws SQLException {
		return newObjFromResultSet(rs);
	}

	private static TaskLogBo newObjFromResultSet(ResultSet rs) throws SQLException {
		TaskLogBo bo = new TaskLogBo();
		bo.setId(rs.getString(COL_ID));
		bo.setJobId(rs.getString(COL_JOB_ID));
		bo.setStatus(rs.getInt(COL_STATUS));
		bo.setMessage(rs.getString(COL_MESSAGE));
		bo.setError(rs.getString(COL_ERROR));
		bo.setOutput(rs.getBytes(COL_OUTPUT));
		bo.setTimestampCreate(rs.getTimestamp(COL_TIMESTAMP_CREATE));
		bo.setNodeCreate(rs.getString(COL_NODE_CREATE));
		bo.setTimestampPickup(rs.getTimestamp(COL_TIMESTAMP_PICKUP));
		bo.setNodePickup(rs.getString(COL_NODE_PICKUP));
		bo.setTimestampFinish(rs.getTimestamp(COL_TIMESTAMP_FINISH));
		bo.markClean();
		return bo;
	}
}
