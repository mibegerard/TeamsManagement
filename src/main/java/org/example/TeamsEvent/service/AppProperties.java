package org.example.TeamsEvent.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "your.application.prefix")
public class AppProperties {
    private String graphApiUrl;
    private String accessToken;

    // Getters and setters for graphApiUrl and accessToken

    public String getGraphApiUrl() {
        return graphApiUrl;
    }

    public void setGraphApiUrl(String graphApiUrl) {
        this.graphApiUrl = graphApiUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
