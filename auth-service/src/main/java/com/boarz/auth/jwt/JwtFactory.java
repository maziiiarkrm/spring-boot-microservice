package com.boarz.auth.jwt;

public interface JwtFactory {

    String getUsername(String token);

    boolean validate(String token, String secret);

    String generateToken(String username, JwtWrapper wrapper);

    UserRoleModel parseToken(String token);

}
