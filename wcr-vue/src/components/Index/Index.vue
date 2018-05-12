<template>
  <div class="hello container-fluid" style="padding:0">

    <nav class="navbar navbar-default navbar-static-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" style="color:#FFF" href="#">智慧教室</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" style="color:#FFF"  data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">{{ userInfo.nickname }}<span class="caret"></span></a>
              <ul class="dropdown-menu" style="min-width:100%;text-align:center">
                <li>
                  <a href="#">个人资料</a>
                  <a href="#" data-toggle="modal" data-target="#myModal" >头像上传</a>
                </li>
                <li style="width:100%;">
                  <a href="#">退出</a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- 头像上传 -->
     <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width:85%">
                    <div class="modal-content" style="height:650px">
                        <cropper @headImage="headImage" @isModelShow="isModelShow" style="margin: 1% auto auto auto"></cropper>
                        <button id="model-close" type="button" class="btn btn-default btn-close" data-dismiss="modal" style="display:none">关闭</button>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
     </div>

    <!--轮播图-->
    <div class="carousel slide" id="carousel-example-generic" data-ride="carousel" >
      <ol class="carousel-indicators">
        <li class="active" data-slide-to="0" data-target="#carousel-example-generic">
        </li>
        <li data-slide-to="1" data-target="#carousel-example-generic">
        </li>
        <li data-slide-to="2" data-target="#carousel-example-generic">
        </li>
      </ol>
      <div class="carousel-inner" role="listbox">
        <div v-for="(imgList,index) in carouselList" class="item" :class="{active:isActive(index)}">
          <img alt="" :src="imgList" style="width:100%"/>
        </div>
      </div>

      <div class="left carousel-control" href="#carousel-example-generic"  role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left"aria-hidden="true"></span>
      </div>
      <div class="right carousel-control" href="#carousel-example-generic"  role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      </div>
    </div>


    <hr style="width:100%;height:3px;background-color:#1FBAA8;border:0;"/>

    <!-- 已创建课程陈列 -->
    <div class="col-md-12 clearfix" style="margin-bottom:5%;width:80%;margin-left:10%;margin-right:10%;display:block;">

      <div v-for="courseInfo in courseList" class="col-xs-12 col-md-3 column container-fluid" style="margin-top:3%;padding-left:0;padding-right:0;">
        <div class="col-1" style="margin-left:1%;margin-right:1%;">
          <img alt="140x140" :src="courseInfo.coverImgUrl" style="width:100%"/>
          <div class="warp">
            <p style="font-size:24px;margin-top:10%">{{courseInfo.name}}</p>
            <div class="but" style="margin-top:10%">
              <button @click="prepare(courseInfo)" type="button" class="btn btn-primary" onMouseOut="this.style.backgroundColor='#1FBAA8'" onMouseOver="this.style.backgroundColor='#FAA83A'" style="background-color:#1FBAA8;border:#1FBAA8;width:20%;padding: 6px">备课</button>
              <button @click="online(courseInfo)" type="button" class="btn btn-primary" onMouseOut="this.style.backgroundColor='#1FBAA8'" onMouseOver="this.style.backgroundColor='#FAA83A'" style="background-color:#1FBAA8;border:#1FBAA8;width:20%;padding: 6px">上课</button>
              <button type="button" class="btn btn-primary" onMouseOut="this.style.backgroundColor='#1FBAA8'" onMouseOver="this.style.backgroundColor='#FAA83A'" style="background-color:#1FBAA8;border:#1FBAA8;width:20%;padding: 6px">统计</button>
              <button type="button" class="btn btn-primary" onMouseOut="this.style.backgroundColor='#1FBAA8'" onMouseOver="this.style.backgroundColor='#FAA83A'" style="background-color:#1FBAA8;border:#1FBAA8;width:20%;padding: 6px">归档</button>
            </div>
          </div>
        </div>
      </div>

      <div class="col-xs-12 col-md-3 column" style="height:100%;margin-top:3%;padding-left:0;padding-right:0;">
        <div class="col-1" style="margin-left:1%;margin-right:1%;">
          <img alt="140x140" src="../../../static/addCourse-2.png" style="width:100%"/>

          <a id="modal-636651" href="#myModal" data-toggle="modal">
            <div class="warp">
             <img src="../../../static/add.png" style="width: 100%;height: 100%"/>
            </div>
          </a>
        </div>
        <!-- 弹出框 -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="margin-top:7%">
          <div class="modal-dialog" role="document" style="width:450px;">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加课程</h4>
              </div>
              <div class="modal-body">
                <form class="form-horizontal" style="margin-top:60px;margin-bottom:60px">
                  <div class="form-group" style="margin:auto">
                    <label for="exampleInputName2" class="col-sm-4 control-label">*课程名称：</label>
                    <input type="text" v-model="message" @keyup.enter="inputName" class="form-control" id="exampleInputName2" placeholder="面向对象基础 " style="width:60%"/>
                    <p v-if="customShow" style="color:red;text-align: left;margin-left: 35%">课程名称不能为空</p>
                    <p v-if="customShow1" style="color:red;text-align: left;margin-left: 35%">课程名称不得小于两个字符</p>
                  </div>
                </form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-primary" @click="createCourseInfo()" style="background-color:#1FBAA8;border:#1FBAA8">创建</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>
    <div class="col-md-12" style="background-color:#1FBAA8;height:40px">
      <div class="col-md-12 column" style="line-height:50px;">
        <p style="font-size:16px;margin:0">@智慧教室</p>
      </div>
    </div>

  </div>
</template>

<script>
  import cropper from '../Course/cropper.1'

  export default {
    name: 'Index',
     components:{
      cropper
    },
    data () {
      return {
        name:'',
        userInfo:{  //初始设置老师的姓名
          id:'',
          nickname:'张四',
          phoneNumber:'',
          headImgUrl:'',
          password:''
        },
        customShow:true,  //弹出框输入信息错误时出现提示信息
        customShow1:true,
        message:"",   //初始设置添加课程名称的信息
        courseList:[],  //获取课程信息的容器
        carouselList:[],//获取轮播图信息的容器
        coverImgUrl:'',
        isShow: false,
      }
    },
    watch:{

      //规定出现提示信息的条件
      message(val){
        if(val == "" || val == null || val.length<2){
          this.customShow = true;
          this.customShow1 = true;
        }else{
          this.customShow = false;
          this.customShow1 = false;
        }
      },
    },

    methods:{
      // cropper
      isModelShow (...data) {
      this.isShow = data[0]
      if(!data[0]){
        $('#model-close').click()
        this.updateUserInfo();
      }
      },
      headImage (...data) {
        
        this.userInfo.headImgUrl = data[0]
      },
      //监听回车键，实现回车完成创建课程
      inputName(e){
        this.createCourseInfo()
      },
      //动态地切换轮播图的class
      isActive(index){
        if(index == 0) {
          return true
        }else {
          return false
        }
      },

      //用户信息修改
      updateUserInfo(){
         axios({
          method: 'post',
          url: '/api/teacher/updateUserInfoById',
          data: this.userInfo,
          }).then((response) => {
            bus.$emit("dialog", {
                  type: "success",//success default error warn
                  body: " 头像上传成功！",
                  auto: true,//是否自动隐藏
                  time: 5000//毫秒
                })
            bus.$emit('headImgUrlChange',this.userInfo.headImgUrl);
          }).catch(function(error) {
            alert(error);
            bus.$emit("dialog", {
                  type: "error",//success default error warn
                  body: " 头像上传失败！",
                  auto: false,//是否自动隐藏
                  time: 5000//毫秒
                })
          });
     
      },

      //获取轮播图信息
      getCarousel(){
        axios.get('/api/getCarousel')
          .then((response)=>{
            this.carouselList = response.data.result;
          }).catch((response)=>{
          console.log(response)
        })
      },

      //往数据库添加新建课程信息
      createCourseInfo(){

            if(this.message == ""){
              alert("课程名称不能为空")
              return false
            }else if(this.message.length<2){
              alert("课程名称不得小于两个字符")
              return false
            }else{
              axios.post('/api/createCourseInfo',
                {
                  name : this.message
                }
              ) .then((response) => {
                this.courseList = response.data.result;
                $("#myModal").modal("hide");
                bus.$emit("dialog",{
                  type:"false",
                  body:"创建成功",
                  auto:true,
                  time:3000
                })
              }).catch((response)=>{
                console.log(response)
              })
            }
      },

      //获取已创建的课程列表
      getCourseInfoList(){
        axios.get('/api/getCourseInfoList',)
          .then((response) => {
            this.courseList = response.data.result;
          }).catch((response)=>{
          console.log(response)
        })
      },

      prepare(courseInfo){
        this.$router.push({ name: 'CourseFlow', params: {courseInfo:courseInfo} })
      },
      online(courseInfo){
        this.$router.push({ name: 'OnlineDashboard', params: {courseInfo:courseInfo} })
      },
      //输入信息错误显示提示信息
      show1(){
        this.customShow = true;
        this.customShow1 = true;
        this.isActive = true;
      },
      onModelChange(val){
        this.customShow = val;
        this.customShow1 = val;
      }
    },
    beforeCreate(){},
    created(){

    },
    beforeMount(){},
    mounted(){



      this.userInfo =JSON.parse(window.sessionStorage.getItem("user"));
      this.getCourseInfoList();
      this.getCarousel();
    },
    beforeUpdate(){},
    updated(){},
    beforeDestroy(){

    },
    destroyed(){}
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1, h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0;
  }

  a {
    color: #42b983;
  }

  .hello{
    text-align: center;
    border:0;
  }
  .col-md-12{
    padding:0;
  }

  .col-1:hover .warp{
    display:block;
    position:absolute;
    background-color: rgba(0, 0, 0, 0.6);
    right:0;
  }

  .warp{
    bottom:0;
    height:100%;
    width:98%;
    margin-left:1%;
    margin-right:1%;
    display:none;
    text-decoration:none;
    position:relative;

  }
  .warp p{
    font-family:"微软雅黑";
    color:white;
  }
  .but{
    position:absolute;
    bottom:1%;
    width:100%;
  }

  .navbar-default {
    margin-bottom:0;
    background-color:#1FBBA6;
    border-radius: 0;
    border-color: #1FBBA6;
  }
  .navbar-default .navbar-brand {
    color: #f1f5f6;
  }
  .navbar-default .navbar-brand:hover,
  .navbar-default .navbar-brand:focus {
    color: #ffffff;
  }
  .navbar-default .navbar-text {
    color: #f1f5f6;
  }
  .navbar-default .navbar-nav > li > a {
    color: #f1f5f6;
  }
  .navbar-default .navbar-nav > li > a:hover,
  .navbar-default .navbar-nav > li > a:focus {
    color: #ffffff;
  }
  .navbar-default .navbar-nav > .active > a,
  .navbar-default .navbar-nav > .active > a:hover,
  .navbar-default .navbar-nav > .active > a:focus {
    color: #ffffff;
    background-color: #12b786;
  }
  .navbar-default .navbar-nav > .open > a,
  .navbar-default .navbar-nav > .open > a:hover,
  .navbar-default .navbar-nav > .open > a:focus {
    color: #ffffff;
    background-color: #12b786;
  }
  .navbar-default .navbar-toggle {
    border-color: #12b786;
  }
  .navbar-default .navbar-toggle:hover,
  .navbar-default .navbar-toggle:focus {
    background-color: #12b786;
  }
  .navbar-default .navbar-toggle .icon-bar {
    background-color: #f1f5f6;
  }
  .navbar-default .navbar-collapse,
  .navbar-default .navbar-form {
    border-color: #f1f5f6;
  }
  .navbar-default .navbar-link {
    color: #f1f5f6;
  }
  .navbar-default .navbar-link:hover {
    color: #ffffff;
  }
</style>
