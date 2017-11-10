/**
 * 
 */
package com.vraj.playground.mom.logger;

/**
 * Factory to spill logger instances.
 * 
 * @author vrajori
 *
 */
public class MomServerLoggerFactory {

	public static MomLogger getLogger(Class<?> clz) {
		return new MomLogger(clz);
	}
}
