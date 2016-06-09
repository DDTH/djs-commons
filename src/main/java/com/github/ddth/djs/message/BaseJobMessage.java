package com.github.ddth.djs.message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ddth.djs.bo.job.JobInfoBo;

/**
 * Base class for other job-related messages.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public abstract class BaseJobMessage extends BaseMessage {
    private static final long serialVersionUID = 1L;

    /*----------------------------------------------------------------------*/

    protected final static String ATTR_JOB_INFO = "job_info";

    @JsonProperty
    public final JobInfoBo jobInfo;

    /**
     * Constructs a new {@link BaseJobMessage} instance.
     * 
     * @param jobInfo
     * @since 0.1.3
     */
    public BaseJobMessage(JobInfoBo jobInfo) {
        this.jobInfo = jobInfo;
    }

    /**
     * Constructs a new {@link BaseJobMessage} instance.
     * 
     * @param id
     * @param jobInfo
     * @since 0.1.3.9
     */
    public BaseJobMessage(String id, JobInfoBo jobInfo) {
        super(id);
        this.jobInfo = jobInfo;
    }

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.2
     */
    @Override
    public String toString() {
        ToStringBuilder tsb = new ToStringBuilder(this);
        tsb.appendSuper(super.toString()).append(ATTR_JOB_INFO, jobInfo);
        return tsb.toString();
    }

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.3
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(19, 81);
        hcb.append(jobInfo).appendSuper(super.hashCode());
        return hcb.hashCode();
    }

    /**
     * @since 0.1.3
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BaseJobMessage) {
            BaseJobMessage another = (BaseJobMessage) obj;
            return super.equals(obj)
                    && ((jobInfo == null && another.jobInfo == null) || jobInfo != null
                            && another.jobInfo != null && jobInfo.equals(another.jobInfo));
        }
        return false;
    }

    @JsonIgnore
    private Lock LOCK = new ReentrantLock();

    @JsonIgnore
    private Map<String, Object> dataAsMap = null;

    /**
     * Serializes mesage's attributes as a {@link Map}.
     * 
     * @return
     * @since 0.1.3
     */
    public Map<String, Object> toMap() {
        if (dataAsMap == null) {
            LOCK.lock();
            if (dataAsMap == null) {
                try {
                    dataAsMap = new HashMap<>();
                    dataAsMap.putAll(super.toMap());
                    dataAsMap.put(ATTR_JOB_INFO, jobInfo);
                } finally {
                    LOCK.unlock();
                }
            }
        }
        return dataAsMap;
    }
}
