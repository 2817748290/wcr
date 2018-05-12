package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.*;
import com.sxito.wcr.mapper.ArrangeDataMapper;
import com.sxito.wcr.mapper.CourseArrangeMapper;
import com.sxito.wcr.mapper.CourseClassMapper;
import com.sxito.wcr.mapper.CourseInfoMapper;
import com.sxito.wcr.service.CourseArrangeService;
import com.sxito.wcr.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CourseArrangeServiceImpl implements CourseArrangeService {

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private CourseArrangeMapper courseArrangeMapper;

    @Autowired
    private CourseClassMapper courseClassMapper;

    @Autowired
    private CourseInfoService courseInfoService;

    @Autowired
    private ArrangeDataMapper arrangeDataMapper;

    @Override
    public CourseArrange checkPermission(int id) throws RuntimeException {
        CourseArrange courseArrange = null;
        try {
            courseArrange = courseArrangeMapper.getCourseArrangeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("未找到课程安排");
        }
        if(courseArrange != null){
            CourseInfo courseInfo = courseInfoService.checkPermission(courseArrange.getCourseId());
            return courseArrange;
        }else{
            throw new RuntimeException("未找到课程安排");
}
    }

    @Override
    @Transactional
    public TableMessage getInitCourse(TableMessage tableMessage){

        try {
            UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
            int teacherId = userInfo.getId();
            tableMessage.setTeacherId(teacherId);
            Map paramMap = new HashMap();
            paramMap.put("teacherId", tableMessage.getTeacherId());
            paramMap.put("courseId", tableMessage.getCourseId());
            CourseInfo courseInfo = null;
            tableMessage.setCourseId(tableMessage.getCourseId());
            courseInfo = courseInfoMapper.getInitCourseInfo(paramMap);

            List<CourseArrange> courseArrangeList = null;

            courseArrangeList = courseArrangeMapper.findPage(tableMessage);

            for (CourseArrange courseArrange : courseArrangeList) {
                CourseClass courseClass = null;
                courseClass = courseClassMapper.getDefaultClassByClassId(courseArrange.getClassId());
                courseArrange.setCourseInfo(courseInfo);
                courseArrange.setCourseClass(courseClass);
            }
            if (tableMessage.getSearch()!=null){
                if (tableMessage.getSearch().equals("")){
                    tableMessage.setRows(courseArrangeMapper.findPage(tableMessage));
                }else{
                    tableMessage.setSearch("%"+tableMessage.getSearch()+"%");
                    List<CourseArrange> searchList = courseArrangeMapper.searchCourseArrange(tableMessage);
                    for (CourseArrange courseArrange: searchList) {
                        CourseClass courseClass = courseClassMapper.getDefaultClassByClassId(courseArrange.getClassId());
                        courseArrange.setCourseInfo(courseInfo);
                        courseArrange.setCourseClass(courseClass);
                    }
                    tableMessage.setRows(searchList);
                    tableMessage.setTotal(courseArrangeMapper.searchCount(tableMessage));
                }
            }else {


                tableMessage.setRows(courseArrangeList);
                tableMessage.setTotal(courseArrangeMapper.count(tableMessage));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableMessage;
    }

    @Override
    public Integer updateCourseArrangeById(CourseArrange courseArrange) {

        CourseArrange courseArrange1;
        int state = 0;
        try {
            int classId = courseArrange.getCourseClass().getClassId();
            courseArrange.setClassId(classId);
            state = courseArrangeMapper.updateCourseArrangeById(courseArrange);
            courseArrange1 = courseArrangeMapper.getCourseArrangeById(courseArrange.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return state;
        }
        return state;
    }

    @Override
    public Integer updateStateCourseArrange(int id) {

        int state = 0;

        try {
            state = courseArrangeMapper.updateStateCourseArrange(id);
        } catch (Exception e) {
            e.printStackTrace();
            return state;
        }
        return state;
    }

    @Override
    public Integer deleteCourseArrangeById(int id) {

        int state = 0;

        try {
            state = courseArrangeMapper.deleteCourseArrangeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return state;
        }
        return state;
    }

    @Override
    public CourseArrange getCourseArrangById(int id) {
        CourseArrange courseArrange = new CourseArrange();

        try {
            courseArrange = courseArrangeMapper.getCourseArrangeById(id);
            CourseClass courseClass = courseClassMapper.getCourseClassByClassId(courseArrange.getClassId());
            CourseInfo courseInfo = courseInfoMapper.getCourseByCourseId(courseArrange.getCourseId());
            courseArrange.setCourseInfo(courseInfo);
            courseArrange.setCourseClass(courseClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return courseArrange;
    }

    @Override
    @Transactional
    public void addCourseArrange(CourseArrange courseArrange) throws RuntimeException{
        try {
//            CourseClass courseClass  = courseClassMapper.getCourseClassByClassId(courseArrange.getClassId());
//            CourseInfo courseInfo = courseInfoMapper.getCourseByCourseId(courseArrange.getCourseId());
//            courseArrange.setCourseClass(courseClass);
//            courseArrange.setCourseInfo(courseInfo);
              int id=arrangeDataMapper.insert(courseArrange.getArrangeData());
              courseArrange.setArrangeDataId(id);
              courseArrangeMapper.addCourseArrange(courseArrange);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    @Transactional
    public List<ArrangeData> getArrangeData(int id) throws RuntimeException{
        try {
            List<ArrangeData> arrangeDataList=arrangeDataMapper.getArrangeData(id);
            return arrangeDataList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库操作失败！");
        }
    }

}
