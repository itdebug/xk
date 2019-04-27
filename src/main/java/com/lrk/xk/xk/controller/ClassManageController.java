package com.lrk.xk.xk.controller;

import com.lrk.xk.xk.model.Clazz;
import com.lrk.xk.xk.service.ClassService;
import com.lrk.xk.xk.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lrk
 * @date 2019/4/27 下午3:37
 */
@RequestMapping("/Admin/class")
@Controller
public class ClassManageController {

    @Resource
    private ClassService service;

    @ResponseBody
    @RequestMapping("/getAll")
    public Message getAll() {
        return new Message(1, "ok", service.findAll());
    }

    @ResponseBody
    @RequestMapping("/add")
    public Message add(@RequestParam String name, @RequestParam int deptId) {
        if (service.isFieldExisted("name", name)) {
            return new Message(-1, "已存在“" + name + "”");
        }
        Clazz clazz = new Clazz();
        clazz.setName(name);
        clazz.setDeptId(deptId);
        service.save(clazz);
        return new Message(1, "ok");
    }

    @ResponseBody
    @RequestMapping("/del")
    public Message del(@RequestParam("ids[]") Integer[] ids) {
        service.deleteByIds(ids);
        return new Message(1, "ok");
    }

    @ResponseBody
    @RequestMapping("/upd")
    public Message upd(@RequestParam int id, @RequestParam String name, @RequestParam int deptId) {
        Clazz clazz = service.findById(id);
        if (!clazz.getName().equals(name) && service.isFieldExisted("name", name)) {
            return new Message(-1, "已存在“" + name + "”");
        }
        clazz.setName(name);
        clazz.setDeptId(deptId);
        service.update(clazz);
        return new Message(1, "ok");
    }

}
