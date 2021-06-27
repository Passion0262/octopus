package com.example.octopus.utils;

import java.io.InputStream;

public class FtpFileUtil {

    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "180.215.1.182";
    //端口号
    private static final int FTP_PORT = 21;
    //用户名 这里用到的是root 可以切合上面遇到的问题，实际开发中需要单独创建一个用户
    private static final String FTP_USERNAME = "aiplatform";
    //密码
    private static final String FTP_PASSWORD = "aiplatform";
    //图片路径
//    private static final String FTP_BASEPATH = "/soft/code/images";

    public  static boolean uploadFile(String originFileName, InputStream input){
//        boolean success = false;
//        FTPClient ftp = new FTPClient();
//        ftp.setControlEncoding("GBK");
//        try {
//            int reply;
//            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
//
//            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
//
//            reply = ftp.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//
//                return success;
//            }
//            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//            ftp.makeDirectory(FTP_BASEPATH );
//            ftp.changeWorkingDirectory(FTP_BASEPATH );
//            ftp.storeFile(originFileName,input);
//
//            input.close();
//            ftp.logout();
//            success = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch (IOException ioe) {
//                }
//            }
//        }
//        return success;
        return true;
    }
}

