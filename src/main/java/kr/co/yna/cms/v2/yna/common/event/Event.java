package kr.co.yna.cms.v2.yna.common.event;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Event {
    boolean rehydration() default true;
    boolean history() default false;
    String name() default "";
}
