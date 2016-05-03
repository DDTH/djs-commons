package com.github.ddth.djs.message.bus;

import com.github.ddth.djs.bo.job.JobInfoBo;
import com.github.ddth.djs.message.BaseJobMessage;
import com.github.ddth.djs.message.BaseMessage;

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

    /*----------------------------------------------------------------------*/
    public static void main(String[] args) {
        JobInfoBo jobInfo = JobInfoBo.newInstance();
        jobInfo.setCron("* * * * *");

        JobInfoRemovedMessage orgMsg = new JobInfoRemovedMessage(jobInfo);
        System.out.println(orgMsg);
        {
            String dataJson = orgMsg.toJson();
            System.out.println(dataJson.getBytes().length + "\t" + dataJson);
            JobInfoRemovedMessage msg = deserialize(dataJson, JobInfoRemovedMessage.class);
            System.out.println(msg);
        }
        {
            byte[] bytes = orgMsg.toBytes();
            System.out.println(bytes.length + "\t" + bytes);
            JobInfoRemovedMessage msg1 = deserialize(bytes, JobInfoRemovedMessage.class);
            System.out.println(msg1);
            BaseMessage msg2 = BaseMessage.deserialize(bytes);
            System.out.println(msg2);
        }
    }
}
