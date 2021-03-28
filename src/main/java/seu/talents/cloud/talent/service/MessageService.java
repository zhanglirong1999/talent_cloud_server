package seu.talents.cloud.talent.service;

public interface MessageService {
    void newMessage(String fromUser, String toUser, Integer type);

    void newMessage(String fromUser, String toUser, Integer type, String title, String content);

    void newMessage(
            String fromUser, String toUser, Integer type, String title, String content,
            String img
    );

    //void newMessageBatch(List<MessageTemp> messageTemps);
}
