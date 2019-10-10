package org.frontear.framework.extensions.java.lang.String;

import lombok.NonNull;
import lombok.val;
import manifold.ext.api.Extension;
import manifold.ext.api.This;

@Extension
public class ExtensionString {
    public static String replaceLast(@This String thiz, @NonNull String s, @NonNull String s1) {
        val lastIndexOf = thiz.lastIndexOf(s);
        if (lastIndexOf > -1) { // found last instance of 'lookup'
            return thiz.substring(0, lastIndexOf) + s1 + thiz.substring(lastIndexOf + s.length());
        }
        else {
            return thiz;
        }
    }
}