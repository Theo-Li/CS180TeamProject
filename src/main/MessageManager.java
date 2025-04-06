package main;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageManager implements IMessageManager {
    private List<Message> messageList = new ArrayList<>();
    private final String messageFile = "messages.txt";

    public MessageManager() {
        loadMessages();
    }

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
