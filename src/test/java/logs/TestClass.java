package logs;

import org.springframework.stereotype.Component;

@Component
class TestClass {

    @MethodLogger
    void testMethod(){}

    @MethodLogger
    void testMethod(final String argument){}

    @MethodLogger
    static String testMethodReturn(){
        return "Test Ok!";
    }

    @MethodLogger(msgOnSuccess = "Msg on Success okay!")
    void testMethodMsg(){}

    @MethodLogger(msgOnException = "Msg on Exception okay!")
    static void testMethodException(){
        throw new RuntimeException();
    }
}
