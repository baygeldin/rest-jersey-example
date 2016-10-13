package fi.jyu.task3.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
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
}
