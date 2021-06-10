package com.example.octopus.utils;


import java.io.*;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: Hao
 * @date: 2021/6/9 20:26
 * Shell 工具类
 */

public class ShellUtils {
    private static final long SERIAL_VERSION_UID = 1L;

    // todo 需要修改docker主机地址等相关配置
    private static final String IP_ADDRESS = "";// ip 地址
    private static final String USER_NAME = "";// 登陆帐号
    private static final String PASS_WORD = "";// 登陆密码

    private static final int DEFAULT_PORT = 22;// 默认端口号

    private static Logger log = LoggerFactory.getLogger(ShellUtils.class);
    private static volatile ShellUtils instance = null;

    private Session session;// JSCH session
    private boolean logined = false;// 是否登陆


    public static ShellUtils getInstance() {
        if (instance == null) {
            instance = new ShellUtils();
//            synchronized (DBCommonTools.class) {
//                if(instance == null) {
//                    instance = new ShellUtils();
//                }
//            }
        }
        return instance;
    }

    /**
     * @Description: 远程登陆
     * @return: void
     */
    public void sshRemoteCallLogin() {
        // 如果登陆就直接返回
        if (logined) {
            log.info("SSH connection has on");
            return;
        }

        // 创建jSch对象
        JSch jSch = new JSch();
        try {
            // 获取到jSch的session, 根据用户名、主机ip、端口号获取一个Session对象
            session = jSch.getSession(USER_NAME, IP_ADDRESS, DEFAULT_PORT);
            // 设置密码
            session.setPassword(PASS_WORD);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            // session.setTimeout(3000);// 设置超时
            session.connect(); //通过Session建立连接

            // 设置登陆状态
            logined = true;
            log.info("SSH connection success ...");
        } catch (JSchException e) {
            // 设置登陆状态为false
            logined = false;
            log.info("主机登录失败, IP = " + IP_ADDRESS + ", USERNAME = " + USER_NAME + ", Exception:" + e);
        }
    }

    /**
     * @Description: 关闭连接
     */
    public void closeSession() {
        // 调用session的关闭连接的方法
        if (session != null) {
            // 如果session不为空,调用session的关闭连接的方法
            session.disconnect();
        }
        log.info("SSH close success ...");
    }

    /**
     * @Description: 执行相关的命令
     * @param: @param command 具体的命令
     * @param: @throws IOException
     * @return: String ： 返回命令执行后的结果
     */
    public String execCommand(String command) {
        InputStream in = null;// 输入流(读)
        Channel channel = null;// 定义channel变量

        try {
            // 如果命令command不等于null
            if (command != null) {
                // 打开channel
                channel = session.openChannel("exec");  //说明：exec用于执行命令;sftp用于文件处理
                ((ChannelExec) channel).setCommand(command);  // 设置command
                channel.connect();  // channel进行连接
                in = channel.getInputStream();  // 获取到输入流
                String processDataStream = processDataStream(in);  // 执行相关的命令
                log.info("command executed");
                return processDataStream;
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.toString());
                    e.printStackTrace();
                }
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return null;
    }

    /**
     * @Description:内部使用 对将要执行的linux的命令进行遍历, 专门处理执行，以及执行后返回的命令
     * @param: @param in
     * @return: String
     */
    private String processDataStream(InputStream in) {
        StringBuffer sb = new StringBuffer();  //用于显示linux终端输出
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = "";
        try {
            while ((result = br.readLine()) != null) {
                sb.append(result + "\n");
                System.out.println(sb);  //显示终端输出
            }
        } catch (Exception e) {
            log.error("Failed to get BufferedReader: ", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.toString());
                }
            }
        }
        return sb.toString();
    }

    /**
     * @Description: 列出目录下的文件
     * @author: mulming
     * @param: @param directory   要列出的目录
     * @param: @throws JSchException
     * @param: @throws SftpException
     * @return: Vector<LsEntry>
     */
    public Vector<LsEntry> listFiles(String directory) throws JSchException, SftpException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 显示目录信息
        @SuppressWarnings("unchecked")
        Vector<LsEntry> ls = channelSftp.ls(directory);
        // 切断连接
        channelSftp.exit();
        return ls;
    }
}
