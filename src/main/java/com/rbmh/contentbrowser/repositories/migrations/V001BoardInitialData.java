package com.rbmh.contentbrowser.repositories.migrations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.rbmh.contentbrowser.repositories.ContentRepository;
import com.rbmh.contentbrowser.repositories.models.ContentDbModel;
import com.rbmh.contentbrowser.repositories.models.MediaTypeDbEnum;


@ChangeLog(order = "001")
public class V001BoardInitialData {

    @ChangeSet(order = "001", id = "boardInitialData", author = "niki")
    public void boardInitialData(ContentRepository repository)
            throws IOException {
        final File file = ResourceUtils.getFile("classpath:content.json");

        final String fileContents = new String(Files.readAllBytes(file.toPath()));
        final String cleanedText = Jsoup.parse(fileContents).text().replace("\\t", "");

        final ObjectMapper objectMapper = new ObjectMapper();
        final List<JsonContent> contentModels = objectMapper.readValue(cleanedText, new TypeReference<>() {

        });

        final List<ContentDbModel> dbEntities = contentModels.stream().map(content ->
                new ContentDbModel(null,
                        content.getId(),
                        MediaTypeDbEnum.valueOf(content.getMediaType().toUpperCase()),
                        content.source,
                        content.title,
                        content.contentUrl,
                        content.previewUrl,
                        0,
                        content.description,
                        content.getLength() == null ? null : lengthStringToSeconds(content.getLength()),
                        content.getAspectRatio(),
                        content.getTopic())
        ).collect(Collectors.toList());

        repository.saveAll(dbEntities);
    }

    private Long lengthStringToSeconds(final String length) {
        String[] units = length.split(":");
        long hours = Long.parseLong(units[0]);
        long minutes = Long.parseLong(units[1]);
        long seconds = Long.parseLong(units[2]);
        return (3600 * hours) + (60 * minutes) + seconds;
    }

    public static class JsonContent {

        private String id;
        private String mediaType;
        private String source;
        private String title;
        private String contentUrl;
        private String previewUrl;
        private String description;
        private String length;
        private String aspectRatio;
        private String topic;

        public JsonContent() {
        }

        public JsonContent(String id, String mediaType, String source, String title, String contentUrl, String previewUrl, String description, String length, String aspectRatio, String topic) {
            this.id = id;
            this.mediaType = mediaType;
            this.source = source;
            this.title = title;
            this.contentUrl = contentUrl;
            this.previewUrl = previewUrl;
            this.description = description;
            this.length = length;
            this.aspectRatio = aspectRatio;
            this.topic = topic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public String getPreviewUrl() {
            return previewUrl;
        }

        public void setPreviewUrl(String previewUrl) {
            this.previewUrl = previewUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getAspectRatio() {
            return aspectRatio;
        }

        public void setAspectRatio(String aspectRatio) {
            this.aspectRatio = aspectRatio;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }
}
