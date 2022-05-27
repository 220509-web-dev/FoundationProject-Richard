package dev.richard.daos;
import dev.richard.entities.Note;

import java.util.List;

public interface NoteDAO {
    /**
     * Puts a new note ito the database.
     * @param note The note to add to the database.
     * @return dev.richard.entities.Note
     */
    Note createNote(Note note);
    Note getNoteById(int id);
    List getAllNotes();
    Note updateNote(Note note);
    Note deleteNoteById(Note note);
}

