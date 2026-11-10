package dev.taskflow.task;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("taskflow.seed")
public record TaskSeedProperties(boolean enabled, List<String> titles) {

    public TaskSeedProperties {
        titles = (titles == null) ? List.of() : List.copyOf(titles);
    }
}