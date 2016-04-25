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
	public Date getUpdateTimestamp() {
		return getAttribute(ATTR_UPDATE_TIMESTAMP, Date.class);
	}

	public JobInfoBo setUpdateTimestamp(Date timestamp) {
		return (JobInfoBo) setAttribute(ATTR_UPDATE_TIMESTAMP, timestamp != null ? timestamp : new Date());
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
		setAttribute(ATTR_UPDATE_TIMESTAMP, tags != null ? tags : "[]");
		lockTags.lock();
		try {
			tagsAsList = null;
		} finally {
			lockTags.unlock();
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	private Set<String> tagsAsList() {
		lockTags.lock();
		try {
			if (tagsAsList == null) {
				try {
					tagsAsList = SerializationUtils.fromJsonString(getTags(), Set.class);
				} catch (Exception e) {
					tagsAsList = new HashSet<>();
				}
			}
			return tagsAsList;
		} finally {
			lockTags.unlock();
		}
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
		lockTags.lock();
		try {
			Set<String> tagList = tagsAsListCopy(true);
			for (String tag : tags) {
				tagList.add(tag);
			}
			setMetadata(SerializationUtils.toJsonString(tagList));
		} finally {
			lockTags.unlock();
		}
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
		setAttribute(ATTR_METADATA, metadata != null ? metadata : "{}");
		lockMetadata.lock();
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
		lockMetadata.lock();
		try {
			if (metadataAsMap == null) {
				try {
					metadataAsMap = SerializationUtils.fromJsonString(getMetadata(), Map.class);
				} catch (Exception e) {
					metadataAsMap = new HashMap<>();
				}
			}
			return metadataAsMap;
		} finally {
			lockMetadata.unlock();
		}
	}

	private Map<String, Object> metadataAsMapCopy = null;

	private Map<String, Object> metadataAsMapCopy(boolean fork) {
		if (metadataAsMapCopy == null) {
			lockTags.lock();
			try {
				metadataAsMapCopy = new HashMap<>(getMetadataAsMap());
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
	public CronFormat getCronFormat() {
		return CronFormat.parse(getCron());
	}

	/**
	 * Gets cron format settings.
	 * 
	 * @return
	 * @see {@link CronFormat}
	 */
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

	/*----------------------------------------------------------------------*/
	@Override
	protected void triggerPopulate() {
		tagsAsList = null;
		metadataAsMap = null;
		metadataAsMapCopy = null;
		super.triggerPopulate();
	}
}
