package com.lrk.xk.xk.interceptor;


import com.lrk.xk.xk.util.Token;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OAuth权限认证
 *
 * @author lrk
 * @date 2019/4/27 下午3:32
 */
@Component
public class AccessInterceptor extends CrossDomainInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String path = request.getContextPath();
        String uri = request.getRequestURI();
        if (!uri.startsWith(path + "/Access")) {
            String auth = request.getHeader("Authorization");
            if (auth == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication Failed: Require Authorization");
                return false;
            }
            Token token = new Token(auth);
            if (token.getErr() == Token.ExpiredJwtError) {
                response
                    .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: 认证过期");
                return false;
            }
            if (token.getErr() == Token.SignatureError) {
                response
                    .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: 非法认证");
                return false;
            }
            boolean isAdmin = !token.getRole().equals("admin") && uri.startsWith(path + "/Admin");
            boolean isTeacher =
                !token.getRole().equals("teacher") && uri.startsWith(path + "/Teacher");
            boolean isStudent =
                !token.getRole().equals("student") && uri.startsWith(path + "/Student");
            if (isAdmin || isTeacher || isStudent) {
                response
                    .sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: 无权访问");
                return false;
            }
        }
        return true;
    }
}
