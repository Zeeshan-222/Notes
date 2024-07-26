package com.soft.notes;

import com.google.firebase.Timestamp;

public class Note {
    public String title;
    public String content;
    public Timestamp timestamp;

    public Note() {
        // Default constructor required for calls to DataSnapshot.getValue(Note.class)
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
