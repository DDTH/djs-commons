package com.github.ddth.djs.message;

import com.github.ddth.djs.bo.job.JobInfoBo;

/**
 * This message is sent when new job is created/added.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JobInfoAddedMessage extends BaseJobMessage {

	private static final long serialVersionUID = 1L;

	public JobInfoAddedMessage(JobInfoBo jobInfo) {
		super(jobInfo);
	}
}
