package com.adnan13.holidayapi.services;

import com.adnan13.holidayapi.helpers.SecretsReader;
import com.adnan13.holidayapi.models.Holiday;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

@Service
public class HolidayService {
    public static final String BASE_CALENDAR_URL = "https://www.googleapis.com/calendar/v3/calendars";
    public static final String BASE_CALENDAR_ID_FOR_PUBLIC_HOLIDAY =
            "holiday@group.v.calendar.google.com";
    public static String API_KEY;
    public static final String CALENDAR_REGION = "en.bd";

    public HolidayService() throws FileNotFoundException {
        SecretsReader secretsReader = new SecretsReader();
        API_KEY = secretsReader.getGoogleCalendarApiKey();
    }

    public List<Holiday> getHolidays(int year) throws IOException {

//        const url = `${BASE_CALENDAR_URL}/${CALENDAR_REGION}%23${BASE_CALENDAR_ID_FOR_PUBLIC_HOLIDAY}/events?key=${API_KEY}`
        String url = BASE_CALENDAR_URL + "/" + CALENDAR_REGION + "%23" + BASE_CALENDAR_ID_FOR_PUBLIC_HOLIDAY + "/events?key=" + API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String responseBody = response.body().string();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);
            JsonNode items = jsonNode.get("items");
            Iterator<JsonNode> iterator = items.iterator();
            List<Holiday> holidays = new ArrayList<>();
            while (iterator.hasNext()) {
                JsonNode item = iterator.next();
                String type = item.get("description").asText();

                if (!type.equals("Public holiday")) {
                    continue;
                }

                String name = item.get("summary").asText();
                String date = item.get("start").get("date").asText();
//                if date year is not equal to the given year, then skip this holiday
                if (!date.startsWith(String.valueOf(year))) {
                    continue;
                }
                LocalDate localDate = LocalDate.parse(date);

                Holiday holiday = new Holiday(name, localDate, type);
                holidays.add(holiday);
            }

//            sort holidays by date
            holidays.sort((o1, o2) -> {
                if (o1.getDate().isBefore(o2.getDate())) {
                    return -1;
                } else if (o1.getDate().isAfter(o2.getDate())) {
                    return 1;
                } else {
                    return 0;
                }
            });

            return holidays;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isHoliday(LocalDate date) throws IOException {
        List<Holiday> holidays = getHolidays(date.getYear());
        for (Holiday holiday : holidays) {
            if (holiday.getDate().equals(date)) {
                return true;
            }
        }
        return false;
    }

}
