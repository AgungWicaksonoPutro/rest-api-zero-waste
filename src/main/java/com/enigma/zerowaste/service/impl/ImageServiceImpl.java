package com.enigma.zerowaste.service.impl;

import com.enigma.zerowaste.service.ImageService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public void storeToServe(byte[] file, String fileName) throws IOException {
        File targetFile = new File("target/classes/static/images/"+fileName);
        if (targetFile.exists()){
            targetFile.mkdirs();
        }

        FileOutputStream outputStream = new FileOutputStream(targetFile);
        outputStream.write(file);
        outputStream.flush();
        outputStream.close();
    }
}
