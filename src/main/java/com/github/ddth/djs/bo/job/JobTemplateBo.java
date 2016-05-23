package com.github.ddth.djs.bo.job;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ddth.dao.BaseBo;

/**
 * Job template.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3.3
 */
public class JobTemplateBo extends BaseBo {

    public final static JobTemplateBo[] EMPTY_ARRAY = new JobTemplateBo[0];

    public static JobTemplateBo newInstance() {
        JobTemplateBo bo = new JobTemplateBo();
        bo.setUpdateTimestamp(new Date());
        return bo;
    }

    public static JobTemplateBo newInstance(JobTemplateBo clone) {
        JobTemplateBo bo = new JobTemplateBo();
        bo.fromJson(clone.toJson());
        return bo;
    }

    /*----------------------------------------------------------------------*/

    private final static String ATTR_ID = "id";
    private final static String ATTR_DESC = "desc";
    private final static String ATTR_UPDATE_TIMESTAMP = "timestamp";
    private final static String ATTR_PARAMS = "params";

    @JsonIgnore
    public String getId() {
        return getAttribute(ATTR_ID, String.class);
    }

    public JobTemplateBo setId(String id) {
        return (JobTemplateBo) setAttribute(ATTR_ID, id != null ? id.trim().toLowerCase() : null);
    }

    @JsonIgnore
    public String getDescription() {
        return getAttribute(ATTR_DESC, String.class);
    }

    public JobTemplateBo setDescription(String desc) {
        return (JobTemplateBo) setAttribute(ATTR_DESC, desc != null ? desc.trim() : "");
    }

    @JsonIgnore
    public Date getUpdateTimestamp() {
        return getAttribute(ATTR_UPDATE_TIMESTAMP, Date.class);
    }

    public JobTemplateBo setUpdateTimestamp(Date timestamp) {
        return (JobTemplateBo) setAttribute(ATTR_UPDATE_TIMESTAMP,
                timestamp != null ? timestamp : new Date());
    }

    @JsonIgnore
    public String getParams() {
        return getAttribute(ATTR_PARAMS, String.class);
    }

    public JobTemplateBo setParams(String params) {
        return (JobTemplateBo) setAttribute(ATTR_PARAMS, params != null ? params.trim() : "");
    }
}
