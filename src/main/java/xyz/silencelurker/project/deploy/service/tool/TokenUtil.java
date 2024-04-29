package xyz.silencelurker.project.deploy.service.tool;

import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

/**
 * @author Silence_Lurker
 */
public class TokenUtil {

    public static final String LAST_VERSION_SECRET_KEY = "";

    public static final String SECRET_KEY = "Token Version 0.0.1-SNAPSHOT";

    public static final Long ONE_DAY_MILLIS_SEC = 24 * 60 * 60 * 1000L;

    public static String encodeToken(Map<String, String> data) {
        var build = JWT.create();

        for (var item : data.entrySet()) {
            build.withClaim(item.getKey(), item.getValue());
        }

        return build.sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public static Map<String, String> decodeToken(String token) {
        var parse = JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token);

        var data = parse.getClaims();
        var returnValue = new HashMap<String, String>(data.size());

        for (var item : data.entrySet()) {
            returnValue.put(item.getKey(), item.getValue().asString());
        }

        return returnValue;
    }
}
