package com.lrk.xk.xk.util;


import com.lrk.xk.xk.model.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lrk
 * @date 2019/4/27 上午10:11
 */
public class TeacherCommon {

    public static List<Map<String, Object>> teacherList(List<Teacher> teachers) {
        List<Map<String, Object>> teacherList = new ArrayList<Map<String, Object>>();
        for (Teacher t : teachers) {
            Map<String, Object> teacherMap = new HashMap<String, Object>();
            teacherMap.put("id", t.getId());
            teacherMap.put("name", t.getName());
            teacherList.add(teacherMap);
        }
        return teacherList;
    }

}
