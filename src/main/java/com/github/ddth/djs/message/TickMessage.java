package com.github.ddth.djs.message;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This message is sent per "tick".
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class TickMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;

	public long timestampMillis = System.currentTimeMillis();

	/**
	 * {@inheritDoc}
	 * 
	 * @since 0.1.2
	 */
	@Override
	public String toString() {
		ToStringBuilder tsb = new ToStringBuilder(this);
		tsb.append("id", this.id).append("timestamp", timestampMillis);
		return tsb.toString();
	}

}
