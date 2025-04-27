package DAO;

import Model.Message;
import java.sql.*;
import Util.ConnectionUtil;

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
}
