package com.github.ddth.djs.utils;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.github.ddth.commons.utils.IdGenerator;
import com.github.ddth.djs.bo.log.TaskLogBo;

public class DjsUtils {
	public final static IdGenerator IDGEN = IdGenerator.getInstance(IdGenerator.getMacAddr());

	public static Date extractTimestamp(String id128Hex) {
		return new Date(IdGenerator.extractTimestamp128(id128Hex));
	}

	public static Date extractTimestamp(TaskLogBo taskLog) {
		return extractTimestamp(taskLog.getId());
	}

    public static byte[] base64Decode(String encodedStr) {
        return encodedStr != null ? Base64.decodeBase64(encodedStr) : null;
    }

    public static String base64Encode(byte[] data) {
        return data != null ? Base64.encodeBase64String(data) : null;
    }
}
