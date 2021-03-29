package seu.talents.cloud.talent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seu.talents.cloud.talent.model.dao.entity.Message;
import seu.talents.cloud.talent.model.dao.mapper.MessageMapper;
import seu.talents.cloud.talent.service.MessageService;
import seu.talents.cloud.talent.util.ConstantUtil;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public void newMessage(String fromUser, String toUser, Integer type) {
        //消息通知
        Message message = new Message();
        message.setTimestamp(System.currentTimeMillis());
        message.setMessageId(ConstantUtil.generateId());

        message.setFromUser(fromUser);
        message.setToUser(toUser);
        message.setType(type);
        messageMapper.insertSelective(message);
//        messageMapper
    }

    @Override
    public void newMessage(String fromUser, String toUser, Integer type, String title, String content) {
        //消息通知
        Message message = new Message();
        message.setTimestamp(System.currentTimeMillis());
        message.setMessageId(ConstantUtil.generateId());

        message.setFromUser(fromUser);
        message.setToUser(toUser);
        message.setType(type);
        message.setContent(content);
        message.setTitle(title);
        messageMapper.insertSelective(message);
    }

    @Override
    public void newMessage(
            String fromUser, String toUser,
            Integer type, String title,
            String content, String img
    ) {
        Message message = new Message();
        message.setTimestamp(System.currentTimeMillis());
        message.setMessageId(ConstantUtil.generateId());
        message.setFromUser(fromUser);
        message.setToUser(toUser);
        message.setType(type);
        message.setContent(content);
        message.setTitle(title);
        message.setImg(img);
        messageMapper.insertSelective(message);
    }
}
