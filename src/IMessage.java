public interface IMessage {
    void setSenderID(int senderID);
    int getSenderID();
    void setReceiverID(int receiverID);
    int getReceiverID();
    String getMessage();
    void setMessage(String message);


}
