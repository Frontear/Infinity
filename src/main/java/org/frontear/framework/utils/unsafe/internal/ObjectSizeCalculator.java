package org.frontear.framework.utils.unsafe.internal;

import sun.misc.Unsafe;

public class ObjectSizeCalculator {
	/**
	 * This works because {@link Unsafe#arrayIndexScale(Class)} returns the 'scale factor' for the array type.
	 * In our case, the 'scale factor' will always be the size of the object,
	 * since the objects are accessed (natively) as ptr + sizeof(type) * index, where sizeof(type) is scalefactor
	 *
	 * @param primitive The primitive class
	 *
	 * @return The primitive size
	 */
	public static long sizeof(final Class<?> primitive) {
		if (primitive == boolean.class || primitive == Boolean.class) {
			return sizeof(byte.class);
		}
		else if (primitive == byte.class || primitive == Byte.class) {
			return Unsafe.ARRAY_BYTE_INDEX_SCALE;
		}
		else if (primitive == char.class || primitive == Character.class) {
			return Unsafe.ARRAY_CHAR_INDEX_SCALE;
		}
		else if (primitive == short.class || primitive == Short.class) {
			return Unsafe.ARRAY_SHORT_INDEX_SCALE;
		}
		else if (primitive == int.class || primitive == Integer.class) {
			return Unsafe.ARRAY_INT_INDEX_SCALE;
		}
		else if (primitive == float.class || primitive == Float.class) {
			return Unsafe.ARRAY_FLOAT_INDEX_SCALE;
		}
		else if (primitive == long.class || primitive == Long.class) {
			return Unsafe.ARRAY_LONG_INDEX_SCALE;
		}
		else if (primitive == double.class || primitive == Double.class) {
			return Unsafe.ARRAY_DOUBLE_INDEX_SCALE;
		}
		else {
			throw new UnsupportedOperationException("ObjectSizeCalculator is not meant to be used on non-primitive classes");
		}
	}
}
