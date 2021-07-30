package VueSpringProj.Simplegram.models.chat;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class ChatMessage {
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private Date timestamp;
    private EMessageStatus status;
}