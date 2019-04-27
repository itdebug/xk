package com.lrk.xk.xk.controller;

import com.lrk.xk.xk.service.CourseService;
import com.lrk.xk.xk.service.CourseStudentService;
import com.lrk.xk.xk.service.TeacherCourseService;
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
 * @date 2019/4/27 下午3:41
 */
@RequestMapping("/Student")
@Controller
public class StudentController {

    @Resource
    private CourseService courseService;
    @Resource
    private TeacherCourseService tcService;
    @Resource
    private CourseStudentService csService;

    @ResponseBody
    @RequestMapping("/course/getAll")
    public Message getAllCourse() {
        return new Message(1, "ok", tcService.getCoursesWithTeacher());
    }

    @ResponseBody
    @RequestMapping("/course/selectCourses")
    public Message selectCourses(@RequestParam("ids[]") Integer[] ids,
        @RequestHeader("Authorization") String token) {
        int userID = new Token(token).getId();
        return csService.selectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/unselectCourses")
    public Message unselectCourses(@RequestParam("ids[]") Integer[] ids,
        @RequestHeader("Authorization") String token) {
        int userID = new Token(token).getId();
        return csService.unselectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/selectedCourses")
    public Message selectCourses(@RequestHeader("Authorization") String token) {
        int id = new Token(token).getId();
        return new Message(1, "ok", courseService.findCoursesBySid(id));
    }

    @ResponseBody
    @RequestMapping("/grade/queryGrade")
    public Message queryGrade(@RequestHeader("Authorization") String token) {
        int id = new Token(token).getId();
        return new Message(1, "ok", csService.queryGrade(id));
    }


}
