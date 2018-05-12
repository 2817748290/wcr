<template>
  <div class="container-fluid" style="padding:0;background-color:#EAEDF2;">

    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" style="color:#FFF" href="#/Course">智慧教室</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" style="color:#FFF"  data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">李子明<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">用户信息</a></li>
                <li><a href="#">密码修改</a></li>
                <li role="separator" class="divider"></li>
                <li><a href="#/Course">登出</a></li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="col-md-12" style="background-color:#246662">
      <h4 style="float:left;color:#bdbbbb">所在位置：</h4>
      <h4 style="float:left;color:#FFF"><a style="color:white" href="#/">首页-</a></h4>
      <h4 style="float:left;color:#FFF"><a style="color:white" :href="stateUrl">{{state}}</a></h4>
      <div role="separator" class="divider"></div>
    </div>

    <div class="col-md-12" >
      <div class="col-md-2" style="padding:15px;padding-left:0">
        <div :style="{height:navHeight+'px',backgroundColor:'#FFF',overflow:'auto'}">
          <CourseDashboardList :courseid="courseId"></CourseDashboardList>
        </div>
      </div>
      <div class="col-md-10" style="padding:15px;padding-right:0">
        <div :style="{height:navHeight+'px',backgroundColor:'#FFF',overflow:'auto'}">
          <router-view></router-view>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import CourseDashboardList from './CourseDashboardList.vue'
import CourseFlow from './CourseFlow.vue'
import CourseClassManager from './CourseClassManager.vue'
export default {
  name: 'CourseDashboard',
  props: {

  },
  components:{
    'CourseDashboardList': CourseDashboardList,
    'CourseFlow':CourseFlow,
    'CourseClassManager':CourseClassManager,
  },
  data() {
    return {
      navHeight:0,
      timer:false,
      state:"备课流程",
      stateUrl:"#/Course",
      courseId:0
    }
  },
  watch: {

  },
  methods: {
    getnavHeight(){
      if (!this.timer) {
        this.timer = true
        let that = this
        setTimeout(function () {
          var winHeight=0;
          if (window.innerHeight){
            winHeight = window.innerHeight;
          }
          else if ((document.body) && (document.body.clientHeight))
          winHeight = document.body.clientHeight;
          if (document.documentElement && document.documentElement.clientHeight && document.documentElement.clientWidth)
          {
            winHeight = document.documentElement.clientHeight;
          }
          that.navHeight=winHeight-123;
          that.timer = false
        }, 400)
      }
    }
  },
  beforeCreate() {},
  created() {

    if(this.$route.params.courseInfo != undefined){
      window.sessionStorage.setItem("courseId", this.$route.params.courseInfo.id);
      this.courseId = this.$route.params.courseInfo.id;
    }else{
      var a = window.sessionStorage.getItem("courseId");

      if(a != undefined){
        this.courseId = 0;
        this.courseId = a;
      }else{
        this.$router.push({ name: 'Index'})
      }
    }

    this.getnavHeight();
  },
  beforeMount() {},
  mounted() {

    const that = this;
    bus.$on('changeState',function(states){
      console.log(states);
      that.state=states.state;
      that.stateUrl=states.stateUrl;
    });
    window.onresize = () => {
      return (() => {
        that.getnavHeight()
      })()
    }

  },
  beforeUpdate() {},
  updated() {},
  beforeDestroy() {
    bus.$off('changeState');
  },
  destroyed() {}
}
</script>

<style scoped>
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

@media (max-width: 767px) {
  .navbar-default .navbar-nav .open .dropdown-menu > li > a {
    color: #f1f5f6;
  }
  .navbar-default .navbar-nav .open .dropdown-menu > li > a:hover,
  .navbar-default .navbar-nav .open .dropdown-menu > li > a:focus {
    color: #ffffff;
  }
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a,
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:hover,
  .navbar-default .navbar-nav .open .dropdown-menu > .active > a:focus {
    color: #ffffff;
    background-color: #12b786;
  }
}

</style>
