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

    public final static int RESPONSE_OK = 200;
    public final static int RESPONSE_NOT_FOUND = 404;
    public final static int RESPONSE_ACCESS_DENIED = 403;
    public final static int RESPONSE_CLIENT_ERROR = 400;
    public final static int RESPONSE_SERVER_ERROR = 500;

    public final static String API_PARAM_CLIENT_ID = "client_id";
    public final static String API_PARAM_TASK = "task";
    public final static String API_RESPONSE_STATUS = "status";
    public final static String API_RESPONSE_MESSAGE = "message";
    public final static String API_RESPONSE_DATA = "data";

}
