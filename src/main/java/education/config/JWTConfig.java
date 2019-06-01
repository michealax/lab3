package education.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public class JWTConfig {
    public String createToken(String username){
        try {
            Algorithm algorithm = Algorithm.HMAC256(username);

            Map<String, Object> map = new HashMap<>();
            map.put("alg", "HS256");
            map.put("typ","JWT");
            Date now = new Date();
            Date expire = getAfterDate(now, 0,0,0,2,0,0);
            String token = JWT.create()
                    .withIssuer(username)
                    .withIssuedAt(now)
                    .withExpiresAt(expire)
                    .sign(algorithm);

            return token;
        }catch (JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    public String createTokenWithClaim(){
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Map<String, Object> map = new HashMap<>();
            Date now = new Date();
            Date expire = getAfterDate(now,0,0,0,2,0,30);
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String token = JWT.create()
                    .withHeader(map)
                    .withClaim("LoginName","Anna")
                    .withIssuer("SERVICE")
                    .withSubject("this is test token")
                    .withAudience("APP")
                    .withIssuedAt(now)
                    .withExpiresAt(expire)
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    private Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date != null){
            date = new Date();
        }

        Calendar calendar = new GregorianCalendar();

        calendar.setTime(date);

        if (year > 0){
            calendar.add(Calendar.YEAR, year);
        }

        if (month > 0){
            calendar.add(Calendar.MONTH, month);
        }

        if (day > 0){
            calendar.add(Calendar.DATE, day);
        }

        if (hour > 0){
            calendar.add(Calendar.HOUR_OF_DAY, hour);
        }

        if (minute > 0){
            calendar.add(Calendar.MINUTE, minute);
        }

        if (second > 0){
            calendar.add(Calendar.SECOND, second);
        }

        return calendar.getTime();
    }

    public boolean verifyToken(String token, String username){
        try {
            Algorithm algorithm = Algorithm.HMAC256(username);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(username)

                    .build();

            DecodedJWT jwt = verifier.verify(token);
//            String subject = jwt.getSubject();
//            Map<String, Claim> claimMap = jwt.getClaims();
//            Claim claim = claimMap.get("LoginName");
//            List<String> audience = jwt.getAudience();
            return true;
        }catch (JWTVerificationException e){
           return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JWTConfig demo = new JWTConfig();
        String token = demo.createTokenWithClaim();
        System.out.println(token);
        System.out.println( demo.verifyToken(token,"username"));
    }
}
