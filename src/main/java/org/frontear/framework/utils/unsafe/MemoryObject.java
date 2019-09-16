package org.frontear.framework.utils.unsafe;

import sun.misc.Unsafe;

public abstract class MemoryObject<T> {
	protected final long address;
	protected final Unsafe unsafe;

	public MemoryObject(long address, Unsafe unsafe) {
		this.address = address;
		this.unsafe = unsafe;
	}

	public abstract T get();

	public abstract void set(final T value);
}
