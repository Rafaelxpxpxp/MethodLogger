package logs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Log methods args and retuning by default
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLogger {

    /**
     * Put a message in the log if it  was a success
     */
    String msgOnSuccess() default "";

    /**
     * Put a message in the log if it throws an error
     */
    String msgOnException() default "";

    /**
     * Tell if it should log its args
     */
    boolean logAllArgs() default true;

    /**
     * Tell if it should log its return
     */
    boolean logReturn() default true;
}
