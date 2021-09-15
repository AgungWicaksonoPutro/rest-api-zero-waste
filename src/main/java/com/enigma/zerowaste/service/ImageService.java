package com.enigma.zerowaste.service;

import java.io.IOException;

public interface ImageService {
    public void storeToServe(byte[] file, String fileName) throws IOException;
}
