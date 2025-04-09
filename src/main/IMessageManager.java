package main;
import java.util.List;

/**
 *
 *
 *
 * @author Tianzhi Li
 * @version 2025-04-02
 */
public interface IMessageManager {
    void loadMessages();
    void saveMessages();
    boolean sendMessage(User from, User to, String message);
    List<Message> getMessagesForUser(User user);
    List<Message> getConversation(User user1, User user2);
}
