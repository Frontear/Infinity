package com.github.frontear.infinity.commands;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    String desc(); // the command description

    int args() default 0; // accepted arguments (highest value)
}
