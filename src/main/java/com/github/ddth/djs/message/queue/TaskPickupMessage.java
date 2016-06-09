package com.github.ddth.djs.message.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ddth.djs.message.BaseJobMessage;

/**
 * This message is put to queue when a job task is picked-up.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.3
 */
public class TaskPickupMessage extends BaseJobMessage {

    private static final long serialVersionUID = 1L;

    public final long pickupTimestamp = System.currentTimeMillis();
    public final String pickupNode;

    public TaskPickupMessage() {
        super(null);
        pickupNode = null;
    }

    public TaskPickupMessage(TaskFireoffMessage taskFireoffMessage, String pickupNode) {
        super(taskFireoffMessage.id, taskFireoffMessage.jobInfo);
        this.pickupNode = pickupNode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(19, 81);
        hcb.append(pickupTimestamp).append(pickupNode).appendSuper(super.hashCode());
        return hcb.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TaskPickupMessage) {
            TaskPickupMessage another = (TaskPickupMessage) obj;
            return super.equals(obj) && pickupTimestamp == another.pickupTimestamp
                    && StringUtils.equals(pickupNode, another.pickupNode);
        }
        return false;
    }

    protected final static String ATTR_PICKUP_TIMESTAMP = "pickup_timestamp";
    protected final static String ATTR_PICKUP_NODE = "pickup_node";

    @JsonIgnore
    private Lock LOCK = new ReentrantLock();

    @JsonIgnore
    private Map<String, Object> dataAsMap = null;

    /**
     * Serializes mesage's attributes as a {@link Map}.
     * 
     * @return
     */
    public Map<String, Object> toMap() {
        if (dataAsMap == null) {
            LOCK.lock();
            if (dataAsMap == null) {
                try {
                    dataAsMap = new HashMap<>();
                    dataAsMap.putAll(super.toMap());
                    dataAsMap.put(ATTR_PICKUP_TIMESTAMP, pickupTimestamp);
                    dataAsMap.put(ATTR_PICKUP_NODE, pickupNode);
                } finally {
                    LOCK.unlock();
                }
            }
        }
        return dataAsMap;
    }
}
