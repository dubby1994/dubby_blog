package cn.dubby.blog.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by teeyoung on 17/12/6.
 */
@RestController
@RequestMapping("file")
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    private static final String UPLOAD_PATH_KEY = "/file/upload/";

    @RequestMapping("upload")
    public Map upload(MultipartHttpServletRequest request) {
        Map<String, MultipartFile> multipartFileMap = request.getFileMap();
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

    @RequestMapping(value = "qrcode")
    public Object getURLQRCode(String content) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();

            Map<String, String> result = new HashMap<>();
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);

            String tempName = base64en.encode(md5.digest(content.getBytes("UTF-8")));

            Date date = new Date();
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String path = UPLOAD_PATH_KEY + dateTimeFormatter.format(date) + "/qrcode";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdir();

            String fileName = tempName + ".png";
            File file = new File(path + "/" + fileName);
            if (file.exists()) {
                result.put("url", "/upload/" + dateTimeFormatter.format(date) + "/qrcode/" + fileName);
                return result;
            }

            file.createNewFile();

            logger.warn("matrix is null?", matrix == null);
            logger.warn("file is null?", file == null);

            MatrixToImageWriter.writeToPath(matrix, "png", file.toPath());


            result.put("url", "/upload/" + dateTimeFormatter.format(date) + "/qrcode/" + fileName);
            return result;
        } catch (Exception e) {
            logger.error("qrcode", e);
            return "";
        }
    }

}
