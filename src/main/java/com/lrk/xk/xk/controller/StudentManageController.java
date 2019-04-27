package com.lrk.xk.xk.controller;

import com.lrk.xk.xk.model.Student;
import com.lrk.xk.xk.service.StudentService;
import com.lrk.xk.xk.util.MD5Encoder;
import com.lrk.xk.xk.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lrk
 * @date 2019/4/27 下午3:42
 */
@RequestMapping("/Admin/student")
@Controller
public class StudentManageController {

    @Resource
    private StudentService service;

    @ResponseBody
    @RequestMapping("/getAll")
    public Message getAll() {
        return new Message(1, "ok", service.findAll());
    }

    @ResponseBody
    @RequestMapping("/add")
    public Message add(@RequestParam String sno, String password, @RequestParam String name,
        @RequestParam byte gender, @RequestParam int classId, String notes) {
        if (service.isFieldExisted("sno", sno)) {
            return new Message(-1, "已存在学号“" + sno + "”");
        }
        Student student = new Student();
        student.setSno(sno);
        student.setPassword(MD5Encoder.encode(password == null ? "111111" : password));
        student.setName(name);
        student.setGender(gender);
        student.setClazzId(classId);
        student.setNotes(notes);
        service.save(student);
        return new Message(1, "ok");
    }

    @ResponseBody
    @RequestMapping("/del")
    public Message del(@RequestParam("ids[]") Integer[] ids) {
        service.deleteCascade(ids);
        return new Message(1, "ok");
    }

    @ResponseBody
    @RequestMapping("/upd")
    public Message upd(@RequestParam int id, String sno, String password, String name, Byte gender,
        Integer classId, String notes) {
        Student student = service.findById(id);
        if (!student.getSno().equals(sno) && service.isFieldExisted("sno", sno)) {
            return new Message(-1, "已存在工号“" + sno + "”");
        }
        if (sno != null) {
            student.setSno(sno);
        }
        if (password != null) {
            student.setPassword(MD5Encoder.encode(password));
        }
        if (name != null) {
            student.setName(name);
        }
        if (gender != null) {
            student.setGender(gender);
        }
        if (classId != null) {
            student.setClazzId(classId);
        }
        if (notes != null) {
            student.setNotes(notes);
        }
        service.update(student);
        return new Message(1, "ok");
    }

}
