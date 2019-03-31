package logs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MethodLoggerConfiguration.class)
class TestConfiguration {
}
