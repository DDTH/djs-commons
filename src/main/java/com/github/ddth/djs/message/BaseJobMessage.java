package com.github.ddth.djs.message;

import com.github.ddth.djs.bo.job.JobInfoBo;

/**
 * Base class for other job-related messages.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public abstract class BaseJobMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;

	public JobInfoBo jobInfo;

	public BaseJobMessage(JobInfoBo jobInfo) {
		this.jobInfo = jobInfo;
	}
}
