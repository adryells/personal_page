package dev.adryell.personalpage.services;

import dev.adryell.personalpage.config.GeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    @Autowired
    private GeneralConfig config = new GeneralConfig();

    public String generateURL(String title) {
        return config.getGcpPrefixUrl() + "/" + title;
    }
}