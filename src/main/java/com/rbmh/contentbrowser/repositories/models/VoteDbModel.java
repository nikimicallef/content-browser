package com.rbmh.contentbrowser.repositories.models;

import java.time.Instant;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("votes")
public class VoteDbModel {

    @Id
    private String id;
    private VoteTypeDbEnum voteType;
    private String contentId;
    private Instant voteTime;

    public VoteDbModel() {
    }

    public VoteDbModel(String id, VoteTypeDbEnum voteType, String contentId, Instant voteTime) {
        this.id = id;
        this.voteType = voteType;
        this.contentId = contentId;
        this.voteTime = voteTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VoteTypeDbEnum getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteTypeDbEnum voteType) {
        this.voteType = voteType;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Instant getVoteTime() {
        return voteTime;
    }

    public void setVoteTime(Instant voteTime) {
        this.voteTime = voteTime;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        VoteDbModel that = (VoteDbModel) o;
        return Objects.equals(id, that.id) && voteType == that.voteType && Objects.equals(contentId, that.contentId) && Objects.equals(voteTime, that.voteTime);
    }

    @Override public int hashCode() {
        return Objects.hash(id, voteType, contentId, voteTime);
    }
}
