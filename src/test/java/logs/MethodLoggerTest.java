package logs;


import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static logs.LoggerTestUtils.setUpLogger;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.util.Assert.isTrue;


@SpringJUnitConfig(classes = {TestConfiguration.class})
class MethodLoggerTest {

    private TestAppender testAppender;

    @BeforeEach
    void setUp(){
        testAppender = setUpLogger(TestClass.class);
    }

    @Test
    @DisplayName("Test if it logs method's name")
    void shouldLogMethodName(@Autowired final TestClass testClass) {
        testClass.testMethod();
        isTrue(testAppender.hasMessage(Level.INFO, "m=testMethod"), "It should logged method's name");
    }

    @Test
    @DisplayName("Test if it logs method's argument")
    void shouldLogMethodArgument(@Autowired final TestClass testClass) {
        testClass.testMethod("Test Ok!");
        isTrue(testAppender.hasMessage(Level.INFO, "a=Test Ok!"), "It should logged method's argument");
    }

    @Test
    @DisplayName("Test if it logs method's return")
    void shouldLogMethodReturn(@Autowired final TestClass testClass) {
        TestClass.testMethodReturn();
        isTrue(testAppender.hasMessage(Level.INFO, "r=Test Ok!"), "It should logged method's return");
    }

    @Test
    @DisplayName("Test if it logs method's msg")
    void shouldLogMethodMsg(@Autowired final TestClass testClass) {
        testClass.testMethodMsg();
        isTrue(testAppender.hasMessage(Level.INFO, "Msg on Success okay!"), "It should logged method's msg");
    }

    @Test
    @DisplayName("Test if it logs method's exception msg")
    void shouldLogMethodExceptionMsg(@Autowired final TestClass testClass) {
        assertThrows(Exception.class, TestClass::testMethodException);
        isTrue(testAppender.hasMessage(Level.ERROR, "Msg on Exception okay!"), "It should logged method's exception msg");
    }
}