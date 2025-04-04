/**
 * Message class represents a simple message exchanged between two users.
 * Implements the IMessage interface.
 *
 * @author Tianzhi Li
 * @version 2025-04-02
 */
public class Message implements IMessage {
    private int senderID;
    private int receiverID;
    private String message;

    public Message(int senderID, int receiverID, String message) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
