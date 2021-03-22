package seu.talents.cloud.talent.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seu.talents.cloud.talent.common.annotation.ConvertParameters;
import seu.talents.cloud.talent.common.config.httprequest.HttpRequestConstValue;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.Post;
import seu.talents.cloud.talent.model.dto.post.PostSearchDTO;
import seu.talents.cloud.talent.service.PostService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

@Service("dynamicParsePostService")
public class DynamicParsePostServiceImpl implements PostService {

    private final RestTemplate restTemplate;
    private final int NUMBER_OF_DATA_PER_PAGE = 20;
    private final HttpRequestConstValue httpRequestConstValue;


    @Autowired
    public DynamicParsePostServiceImpl(RestTemplate restTemplate, HttpRequestConstValue httpRequestConstValue) {
        this.restTemplate = restTemplate;
        this.httpRequestConstValue = httpRequestConstValue;
    }

    private List<Post> requestAndParseHtml(Integer pageIndex, String urlTemplate, Object... arguments) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlTemplate, String.class, arguments);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BizException(ConstantUtil.BizExceptionCause.REQUEST_HAS_BEEN_REFUSED);
        }

        List<Post> resultList = new ArrayList<>();
        Document responseHtml = Jsoup.parse(responseEntity.getBody());
        Elements container = responseHtml.select("div.infoBox");
        double numberOfData = Double.parseDouble(container.select("div.pages").first().child(0).text());
        if (pageIndex > Math.ceil(numberOfData / NUMBER_OF_DATA_PER_PAGE)) {
            return resultList;
        }
        Elements informationList = container.select("ul.infoList");

        for (Element information: informationList) {
            Post tempPost = new Post();
            Element postTitle = information.child(0).child(0);
            Element companyTitle = information.child(1).child(0);
            String postName = postTitle.text();
            String companyName = companyTitle.text();
            String postLink = postTitle.attr("href");
            String companyLink = companyTitle.attr("href");
            if (!RegexUtil.isUrl(postLink)) {
                postLink = httpRequestConstValue.getUrlPrefix() + postLink;
            }

            if (!RegexUtil.isUrl(companyLink)) {
                companyLink = httpRequestConstValue.getUrlPrefix() + companyLink;
            }
            tempPost.setPostName(postName);
            tempPost.setDetailLink(postLink);
            tempPost.setCompanyName(companyName);
            tempPost.setCompanyLink(companyLink);
            tempPost.setCity(information.child(2).text());
            tempPost.setIndustry(information.child(3).text());
            tempPost.setTime(information.child(4).text());
            resultList.add(tempPost);
        }

        return resultList;
    }

    @Override
    public List<Post> getPostInformationByPage(Integer pageIndex, Integer pageSize, Integer postType) {
        return requestAndParseHtml(
                pageIndex,
                postType == 0 ? httpRequestConstValue.getPostUrlTemplateForGetFullTimeJob() : httpRequestConstValue.getPostUrlTemplateForGetIntern(),
                pageIndex
        );
    }

    @Override
    @ConvertParameters
    public List<Post> searchPost(PostSearchDTO postSearchDTO) {
        return requestAndParseHtml(
                postSearchDTO.getPageIndex(),
                postSearchDTO.getPostType() == 0 ? httpRequestConstValue.getPostUrlTemplateForSearchFullTimeJob() : httpRequestConstValue.getPostUrlTemplateForSearchIntern(),
                postSearchDTO.getKeyWord(),
                postSearchDTO.getIndustry(),
                postSearchDTO.getSkill(),
                postSearchDTO.getCity(),
                postSearchDTO.getSalary(),
                postSearchDTO.getEducation(),
                postSearchDTO.getTime(),
                postSearchDTO.getNature(),
                postSearchDTO.getScale(),
                postSearchDTO.getPageIndex()
        );
    }
}
