package org.frontear.framework.extensions.java.lang.Object;

import manifold.ext.api.Extension;
import manifold.ext.api.This;

@Extension public class ExtensionObject {
	public static String getSimpleName(@This Object thiz) {
		return thiz.getClass().getSimpleName();
	}
}
