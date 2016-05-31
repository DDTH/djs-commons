package com.github.ddth.djs.message.bus;

import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;

/**
 * This message is sent when new job is removed.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JobInfoRemovedMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@link JobInfoRemovedMessage} object (used for
     * deserialization).
     * 
     * @since 0.1.3
     */
    public JobInfoRemovedMessage() {
        super(null);
    }

    /**
     * Constructs a new {@link JobInfoRemovedMessage} object.
     * 
     * @param jobInfo
     */
    public JobInfoRemovedMessage(JobInfoBo jobInfo) {
        super(jobInfo);
    }

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.3
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof JobInfoRemovedMessage && super.equals(obj);
    }
}
