package seu.talents.cloud.talent.model.dao.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import seu.talents.cloud.talent.model.dao.entity.Message;
import seu.talents.cloud.talent.model.dto.post.MessageDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MessageMapper extends Mapper<Message> {
    @Select("select m.message_id,\n" +
            "\t\tm.type,\n" +
            "\t\t'交换名片申请' as typeDesc,\n" +
            "\t\tm.title,\n" +
            "\t\tm.content,\n" +
            "\t\tm.img,\n" +
            "\t\tm.status,\n" +
            "\t\tm.timestamp,\n" +
            "\t\tm.from_user,\n" +
            "\t\ta.name as fromUserName,\n" +
            "\t\ta.avatar,\n" +
            "\t\tm.to_user\n" +
            "\t\tfrom message m\n" +
            "\t\tleft join account a on m.from_user = a.accountId\n" +
            "\t\twhere m.to_user = #{accountId} and m.type != 10")
    List<MessageDTO> getMessagesByAccountId(Long accountId, Integer status);
}
