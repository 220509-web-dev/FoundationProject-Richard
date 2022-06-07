package dev.richard.soulnotes.daos;
import dev.richard.soulnotes.entities.Note;
import dev.richard.soulnotes.entities.User;

import java.util.List;

public interface NoteDAO {
    /**
     * Puts a new note into the database.
     * @param note The note to add to the database.
     * @return Note
     */
    Note createNote(Note note);
    /**
     * Retrieves a note from the database by the id.
     * @param id The id of the note.
     * @return Note
     */
    Note getNoteById(int id);

    /**
     * Gets all notes by a certain author.
     * @param username The username of the author.
     * @return java.util.List
     */
    List getAllNotesByAuthor(String username);
    /**
     * Gets all notes by a certain author.
     * @param id The user id of the author.
     * @return java.util.List
     */
    List getAllNotesByAuthor(int id);

    /**
     * Retrieves all notes from the database.
     * @return java.util.List
     */
    List getAllNotes();

    /**
     * Updates a note in the database.
     * @param note The note that has been updated.
     * @return Note
     */
    Note updateNote(Note note);

    /**
     * Removes a note from the database.
     * @param note
     * @return null
     */
    Note deleteNoteById(Note note);

    /**
     * Removes all notes of a certain author from the database.
     * @param user The author of the notes.
     * @return null
     */
    Note deleteAllNotesFromAuthor(User user);
}

