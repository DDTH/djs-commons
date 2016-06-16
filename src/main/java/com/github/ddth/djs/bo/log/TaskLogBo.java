package com.github.ddth.djs.bo.log;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ddth.dao.BaseBo;
import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;
import com.github.ddth.djs.utils.DjsConstants;
import com.github.ddth.djs.utils.DjsUtils;

/**
 * Task log entry.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class TaskLogBo extends BaseBo {

    public final static TaskLogBo[] EMPTY_ARRAY = new TaskLogBo[0];

    public static TaskLogBo newInstance() {
        String id = DjsUtils.IDGEN.generateId128Hex();
        TaskLogBo bo = new TaskLogBo();
        bo.setId(id).setTimestampCreate(new Date()).setStatus(DjsConstants.TASK_STATUS_NEW);
        return bo;
    }

    public static TaskLogBo newInstance(TaskLogBo clone) {
        TaskLogBo bo = new TaskLogBo();
        bo.fromJson(clone.toJson());
        return bo;
    }

    /**
     * 
     * @param jobInfo
     * @return
     * @since 0.1.3.11
     */
    public static TaskLogBo newInstance(JobInfoBo jobInfo) {
        TaskLogBo bo = newInstance();
        bo.setJobId(jobInfo.getId());
        return bo;
    }

    /**
     * 
     * @param jobMgs
     * @return
     * @since 0.1.3.11
     */
    public static TaskLogBo newInstance(BaseJobMessage jobMgs) {
        TaskLogBo bo = newInstance(jobMgs.jobInfo);
        bo.setId(jobMgs.id);
        return bo;
    }

    /*----------------------------------------------------------------------*/

    private final static String ATTR_ID = "id";
    private final static String ATTR_JOB_ID = "jid";
    private final static String ATTR_STATUS = "status";
    private final static String ATTR_MESSAGE = "msg";
    private final static String ATTR_ERROR = "error";
    private final static String ATTR_OUTPUT = "output";
    private final static String ATTR_TIMESTAMP_CREATE = "tcreate";
    private final static String ATTR_NODE_CREATE = "ncreate";
    private final static String ATTR_TIMESTAMP_PICKUP = "tpickup";
    private final static String ATTR_DURATION_PICKUP = "dpickup";
    private final static String ATTR_NODE_PICKUP = "npickup";
    private final static String ATTR_TIMESTAMP_FINISH = "tfinish";
    private final static String ATTR_DURATION_FINISH = "dfinish";

    @JsonIgnore
    public String getId() {
        return getAttribute(ATTR_ID, String.class);
    }

    public TaskLogBo setId(String id) {
        return (TaskLogBo) setAttribute(ATTR_ID, id != null ? id.trim().toLowerCase() : null);
    }

    @JsonIgnore
    public String getJobId() {
        return getAttribute(ATTR_JOB_ID, String.class);
    }

    public TaskLogBo setJobId(String jobId) {
        return (TaskLogBo) setAttribute(ATTR_JOB_ID,
                jobId != null ? jobId.trim().toLowerCase() : null);
    }

    @JsonIgnore
    public int getStatus() {
        Integer value = getAttribute(ATTR_STATUS, Integer.class);
        return value != null ? value.intValue() : DjsConstants.TASK_STATUS_DEFAULT;
    }

    public TaskLogBo setStatus(int status) {
        return (TaskLogBo) setAttribute(ATTR_STATUS, status);
    }

    @JsonIgnore
    public String getMessage() {
        return getAttribute(ATTR_MESSAGE, String.class);
    }

    public TaskLogBo setMessage(String message) {
        return (TaskLogBo) setAttribute(ATTR_MESSAGE, message != null ? message.trim() : null);
    }

    @JsonIgnore
    public String getError() {
        return getAttribute(ATTR_ERROR, String.class);
    }

    public TaskLogBo setError(String error) {
        return (TaskLogBo) setAttribute(ATTR_ERROR, error != null ? error.trim() : null);
    }

    @JsonIgnore
    public byte[] getOutput() {
        return getAttribute(ATTR_OUTPUT, byte[].class);
    }

    public String getOutputAsString() {
        byte[] output = getOutput();
        return output != null ? new String(output, DjsConstants.UTF8) : "";
    }

    public TaskLogBo setOutput(byte[] output) {
        return (TaskLogBo) setAttribute(ATTR_OUTPUT,
                output != null ? output : ArrayUtils.EMPTY_BYTE_ARRAY);
    }

    public TaskLogBo setOutput(String output) {
        return setOutput(output != null ? output.trim().getBytes(DjsConstants.UTF8) : null);
    }

    @JsonIgnore
    public Date getTimestampCreate() {
        return getAttribute(ATTR_TIMESTAMP_CREATE, Date.class);
    }

    public TaskLogBo setTimestampCreate(Date timestamp) {
        return (TaskLogBo) setAttribute(ATTR_TIMESTAMP_CREATE,
                timestamp != null ? timestamp : new Date());
    }

    @JsonIgnore
    public String getNodeCreate() {
        return getAttribute(ATTR_NODE_CREATE, String.class);
    }

    public TaskLogBo setNodeCreate(String nodeName) {
        return (TaskLogBo) setAttribute(ATTR_NODE_CREATE, nodeName != null ? nodeName.trim() : "");
    }

    @JsonIgnore
    public Date getTimestampPickup() {
        return getAttribute(ATTR_TIMESTAMP_PICKUP, Date.class);
    }

    public TaskLogBo setTimestampPickup(Date timestamp) {
        setAttribute(ATTR_TIMESTAMP_PICKUP, timestamp);
        if (timestamp == null) {
            setAttribute(ATTR_DURATION_PICKUP, null);
        }
        Date timestampCreate = getTimestampCreate();
        long duration = timestampCreate != null ? timestamp.getTime() - timestampCreate.getTime()
                : 0;
        setAttribute(ATTR_DURATION_PICKUP, duration);
        return this;
    }

    @JsonIgnore
    public String getNodePickup() {
        return getAttribute(ATTR_NODE_PICKUP, String.class);
    }

    public TaskLogBo setNodePickup(String nodeName) {
        return (TaskLogBo) setAttribute(ATTR_NODE_PICKUP, nodeName != null ? nodeName.trim() : "");
    }

    @JsonIgnore
    public int getDurationPickup() {
        Integer duration = getAttribute(ATTR_DURATION_PICKUP, Integer.class);
        return duration != null ? duration.intValue() : -1;
    }

    @JsonIgnore
    public Date getTimestampFinish() {
        return getAttribute(ATTR_TIMESTAMP_FINISH, Date.class);
    }

    public TaskLogBo setTimestampFinish(Date timestamp) {
        setAttribute(ATTR_TIMESTAMP_FINISH, timestamp);
        if (timestamp == null) {
            setAttribute(ATTR_DURATION_FINISH, null);
        }
        Date timestampPickup = getTimestampPickup();
        long duration = timestampPickup != null ? timestamp.getTime() - timestampPickup.getTime()
                : 0;
        setAttribute(ATTR_DURATION_FINISH, duration);
        return this;
    }

    @JsonIgnore
    public int getDurationFinish() {
        Integer duration = getAttribute(ATTR_DURATION_FINISH, Integer.class);
        return duration != null ? duration.intValue() : -1;
    }

}
