package com.sxito.wcr.bean;

import java.util.Date;
import java.util.List;

public class UserInfo {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String salt;
    private int locked;
    private String headImgUrl;
    private int groupId;
    private int defaultClassId;

    private Date gmtCreate;
    private Date gmtModified;

    private UserGroup group;
    private List<String> roles;
    private CourseClass defaultClass;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", salt='" + salt + '\'' +
                ", locked=" + locked +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", groupId=" + groupId +
                ", defaultClassId=" + defaultClassId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", group=" + group +
                ", roles=" + roles +
                ", defaultClass=" + defaultClass +
                '}';
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDefaultClassId() {
        return defaultClassId;
    }

    public void setDefaultClassId(int defaultClassId) {
        this.defaultClassId = defaultClassId;
    }

    public CourseClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(CourseClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }
}
