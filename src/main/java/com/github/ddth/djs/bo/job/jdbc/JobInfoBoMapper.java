package com.github.ddth.djs.bo.job.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.ddth.djs.bo.job.JobInfoBo;

public class JobInfoBoMapper implements RowMapper<JobInfoBo> {

    public final static JobInfoBoMapper instance = new JobInfoBoMapper();

    public final static String COL_ID = "job_id";
    public final static String COL_DESC = "job_desc";
    public final static String COL_TEMPLATE_ID = "job_template_id";
    public final static String COL_UPDATE_TIMESTAMP = "job_update_timestamp";
    public final static String COL_TAGS = "job_tags";
    public final static String COL_METADATA = "job_metadata";

    /**
     * {@inheritDoc}
     */
    @Override
    public JobInfoBo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return newObjFromResultSet(rs);
    }

    private static JobInfoBo newObjFromResultSet(ResultSet rs) throws SQLException {
        JobInfoBo bo = new JobInfoBo();
        bo.setId(rs.getString(COL_ID));
        bo.setDescription(rs.getString(COL_DESC));
        bo.setTemplateId(rs.getString(COL_TEMPLATE_ID));
        bo.setUpdateTimestamp(rs.getTimestamp(COL_UPDATE_TIMESTAMP));
        bo.setTags(rs.getString(COL_TAGS));
        bo.setMetadata(rs.getString(COL_METADATA));
        bo.markClean();
        return bo;
    }
}
