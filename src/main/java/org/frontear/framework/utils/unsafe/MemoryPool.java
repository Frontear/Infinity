package org.frontear.framework.utils.unsafe;

import lombok.val;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.utils.unsafe.impl.*;
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

	public BooleanMemoryObject push(boolean value) {
		val object = new BooleanMemoryObject(addressof(boolean.class), unsafe);
		object.set(value);

		return object;
	}

	private long addressof(Class<?> object) {
		val offset = ObjectSizeCalculator.sizeof(object);
		this.address = unsafe.reallocateMemory(address, size += offset);
		logger.debug("Pushing %s to 0x%x", object.getSimpleName(), address);

		return address + offset;
	}

	public ByteMemoryObject push(byte value) {
		val object = new ByteMemoryObject(addressof(byte.class), unsafe);
		object.set(value);

		return object;
	}

	public CharacterMemoryObject push(char value) {
		val object = new CharacterMemoryObject(addressof(char.class), unsafe);
		object.set(value);

		return object;
	}

	public DoubleMemoryObject push(double value) {
		val object = new DoubleMemoryObject(addressof(double.class), unsafe);
		object.set(value);

		return object;
	}

	public FloatMemoryObject push(float value) {
		val object = new FloatMemoryObject(addressof(float.class), unsafe);
		object.set(value);

		return object;
	}

	public IntegerMemoryObject push(int value) {
		val object = new IntegerMemoryObject(addressof(int.class), unsafe);
		object.set(value);

		return object;
	}

	public LongMemoryObject push(long value) {
		val object = new LongMemoryObject(addressof(long.class), unsafe);
		object.set(value);

		return object;
	}

	public ShortMemoryObject push(short value) {
		val object = new ShortMemoryObject(addressof(short.class), unsafe);
		object.set(value);

		return object;
	}

	@Override protected void finalize() {
		if (address != 0) {
			logger.warn("The pool pointing to address 0x%x was not closed properly.", address);
			this.close();
		}
	}

	@Override public void close() {
		if (address != 0) {
			logger.debug("Freeing 0x%x", address);
			unsafe.freeMemory(address);
			address = 0L;
		}
		else {
			logger.warn("Attempting to free pool which has already been freed!");
		}
	}
}
