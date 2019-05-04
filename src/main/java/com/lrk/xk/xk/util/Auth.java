package com.lrk.xk.xk.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lrk
 * @date 2019/4/27 上午10:00
 */
public class Auth {

    public static final Key key = MacProvider.generateKey();
    //2 hours
    public static final long expire = 7200000;

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", 1);
        claims.put("name", "zeven");
        claims.put("role", "admin");
        String s =
            Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, key).compact();
        System.out.println(s);
        Claims result;
        result = Jwts.parser().setSigningKey(key).parseClaimsJws(s).getBody();
        System.out.println(result.get("id") + "|" + result.get("name") + "|" + result.get("role"));
    }
}
