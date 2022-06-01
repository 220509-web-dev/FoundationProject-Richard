package dev.richard.entities;

import java.util.Objects;

public class Note {
    private int noteId;
    private String noteTitle;
    private String noteBody;
    private int ownerId;
    private boolean visibility;

    public Note() {

    }

    public Note(int noteId, String noteTitle, String noteBody, int ownerId, boolean visibility) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.ownerId = ownerId;
        this.visibility = visibility;
    }
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteBody='" + noteBody + '\'' +
                ", ownerId=" + ownerId +
                ", visibility=" + visibility +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return noteId == note.noteId && ownerId == note.ownerId && visibility == note.visibility && Objects.equals(noteTitle, note.noteTitle) && Objects.equals(noteBody, note.noteBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, noteTitle, noteBody, ownerId, visibility);
    }
}