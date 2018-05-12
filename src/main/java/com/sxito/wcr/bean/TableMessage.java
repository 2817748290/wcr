package com.sxito.wcr.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 62450 on 2017/11/7.
 */
public class TableMessage {
    private String search;  //当搜索框启用并进行搜索时返回搜索值
    private String sort;    //前端table表sortName属性定义的分类Id
    private String order;   //升序或倒序
    private int offset;     //分页偏移数（下一页就是查往后偏移offset个后，拿limit个）(就是从第几个开始拿)
    private int limit;      //前端需要的数据数
    private String searchName;  //自定义搜索框前的搜索条件约束，方便后台搜索
    private int total;      //table需要后台返回两个值，total表示总数
    private Object rows;    //rows放返回数据
    private int status;


    private int classId;    //这个是课程班级管理特别需要的，其他人不用管，忽略这个
    private int tags;
    private int nodeId;     //章节ID
    private int courseId;
    private int teacherId;

    public Map result(){
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",rows);
        return map;
    }

    @Override
    public String toString() {
        return "TableMessage{" +
                "search='" + search + '\'' +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", searchName='" + searchName + '\'' +
                ", total=" + total +
                ", rows=" + rows +
                ", status=" + status +
                ", classId=" + classId +
                ", nodeId=" + nodeId +
                ", tags=" + tags +
                ", courseId=" + courseId +
                ", teacherId=" + teacherId +
                '}';
    }

    public int getTags() {
        return tags;
    }

    public void setTags(int tags) {
        this.tags = tags;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getNodeId() { return nodeId; }

    public void setNodeId(int nodeId) { this.nodeId = nodeId; }

}
