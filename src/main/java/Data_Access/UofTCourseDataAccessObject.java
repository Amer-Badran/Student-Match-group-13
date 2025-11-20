package Data_Access;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UofTCourseDataAccessObject {

    public static void main(String[] args) {
        String baseUrl = "https://artsci.calendar.utoronto.ca/search-courses";
        int totalPages = 178; // pages start from 0 to 178
        List<Map<String, String>> coursesList = new ArrayList<>();

        try {
            for (int i = 0; i <= totalPages; i++) {
                String pageUrl = baseUrl + "?page=" + i;
                System.out.println("Scraping page: " + i);

                Document doc = Jsoup.connect(pageUrl)
                        .userAgent("Mozilla/5.0") // mimic browser
                        .timeout(10_000)
                        .get();

                Elements courses = doc.select("h3.js-views-accordion-group-header div");

                for (Element course : courses) {
                    String fullTitle = course.text().trim();
                    if (fullTitle.isEmpty()) continue;

                    Map<String, String> courseData = new HashMap<>();
                    if (fullTitle.contains(" - ")) {
                        String[] parts = fullTitle.split(" - ", 2);
                        courseData.put("code", parts[0].trim());
                        courseData.put("title", parts[1].trim());
                    } else {
                        courseData.put("title", fullTitle);
                    }
                    coursesList.add(courseData);
                }

                // polite scraping: short pause between pages
                Thread.sleep(300);
            }

            // Write JSON to file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter("uoft_courses_stgeorge.json")) {
                gson.toJson(coursesList, writer);
            }

            System.out.println("Scraping complete! JSON file created with " + coursesList.size() + " courses.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}