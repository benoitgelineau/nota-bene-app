package com.noalino.notabene;

import java.util.Date;

public class NoteBuilder {
    private String id;
    private String content;

    // Default constructor, for ObjectMapper
    public NoteBuilder() {}

    public NoteBuilder(String id, String content) {
        this.id = id;
        this.content = content;
    }

    // Default getter, for ObjectMapper
    public String getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
}
