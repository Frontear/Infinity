package org.frontear.framework.utils.opengl;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.var;
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

	/**
	 * Attempts to enable an OpenGL capability
	 *
	 * @param cap The targeted OpenGL capability
	 *
	 * @return Whether the targeted capability was just enabled, and was <b>not</b>> previously enabled
	 */
	public boolean enable(int cap) {
		var flag = false;

		if (matrix) {
			flag = glIsEnabled(cap);
			if (!flag) {
				glEnable(cap);
			}
		}

		return flag;
	}

	/**
	 * Attempts to disable an OpenGL capability
	 *
	 * @param cap The targeted OpenGL capability
	 *
	 * @return Whether the targeted capability was just disabled, and was <b>not</b>> previously disabled
	 */
	public boolean disable(int cap) {
		var flag = false;

		if (matrix) {
			flag = glIsEnabled(cap);
			if (flag) {
				glDisable(cap);
			}
		}

		return flag;
	}

	public void blendFunc(int sfactor, int dfactor) {
		if (matrix) {
			glBlendFunc(sfactor, dfactor);
		}
		else {
			logger.error("Cannot glBlendFunc(0x%x, 0x%x), no active matrix", sfactor, dfactor);
		}
	}

	public void vertex2(@NonNull Number x, @NonNull Number y) {
		if (matrix && drawing) {
			if (x instanceof Float) {
				glVertex2f(x.floatValue(), y.floatValue());
			}
			else if (x instanceof Double) {
				glVertex2d(x.doubleValue(), y.doubleValue());
			}
			else if (x instanceof Integer) {
				glVertex2i(x.intValue(), y.intValue());
			}
			else {
				logger.error("Failed to glVertex2(%s, %s), parameters not of type float, double, or int", x, y);
			}
		}
		else {
			logger.error("Cannot glVertex2(%s, %s), no active rendering/matrix set", x, y);
		}
	}
}
