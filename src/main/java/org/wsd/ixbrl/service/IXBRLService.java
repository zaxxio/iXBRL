package org.wsd.ixbrl.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestScope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IXBRLService {

    private final ResourceLoader resourceLoader;

    public String convert(String content) throws IOException {
        Document rootDocument = Jsoup.parse(content);
        Resource resource = resourceLoader.getResource("classpath:/ixbrl/template.htm");

        String templateHTML = readResourceToString(resource);

        Elements rows = rootDocument.select("table tr");
        final List<String> columns = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            if (i == 0) continue;
            Elements cells = rows.get(i).select("td");
            for (Element cell : cells) {
                columns.add(cell.text());
            }
        }
        for (int i = 0; i < columns.size(); i++) {
            templateHTML = templateHTML.replace("{{cellContent" + i + "}}", columns.get(i));
        }
        return templateHTML;
    }

    public String readResourceToString(Resource resource) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        }

        return stringBuilder.toString();
    }
}
