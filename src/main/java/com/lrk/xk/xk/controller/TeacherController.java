package com.lrk.xk.xk.controller;

import com.lrk.xk.xk.service.*;
import com.lrk.xk.xk.util.Message;
import com.lrk.xk.xk.util.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lrk
 * @date 2019/4/27 下午3:43
 */
@RequestMapping("/Teacher")
@Controller
public class TeacherController {

    @Resource
    private CourseService courseService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherCourseService tcService;
    @Resource
    private ClassService classService;
    @Resource
    private CourseStudentService csService;

    @ResponseBody
    @RequestMapping("/course/getAll")
    public Message getAllCourse() {
        return new Message(1, "ok", courseService.getAvailableCourses());
    }

    @ResponseBody
    @RequestMapping("/course/selectCourses")
    public Message selectCourses(@RequestParam("ids[]") Integer[] ids,
        @RequestHeader("Authorization") String token) {
        int userID = new Token(token).getId();
        return tcService.selectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/unselectCourses")
    public Message unselectCourses(@RequestParam("ids[]") Integer[] ids,
        @RequestHeader("Authorization") String token) {
        int userID = new Token(token).getId();
        return tcService.unselectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/selectedCourses")
    public Message selectCourses(@RequestHeader("Authorization") String token) {
        int id = new Token(token).getId();
        return new Message(1, "ok", courseService.findCoursesByTid(id));
    }

    @ResponseBody
    @RequestMapping("/grade/findStudentsByCid")
    public Message findStudentsByCid(@RequestParam int id) {
        return new Message(1, "ok", studentService.findStudentsByCid(id));
    }

    @ResponseBody
    @RequestMapping("/class/getAll")
    public Message getAll() {
        return new Message(1, "ok", classService.findAll());
    }

    @ResponseBody
    @RequestMapping("/class/getGradeByCid")
    public Message getGradeByCid(@RequestParam int id) {
        return new Message(1, "ok", csService.getGradeByCid(id));
    }

    @ResponseBody
    @RequestMapping("/grade/recordGrade")
    public Message recordGrade(@RequestParam int cid, @RequestParam int sid,
        @RequestParam double grade, @RequestHeader("Authorization") String token) {
        return csService.recordGrade(new Token(token).getId(), cid, sid, grade);
    }

}
