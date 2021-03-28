package seu.talents.cloud.talent.model.dto.post;

import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import seu.talents.cloud.talent.model.dao.entity.Message;

import java.lang.reflect.InvocationTargetException;

@Data
public class MessageDTO {
    private Long messageId;

    private Integer type;
    private String typeDesc;

    private String title;
    private String content;

    /**
     * 0未读1已读
     * 默认未读
     */
    private Integer status = 0;

    private Long timestamp;

    private String fromUser;
    private String fromUserName;
    private String avatar;

    private String toUser;

    private String img;

    MessageDTO() {

    }

    public MessageDTO(Message message) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(message, this);
    }

    public Message toMessage() throws InvocationTargetException, IllegalAccessException {
        Message message = new Message();
        BeanUtils.copyProperties(this, message);
        return message;
    }
}

