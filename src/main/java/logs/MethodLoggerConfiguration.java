package logs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("logs")
@EnableAspectJAutoProxy
class MethodLoggerConfiguration {
}
