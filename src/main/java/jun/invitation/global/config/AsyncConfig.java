package jun.invitation.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "imageUploadExecutor")
    public Executor imageUploadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setThreadGroupName("imageUploadExecutor");
        executor.setCorePoolSize(14);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.initialize();

        return executor;
    }
}
