package com.noalino.notabene;

public class NoteBuilder {
    private String id;
    private String content;
    private Boolean isPinned;
    private String createdAt;
    private String updatedAt;
    private String remindedAt;

    // Default constructor, for ObjectMapper
    public NoteBuilder() {}

    public NoteBuilder(String id, String content, Boolean isPinned, String createdAt, String updatedAt, String remindedAt) {
        this.id = id;
        this.content = content;
        this.isPinned = isPinned;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.remindedAt = remindedAt;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsPinned() {
        return isPinned;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getRemindedAt() {
        return remindedAt;
    }
}
