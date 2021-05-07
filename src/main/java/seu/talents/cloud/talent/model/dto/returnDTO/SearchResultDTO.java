package seu.talents.cloud.talent.model.dto.returnDTO;

import lombok.Data;
import seu.talents.cloud.talent.model.dto.post.BriefInfo;
import seu.talents.cloud.talent.model.dto.post.SearchType;

import java.util.List;

@Data
public class SearchResultDTO {
    private long count;
    private SearchType type;
    private List<BriefInfo> list;

    public SearchResultDTO(long count, SearchType type, List<BriefInfo> list) {
        this.count = count;
        this.type = type;
        this.list = list;
    }
}
