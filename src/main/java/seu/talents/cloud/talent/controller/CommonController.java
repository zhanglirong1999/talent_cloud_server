package seu.talents.cloud.talent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import seu.talents.cloud.talent.common.annotation.TokenRequired;
import seu.talents.cloud.talent.common.annotation.WebResponse;
import seu.talents.cloud.talent.service.AccountService;
import seu.talents.cloud.talent.service.QCloudFileManager;
import seu.talents.cloud.talent.util.ConstantUtil;

import java.io.File;
import java.io.IOException;

@WebResponse
@RestController
@CrossOrigin
public class CommonController {

    @Autowired
    QCloudFileManager qCloudFileManager;

    @TokenRequired
    @PostMapping("/uploadFile")
    public Object uploadFile(
            @RequestParam(name = "file") MultipartFile multipartFile
    ) throws IOException {
        // 首先获取 newName
        String newNameWithoutType = String.valueOf(ConstantUtil.generateId());
        String newNameWithType = this.qCloudFileManager.buildNewFileNameWithType(
                multipartFile, newNameWithoutType
        );
        String ansUrl = null;
        try {
            ansUrl = qCloudFileManager.uploadOneFile(
                    multipartFile,
                    newNameWithoutType
            );
        } catch (IOException e) {
            return "上传文件失败";
        }
        // 要删除文件
        ConstantUtil.deleteFileUnderProjectDir(newNameWithType);
        // 返回最终结果
        return ansUrl;
    }
}
