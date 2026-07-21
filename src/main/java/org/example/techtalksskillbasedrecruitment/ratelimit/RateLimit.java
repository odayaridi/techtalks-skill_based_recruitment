package org.example.techtalksskillbasedrecruitment.ratelimit;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    int requests();

    int windowSeconds();

}