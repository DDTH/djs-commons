package com.github.ddth.djs.bo.job.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.github.ddth.djs.bo.job.JobTemplateBo;

public class JobTemplateBoMapper implements RowMapper<JobTemplateBo> {

    public final static JobTemplateBoMapper instance = new JobTemplateBoMapper();

    public final static String COL_ID = "tpl_id";
    public final static String COL_DESC = "tpl_desc";
    public final static String COL_UPDATE_TIMESTAMP = "tpl_update_timestamp";
    public final static String COL_PARAMS = "tpl_params";

    /**
     * {@inheritDoc}
     */
    @Override
    public JobTemplateBo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return newObjFromResultSet(rs);
    }

    private static JobTemplateBo newObjFromResultSet(ResultSet rs) throws SQLException {
        JobTemplateBo bo = new JobTemplateBo();
        bo.setId(rs.getString(COL_ID));
        bo.setDescription(rs.getString(COL_DESC));
        bo.setUpdateTimestamp(rs.getTimestamp(COL_UPDATE_TIMESTAMP));
        bo.setParams(rs.getString(COL_PARAMS));
        bo.markClean();
        return bo;
    }
}
