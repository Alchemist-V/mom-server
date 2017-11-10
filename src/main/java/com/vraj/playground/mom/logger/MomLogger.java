/**
 * 
 */
package com.vraj.playground.mom.logger;

import java.util.Date;

import org.springframework.util.CollectionUtils;

/**
 * Logger util for mom server.
 * 
 * @author vrajori
 *
 */
public class MomLogger {

	private final static String TAB = "   ";
	private String className;

	public MomLogger(Class<?> clz) {
		this.className = clz.getCanonicalName();
	}

	public String getClassName() {
		return className;
	}

	public void log(String tag, Object... objects) {
		String logStr = generateLogEntry(tag, objects);
		// logging it on console for the time being.
		// this can be delegated to a file logger.
		System.out.println(logStr);
	}

	private String generateLogEntry(String tag, Object[] objects) {
		StringBuilder sb = new StringBuilder();
		sb.append((new Date()).toString()).append(TAB);
		sb.append(this.getClassName()).append(TAB);
		sb.append(tag).append(TAB);
		for (Object obj : objects) {
			if (obj != null) {
				sb.append(obj.toString()).append(TAB);
			}
		}
		return sb.toString();
	}
}
