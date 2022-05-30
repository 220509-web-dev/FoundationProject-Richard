package dev.richard.daos;

import dev.richard.entities.Note;
import dev.richard.utils.*;

import java.sql.*;
import java.util.List;
public class NoteDaoPostgres implements NoteDAO {
    private String logString;
    @Override
    public Note createNote(Note note) {
        try (Connection c = ConnectionUtil.getConnection()) {
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
            return note;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Note getNoteById(int id) {
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
