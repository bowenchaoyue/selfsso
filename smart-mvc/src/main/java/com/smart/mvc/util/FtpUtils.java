package com.smart.mvc.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FtpUtils {
	
	private String url;
	
	private int port;
	
	private String username;
	
	private String password;

    public FtpUtils() {
    }

    /**
     * Description: 从FTP服务器下载文件
     * 
     * @Version1.0
     * @param url
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     */
    public boolean downFile(String remotePath, String fileName,
            String localPath) {
    	FTPClient ftpClient = new FTPClient();
        boolean result = false;
        try {
            int reply;
            //ftpClient.setControlEncoding(encoding);
             
            /*
             *  为了上传和下载中文文件，有些地方建议使用以下两句代替
             *  new String(remotePath.getBytes(encoding),"iso-8859-1")转码。
             *  经过测试，通不过。
             */
//            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
//            conf.setServerLanguageCode("zh");
 
            ftpClient.connect(url, port);
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(username, password);// 登录
            // 设置文件传输类型为二进制
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 获取ftp登录应答代码
            reply = ftpClient.getReplyCode();
            // 验证是否登陆成功
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                return result;
            }
            // 转移到FTP服务器目录至指定的目录下
            ftpClient.changeWorkingDirectory(remotePath);
            // 获取文件列表
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
 
            ftpClient.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    } 
    
	/** 
     * 递归遍历出目录下面所有文件 
     * @param pathName 需要遍历的目录，必须以"/"开始和结束 
     * @throws IOException 
     */  
    public List<FTPFile> getFileList(String pathName) throws IOException{
    	List<FTPFile> arFiles = new ArrayList<FTPFile>();
    	FTPClient ftp = new FTPClient();
        ftp.connect(url, port);//连接FTP服务器   
        //ftp.connect(url);//连接FTP服务器 
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器   
        ftp.login(username, password);//登录 
        if(pathName.startsWith("/")&&pathName.endsWith("/")){
            //更换目录到当前目录 
            ftp.changeWorkingDirectory(pathName);  
            FTPFile[] files = ftp.listFiles();  
            for(FTPFile file:files){
                if(file.isFile()){
                    arFiles.add(file);  
                }else if(file.isDirectory()){
                     
                }
            }
            System.out.println(arFiles.toString());
        }
        return arFiles;
    }
	
	
	/** 
     * Description: 向FTP服务器上传文件 
     * @param url FTP服务器 hostname 
     * @param port FTP服务器端口 默认端 21 
     * @param username FTP登录账号 
     * @param password FTP登录密码 
     * @param path FTP服务器保存目录 
     * @param filename 上传到FTP服务器上的文件名 
     * @param filePath 需要上传的文件路径
     * @return 成功返回true，否则返回false 
     */
    public boolean uploadFile(String path, String filename, String filePath) {   
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
        	//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器   
            ftp.connect(url, port);//连接FTP服务器   
            //ftp.connect(url);//连接FTP服务器   
            //登录 
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {  
                ftp.disconnect();
                return success;
            }
            File file = new File(filePath); 
            InputStream input = new FileInputStream(file); 
            ftp.changeWorkingDirectory(path);   
            ftp.storeFile(filename, input);        
               
            input.close();
            ftp.logout();   
            success = true;   
        } catch (IOException e) {
            e.printStackTrace();   
        } finally {
            if (ftp.isConnected()) {   
                try {   
                    ftp.disconnect();   
                } catch (IOException ioe) {   
                }   
            }   
        }   
        return success;    
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
