package com.github.ddth.djs.bo.job.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.ddth.djs.bo.job.JobExecInfoBo;

public class JobExecInfoBoMapper implements RowMapper<JobExecInfoBo> {

    public final static JobExecInfoBoMapper instance = new JobExecInfoBoMapper();

    public final static String COL_ID = "job_id";
    public final static String COL_LAST_FIREOFF_TASKID = "job_last_fireoff_task_id";
    public final static String COL_LAST_EXECUTE_TASKID = "job_last_execute_task_id";

    /**
     * {@inheritDoc}
     */
    @Override
    public JobExecInfoBo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return newObjFromResultSet(rs);
    }

    private static JobExecInfoBo newObjFromResultSet(ResultSet rs) throws SQLException {
        JobExecInfoBo bo = new JobExecInfoBo();
        bo.setId(rs.getString(COL_ID));
        bo.setLastExecuteTaskId(rs.getString(COL_LAST_EXECUTE_TASKID));
        bo.setLastFireoffTaskId(rs.getString(COL_LAST_FIREOFF_TASKID));
        bo.markClean();
        return bo;
    }
}
