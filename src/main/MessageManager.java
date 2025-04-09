package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The MessageManager class manages message-related operations.
 * It implements the IMessageManager interface and handles loading and saving messages.
 *
 * @author Tianzhi Li
 * @version  2025-04-02
 */
public class MessageManager implements IMessageManager {
    private List<Message> messageList = new ArrayList<>();
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
     * This method clears the current list, reads the file line by line,
     * splits each line into parts, parses the senderID, receiverID, and message content,
     * creates a new Message object, and adds it to the list.
     *
     * The method is synchronized to ensure thread safety.
     */
    @Override
    public synchronized void loadMessages() {
        messageList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(messageFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
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
                    Message message = new Message(senderID, receiverID, messageContent);
                    messageList.add(message);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load messages: " + e.getMessage());
        }
    }

    /**
     * Saves the current messageList to the data file.
     * This method iterates over each Message in the list,
     * formats its details into a comma-separated string,
     * and writes each string to the file.
     *
     * The method is synchronized to ensure thread safety.
     */
    @Override
    public synchronized void saveMessages() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(messageFile))) {
            for (Message msg : messageList) {
                String line = msg.getSenderID() + "," + msg.getReceiverID() + "," + msg.getMessage();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to save messages: " + e.getMessage());
        }
    }

    /**
     * Sends a message from one user to another.
     * Creates a new Message object, adds it to the message list, and saves the updated list.
     *
     * @param from the sender user
     * @param to the receiver user
     * @param message the message content
     * @return true if the message is sent successfully
     */
    @Override
    public synchronized boolean sendMessage(User from, User to, String message) {
        Message newMessage = new Message(from.getUserID(), to.getUserID(), message);
        messageList.add(newMessage);
        saveMessages();
        return true;
    }

    /**
     * Retrieves all messages where the given user is either the sender or the receiver.
     *
     * @param user the user for whom messages are retrieved
     * @return a list of messages associated with the user
     */
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

    /**
     * Retrieves the conversation between two users.
     * Collects all messages exchanged between user1 and user2.
     *
     * @param user1 the first user
     * @param user2 the second user
     * @return a list of messages forming the conversation between the two users
     */
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
