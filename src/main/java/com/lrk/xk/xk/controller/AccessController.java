package com.lrk.xk.xk.controller;

import com.lrk.xk.xk.model.Student;
import com.lrk.xk.xk.model.Teacher;
import com.lrk.xk.xk.service.StudentService;
import com.lrk.xk.xk.service.TeacherService;
import com.lrk.xk.xk.util.Auth;
import com.lrk.xk.xk.util.MD5Encoder;
import com.lrk.xk.xk.util.Message;
import com.lrk.xk.xk.util.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lrk
 * @date 2019/4/27 下午3:52
 */
@RequestMapping("/Access")
@Controller
public class AccessController {

    @Resource
    private StudentService sService;
    @Resource
    private TeacherService tService;

    @ResponseBody
    @RequestMapping("signin")
    public Message signin(@RequestParam String name, @RequestParam String password,
        @RequestParam String role) {
        Message message = new Message(1, "ok");
        Map<String, Object> claims = new HashMap<String, Object>();
        if ("student".equals(role)) {
            //学生账号
            Student student = sService.login(name, password);
            if (student == null) {
                return new Message(-1, "用户名或密码错误");
            }
            claims.put("id", student.getId());
            claims.put("name", student.getName());
            claims.put("role", "student");
        } else if ("teacher".equals(role)) {
            //教师账号
            Teacher teacher = tService.login(name, password);
            if (teacher == null) {
                return new Message(-1, "用户名或密码错误");
            }
            claims.put("id", teacher.getId());
            claims.put("name", teacher.getName());
            claims.put("role", "teacher");
        } else if ("admin".equals(role)) {
            //管理员账号
            Teacher teacher = tService.login(name, password);
            if (teacher == null) {
                return new Message(-1, "用户名或密码错误");
            }
            if (teacher.getIsAdmin() == 0) {
                return new Message(-2, "非管理员账号");
            }
            claims.put("id", teacher.getId());
            claims.put("name", teacher.getName());
            claims.put("role", "admin");
        }
        String s = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Auth.key)
            .setExpiration(new Date(System.currentTimeMillis() + Auth.expire)).compact();
        claims.put("token", s);
        claims.remove("exp");
        message.setData(claims);
        return message;
    }

    @ResponseBody
    @RequestMapping("userInfo")
    public Message userInfo(@RequestHeader("Authorization") String token) {
        Token t = new Token(token);
        int id = t.getId();
        String role = t.getRole();
        if ("student".equals(role)) {
            return new Message(1, "ok", sService.findById(id));
        } else if (role.equals("teacher") || role.equals("admin")) {
            return new Message(1, "ok", tService.findById(id));
        } else {
            return new Message(-1, "用户身份不符");
        }
    }

    @ResponseBody
    @RequestMapping("changePwd")
    public Message changePwd(@RequestParam String oldPwd, @RequestParam String newPwd,
        @RequestHeader("Authorization") String token) {
        Token t = new Token(token);
        int id = t.getId();
        String role = t.getRole();
        if ("student".equals(role)) {
            Student student = sService.findById(id);
            if (!student.getPassword().equals(MD5Encoder.encode(oldPwd))) {
                return new Message(-2, "原密码错误");
            }
            student.setPassword(MD5Encoder.encode(newPwd));
            sService.update(student);
        } else if ("teacher".equals(role) || "admin".equals(role)) {
            Teacher teacher = tService.findById(id);
            if (!teacher.getPassword().equals(MD5Encoder.encode(oldPwd))) {
                return new Message(-2, "原密码错误");
            }
            teacher.setPassword(MD5Encoder.encode(newPwd));
            tService.update(teacher);
        }
        return new Message(1, "ok");
    }

}
