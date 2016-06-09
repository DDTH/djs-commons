package com.github.ddth.djs.message;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ddth.commons.utils.SerializationUtils;
import com.github.ddth.djs.utils.DjsUtils;

/**
 * Base class for other message classes.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public abstract class BaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Deserializes from a JSON string. See {@link #toJson()}.
     *
     * @param dataJson
     * @return
     * @since 0.1.3
     */
    public static <T extends BaseMessage> T deserialize(String dataJson, Class<T> clazz) {
        return dataJson != null ? SerializationUtils.fromJsonString(dataJson, clazz) : null;
    }

    /**
     * Deserializes from a {@code byte[]}. See {@link #toBytes()}.
     *
     * @param dataBytes
     * @return
     * @since 0.1.3
     */
    public static BaseMessage deserialize(byte[] dataBytes) {
        return deserialize(dataBytes, BaseMessage.class);
    }

    /**
     * Deserializes from a {@code byte[]}. See {@link #toBytes()}.
     *
     * @param dataBytes
     * @return
     * @since 0.1.3
     */
    public static <T extends BaseMessage> T deserialize(byte[] dataBytes, Class<T> clazz) {
        try {
            return dataBytes != null ? SerializationUtils.fromByteArray(dataBytes, clazz) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*----------------------------------------------------------------------*/

    protected final static String ATTR_ID = "id";
    protected final static String ATTR_TIMESTAMP_MS = "timestampMillis";

    @JsonProperty
    public final String id;

    @JsonProperty
    public final long timestampMillis = System.currentTimeMillis();

    /**
     * Constructs a new {@link BaseMessage} instance.
     * 
     * @since 0.1.3.9
     */
    public BaseMessage() {
        id = DjsUtils.IDGEN.generateId128Hex();
    }

    /**
     * Constructs a new {@link BaseMessage} instance.
     * 
     * @param id
     * @since 0.1.3.9
     */
    public BaseMessage(String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     * 
     * @since 0.1.2
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(19, 81);
        hcb.append(id).append(timestampMillis);
        return hcb.hashCode();
    }

    /**
     * Equals only if both {@link #id} and {@link #timestampMillis} are equal.
     * 
     * @since 0.1.2
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BaseMessage) {
            BaseMessage another = (BaseMessage) obj;
            return StringUtils.equalsIgnoreCase(id, another.id)
                    && timestampMillis == another.timestampMillis;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @since 0.1.3
     */
    @Override
    public String toString() {
        ToStringBuilder tsb = new ToStringBuilder(this);
        tsb.append(ATTR_ID, this.id).append(ATTR_TIMESTAMP_MS, timestampMillis);
        return tsb.toString();
    }

    @JsonIgnore
    private Lock LOCK = new ReentrantLock();

    @JsonIgnore
    private Map<String, Object> dataAsMap = null;

    /**
     * Serializes mesage's attributes as a {@link Map}.
     * 
     * @return
     * @since 0.1.3
     */
    public Map<String, Object> toMap() {
        if (dataAsMap == null) {
            LOCK.lock();
            if (dataAsMap == null) {
                try {
                    dataAsMap = new HashMap<>();
                    dataAsMap.put(ATTR_ID, id);
                    dataAsMap.put(ATTR_TIMESTAMP_MS, timestampMillis);
                } finally {
                    LOCK.unlock();
                }
            }
        }
        return Collections.unmodifiableMap(dataAsMap);
    }

    @JsonIgnore
    private String dataAsJson = null;

    /**
     * Serializes message's attributes as a JSON string.
     * 
     * @return
     * @since 0.1.3
     */
    public String toJson() {
        if (dataAsJson == null) {
            LOCK.lock();
            if (dataAsJson == null) {
                try {
                    dataAsJson = SerializationUtils.toJsonString(this);
                } finally {
                    LOCK.unlock();
                }
            }
        }
        return dataAsJson;
    }

    @JsonIgnore
    private byte[] dataAsBytes = null;

    /**
     * Serializes message's attributes as a {@code byte[]}.
     * 
     * @return
     * @since 0.1.3
     */
    public byte[] toBytes() {
        if (dataAsBytes == null) {
            LOCK.lock();
            if (dataAsBytes == null) {
                try {
                    dataAsBytes = SerializationUtils.toByteArray(this);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    LOCK.unlock();
                }
            }
        }
        return Arrays.copyOf(dataAsBytes, dataAsBytes.length);
    }
}
