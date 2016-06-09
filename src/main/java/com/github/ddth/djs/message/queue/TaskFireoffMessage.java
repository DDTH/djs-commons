package com.github.ddth.djs.message.queue;

import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;

/**
 * This message is put to queue when a new job task is fired-off.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3
 */
public class TaskFireoffMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    public TaskFireoffMessage() {
        super(null);
    }

    public TaskFireoffMessage(JobInfoBo jobInfo) {
        super(jobInfo);
    }

    public TaskFireoffMessage(String id, JobInfoBo jobInfo) {
        super(id, jobInfo);
    }

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.3
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof TaskFireoffMessage && super.equals(obj);
    }
}
