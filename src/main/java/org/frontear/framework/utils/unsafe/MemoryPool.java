package org.frontear.framework.utils.unsafe;

import com.google.common.base.Preconditions;
import lombok.val;
import org.apache.commons.lang3.ClassUtils;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.utils.unsafe.internal.ObjectSizeCalculator;
import sun.misc.Unsafe;

public class MemoryPool implements AutoCloseable {
	private static final Logger logger = new Logger();
	private static final Unsafe unsafe;

	static {
		val theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafe.setAccessible(true);
		unsafe = (Unsafe) theUnsafe.get(null);
	}

	private long address = 0L; // the address of the memory
	private long size = 0L; // global size, used when calling realloc

	@SuppressWarnings("unchecked") public <T> T push(T value) {
		val clazz = value.getClass();
		Preconditions.checkArgument(ClassUtils
				.isPrimitiveWrapper(clazz)); // a primitive does not fix the specifications of 'Object', therefore we don't need to check if its a primitive class, because it can never be

		val address = addressof(clazz);

		if (value instanceof Boolean) {
			unsafe.putByte(address, (byte) ((Boolean) value ? 1 : 0));
			return (T) (Object) unsafe.getByte(address);
		}
		else if (value instanceof Byte) {
			unsafe.putByte(address, (Byte) value);
			return (T) (Object) unsafe.getByte(address);
		}
		else if (value instanceof Character) {
			unsafe.putChar(address, (Character) value);
			return (T) (Object) unsafe.getChar(address);
		}
		else if (value instanceof Short) {
			unsafe.putShort(address, (Short) value);
			return (T) (Object) unsafe.getShort(address);
		}
		else if (value instanceof Integer) {
			unsafe.putInt(address, (Integer) value);
			return (T) (Object) unsafe.getInt(address);
		}
		else if (value instanceof Long) {
			unsafe.putLong(address, (Long) value);
			return (T) (Object) unsafe.getLong(address);
		}
		else if (value instanceof Double) {
			unsafe.putDouble(address, (Double) value);
			return (T) (Object) unsafe.getDouble(address);
		}
		else if (value instanceof Float) {
			unsafe.putFloat(address, (Float) value);
			return (T) (Object) unsafe.getFloat(address);
		}

		throw logger.fatal(new UnsupportedOperationException(), "This should never happen");
	}

	private long addressof(Class<?> object) {
		val offset = ObjectSizeCalculator.sizeof(object);
		this.address = unsafe.reallocateMemory(address, size += offset);
		logger.debug("Pushing %s to 0x%x", object.getSimpleName(), address);

		return address + offset;
	}

	@Override public void close() {
		if (address != 0) {
			logger.debug("Freeing 0x%x", address);
			unsafe.freeMemory(address);

			address = 0L;
			size = 0L;
		}
		else {
			logger.warn("Attempting to free pool which has already been freed!");
		}
	}

	@Override public String toString() {
		return "MemoryPool(address=0x${Long.toHexString(address)}, size=${size})";
	}
}
