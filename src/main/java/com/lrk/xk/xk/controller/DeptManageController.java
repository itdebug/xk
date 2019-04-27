package com.lrk.xk.xk.controller;

import com.lrk.xk.xk.model.Dept;
import com.lrk.xk.xk.service.DeptService;
import com.lrk.xk.xk.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lrk
 * @date 2019/4/27 下午3:40
 */
@RequestMapping("/Admin/dept")
@Controller
public class DeptManageController {

    @Resource
    private DeptService service;

    @ResponseBody
    @RequestMapping("/getAll")
    public Message getAll() {
        Message msg = new Message(1, "ok");
        msg.setData(service.findAll());
        return msg;
    }

    @ResponseBody
    @RequestMapping("/add")
    public Message add(@RequestParam String name, String description) {
        if (service.isFieldExisted("name", name)) {
            return new Message(-1, "已存在“" + name + "”");
        }
        Dept dept = new Dept();
        dept.setName(name);
        if (description != null) {
            dept.setDescription(description);
        }
        service.save(dept);
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
    public Message upd(@RequestParam int id, @RequestParam String name, String description) {
        Dept dept = service.findById(id);
        if (!dept.getName().equals(name) && service.isFieldExisted("name", name)) {
            return new Message(-1, "已存在“" + name + "”");
        }
        dept.setName(name);
        if (description != null) {
            dept.setDescription(description);
        }
        service.update(dept);
        return new Message(1, "ok");
    }

}
