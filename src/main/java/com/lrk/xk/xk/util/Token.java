package com.lrk.xk.xk.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * @author lrk
 * @date 2019/4/27 上午9:18
 */
public class Token {

    private int id;
    private String name;
    private String role;
    private int err = 0;
    public static final int ExpiredJwtError = 1;
    public static final int SignatureError = 2;

    public Token(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(Auth.key).parseClaimsJws(token).getBody();
            this.id = Integer.parseInt(claims.get("id").toString());
            this.name = claims.get("name").toString();
            this.role = claims.get("role").toString();
        } catch (ExpiredJwtException e) {
            this.err = ExpiredJwtError;
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getErr() {
        return err;
    }

}
