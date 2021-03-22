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
import seu.talents.cloud.talent.model.dao.entity.JobFair;
import seu.talents.cloud.talent.model.dto.post.JobFairSearchDTO;
import seu.talents.cloud.talent.service.JobFairService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("dynamicParseJobFairService")
public class DynamicParseJobFairServiceImpl implements JobFairService {

    private final RestTemplate restTemplate;
    private final HttpRequestConstValue httpRequestConstValue;

    @Autowired
    public DynamicParseJobFairServiceImpl(RestTemplate restTemplate, HttpRequestConstValue httpRequestConstValue) {
        this.restTemplate = restTemplate;
        this.httpRequestConstValue = httpRequestConstValue;
    }

    private List<JobFair> requestAndParseHtml(Integer pageIndex, String urlTemplate, Object... arguments) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlTemplate, String.class, arguments);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BizException(ConstantUtil.BizExceptionCause.REQUEST_HAS_BEEN_REFUSED);
        }

        List<JobFair> resultList = new ArrayList<>();
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
            JobFair tempJobFair = new JobFair();
            Element title = information.child(0).selectFirst("a");
            String name = title.text();
            String link = title.attr("href");
            if (!RegexUtil.isUrl(link)) {
                link = httpRequestConstValue.getUrlPrefix() + link;
            }
            tempJobFair.setJobFairName(name);
            tempJobFair.setDetailLink(link);
            tempJobFair.setCity(information.child(1).text());
            tempJobFair.setAddress(information.child(2).text());
            tempJobFair.setType(information.child(3).text());
            tempJobFair.setTime(information.child(4).text());
            resultList.add(tempJobFair);
        }

        return resultList;
    }

    @Override
    public List<JobFair> getJobFairsByPage(Integer pageIndex, Integer pageSize) {
        return requestAndParseHtml(pageIndex, httpRequestConstValue.getJobFairUrlTemplateForGet(), pageIndex);
    }

    @Override
    @ConvertParameters
    public List<JobFair> searchJobFairs(JobFairSearchDTO jobFairSearchDTO) {
        return requestAndParseHtml(
                jobFairSearchDTO.getPageIndex(),
                httpRequestConstValue.getJobFairUrlTemplateForSearch(),
                jobFairSearchDTO.getKeyWord(),
                jobFairSearchDTO.getRange(),
                jobFairSearchDTO.getType(),
                jobFairSearchDTO.getCity(),
                jobFairSearchDTO.getTime(),
                jobFairSearchDTO.getPageIndex()
        );
    }
}
