package org.frontear.framework.utils.opengl;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.frontear.framework.logger.impl.Logger;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * A state wrapper for OpenGL calls. Makes use of {@link org.lwjgl.opengl.GL11}
 */
@UtilityClass public class OpenGLState {
	private final Logger logger = new Logger();
	private boolean matrix = false, attrib = false, drawing = false;

	public void pushMatrix() {
		if (!matrix) {
			glPushMatrix();
			matrix = true;
		}
		else {
			logger.error("Couldn't push glMatrix, already exists");
		}
	}

	public void popMatrix() {
		if (matrix) {
			glPopMatrix();
			matrix = false;
		}
		else {
			logger.error("Couldn't pop glMatrix, doesn't exist");
		}
	}

	public void pushAttrib(int mask) {
		if (!attrib) {
			glPushAttrib(mask);
			attrib = true;
		}
		else {
			logger.error("Couldn't push glAttrib, already exists");
		}
	}

	public void popAttrib() {
		if (attrib) {
			glPopAttrib();
			attrib = false;
		}
		else {
			logger.error("Couldn't pop glAttrib, doesn't exist");
		}
	}

	public void begin(int mode) {
		if (!drawing) {
			glBegin(mode);
			drawing = true;
		}
		else {
			logger.error("Failed to glBegin, already drawing");
		}
	}

	public void end() {
		if (drawing) {
			glEnd();
			drawing = false;
		}
		else {
			logger.error("Failed to glEnd, no drawing occuring");
		}
	}

	public void color(@NonNull Color color) {
		if (matrix) {
			glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
		}
		else {
			logger.error("Cannot glColor %s, no active matrix", color.toString());
		}
	}

	public void enable(int... caps) {
		if (matrix) {
			for (int cap : caps) {
				if (!glIsEnabled(cap)) {
					glEnable(cap);
				}
				else {
					logger.debug("cannot glEnable 0x%s, it's already enabled", String.format("%x", cap).toUpperCase());
				}
			}
		}
	}

	public void disable(int... caps) {
		if (matrix) {
			for (int cap : caps) {
				if (glIsEnabled(cap)) {
					glDisable(cap);
				}
				else {
					logger.debug("cannot glDisable 0x%s, as it's not enabled", String.format("%x", cap).toUpperCase());
				}
			}
		}
	}

	public void blendFunc(int sfactor, int dfactor) {
		if (matrix) {
			glBlendFunc(sfactor, dfactor);
		}
		else {
			logger.error("Cannot glBlendFunc(0x%x, 0x%x), no active matrix", sfactor, dfactor);
		}
	}
}
