package seu.talents.cloud.talent.util;

import lombok.AllArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import seu.talents.cloud.talent.common.IdGenerator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@Component
public class ConstantUtil {
    static IdGenerator idGenerator;

    @AllArgsConstructor
    public enum BizExceptionCause{
        MISMATCH(1,"不匹配"),
        TRY_EXCEED(2,"超过最大尝试次数"),
        TIME_EXCEED(3,"超时"),
        NOT_FOUND(4,"无效token"),
        LOW_AUTHORITY(5,"权限不够"),
        NOT_LOGINED(6,"未登录"),
        NOT_OPENID(7,"获取openid失败"),
        NOT_USER(8,"找不到此用户"),
        HAS_DELETE(9,"此公司已经注销"),
        PAGE_INDEX_OUT_OF_RANGE(10, "按页查询页码范围有误"),
        REQUEST_HAS_BEEN_REFUSED(11, "请求就业办网页页面信息失败")


        ;
        public final Integer code;
        public final String reason;//解释

    }

    static {
//        idGenerator = new SnowflakeIdGenerator(0, 0);
        idGenerator = new IdGenerator();
    }

    /**
     * bigint生成器
     * @return
     */
    public static long generateId() {
        return idGenerator.nextId();
    }


    public static MultipartFile fileToMultipartFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
    }

    public static Date addEightHours(Date date) {
        return new Date(date.getTime() + 8 * 60 * 60 * 1000);
    }

    public static Boolean deleteFileUnderProjectDir(String fileName) {
        // 然后应该删除项目目录下的本地文件
        File targetFile = new File(System.getProperty("user.dir") + File.separator + fileName);
        return targetFile.delete();
    }

    /**
     * @param requestUrl    请求地址
     * @param requestMethod 请求方法
     * @param outputStr     参数
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        // 创建SSLContext
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }
            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


}
