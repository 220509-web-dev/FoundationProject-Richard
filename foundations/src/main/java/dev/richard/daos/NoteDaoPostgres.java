package dev.richard.daos;

import dev.richard.entities.Note;
import dev.richard.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NoteDaoPostgres implements NoteDAO {

    @Override
    public Note createNote(Note note) {
        try (Connection c = ConnectionUtil.getConnection()) {
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
    public List getNoteByAuthors(String username) {
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
