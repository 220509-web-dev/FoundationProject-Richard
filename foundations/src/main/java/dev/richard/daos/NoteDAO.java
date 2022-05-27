package dev.richard.daos;
import dev.richard.entities.Note;

import java.util.List;

public interface NoteDAO {
    Note createNote(Note note);
    Note getNoteById(int id);
    List getAllNotes();
    Note updateNote(Note note);
    Note deleteNoteById(Note note);
}

