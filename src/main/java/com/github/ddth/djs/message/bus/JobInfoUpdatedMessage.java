package com.github.ddth.djs.message.bus;

import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;
import com.github.ddth.djs.message.BaseMessage;

/**
 * This message is sent when new job is updated/changed.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JobInfoUpdatedMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@link JobInfoUpdatedMessage} object (used for
     * deserialization).
     * 
     * @since 0.1.3
     */
    public JobInfoUpdatedMessage() {
        super(null);
    }

    /**
     * Constructs a new {@link JobInfoUpdatedMessage} object.
     * 
     * @param jobInfo
     */
    public JobInfoUpdatedMessage(JobInfoBo jobInfo) {
        super(jobInfo);
    }

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.3
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof JobInfoUpdatedMessage && super.equals(obj);
    }

    /*----------------------------------------------------------------------*/
    public static void main(String[] args) {
        JobInfoBo jobInfo = JobInfoBo.newInstance();
        jobInfo.setCron("* * * * *");

        JobInfoUpdatedMessage orgMsg = new JobInfoUpdatedMessage(jobInfo);
        System.out.println(orgMsg);
        {
            String dataJson = orgMsg.toJson();
            System.out.println(dataJson.getBytes().length + "\t" + dataJson);
            JobInfoUpdatedMessage msg = deserialize(dataJson, JobInfoUpdatedMessage.class);
            System.out.println(msg);
        }
        {
            byte[] bytes = orgMsg.toBytes();
            System.out.println(bytes.length + "\t" + bytes);
            JobInfoUpdatedMessage msg1 = deserialize(bytes, JobInfoUpdatedMessage.class);
            System.out.println(msg1);
            BaseMessage msg2 = BaseMessage.deserialize(bytes);
            System.out.println(msg2);
        }
    }
}
