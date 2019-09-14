package org.frontear.framework.extensions.java.lang.Object;

import manifold.ext.api.Extension;
import manifold.ext.api.This;

@Extension public class ExtensionObject {
	public static String getClassName(@This Object thiz) {
		return thiz.getClassName();
	}
}
