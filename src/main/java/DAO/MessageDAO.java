package DAO;

import Model.Message;
import java.sql.*;
import Util.ConnectionUtil;
import java.util.List;
import java.util.ArrayList;


public class MessageDAO {
    
    // inserts a new record into the Message table
    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String sqlLine = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlLine, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, message.getPosted_by());
            stmt.setString(2, message.getMessage_text());
            stmt.setLong(3, message.getTime_posted_epoch());
            stmt.executeUpdate();

            ResultSet assignedPKey = stmt.getGeneratedKeys();
            if (assignedPKey.next()) {
                // this means the insertion was successful and got back valid primary key
                int assignedID = assignedPKey.getInt(1);
                message.setMessage_id(assignedID);
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // insertion failed
        return null;
    }

    // helper method that creates Message object from a retrieved row of data
    private Message constructMessage(ResultSet record) throws SQLException {
        int message_id = record.getInt("message_id");
        int postedBy = record.getInt("posted_by");
        String msgText = record.getString("message_text");
        long timePosted = record.getLong("time_posted_epoch");
        return new Message(message_id, postedBy, msgText, timePosted);
    }

    // queries the full Message table to get all messages
    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        ArrayList<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM message;";

        try {
            ResultSet result = conn.createStatement().executeQuery(query);
            while (result.next())
                messages.add(constructMessage(result));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return messages;
    }

    // queries Message table to find record with given message id
    public Message getMessageByID(int id) {
        Connection conn = ConnectionUtil.getConnection();
        String sqlLine = "SELECT * FROM message WHERE message_id = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlLine);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next())
                return constructMessage(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // message with the given id was not found
        return null;
    }

    // deletes message record from Message table with the given message_id
    public void deleteMessageWithID(int id) {
        Connection conn = ConnectionUtil.getConnection();
        String sqlLine = "DELETE FROM message WHERE message_id = ?;";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlLine);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
