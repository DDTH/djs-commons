package com.github.ddth.djs.message;

import java.io.Serializable;

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
}
