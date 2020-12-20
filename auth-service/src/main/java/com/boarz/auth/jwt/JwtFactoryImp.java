package com.boarz.auth.jwt;

import com.boarz.auth.exception.TokenNotValidateWithThisSecretPass;
import com.boarz.auth.service.TimeService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;


@Component
public class JwtFactoryImp implements JwtFactory {


    private Clock clock = DefaultClock.INSTANCE;
    @Autowired
    private TimeService timeService;
    @Autowired
    private Environment env;
    @Value("${jwt.server.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;


    @Override
    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public String generateToken(String username, JwtWrapper wrapper) {
        Date createdDate = clock.now();
        Date expirationDate = calculateExpirationDate(createdDate, Integer.parseInt(env.getProperty("jwt.expiration")));
        Map<String, Object> map = new HashMap<>();
        map.put("token", wrapper.getUsersList().get(0));
        return doGenerateToken(username, createdDate, expirationDate, secret, map);
    }

    @Override
    public boolean validate(String token, String secret) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new TokenNotValidateWithThisSecretPass();
        }
        if (claims == null)
            return false;
        return true;
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String doGenerateToken(String subject, Date createdDate, Date expirationDate,
                                   String secret, Map<String, Object> map) {
        JwtBuilder builder = Jwts.builder();
        if (subject != null)
            builder.setSubject(subject);
        if (createdDate != null)
            builder.setIssuedAt(createdDate);
        if (expirationDate != null)
            builder.setExpiration(expirationDate);
        if (map != null)
            builder.setClaims(map);
        return builder.setClaims(map).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    @Override
    public UserRoleModel parseToken(String token) {
        if (!this.validate(token, env.getProperty("jwt.server.secret")))
            throw new TokenNotValidateWithThisSecretPass();
        String username = ((LinkedHashMap) Jwts.parser()
                .setSigningKey(env.getProperty("jwt.server.secret"))
                .parseClaimsJws(token)
                .getBody().get("token")).get("username").toString();
        List roleDetail = (List) ((LinkedHashMap) Jwts.parser()
                .setSigningKey(env.getProperty("jwt.server.secret"))
                .parseClaimsJws(token)
                .getBody().get("token")).get("roles");
        String role = ((LinkedHashMap) roleDetail.get(0)).get("name").toString();
        return new UserRoleModel(username, role);
    }

    private Date calculateExpirationDate(Date createdDate, int expiration) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
