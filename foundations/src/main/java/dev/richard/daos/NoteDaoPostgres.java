package dev.richard.daos;

import dev.richard.entities.Note;
import dev.richard.utils.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.*;
import java.util.List;
public class NoteDaoPostgres implements NoteDAO {
    private String logString;
    @Override
    public Note createNote(Note note) {
        try (Connection c = ConnectionUtil.getConnection()) {
            logString = String.format("Attempting to add new note with id of %d into the database...", note.getNoteId());
            LoggerUtil.log(logString, LogLevel.INFO);

            String query = "insert into notes values (default, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, note.getNoteTitle());
            ps.setString(2, note.getNoteBody());
            ps.setInt(3, note.getOwnerId());
            ps.setBoolean(4, note.isVisible());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int noteId = rs.getInt("id");
            note.setNoteId(noteId);
            logString = String.format("Added note with id of %d successfully.", noteId);
            LoggerUtil.log(logString, LogLevel.INFO);
            return note;

        } catch (SQLException e) {
            logString = String.format("Could not add note to the database. More information: %s", ExceptionUtils.getStackTrace(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Note getNoteById(int id) {
        try (Connection c = ConnectionUtil.getConnection()) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List getAllNotesByAuthor(String username) {
        return null;
    }

    @Override
    public List getAllNotes() {
        return null;
    }

    @Override
    public Note updateNote(Note note) {
        return null;
    }

    @Override
    public Note deleteNoteById(Note note) {
        return null;
    }
}
