package org.frontear.framework.extensions.java.lang.Object;

import manifold.ext.api.Extension;
import manifold.ext.api.This;

@Extension
public final class ExtensionObject {
    public static String getSimpleName(@This final Object thiz) {
        return thiz.getClass().getSimpleName();
    }
}
