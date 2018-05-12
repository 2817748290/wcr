package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.StudentClass;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentClassMapper {
    /**
     * 插入到学生与班级的中间表
     * @param studentClass classId studentId
     * @return 影响的行数
     * @throws Exception
     */
    int insert(StudentClass studentClass) throws Exception;

    /**
     * 查询学生是否已存在
     * @param studentClass
     * @return
     * @throws Exception
     */
    int selectByClassIdAndStudentId(StudentClass studentClass) throws Exception;

    /**
     * 删除该课程班级下的学生
     * @param studentClass
     */
    void deleteClassStudent(StudentClass studentClass) throws Exception;
}
