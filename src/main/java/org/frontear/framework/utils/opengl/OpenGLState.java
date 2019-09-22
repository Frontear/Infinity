//#define IMPLEMENT

#if IMPLEMENT
package org.frontear.framework.utils.opengl;

import lombok.experimental.UtilityClass;
import org.frontear.framework.logger.impl.Logger;

/**
 * A state wrapper for OpenGL calls. Makes use of {@link org.lwjgl.opengl.GL11}.
 */
@UtilityClass public class OpenGLState {
	private final Logger logger = new Logger();
}
#endif