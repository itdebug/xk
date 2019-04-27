package com.lrk.xk.xk.controller;

import com.lrk.xk.xk.model.Teacher;
import com.lrk.xk.xk.service.TeacherService;
import com.lrk.xk.xk.util.MD5Encoder;
import com.lrk.xk.xk.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lrk
 * @date 2019/4/27 下午3:44
 */
@RequestMapping("/Admin/teacher")
@Controller
public class TeacherManageController {

    @Resource
    private TeacherService service;

    @ResponseBody
    @RequestMapping("/getAll")
    public Message getAll() {
        return new Message(1, "ok", service.findAllWithoutAdmin());
    }

    @ResponseBody
    @RequestMapping("/add")
    public Message add(@RequestParam String tno, @RequestParam String password,
        @RequestParam String name, @RequestParam byte gender, @RequestParam int deptId,
        String notes) {
        if (service.isFieldExisted("tno", tno)) {
            return new Message(-1, "已存在工号“" + tno + "”");
        }
        Teacher teacher = new Teacher();
        teacher.setTno(tno);
        teacher.setPassword(MD5Encoder.encode(password));
        teacher.setName(name);
        teacher.setGender(gender);
        teacher.setDeptId(deptId);
        teacher.setNotes(notes);
        teacher.setIsAdmin((byte) 0);
        service.save(teacher);
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
    public Message upd(@RequestParam int id, String tno, String password, String name, Byte gender,
        Integer deptId, String notes) {
        Teacher teacher = service.findById(id);
        if (!teacher.getTno().equals(tno) && service.isFieldExisted("tno", tno)) {
            return new Message(-1, "已存在工号“" + tno + "”");
        }
        if (tno != null) {
            teacher.setTno(tno);
        }
        if (password != null) {
            teacher.setPassword(MD5Encoder.encode(password));
        }
        if (name != null) {
            teacher.setName(name);
        }
        if (gender != null) {
            teacher.setGender(gender);
        }
        if (deptId != null) {
            teacher.setDeptId(deptId);
        }
        if (notes != null) {
            teacher.setNotes(notes);
        }
        service.update(teacher);
        return new Message(1, "ok");
    }

}
