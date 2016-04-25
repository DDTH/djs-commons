package com.github.ddth.djs.message;

import com.github.ddth.djs.bo.job.JobInfoBo;

/**
 * This message is sent when new job is updated/changed.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JobInfoUpdatedMessage extends BaseJobMessage {

	private static final long serialVersionUID = 1L;

	public JobInfoUpdatedMessage(JobInfoBo jobInfo) {
		super(jobInfo);
	}
}
