package main;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The MessageManager class manages message-related operations.
 * It implements the IMessageManager interface and handles loading,
 * and saving messages.
 *
 * Author: Tianzhi Li
 * Version: 2025-04-02
 */
public class MessageManager implements IMessageManager {
    // A list to store Message objects.
    // It holds all the messages managed by this class.
    private List<Message> messageList = new ArrayList<>();

    // The filename for storing message data persistently.
    // The file "messages.txt" is used for reading and writing message details.
    private final String messageFile = "messages.txt";

    /**
     * Constructor for MessageManager.
     * Initializes the message manager by loading message data from the file.
     */
    public MessageManager() {
        loadMessages();
    }

    /**
     * Loads messages from the data file into the messageList.
     *
     * This method performs the following steps:
     * 1. Clears the current messageList to remove any existing messages.
     * 2. Opens the file specified by messageFile using a BufferedReader.
     * 3. Reads the file line by line; each line should have comma-separated values.
     * 4. Splits each line into parts and checks if there are at least 3 parts.
     * 5. Parses the senderID, receiverID, and messageContent from the line.
     * 6. Creates a new Message object with the parsed senderID, receiverID, and messageContent.
     * 7. Adds the message to messageList.
     * 8. Catches and handles any IOException during file reading.
     *
     * The method is synchronized to ensure thread safety.
     */
    public synchronized void loadMessages() {
        // Clear any existing messages.
        messageList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(messageFile))) {
            String line;
            // Read file line by line.
            while ((line = br.readLine()) != null) {
                // Split the line into parts using a comma as a delimiter.
                String[] parts = line.split(",");
                // Ensure there are at least 3 parts (senderID, receiverID, messageContent).
                if (parts.length >= 3) {
                    int senderID = Integer.parseInt(parts[0].trim());
                    int receiverID = Integer.parseInt(parts[1].trim());
                    StringBuilder sb = new StringBuilder();
                    for (int i = 2; i < parts.length; i++) {
                        if (i > 2) {
                            sb.append(",");
                        }
                        sb.append(parts[i]);
                    }
                    String messageContent = sb.toString();
                    // Create a new User with the provided senderID, receiverID, and messageContent.
                    Message message = new Message(senderID, receiverID, messageContent);
                    // Add the new message to the messageList.
                    messageList.add(message);
                }
            }
        } catch (IOException e) {
            // Print an error message if file reading fails.
            System.out.println("Failed to load messages: " + e.getMessage());
        }
    }

    /**
     * Saves the current messageList to the data file.
     *
     * This method performs the following steps:
     * 1. Opens the file specified by messageFile using a BufferedWriter.
     * 2. Iterates over each Message in messageList.
     * 3. Formats each message's details into a comma-separated string.
     * 4. Writes the formatted string to the file, one message per line.
     * 5. Catches and handles any IOException during file writing.
     *
     * The method is synchronized to ensure thread safety.
     */
    public synchronized void saveMessages() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(messageFile))) {
            // Loop through all messages in the list.
            for (Message msg : messageList) {
                // Format the message's details: senderID,receiverID,messageContent.
                String line = msg.getSenderID() + "," + msg.getReceiverID() + "," + msg.getMessage();
                // Write the line to the file.
                bw.write(line);
                // Start a new line for the next message.
                bw.newLine();
            }
        } catch (IOException e) {
            // Print an error message if file writing fails.
            System.out.println("Failed to save messages: " + e.getMessage());
        }
    }

    @Override
    public synchronized boolean sendMessage(User from, User to, String message) {
        Message newMessage = new Message(from.getUserID(), to.getUserID(), message);
        messageList.add(newMessage);
        saveMessages();
        return true;
    }

    @Override
    public synchronized List<Message> getMessagesForUser(User user) {
        List<Message> results = new ArrayList<>();
        for (Message msg : messageList) {
            if (msg.getSenderID() == user.getUserID() || msg.getReceiverID() == user.getUserID()) {
                results.add(msg);
            }
        }
        return results;
    }

    @Override
    public synchronized List<Message> getConversation(User user1, User user2) {
        List<Message> conversation = new ArrayList<>();
        for (Message msg : messageList) {
            if ((msg.getSenderID() == user1.getUserID() && msg.getReceiverID() == user2.getUserID()) ||
                    (msg.getSenderID() == user2.getUserID() && msg.getReceiverID() == user1.getUserID())) {
                conversation.add(msg);
            }
        }
        return conversation;
    }
}
