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
import seu.talents.cloud.talent.model.dao.entity.Lecture;
import seu.talents.cloud.talent.model.dto.post.LectureSearchDTO;
import seu.talents.cloud.talent.service.LectureService;
import seu.talents.cloud.talent.util.ConstantUtil;
import seu.talents.cloud.talent.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

@Service("dynamicParseLectureService")
public class DynamicParseLectureServiceImpl implements LectureService {

    private final RestTemplate restTemplate;
    private final HttpRequestConstValue httpRequestConstValue;

    @Autowired
    public DynamicParseLectureServiceImpl(RestTemplate restTemplate, HttpRequestConstValue httpRequestConstValue) {
        this.restTemplate = restTemplate;
        this.httpRequestConstValue = httpRequestConstValue;
    }

    private List<Lecture> requestAndParseHtml(Integer pageIndex, String urlTemplate, Object... arguments) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlTemplate, String.class, arguments);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BizException(ConstantUtil.BizExceptionCause.REQUEST_HAS_BEEN_REFUSED);
        }

        List<Lecture> resultList = new ArrayList<>();
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
            Lecture tempLecture = new Lecture();
            Element title = information.child(0).selectFirst("a");
            String name = title.text();
            String link = title.attr("href");
            if (!RegexUtil.isUrl(link)) {
                link = httpRequestConstValue.getUrlPrefix() + link;
            }
            tempLecture.setLectureName(name);
            tempLecture.setDetailLink(link);
            tempLecture.setCity(information.child(1).text());
            tempLecture.setSchool(information.child(2).text());
            tempLecture.setAddress(information.child(3).text());
            tempLecture.setTime(information.child(4).text());
            tempLecture.setSource(httpRequestConstValue.getSource());
            resultList.add(tempLecture);
        }

        return resultList;
    }


    @Override
    public List<Lecture> getLectureByPage(Integer pageIndex, Integer pageSize) {
        return requestAndParseHtml(pageIndex, httpRequestConstValue.getLectureUrlTemplateForGet(), pageIndex);
    }

    @Override
    public List<Lecture> searchLecture(LectureSearchDTO lectureSearchDTO) {
        return requestAndParseHtml(
                lectureSearchDTO.getPageIndex(),
                httpRequestConstValue.getLectureUrlTemplateForSearch(),
                lectureSearchDTO.getKeyWord(),
                lectureSearchDTO.getTime(),
                lectureSearchDTO.getPageIndex()
        );
    }

    @Override
    public void addLecture(Lecture lecture) {

    }

    @Override
    public void deleteById(Long lectureId) {

    }

    @Override
    public void updateById(Lecture lecture) {

    }
}
