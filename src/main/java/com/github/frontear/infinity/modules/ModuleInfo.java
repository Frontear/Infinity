package com.github.frontear.infinity.modules;

import com.github.frontear.infinity.utils.key.Keyboard;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    Keyboard bind(); // the module binding

    boolean friendly(); // whether the module can be used during Ghost

    ModuleCategory category(); // the module category
}
