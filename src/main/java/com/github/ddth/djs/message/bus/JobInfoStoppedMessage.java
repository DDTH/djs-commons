package com.github.ddth.djs.message.bus;

import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;

/**
 * This message is sent when a job is stopped.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3.6
 */
public class JobInfoStoppedMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@link JobInfoStoppedMessage} object (used for
     * deserialization).
     */
    public JobInfoStoppedMessage() {
        super(null);
    }

    /**
     * Constructs a new {@link JobInfoStoppedMessage} object.
     * 
     * @param jobInfo
     */
    public JobInfoStoppedMessage(JobInfoBo jobInfo) {
        super(jobInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof JobInfoStoppedMessage && super.equals(obj);
    }
}
