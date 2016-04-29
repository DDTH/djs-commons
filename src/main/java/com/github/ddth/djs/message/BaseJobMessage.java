package com.github.ddth.djs.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

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

	/**
	 * {@inheritDoc}
	 * 
	 * @since 0.1.2
	 */
	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.appendSuper(super.toString()).append("jobInfo", jobInfo);
		return tsb.toString();
	}
}
