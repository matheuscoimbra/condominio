package com.br.condomio.apt.service;

import com.br.condomio.apt.util.BASE64DecodedMultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinaryConfig;

    public String uploadFile(String base64) {
        try {
            MultipartFile file = base64ToMultipart(base64);
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            uploadedFile.delete();
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static MultipartFile base64ToMultipart(String base64) {

			String[] baseStr = base64.split(",");

            byte[] b = new byte[0];
            b = Base64.getDecoder().decode(baseStr[1]);

        return new BASE64DecodedMultipartFile(b, baseStr[0]);

    }

}