package dev.adryell.personalpage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class GeneralConfig {

    private String GCP_BUCKET_NAME;
    private String GCP_PUBLIC_URL;

    public String getGCP_BUCKET_NAME() {
        return GCP_BUCKET_NAME;
    }

    public void setGCP_BUCKET_NAME(String GCP_BUCKET_NAME) {
        this.GCP_BUCKET_NAME = GCP_BUCKET_NAME;
    }

    public String getGCP_PUBLIC_URL() {
        return GCP_PUBLIC_URL;
    }

    public void setGCP_PUBLIC_URL(String GCP_PUBLIC_URL) {
        this.GCP_PUBLIC_URL = GCP_PUBLIC_URL;
    }

    public String getGcpPrefixUrl() {
        return GCP_PUBLIC_URL + GCP_BUCKET_NAME;
    }
}
