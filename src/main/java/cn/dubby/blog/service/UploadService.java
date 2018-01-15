package cn.dubby.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by yangzheng03 on 2018/1/15.
 */
@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private static final String UPLOAD_PATH_KEY = "/file/upload/";

    public Map uploadFile(Map<String, MultipartFile> multipartFileMap) {
        File file = null;
        for (Map.Entry<String, MultipartFile> entry : multipartFileMap.entrySet()) {
            String originFileName = entry.getValue().getOriginalFilename();
            String[] originFileNameSplit = originFileName.split("\\.");

            String url = UUID.randomUUID().toString() + "." + originFileNameSplit[originFileNameSplit.length - 1];
            Date date = new Date();
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String path = UPLOAD_PATH_KEY + dateTimeFormatter.format(date);
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdir();

            try {
                file = new File(path + "/" + url);
                file.setExecutable(true, false);
                file.setWritable(true, false);
                file.setReadable(true, false);
                entry.getValue().transferTo(file);
            } catch (IOException e) {
                logger.error("file upload error", e);
            }

            Map result = new HashMap();

            result.put("success", 1);
            result.put("url", file.getAbsolutePath().replace("/file", ""));

            return result;
        }
        Map result = new HashMap();

        result.put("success", 0);
        result.put("message", "没有文件");
        return result;
    }

}
