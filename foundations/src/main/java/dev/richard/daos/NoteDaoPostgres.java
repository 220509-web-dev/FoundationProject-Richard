package dev.richard.daos;

import dev.richard.entities.Note;
import dev.richard.entities.User;
import dev.richard.utils.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.management.LockInfo;
import java.sql.*;
import java.util.ArrayList;
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
            logString = String.format("Could not add note to the database. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Note getNoteById(int id) {
        try (Connection c = ConnectionUtil.getConnection()) {
            logString = String.format("Attempting to retrieve note with id of %d from the database...", id);
            LoggerUtil.log(logString, LogLevel.INFO);
            String query = "select * from notes where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            Note n = new Note(rs.getInt("id"), rs.getString("name"), rs.getString("note_body"), rs.getInt("owner_id"), rs.getBoolean("visible"));

            logString = String.format("Retrieved note with id of %d successfully.", id);
            LoggerUtil.log(logString, LogLevel.INFO);
            return n;
        } catch (SQLException e) {
            logString = String.format("Failed to retrieve note. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getAllNotesByAuthor(String username) {
        UserDAO dao = new UserDaoPostgres();
        try (Connection c = ConnectionUtil.getConnection()) {
            logString = String.format("Attempting to retrieve all notes from the user with the username of %s...", username);
            LoggerUtil.log(logString, LogLevel.INFO);
            String query = "select * from notes where owner_id = ?";
            User u = dao.getUserByUsername(username);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, u.getUserId());
            ResultSet rs = ps.executeQuery();

            List allNotes = new ArrayList();
            while (rs.next()) {
                allNotes.add(new Note(rs.getInt("id"), rs.getString("name"), rs.getString("note_body"), u.getUserId(), rs.getBoolean("visible")));
            }
            logString = String.format("Retrieved all notes from the user with the user of %s successfully.", username);
            LoggerUtil.log(logString, LogLevel.INFO);
        return allNotes;
        } catch (SQLException e) {
            logString = String.format("Could not retrieve all notes from the user. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getAllNotesByAuthor(int id) {
        UserDAO dao = new UserDaoPostgres();
        try (Connection c = ConnectionUtil.getConnection()) {
            logString = String.format("Attempting to retrieve all notes from the user with the id of %d...", id);
            LoggerUtil.log(logString, LogLevel.INFO);
            String query = "select * from notes where owner_id = ?";
            User u = dao.getUserById(id);
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, u.getUserId());
            ResultSet rs = ps.executeQuery();

            List allNotes = new ArrayList();
            while (rs.next()) {
                allNotes.add(new Note(rs.getInt("id"), rs.getString("name"), rs.getString("note_body"), u.getUserId(), rs.getBoolean("visible")));
            }
            return allNotes;
        } catch (SQLException e) {
            logString = String.format("Could not retrieve all notes from the user. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List getAllNotes() {
        UserDAO dao = new UserDaoPostgres();
        try (Connection c = ConnectionUtil.getConnection()) {
            logString = "Attempting to retrieve all notes from the database...";
            LoggerUtil.log(logString, LogLevel.INFO);
            String query = "select * from notes";
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List allNotes = new ArrayList();
            while (rs.next()) {
                allNotes.add(new Note(rs.getInt("id"), rs.getString("name"), rs.getString("note_body"), rs.getInt("owner_id"), rs.getBoolean("visible")));
            }
            return allNotes;
        } catch (SQLException e) {
            logString = String.format("Could not retrieve all notes from the database. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Note updateNote(Note note) {
        try (Connection c = ConnectionUtil.getConnection()) {
            Note oldNote = note;
            logString = String.format("Attempting to update note with id of %s...", note.getNoteId());
            LoggerUtil.log(logString, LogLevel.INFO);
            String query = "update notes set name = ?, note_body = ?, visible = ? where id = ?";
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, note.getNoteTitle());
            ps.setString(2, note.getNoteBody());
            ps.setBoolean(3, note.isVisible());
            ps.setInt(4, note.getOwnerId());
            ps.execute();

            logString = String.format("Updated note successfully. Old note contents: %s, new note contents: %s", oldNote, note);
            LoggerUtil.log(logString, LogLevel.INFO);
            return note;

        } catch (SQLException e) {
            logString = String.format("An error occurred while attempting to update note. More information: %s", ExceptionUtils.getMessage(e));
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Note deleteNoteById(Note note) {
        try (Connection c = ConnectionUtil.getConnection()) {
            logString = String.format("Attempting to delete note with id of %s...", note.getNoteId());
            String query = "delete from notes where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, note.getNoteId());

        } catch (SQLException e) {
            logString = String.format("An error occurred while attempting to delete note. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Note deleteAllNotesFromAuthor(User user) {
        try (Connection c = ConnectionUtil.getConnection()) {
            logString = String.format("Attempting to delete all notes from author %s...", user.getUsername());
            String query = "delete * from notes where id = ?";
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, user.getUserId());

        } catch (SQLException e) {
            logString = String.format("An error occurred while attempting to delete all notes from author. More information: %s", ExceptionUtils.getMessage(e));
            LoggerUtil.log(logString, LogLevel.ERROR);
            e.printStackTrace();
        }
        return null;
    }
}
