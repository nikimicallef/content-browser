package com.rbmh.contentbrowser.repositories.models;

import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;


@Document("content")
public class ContentDbModel {
    private String id;
    private String externalId;
    private MediaTypeDbEnum mediaType;
    private String source;
    private String title;
    private String contentUrl;
    private String previewUrl;
    private Integer upvotes;
    private Integer downvotes;
    private String description;
    private Long lengthSeconds;
    private String aspectRatio;
    private String topic;

    public ContentDbModel() {
    }

    public ContentDbModel(String id, String externalId, MediaTypeDbEnum mediaType, String source, String title, String contentUrl, String previewUrl, Integer upvotes, Integer downvotes, String description, Long lengthSeconds, String aspectRatio, String topic) {
        this.id = id;
        this.externalId = externalId;
        this.mediaType = mediaType;
        this.source = source;
        this.title = title;
        this.contentUrl = contentUrl;
        this.previewUrl = previewUrl;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.description = description;
        this.lengthSeconds = lengthSeconds;
        this.aspectRatio = aspectRatio;
        this.topic = topic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public MediaTypeDbEnum getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaTypeDbEnum mediaType) {
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

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(Long lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
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

    @Override public String toString() {
        return "ContentDbModel{" +
                "id='" + id + '\'' +
                ", externalId='" + externalId + '\'' +
                ", mediaType=" + mediaType +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                ", description='" + description + '\'' +
                ", lengthSeconds=" + lengthSeconds +
                ", aspectRatio='" + aspectRatio + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ContentDbModel that = (ContentDbModel) o;
        return Objects.equals(id, that.id) && Objects.equals(externalId, that.externalId) && mediaType == that.mediaType && Objects.equals(source, that.source) && Objects.equals(title, that.title) && Objects.equals(contentUrl, that.contentUrl) && Objects.equals(previewUrl, that.previewUrl) && Objects.equals(upvotes, that.upvotes) && Objects.equals(downvotes, that.downvotes) && Objects.equals(description, that.description) && Objects.equals(lengthSeconds, that.lengthSeconds) && Objects.equals(aspectRatio, that.aspectRatio) && Objects.equals(topic, that.topic);
    }

    @Override public int hashCode() {
        return Objects.hash(id, externalId, mediaType, source, title, contentUrl, previewUrl, upvotes, downvotes, description, lengthSeconds, aspectRatio, topic);
    }
}
