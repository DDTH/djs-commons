package com.github.ddth.djs.utils;

import java.nio.charset.Charset;

/**
 * Commonly used constants.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class DjsConstants {

	public final static Charset UTF8 = Charset.forName("UTF-8");

	public final static int TASK_STATUS_NEW = 0;
	public final static int TASK_STATUS_PICKED = 1;
	public final static int TASK_STATUS_SKIPPED = 2;
	public final static int TASK_STATUS_FINISHED_OK = 10;
	public final static int TASK_STATUS_FINISHED_CANCEL = 11;
	public final static int TASK_STATUS_FINISHED_ERROR = 12;
	public final static int TASK_STATUS_DEFAULT = TASK_STATUS_NEW;

}
