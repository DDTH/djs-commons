package com.github.ddth.djs.message.bus;

import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;

/**
 * This message is sent when a job is started.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3.6
 */
public class JobInfoStartedMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@link JobInfoStartedMessage} object (used for
     * deserialization).
     */
    public JobInfoStartedMessage() {
        super(null);
    }

    /**
     * Constructs a new {@link JobInfoStartedMessage} object.
     * 
     * @param jobInfo
     */
    public JobInfoStartedMessage(JobInfoBo jobInfo) {
        super(jobInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof JobInfoStartedMessage && super.equals(obj);
    }
}
