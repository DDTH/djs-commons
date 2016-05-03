package com.github.ddth.djs.bo.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ddth.dao.BaseBo;

/**
 * Job execution info.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3
 */
public class JobExecInfoBo extends BaseBo {

    public final static JobExecInfoBo[] EMPTY_ARRAY = new JobExecInfoBo[0];

    public static JobExecInfoBo newInstance(String jobId) {
        JobExecInfoBo bo = new JobExecInfoBo();
        bo.setId(jobId);
        return bo;
    }

    public static JobExecInfoBo newInstance(String jobId, String lastFireoffTaskId,
            String lastExecuteTaskId) {
        JobExecInfoBo bo = newInstance(jobId);
        bo.setLastExecuteTaskId(lastExecuteTaskId).setLastFireoffTaskId(lastFireoffTaskId);
        return bo;
    }

    public static JobExecInfoBo newInstance(JobExecInfoBo clone) {
        JobExecInfoBo bo = new JobExecInfoBo();
        bo.fromJson(clone.toJson());
        return bo;
    }

    /*----------------------------------------------------------------------*/

    private final static String ATTR_ID = "id";
    private final static String ATTR_LAST_FIREOFF_TASKID = "fireoffTaskId";
    private final static String ATTR_LAST_EXECUTE_TASKID = "executeTaskId";

    @JsonIgnore
    public String getId() {
        return getAttribute(ATTR_ID, String.class);
    }

    public JobExecInfoBo setId(String id) {
        return (JobExecInfoBo) setAttribute(ATTR_ID, id != null ? id.trim().toLowerCase() : null);
    }

    @JsonIgnore
    public String getLastFireoffTaskId() {
        return getAttribute(ATTR_LAST_FIREOFF_TASKID, String.class);
    }

    public JobExecInfoBo setLastFireoffTaskId(String taskId) {
        return (JobExecInfoBo) setAttribute(ATTR_LAST_FIREOFF_TASKID,
                taskId != null ? taskId.trim().toLowerCase() : null);
    }

    @JsonIgnore
    public String getLastExecuteTaskId() {
        return getAttribute(ATTR_LAST_EXECUTE_TASKID, String.class);
    }

    public JobExecInfoBo setLastExecuteTaskId(String taskId) {
        return (JobExecInfoBo) setAttribute(ATTR_LAST_EXECUTE_TASKID,
                taskId != null ? taskId.trim().toLowerCase() : null);
    }
}
