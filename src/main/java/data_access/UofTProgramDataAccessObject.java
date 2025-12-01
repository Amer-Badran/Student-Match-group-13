package data_access;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UofTProgramDataAccessObject {
    public static void main(String[] args) {
        String url = "https://www.utoronto.ca/academics/undergraduate-programs";

        try {
            // Load the web page from URL
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0") // some sites require a user agent
                    .get();

            List<Map<String, Object>> programs = new ArrayList<>();

            // Select all program cards
            Elements cards = doc.select("div.card");

            for (Element card : cards) {
                Element nameEl = card.selectFirst(".card-header a.nav-accordion");
                Element degreeEl = card.selectFirst(".card-header-body b");
                Elements typeEls = card.select(".types-wrapper .types");
                Element campusEl = card.selectFirst(".campus-wrapper .campus");
                Element linkEl = card.selectFirst(".card-body a[href]");

                // Filter only St. George campus programs
                if (campusEl == null || !campusEl.text().toLowerCase().contains("st. george")) continue;

                String name = nameEl != null ? nameEl.text().trim() : "";
                String degree = degreeEl != null ? degreeEl.text().trim() : "";
                List<String> types = new ArrayList<>();
                for (Element t : typeEls) {
                    types.add(t.text().trim());
                }
                String campus = campusEl.text().trim();
                String link = linkEl != null ? linkEl.attr("href") : "";

                Map<String, Object> program = new LinkedHashMap<>();
                program.put("name", name);
                program.put("degree", degree);
                program.put("types", types);
                program.put("campus", campus);
                program.put("link", link);

                programs.add(program);
            }

            // Write to JSON file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter("uoft_programs_stgeorge.json")) {
                gson.toJson(programs, writer);
            }

            System.out.println("Saved " + programs.size() + " St. George programs to uoft_programs_stgeorge.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
