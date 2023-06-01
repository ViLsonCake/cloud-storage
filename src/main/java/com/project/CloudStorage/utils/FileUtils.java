package com.project.CloudStorage.utils;

public class FileUtils {
    public static String convertToReadableSize(Long bytes) {
        double sizeInKilobytes = (double) bytes / 1024;

        return sizeInKilobytes < 1024 ? (int) sizeInKilobytes + "KB" : sizeInKilobytes / 1024 < 1024 ?
                String.format("%.1fMB", sizeInKilobytes / 1024) :
                String.format("%.1fGB", sizeInKilobytes / (1024 * 1024));
    }
}
