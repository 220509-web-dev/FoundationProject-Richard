package dev.richard.daos;
import dev.richard.entities.Note;
import dev.richard.entities.User;

import java.util.List;

public interface NoteDAO {
    /**
     * Puts a new note into the database.
     * @param note The note to add to the database.
     * @return dev.richard.entities.Note
     */
    Note createNote(Note note);
    /**
     * Retrieves a note from the database by the id.
     * @param id The id of the note.
     * @return dev.richard.entities.Note
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
     * @return dev.richard.entities.Note
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

