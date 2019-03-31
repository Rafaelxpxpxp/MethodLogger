package logs;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

class LoggerTestUtils {

    static TestAppender setUpLogger(final Class clazz) {
        final Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        final TestAppender testAppender = new TestAppender();
        logger.addAppender(testAppender);
        testAppender.start();
        return testAppender;
    }
}
