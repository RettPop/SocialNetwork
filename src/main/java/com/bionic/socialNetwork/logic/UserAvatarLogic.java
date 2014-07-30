package com.bionic.socialNetwork.logic;

import com.bionic.socialNetwork.dao.UserDao;
import com.bionic.socialNetwork.dao.impl.UserDaoImpl;
import com.bionic.socialNetwork.models.User;

import java.io.*;

/**
 * @version 1.0 on 30.07.2014.
 * @autor Bish_UA
 */
public class UserAvatarLogic {

    final String AVATAR_DIR = "\\avatar\\";

    public void saveAvatar(InputStream uploadedInputStream,
                           String uploadedFileLocation,
                           String uploadedFileName,
                           long userId) {

        String filePath = uploadedFileLocation + AVATAR_DIR + userId + uploadedFileName;
        System.out.println(uploadedFileLocation + AVATAR_DIR + userId + uploadedFileName);
        try {
            OutputStream out = new FileOutputStream(new File(
                    filePath));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(filePath));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        UserDao userDao = new UserDaoImpl();
        User user = null;

        try {
            user = userDao.selectById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        user.setPathToAvatar(AVATAR_DIR + userId + uploadedFileName);

        try {
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public File getAvatar(String realPath, long userId) {

        UserDao userDao = new UserDaoImpl();
        User user = null;

        try {
            user = userDao.selectById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(realPath + user.getPathToAvatar());

        return file;
    }

}
