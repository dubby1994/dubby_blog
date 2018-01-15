package cn.dubby.blog.controller;

import cn.dubby.blog.service.QRCodeService;
import cn.dubby.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.*;

/**
 * Created by teeyoung on 17/12/6.
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private QRCodeService qrCodeService;

    @RequestMapping("upload")
    public Map upload(MultipartHttpServletRequest request) {
        return uploadService.uploadFile(request.getFileMap());
    }

    @RequestMapping(value = "qrcode")
    public Object getURLQRCode(String content, @RequestParam(name = "w", required = false, defaultValue = "300") int width, @RequestParam(name = "h", required = false, defaultValue = "300") int height) {
        return qrCodeService.generateQRCode(content, width, height);
    }

}
