package logs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public final class TestAppender extends AppenderBase<ILoggingEvent> {
    private final List<ILoggingEvent> events = new ArrayList<>();

    boolean hasMessage(final Level level, final String message) {
        return events
                .stream()
                .filter(iLoggingEvent -> iLoggingEvent.getLevel().equals(level))
                .anyMatch(iLoggingEvent -> iLoggingEvent.getMessage().contains(message));
    }

    @Override
    protected void append(final ILoggingEvent e) {
        events.add(e);
    }
}