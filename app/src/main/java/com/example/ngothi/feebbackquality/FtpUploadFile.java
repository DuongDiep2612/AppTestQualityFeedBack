package com.example.ngothi.feebbackquality;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpUploadFile extends AsyncTask{

    // host, username, password.
    private final String hostname = "files.000webhost.com";                    //"ftp.dlptest.com";  host hay cai ip cua server.
    private final String userName = "duchuynm";                             //"dlpuser@dlptest.com";    tai khoan cua user.
    private final String password = "choivuive";                             //"3D6XZV9MKdhM5fF";    PassWord.

    private String filePath;
    private FTPClient ftpClient = null;

    private boolean login = false;
    boolean storeFile = false;

    public FtpUploadFile(String filePath) {
        this.filePath = filePath;
    }

    public void connect() {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(hostname,21);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("connect",ftpClient.getReplyString());
        try {
            login = ftpClient.login(userName,password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("login",ftpClient.getReplyString());
    }

    private void ftpConfig() {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setDataTimeout(60);
        try {
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory("/public_html");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE,FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean upload() {
        FileInputStream fileInputStream = null;

        try {
            connect();
            ftpConfig();

            File image = new File(filePath);
            fileInputStream = new FileInputStream(image);

            storeFile = ftpClient.storeFile(image.getName(),fileInputStream);

            Log.e("storeFile", String.valueOf(storeFile));

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(login) {
                    ftpClient.logout();
                }
                if(ftpClient != null) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return storeFile;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        boolean success = upload();
        return (boolean)success;
    }
}
