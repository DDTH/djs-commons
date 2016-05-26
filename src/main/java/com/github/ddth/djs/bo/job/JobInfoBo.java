package com.github.ddth.djs.bo.job;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ddth.commons.utils.DPathUtils;
import com.github.ddth.commons.utils.SerializationUtils;
import com.github.ddth.dao.BaseBo;
import com.github.ddth.djs.utils.CronFormat;

/**
 * Job description info.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class JobInfoBo extends BaseBo {

    public final static JobInfoBo[] EMPTY_ARRAY = new JobInfoBo[0];

    public static JobInfoBo newInstance() {
        JobInfoBo bo = new JobInfoBo();
        bo.setUpdateTimestamp(new Date());
        return bo;
    }

    public static JobInfoBo newInstance(JobInfoBo clone) {
        JobInfoBo bo = new JobInfoBo();
        bo.fromJson(clone.toJson());
        return bo;
    }

    /*----------------------------------------------------------------------*/

    private final static String ATTR_ID = "id";
    private final static String ATTR_DESC = "desc";
    private final static String ATTR_TEMPLATE_ID = "tmpl_id";
    private final static String ATTR_IS_RUNNING = "running";
    private final static String ATTR_UPDATE_TIMESTAMP = "timestamp";
    private final static String ATTR_TAGS = "tags";
    private final static String ATTR_METADATA = "metadata";

    @JsonIgnore
    public String getId() {
        return getAttribute(ATTR_ID, String.class);
    }

    public JobInfoBo setId(String id) {
        return (JobInfoBo) setAttribute(ATTR_ID, id != null ? id.trim().toLowerCase() : null);
    }

    @JsonIgnore
    public String getDescription() {
        return getAttribute(ATTR_DESC, String.class);
    }

    public JobInfoBo setDescription(String desc) {
        return (JobInfoBo) setAttribute(ATTR_DESC, desc != null ? desc.trim() : "");
    }

    @JsonIgnore
    public String getTemplateId() {
        return getAttribute(ATTR_TEMPLATE_ID, String.class);
    }

    public JobInfoBo setTemplateId(String templateId) {
        return (JobInfoBo) setAttribute(ATTR_TEMPLATE_ID,
                templateId != null ? templateId.trim() : null);
    }

    @JsonIgnore
    public int getIsRunning() {
        Integer value = getAttribute(ATTR_IS_RUNNING, Integer.class);
        return value != null ? value.intValue() : 0;
    }

    @JsonIgnore
    public boolean isRunning() {
        return getIsRunning() != 0;
    }

    public JobInfoBo setIsRunning(int value) {
        return (JobInfoBo) setAttribute(ATTR_IS_RUNNING, value != 0 ? 1 : 0);
    }

    public JobInfoBo setIsRunning(boolean value) {
        return (JobInfoBo) setAttribute(ATTR_IS_RUNNING, value ? 1 : 0);
    }

    @JsonIgnore
    public Date getUpdateTimestamp() {
        return getAttribute(ATTR_UPDATE_TIMESTAMP, Date.class);
    }

    public JobInfoBo setUpdateTimestamp(Date timestamp) {
        return (JobInfoBo) setAttribute(ATTR_UPDATE_TIMESTAMP,
                timestamp != null ? timestamp : new Date());
    }

    /*----------------------------------------------------------------------*/
    @JsonIgnore
    private Set<String> tagsAsList = null;

    @JsonIgnore
    private Lock lockTags = new ReentrantLock();

    @JsonIgnore
    public String getTags() {
        return getAttribute(ATTR_TAGS, String.class);
    }

    public JobInfoBo setTags(String tags) {
        lockTags.lock();
        setAttribute(ATTR_TAGS, tags != null ? tags : "[]");
        try {
            tagsAsList = null;
        } finally {
            lockTags.unlock();
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    private Set<String> tagsAsList() {
        if (tagsAsList == null) {
            lockTags.lock();
            try {
                if (tagsAsList == null) {
                    try {
                        tagsAsList = (tagsAsList = SerializationUtils.fromJsonString(getTags(),
                                Set.class)) != null ? tagsAsList : new HashSet<>();
                    } catch (Exception e) {
                        tagsAsList = new HashSet<>();
                    }
                }
            } finally {
                lockTags.unlock();
            }
        }
        return tagsAsList;
    }

    private Set<String> tagsAsListCopy(boolean mutable) {
        lockTags.lock();
        try {
            Set<String> tags = tagsAsList();
            return mutable ? new HashSet<>(tags) : Collections.unmodifiableSet(tags);
        } finally {
            lockTags.unlock();
        }
    }

    /**
     * Gets all tags as an array of {@code String}s.
     * 
     * @return
     */
    @JsonIgnore
    public String[] getTagsAsArray() {
        lockTags.lock();
        try {
            Set<String> tags = tagsAsList();
            return tags.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        } finally {
            lockTags.unlock();
        }
    }

    /**
     * Checks if a tag exists.
     * 
     * @param tag
     * @return
     */
    @JsonIgnore
    public boolean hasTag(String tag) {
        return tagsAsListCopy(false).contains(tag);
    }

    public void addTags(String... tags) {
        Set<String> tagList = tagsAsListCopy(true);
        for (String tag : tags) {
            tagList.add(tag);
        }
        setTags(SerializationUtils.toJsonString(tagList));
    }

    @JsonIgnore
    private Map<String, Object> metadataAsMap = null;

    @JsonIgnore
    private Lock lockMetadata = new ReentrantLock();

    @JsonIgnore
    public String getMetadata() {
        return getAttribute(ATTR_METADATA, String.class);
    }

    public JobInfoBo setMetadata(String metadata) {
        lockMetadata.lock();
        setAttribute(ATTR_METADATA, metadata != null ? metadata : "{}");
        try {
            metadataAsMap = null;
            metadataAsMapCopy = null;
        } finally {
            lockMetadata.unlock();
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    private Map<String, Object> getMetadataAsMap() {
        if (metadataAsMap == null) {
            lockMetadata.lock();
            try {
                if (metadataAsMap == null) {
                    try {
                        metadataAsMap = (metadataAsMap = SerializationUtils
                                .fromJsonString(getMetadata(), Map.class)) != null ? metadataAsMap
                                        : new HashMap<>();
                    } catch (Exception e) {
                        metadataAsMap = new HashMap<>();
                    }
                }
            } finally {
                lockMetadata.unlock();
            }
        }
        return metadataAsMap;
    }

    @JsonIgnore
    private Map<String, Object> metadataAsMapCopy = null;

    private Map<String, Object> metadataAsMapCopy(boolean fork) {
        if (metadataAsMapCopy == null) {
            lockTags.lock();
            try {
                if (metadataAsMapCopy == null) {
                    metadataAsMapCopy = new HashMap<>(getMetadataAsMap());
                }
            } finally {
                lockTags.unlock();
            }
        }
        return fork ? new HashMap<>(metadataAsMapCopy) : metadataAsMapCopy;
    }

    @JsonIgnore
    public <T> T getMetadataField(String field, Class<T> type) {
        Map<String, Object> dataMap = metadataAsMapCopy(false);
        return DPathUtils.getValue(dataMap, field, type);
    }

    public JobInfoBo setMetadataField(String field, Object value) {
        Map<String, Object> dataMap = metadataAsMapCopy(true);
        DPathUtils.setValue(dataMap, field, value);
        return setMetadata(SerializationUtils.toJsonString(dataMap));
    }

    public final static String METADATA_FIELD_CRON = "cron";

    /**
     * Gets {@link CronFormat} instance.
     * 
     * @return
     */
    @JsonIgnore
    public CronFormat getCronFormat() {
        return CronFormat.parse(getCron());
    }

    /**
     * Gets cron format settings.
     * 
     * @return
     * @see {@link CronFormat}
     */
    @JsonIgnore
    public String getCron() {
        return getMetadataField(METADATA_FIELD_CRON, String.class);
    }

    /**
     * Sets cron format settings.
     * 
     * @param cronFormat
     * @return
     * @see {@link CronFormat}
     */
    public JobInfoBo setCron(String cronFormat) {
        return setMetadataField(METADATA_FIELD_CRON, cronFormat);
    }

    public final static String METADATA_FIELD_PARAMS = "params";

    /**
     * Gets job's parameters.
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    @JsonIgnore
    public Map<String, Object> getParams() {
        return getMetadataField(METADATA_FIELD_PARAMS, Map.class);
    }

    /**
     * Sets job's parameters.
     * 
     * @param jobParams
     * @return
     */
    public JobInfoBo setParams(Map<String, Object> jobParams) {
        return setMetadataField(METADATA_FIELD_PARAMS, jobParams);
    }

    /*----------------------------------------------------------------------*/
    @Override
    protected void triggerPopulate() {
        tagsAsList = null;
        metadataAsMap = null;
        metadataAsMapCopy = null;
        super.triggerPopulate();
    }
}
