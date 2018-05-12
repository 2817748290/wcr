<template>
<el-container>
  <el-aside width="300px" style="height: 100vh;background-color:#F5F5F5;overflow-x:hidden;overflow-y:auto;">
    <div style="width:300px;height:210px;text-align: center;margin-top:50px">
      <img :src="user.headImgUrl" width="150px" height="150px" style="border-radius:75px;overflow:hidden;background-color:#F5F5F5"/>
      <h3 style="color:#000">{{user.nickname}}</h3>
    </div>
    <el-menu
      router :default-active="'IndexManage'"
      class="el-menu-vertical-demo"
      :background-color="myColor"
      text-color="#fff">
      <el-menu-item index="IndexManage">
        <i class="el-icon-menu"></i>
        <span slot="title">公告管理</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-plus"></i>
          <span slot="title">用户管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="StudentManage">学生管理</el-menu-item>
          <el-menu-item index="TeacherManage">教师管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-star-on"></i>
          <span slot="title">行政班级管理</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="CollegeManage">学院管理</el-menu-item>
          <el-menu-item index="MajorManage">专业管理</el-menu-item>
          <el-menu-item index="DefaultClassManage">班级管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-tickets"></i>
          <span slot="title">课程班级管理</span>
        </template>
        <el-menu-item-group >
          <el-menu-item index="CourseManage">教师课程管理</el-menu-item>
          <el-menu-item index="CourseClassManage">课程班级管理</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </el-aside>
  <el-container>
    <el-header style="background-color:#545c64;color:#FFF;padding:10px;padding-left:25px;" :style="{backgroundColor:myColor}">
      <img src="../assets/img/logo.png" height="100%;" style="float:left;"/>
      <el-color-picker v-model="myColor" style="float:right"></el-color-picker>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
    <el-footer style="background-color:#545c64;color:#FFF" :style="{backgroundColor:myColor}">
        <strong>Copyright © 2017 <a href="http://www.lsu.edu.cn" style="color:#FFF">丽水学院</a>-尚轩ITO</strong>-FourZeroEight小分队
    </el-footer>
  </el-container>
</el-container>

</template>

<script>
export default {
  name: "HelloWorld",
  data() {
    return {
      user:null,
      myColor:"#545c64"
    };
  },
  created(){
    window.userinfo = JSON.parse(window.sessionStorage.getItem("user"));
    this.user =  JSON.parse(window.sessionStorage.getItem("user"));
    console.log(this.user);

  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
</style>
