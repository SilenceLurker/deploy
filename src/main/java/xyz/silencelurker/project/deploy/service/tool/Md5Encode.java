package xyz.silencelurker.project.deploy.service.tool;

import java.security.MessageDigest;

/**
 * MD5加密
 * 
 * @author Silence_Lurker
 */
public class Md5Encode {
    public static String md5Encode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder builder = new StringBuilder();

            for (byte b : messageDigest) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
