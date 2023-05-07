package com.adnan13.holidayapi.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class SecretsReader {

    /**
     * Reads the Google Calendar API key from the file Secrets/GoogleCalendarApiKey.json
     * for this method to work, the file must be in the classpath
     * put the file in src/main/resources/Secrets/GoogleCalendarApiKey.json
     * the contents of the file should be like this:
     * {
     * "api_key": "YOUR_API_KEY"
     * }
     */
    public String getGoogleCalendarApiKey() throws FileNotFoundException {

        String GOOGLE_CALENDAR_API_KEY_FILE_PATH = "classpath:Secrets/GoogleCalendarApiKey.json";

        File GOOGLE_CALENDAR_API_KEY_FILE = ResourceUtils.getFile(GOOGLE_CALENDAR_API_KEY_FILE_PATH);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(GOOGLE_CALENDAR_API_KEY_FILE);
            return jsonNode.get("api_key").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
