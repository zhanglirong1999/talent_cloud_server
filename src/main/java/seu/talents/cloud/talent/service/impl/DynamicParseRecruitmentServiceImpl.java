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
import seu.talents.cloud.talent.model.dao.entity.Recruitment;
import seu.talents.cloud.talent.model.dto.post.RecruitmentSearchDTO;
import seu.talents.cloud.talent.service.RecruitmentService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

@Service("dynamicParseRecruitmentService")
public class DynamicParseRecruitmentServiceImpl implements RecruitmentService {
    private final RestTemplate restTemplate;
    private final HttpRequestConstValue httpRequestConstValue;

    @Autowired
    public DynamicParseRecruitmentServiceImpl(RestTemplate restTemplate, HttpRequestConstValue httpRequestConstValue) {
        this.restTemplate = restTemplate;
        this.httpRequestConstValue = httpRequestConstValue;
    }

    private List<Recruitment> requestAndParseHtml(Integer pageIndex, String urlTemplate, Object... arguments) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlTemplate, String.class, arguments);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BizException(ConstantUtil.BizExceptionCause.REQUEST_HAS_BEEN_REFUSED);
        }

        List<Recruitment> resultList = new ArrayList<>();
        Document responseHtml = Jsoup.parse(responseEntity.getBody());
        Elements container = responseHtml.select("div.infoBox");
        Element pagesInfo = container.select("div.pages").first();
        int numberOfPage = 1;
        if (pagesInfo.childrenSize() > 0) {
            numberOfPage = Integer.parseInt(pagesInfo.child(0).child(0).text());
        }
        if (pageIndex > numberOfPage) {
            return resultList;
        }
        Elements informationList = container.select("ul.infoList");

        for (Element information: informationList) {
            Recruitment tempRecruitment = new Recruitment();
            Element title = information.child(0).selectFirst("a");
            String name = title.text();
            String link = title.attr("href");
            if (!RegexUtil.isUrl(link)) {
                link = httpRequestConstValue.getUrlPrefix() + link;
            }
            tempRecruitment.setRecruitmentName(name);
            tempRecruitment.setDetailLink(link);
            tempRecruitment.setCity(information.child(1).text());
            tempRecruitment.setTime(information.child(2).text());

            resultList.add(tempRecruitment);
        }

        return resultList;
    }

    @Override
    public List<Recruitment> getRecruitmentByPage(Integer pageIndex, Integer pageSize) {
        return requestAndParseHtml(pageIndex, httpRequestConstValue.getRecruitmentUrlTemplateForGet(), pageIndex);
    }

    @Override
    @ConvertParameters
    public List<Recruitment> searchRecruitment(RecruitmentSearchDTO recruitmentSearchDTO) {
        return requestAndParseHtml(
                recruitmentSearchDTO.getPageIndex(),
                httpRequestConstValue.getRecruitmentUrlTemplateForSearch(),
                recruitmentSearchDTO.getKeyWord(),
                recruitmentSearchDTO.getRange(),
                recruitmentSearchDTO.getCity(),
                recruitmentSearchDTO.getTime(),
                recruitmentSearchDTO.getPageIndex()
        );
    }
}
