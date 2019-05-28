package com.zealtech.policephonebook2019.Config;

public class ApplicationConfig {

    public static final String SERVER_URL = "http://ztidev.com:8081/phonebook/";
//    public static final String SERVER_URL = "http://209.97.162.75/phonebook/";
    public static final String IMAGE_URL = "download?file=";

    public static String getApiBaseUrl() {
        return SERVER_URL;
    }
    public static String getImageUrl() {
        return SERVER_URL + IMAGE_URL; }
}
