package com.github.ddth.djs.bo.log;

/**
 * DAO to interact with {@link TaskLogBo} storage.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public interface ITaskLogDao {
    /**
     * Persists a new taks log to storage.
     * 
     * @param taskLog
     * @return
     */
    public boolean create(TaskLogBo taskLog);

    /**
     * Deletes an existing task log from storage.
     * 
     * @param taskLog
     * @return
     */
    public boolean delete(TaskLogBo taskLog);

    /**
     * Updates an exiting task log.
     * 
     * @param taskLog
     * @return
     */
    public boolean update(TaskLogBo taskLog);

    /**
     * Fetches an existing task log from storage.
     * 
     * @param id
     * @return
     */
    public TaskLogBo getTaskLog(String id);

    /**
     * Gets list of latest task log ids.
     * 
     * @return
     * @since 0.1.3.12
     */
    public String[] getLatestTaskLogIds();

    /**
     * Cleanup old latest task logs.
     * 
     * @since 0.1.3.12
     */
    public void cleanupLatestTaskLogs();
}
