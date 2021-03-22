package seu.talents.cloud.talent.service.impl;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import seu.talents.cloud.talent.common.config.httprequest.HttpRequestConstValue;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dao.entity.SelectedGraduatesOrInternationalOrganization;
import seu.talents.cloud.talent.model.dto.post.SelectedGraduatesAndInternationalOrganizationSearchDTO;
import seu.talents.cloud.talent.service.SelectedGraduatesAndInternationalOrganizationService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("dynamicParseSelectedGraduatesAndInternationalOrganizationService")
public class DynamicParseSelectedGraduatesAndInternationalOrganizationServiceImpl
    implements SelectedGraduatesAndInternationalOrganizationService {

    private final RestTemplate restTemplate;
    private final HttpRequestConstValue httpRequestConstValue;

    @Autowired
    public DynamicParseSelectedGraduatesAndInternationalOrganizationServiceImpl(RestTemplate restTemplate, HttpRequestConstValue httpRequestConstValue) {
        this.restTemplate = restTemplate;
        this.httpRequestConstValue = httpRequestConstValue;
    }

    private List<SelectedGraduatesOrInternationalOrganization> requestAndParseHtml(
            Integer pageIndex,
            String urlTemplate,
            Object... arguments
    ) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlTemplate, String.class, arguments);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BizException(ConstantUtil.BizExceptionCause.REQUEST_HAS_BEEN_REFUSED);
        }

        List<SelectedGraduatesOrInternationalOrganization> resultList = new ArrayList<>();
        Document responseHtml = Jsoup.parse(responseEntity.getBody());
        Element newsBox = responseHtml.selectFirst("div.newsBox");
        if (newsBox == null) {
            return resultList;
        }

        Element pageInfo = newsBox.selectFirst("div.pages");
        int pageSize = 1;
        if (pageInfo.childrenSize() != 0) {
            pageSize = Integer.parseInt(pageInfo.child(0).child(0).text());
        }
        if (pageIndex > pageSize) {
            return resultList;
        }
        Elements informationList = newsBox.select("ul.newsList");
        for (Element information: informationList) {
            SelectedGraduatesOrInternationalOrganization temp = new SelectedGraduatesOrInternationalOrganization();
            Element title = information.child(1).child(0);
            String name = title.text();
            String link = title.attr("href");

            if (!RegexUtil.isUrl(link)) {
                link = httpRequestConstValue.getUrlPrefix() + link;
            }
            temp.setTime(information.child(0).text());
            temp.setName(name);
            temp.setDetailLink(link);

            resultList.add(temp);
        }
        return resultList;
    }

    @Override
    public List<SelectedGraduatesOrInternationalOrganization> getByPage(Integer pageIndex, Integer pageSize, Integer type) {
        return requestAndParseHtml(
                pageIndex,
                type == 0? httpRequestConstValue.getSelectedGraduatesUrlTemplateForGet(): httpRequestConstValue.getInternationalOrganizationUrlTemplateForGet(),
                pageIndex
        );
    }

    @Override
    public List<SelectedGraduatesOrInternationalOrganization> search(Integer type, SelectedGraduatesAndInternationalOrganizationSearchDTO dto) {
        return requestAndParseHtml(
                dto.getPageIndex(),
                type == 0? httpRequestConstValue.getSelectedGraduatesUrlTemplateForSearch(): httpRequestConstValue.getInternationalOrganizationUrlTemplateForSearch(),
                dto.getKeyWord(),
                dto.getPageIndex()
        );
    }
}
