package com.github.ddth.djs.message;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.github.ddth.djs.utils.DjsUtils;

/**
 * Base class for other message classes.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public abstract class BaseMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	public String id = DjsUtils.IDGEN.generateId128Hex();

	/**
	 * {@inheritDoc}
	 * 
	 * @since 0.1.2
	 */
	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder(19, 81);
		hcb.append(id);
		return hcb.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 0.1.2
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BaseMessage) {
			BaseMessage another = (BaseMessage) obj;
			return StringUtils.equalsIgnoreCase(id, another.id);
		}
		return false;
	}
}
