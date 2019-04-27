package com.lrk.xk.xk.service;

import com.lrk.xk.xk.dao.DaoSupportImpl;
import com.lrk.xk.xk.model.Course;
import com.lrk.xk.xk.model.Teacher;
import com.lrk.xk.xk.model.TeacherCourse;
import com.lrk.xk.xk.util.TeacherCommon;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lrk
 * @date 2019/4/27 上午9:17
 */
@Component
public class CourseService extends DaoSupportImpl<Course> {

    public List getAvailableCourses() {
        Query query = getSession()
            .createQuery("FROM Course WHERE id not in (SELECT distinct cid FROM TeacherCourse)");
        List list = query.list();

        return list;
    }

    public List findCoursesByTid(int id) {
        Query query = getSession().createQuery(
            "FROM Course WHERE id in (SELECT cid FROM TeacherCourse WHERE tid = (:tid))");
        List list = query.setParameter("tid", id).list();

        return list;
    }

    public Map<String, List> findCoursesBySid(int id) {
        Map<String, List> data = new HashMap<String, List>();
        Query query = getSession().createQuery(
            "FROM Course WHERE id in (SELECT cid FROM CourseStudent WHERE sid = (:sid))");
        List<Course> courses = query.setParameter("sid", id).list();
        data.put("courses", courses);
        Integer[] cids = new Integer[courses.size()];
        for (int i = 0; i < courses.size(); i++)
            cids[i] = courses.get(i).getId();
        List<Teacher> teachers;
        List<TeacherCourse> tc;
        if (cids.length == 0) {
            teachers = new ArrayList<Teacher>();
            tc = new ArrayList<TeacherCourse>();
        } else {
            teachers = getSession().createQuery(
                "FROM Teacher WHERE id in (SELECT DISTINCT tid FROM TeacherCourse WHERE cid in (:cid))")
                .setParameterList("cid", cids).list();
            tc = getSession().createQuery("FROM TeacherCourse WHERE cid in (:cid)")
                .setParameterList("cid", cids).list();
        }

        data.put("teachers", TeacherCommon.teacherList(teachers));
        data.put("tc", tc);
        return data;
    }

}
