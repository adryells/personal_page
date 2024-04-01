package dev.adryell.personalpage.config;

public class GeneralConfig {
    public String GCP_BUCKET_NAME = "yell-personal-page";
    public String GCP_PUBLIC_URL = "https://storage.googleapis.com/";

    public String getGCP_BUCKET_NAME() {
        return GCP_BUCKET_NAME;
    }

    public String getGCP_PUBLIC_URL() {
        return GCP_PUBLIC_URL;
    }
}
