package com.github.ddth.djs.message;

/**
 * This message is sent per "tick".
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class TickMessage extends BaseMessage {
	private static final long serialVersionUID = 1L;

	public long timestampMillis = System.currentTimeMillis();
}
