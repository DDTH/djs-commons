package com.github.ddth.djs.message.bus;

import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;

/**
 * This message is sent when new job is created/added.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JobInfoAddedMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@link JobInfoAddedMessage} object (used for
     * deserialization).
     * 
     * @since 0.1.3
     */
    public JobInfoAddedMessage() {
        super(null);
    }

    /**
     * Constructs a new {@link JobInfoAddedMessage} object.
     * 
     * @param jobInfo
     */
    public JobInfoAddedMessage(JobInfoBo jobInfo) {
        super(jobInfo);
    }

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.3
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof JobInfoAddedMessage && super.equals(obj);
    }
}
