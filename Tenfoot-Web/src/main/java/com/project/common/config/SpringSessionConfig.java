package com.project.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = "project", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

}
