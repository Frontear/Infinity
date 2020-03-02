package com.github.frontear.infinity.modules;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    int bind();

    ModuleCategory category();
}
