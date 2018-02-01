package com.smart.mvc.util;

public class FtpTest {

    //test ftpclient  success
    public static void main(String[] args) {
        FtpUtils ftpUtils = new FtpUtils();
        ftpUtils.setUrl("10.4.6.27");
        ftpUtils.setPort(21);
        ftpUtils.setUsername("test");
        ftpUtils.setPassword("test");

        ftpUtils.uploadFile("/dirone","bbb.properties","F:\\ftptest\\bbb.properties");

        ftpUtils.downFile("/config","db.properties","F:\\AngularTest");
    }
}
