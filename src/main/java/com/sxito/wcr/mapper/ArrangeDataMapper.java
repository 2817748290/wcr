package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.ArrangeData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 62450 on 2017/12/9.
 */
@Mapper
public interface ArrangeDataMapper {
    int insert(ArrangeData arrangeData)throws Exception;

    List<ArrangeData> getArrangeData(int id);
}
