package com.github.ddth.djs.message.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ddth.djs.message.BaseJobMessage;
import com.github.ddth.djs.utils.DjsConstants;

/**
 * This message is put to queue when a job task is finished.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3
 */
public class TaskFinishMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    public final long finishTimestamp = System.currentTimeMillis();
    public final long status;
    public final String message, error;
    public final byte[] output;

    public TaskFinishMessage() {
        super(null);
        status = DjsConstants.TASK_STATUS_FINISHED_OK;
        message = null;
        error = null;
        output = null;
    }

    public TaskFinishMessage(TaskFireoffMessage taskFireoffMessage, int status, String message,
            String error, byte[] output) {
        super(taskFireoffMessage.id, taskFireoffMessage.jobInfo);
        this.status = status;
        this.message = message;
        this.error = error;
        this.output = output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(19, 81);
        hcb.append(finishTimestamp).append(status).append(message).append(error).append(output)
                .appendSuper(super.hashCode());
        return hcb.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TaskFinishMessage) {
            TaskFinishMessage another = (TaskFinishMessage) obj;
            EqualsBuilder eq = new EqualsBuilder();
            eq.appendSuper(super.equals(obj)).append(finishTimestamp, another.finishTimestamp)
                    .append(status, another.status).append(message, another.message)
                    .append(error, another.error).append(output, another.output);
        }
        return false;
    }

    protected final static String ATTR_FINISH_TIMESTAMP = "finish_timestamp";
    protected final static String ATTR_STATUS = "status";
    protected final static String ATTR_MESSAGE = "message";
    protected final static String ATTR_ERROR = "error";
    protected final static String ATTR_OUTPUT = "output";

    @JsonIgnore
    private Lock LOCK = new ReentrantLock();

    @JsonIgnore
    private Map<String, Object> dataAsMap = null;

    /**
     * Serializes mesage's attributes as a {@link Map}.
     * 
     * @return
     */
    public Map<String, Object> toMap() {
        if (dataAsMap == null) {
            LOCK.lock();
            if (dataAsMap == null) {
                try {
                    dataAsMap = new HashMap<>();
                    dataAsMap.putAll(super.toMap());
                    dataAsMap.put(ATTR_FINISH_TIMESTAMP, finishTimestamp);
                    dataAsMap.put(ATTR_STATUS, status);
                    dataAsMap.put(ATTR_MESSAGE, message);
                    dataAsMap.put(ATTR_ERROR, error);
                    dataAsMap.put(ATTR_OUTPUT, output);
                } finally {
                    LOCK.unlock();
                }
            }
        }
        return dataAsMap;
    }
}
