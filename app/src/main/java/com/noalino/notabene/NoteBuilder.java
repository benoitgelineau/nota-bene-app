package com.noalino.notabene;

public class NoteBuilder {
    private String content;

    public NoteBuilder() {
    }

    public NoteBuilder(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
