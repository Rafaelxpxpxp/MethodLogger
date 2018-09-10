package logs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;


@Aspect
@Configuration
public class MethodLoggerAspect {

    @Before(value = "@annotation(methodLogger))")
    public void methodEntryLogger(JoinPoint joinPoint, MethodLogger methodLogger){
        StringBuilder logBuilder = new StringBuilder();
        logMethod(joinPoint, logBuilder);
        logArgs(joinPoint, logBuilder, methodLogger);
        createLog(joinPoint, logBuilder);
    }


    @AfterReturning(value = "@annotation(methodLogger))", returning = "returningValue")
    public void methodReturnLogger(JoinPoint joinPoint, MethodLogger methodLogger, Object returningValue) {
        StringBuilder logBuilder = new StringBuilder();
        if (hasReturn(returningValue)) {
            logMethod(joinPoint, logBuilder);
            logReturn(returningValue, logBuilder, methodLogger);
            createLog(joinPoint, logBuilder);
        }
        logSuccessMessage(methodLogger, logBuilder);
    }

    @AfterThrowing(value = "@annotation(methodLogger))", throwing = "exception")
    public void methodExceptionLogger(JoinPoint joinPoint, MethodLogger methodLogger, Exception exception) {
        StringBuilder logBuilder = new StringBuilder();
        logMethod(joinPoint, logBuilder);
        logErrorMessage(methodLogger, logBuilder,exception);
        createErrorLog(joinPoint, logBuilder);
    }

    private boolean hasReturn(Object returningValue) {
        return returningValue != null;
    }



    private void logReturn(Object returningValue, StringBuilder logBuilder, MethodLogger methodLogger) {
            logBuilder.append("r=");
            logBuilder.append(returningValue);
            logBuilder.append(" ");
    }

    private void createLog(JoinPoint joinPoint, StringBuilder logBuilder) {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        log.info(logBuilder.toString());
    }

    private void createErrorLog(JoinPoint joinPoint, StringBuilder logBuilder) {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        log.error(logBuilder.toString());
    }

    private void logSuccessMessage(MethodLogger methodLogger, StringBuilder logBuilder) {
        String msgOnSuccess = methodLogger.msgOnSuccess();
        if (!msgOnSuccess.isEmpty()) {
            logBuilder.append(msgOnSuccess);
            logBuilder.append(" ");
        }
    }

    private void logErrorMessage(MethodLogger methodLogger, StringBuilder logBuilder, Exception exception) {
        String msgOnException = methodLogger.msgOnException();
        if (!msgOnException.isEmpty()) {
            logBuilder.append(msgOnException);
            logBuilder.append(": \n");
        }
        logBuilder.append(exception.getMessage());
        exception.printStackTrace();
    }

    private static void logArgs(JoinPoint joinPoint, StringBuilder logBuilder, MethodLogger methodLogger){
        if (methodLogger.logAllArgs()) {
            logAllArgs(joinPoint, logBuilder);
        } else {
            logOptionalArgs(joinPoint, logBuilder);
        }
    }

    private static void logOptionalArgs(JoinPoint joinPoint, StringBuilder logBuilder) {
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            boolean hasArgumentLogger = hasArgumentLogger(joinPoint, i);
            if (hasArgumentLogger) {
                appendArgLog(joinPoint.getArgs()[i], logBuilder);
            }
        }
    }

    private static void appendArgLog(Object object, StringBuilder logBuilder) {
        logBuilder.append("a=");
        logBuilder.append(object);
        logBuilder.append(" ");
    }

    private static boolean hasArgumentLogger(JoinPoint joinPoint, int i) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        return Stream.of(parameters[i].getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().isAssignableFrom(ArgumentLogger.class));
    }

    private static void logAllArgs(JoinPoint joinPoint, StringBuilder logBuilder) {
        for (Object object : joinPoint.getArgs()) {
            appendArgLog(object, logBuilder);
        }
    }

    private static void logMethod(JoinPoint joinPoint, StringBuilder logBuilder) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.getName();

        logBuilder.append("m=");
        logBuilder.append(methodName);
        logBuilder.append(" ");
    }
}
