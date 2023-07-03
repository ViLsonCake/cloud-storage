package com.project.CloudStorage.utils;

import java.util.Base64;

public class FileUtils {
    public static String convertToReadableSize(Long bytes) {
        double sizeInKilobytes = (double) bytes / 1024;

        return bytes < 1024 ? bytes + "B" : sizeInKilobytes < 1024 ? (int) sizeInKilobytes + "KB" : sizeInKilobytes / 1024 < 1024 ?
                String.format("%.1fMB", sizeInKilobytes / 1024) :
                String.format("%.1fGB", sizeInKilobytes / (1024 * 1024));
    }

    public static String getUsernameFromHeader(String authHeader) {
        final int usernameIndex = 0;
        final String usernameWithPassword = authHeader.substring(6);

        byte[] bytes = Base64.getDecoder().decode(usernameWithPassword);
        String decodedHeader = new String(bytes); // Get string format username:password

        return decodedHeader.split(":")[usernameIndex];
    }
}
