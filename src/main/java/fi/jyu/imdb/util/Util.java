package fi.jyu.imdb.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class Util {
    public static String getSecret() { return "qwerty"; }

    public static String base64Encode(String content) {
        return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64Encode(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    public static String base64Decode(String content) {
        return new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8);
    }

    public static byte[] encodeHS256(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return sha256_HMAC.doFinal(data.getBytes("UTF-8"));
    }

    public static String getRequestAsString(HttpServletRequest request) {
        StringBuffer jb = new StringBuffer();

        try {
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jb.toString();
    }

    public static byte[] encodeMD5(String content) {
        byte[] bytes = new byte[0];
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
            bytes = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] hash = md.digest(bytes);
        return hash;
    }
}
