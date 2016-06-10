package com.github.ddth.djs.api;

import com.github.ddth.djs.message.queue.TaskFinishMessage;
import com.github.ddth.djs.message.queue.TaskFireoffMessage;
import com.github.ddth.djs.message.queue.TaskPickupMessage;

/**
 * APIs to interact with DJS nodes.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3.7
 */
public interface IDjsApi {

    /**
     * Polls a job task from server.
     * 
     * @param clientId
     * @return
     */
    public TaskFireoffMessage pollTask(String clientId);

    /**
     * Returns a job task back to server, untouched.
     * 
     * @param clientId
     * @param taskFireoffMessage
     * @return
     */
    public boolean returnTask(String clientId, TaskFireoffMessage taskFireoffMessage);

    /**
     * Notifies that job task has been picked up by a worker.
     * 
     * @param clientId
     * @param taskPickupMessage
     * @return
     */
    public boolean notifyTaskPickup(String clientId, TaskPickupMessage taskPickupMessage);

    /**
     * Notifies that worker has finished a job task.
     * 
     * @param clientId
     * @param taskFinishMessage
     * @return
     */
    public boolean notifyTaskFinish(String clientId, TaskFinishMessage taskFinishMessage);
}
