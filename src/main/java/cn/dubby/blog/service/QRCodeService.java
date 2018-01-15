package cn.dubby.blog.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yangzheng03 on 2018/1/15.
 */
@Service
public class QRCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeService.class);

    private static final String UPLOAD_PATH_KEY = "/file/upload/";

    public Map generateQRCode(String content, int width, int height) {
        Map<String, String> result = new HashMap<>();
        try {
            if (width > 3000)
                width = 300;
            if (height > 3000)
                height = 300;

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            String tempName = base64en.encode(md5.digest(content.getBytes("UTF-8"))) + width + "-" + height;
            tempName = tempName.replace("/", "-");

            Date date = new Date();
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String path = UPLOAD_PATH_KEY + dateTimeFormatter.format(date) + "/qrcode";
            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            String fileName = tempName + ".png";
            File file = new File(path + "/" + fileName);
            if (file.exists()) {
                result.put("url", "/upload/" + dateTimeFormatter.format(date) + "/qrcode/" + fileName);
                return result;
            }

            file.createNewFile();

            MatrixToImageWriter.writeToPath(matrix, "png", file.toPath());


            result.put("url", "/upload/" + dateTimeFormatter.format(date) + "/qrcode/" + fileName);
            return result;
        } catch (Exception e) {
            logger.error("qrcode", e);
            result.put("error", e.toString());
            return result;
        }
    }

}
