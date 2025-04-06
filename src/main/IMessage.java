package main;
/**
 *
 *
 *
 * @author Tianzhi Li
 * @version 2025-04-02
 */
public interface IMessage {
    void setSenderID(int senderID);
    int getSenderID();
    void setReceiverID(int receiverID);
    int getReceiverID();
    String getMessage();
    void setMessage(String message);


}
