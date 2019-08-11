package org.frontear.framework.info;

/**
 * Contains all the important information found from the mcmod.info
 */
public interface IModInfo {
	/**
	 * @return The name of the {@link org.frontear.ForgeMod}
	 */
	String getName();

	/**
	 * @return The version of the {@link org.frontear.ForgeMod}
	 */
	String getVersion();

	/**
	 * This is just the {@link IModInfo#getName()} + {@link IModInfo#getVersion()}
	 * @return The fullname of the {@link org.frontear.ForgeMod}
	 */
	String getFullname();

	/**
	 * @return The authors of the {@link org.frontear.ForgeMod}
	 */
	String getAuthors();
}
