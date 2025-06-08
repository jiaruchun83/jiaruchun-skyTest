package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.common.properties.OssProperties;
import jiaruchun.common.utils.AliyunOSSOUtils;
import jiaruchun.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/admin/common/upload")
public class Upload {
    @Autowired
    private OssProperties ossProperties;

    @PostMapping
    public Result upLoad(MultipartFile file) throws Exception {
        String upload = AliyunOSSOUtils.upload(file.getBytes(), Objects.requireNonNull(file.getOriginalFilename()), ossProperties.getEndpoint(), ossProperties.getBucketName(), ossProperties.getRegion());
        return Result.success(upload);
    }

}
