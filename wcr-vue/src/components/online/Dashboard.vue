<template>
  <div style="width: 100vm;display: flex;flex-direction: row;flex-wrap: nowrap;">
    <div style="padding:5px;padding-right:0px;flex-grow: 1;height: 100vh;">
      <div style="overflow-y:auto;overflow-x:hidden;width: 100%;height: 100%;background-color: #F2F2F2;display: block">
       
        <Remote v-show="customShow == 'Remote'"></Remote>
        <SignIn v-show="customShow == 'SignIn'"></SignIn>
        <Contents :nodeTree="CHAT.arrangeData.nodeTree" v-show="customShow == 'Content'"></Contents>
        <Issues :issue="CHAT.arrangeData.nodeNormal" v-show="customShow == 'Issues'"></Issues>
        <Vote :vote="CHAT.arrangeData.nodeNormal" v-show="customShow == 'Vote'"></Vote>
        <Responder v-show="customShow == 'Responder'"></Responder>
        <SelectPerson v-show="customShow == 'SelectPerson'"></SelectPerson>
        <Discussion v-show="customShow == 'Discussion'"></Discussion>
        <Information v-show="customShow == 'Information'"></Information>
      </div>
    </div>
    <div style="padding:2px;padding-right:0px;height: 100vh;display: flex;flex-direction: row;overflow:hidden">
      <div style="margin-right:-2px;width: 15px;height: 100%;display: flex;flex-direction: column;align-items:center;justify-content: center;">
        <div @click="toolbarShow = !toolbarShow" style="color:#FFF;cursor:pointer;border-bottom-left-radius: 5px;border-top-left-radius:5px;background-color: #53cfab;height: 100px;display: flex;flex-direction: column;align-items:center;justify-content: center;">
          《
        </div>
      </div>
      <transition name="bounce">
        <div v-if="toolbarShow" style="margin-right:2px;overflow:hidden;text-align: center;color:#FFF;width: 75px;height: 100%;display: flex;flex-direction: column;flex-wrap: wrap;background-color: #53cfab">
          <div class="item">
            <button @click="customShow = 'Remote'"  class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'Remote'}" type="submit">同屏</button>
          </div>
          <div class="item">
            <button @click="customShow = 'SignIn'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'SignIn'}" type="submit">签到</button>
          </div>
          <div class="item">
            <button @click="customShow = 'Content'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'Content'}" type="submit">内容</button>
          </div>
          <div class="item">
            <button @click="customShow = 'Issues'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'Issues'}" type="submit">议题</button>
          </div>
          <div class="item">
            <button @click="customShow = 'Vote'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'Vote'}" type="submit">表决</button>
          </div>
          <div class="item">
            <button @click="customShow = 'Responder'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'Responder'}" type="submit">抢答</button>
          </div>
          <div class="item">
            <button @click="customShow = 'SelectPerson'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'SelectPerson'}" type="submit">选人</button>
          </div>
          <div class="item">
            <button @click="customShow = 'Discussion'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'Discussion'}" type="submit">讨论</button>
          </div>
          <div class="item">
            <button @click="customShow = 'Information'" class="btn btn-default btn-custom" :class="{ btnSelect : customShow == 'Information'}" type="submit">信息</button>
          </div>
          <div class="item">
            <button @click="close()" class="btn btn-danger btn-custom" style="color:#fff" type="submit">结束</button>
          </div>
        </div>
      </transition>
    </div>


    <!-- 选择课程安排 -->
    <div class="modal fade" id="chooseCourseArrangeList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">选择课程安排</h4>
          </div>
          <div class="modal-body">
            <table id="courseArrangeList"></table>
          </div>
          <div class="modal-footer">
            <button @click="start" type="button" class="btn btn-primary">上课！</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from 'vue'

import Remote from "./Remote.vue"
import SignIn from "./SignIn.vue"
import Contents from "./Contents.vue"
import Issues from "./Issues.vue"
import Vote from "./Vote.vue"
import Responder from "./Responder.vue"
import SelectPerson from "./SelectPerson.vue"
import Discussion from "./Discussion.vue"
import Information from "./Information.vue"

import CHAT from "./client"

import 'bootstrap-table/js/bootstrap-table.min.js'
import 'bootstrap-table/js/bootstrap-table-zh-CN.min.js'
import 'bootstrap-table/js/bootstrap-table-fixbug.js'
import 'bootstrap-table/css/bootstrap-table.min.css'

// import ECharts modules manually to reduce bundle size
import ECharts from 'vue-echarts/components/ECharts.vue';

import 'echarts/lib/component/polar'
import 'echarts/lib/chart/bar'

Vue.component('chart', ECharts);

export default {
  name: 'Dashboard',
  components:{
    Remote:Remote,
    SignIn:SignIn,
    Contents:Contents,
    Issues:Issues,
    Vote:Vote,
    Responder:Responder,
    SelectPerson:SelectPerson,
    Discussion:Discussion,
    Information:Information,
  },
  data () {
    return {
      CHAT,
      toolbarShow:true,
      customShow:"SignIn",
      courseInfo:{

      },
    }
  },
  watch:{
  },
  methods:{
    close(){
      if(confirm("你确定要关闭课堂嘛？")){
        CHAT.status = false;
        CHAT.submit({
          type: CHAT.LOGOUT,
          allSend:1,
          studentId: 0,
          classId: 0,
          result: null,
        })
        this.$router.go(-1);
      }
    },
    start(){
      var data = $("#courseArrangeList") .bootstrapTable("getSelections");
      if(data.length == 1){
        CHAT.courseArrange = data[0];
        axios.post('/public/getStudentListByGroups',
          {groups:CHAT.courseArrange.groups}
        ).then((response) => {
          if(response.data.status==0){
            alert(response.data.message);
          }else{
            CHAT.groups = response.data.result;
             $("#chooseCourseArrangeList").modal("hide");
            CHAT.init();

            axios.post('/public/getArrangeData',
              {id:CHAT.courseArrange.arrangeDataId}
            ).then((response) => {
              if(response.data.status==0){
                alert(response.data.message);
              }else{
                //console.log(response.data.result[0].issue);
                //CHAT.arrangeData.issue = JSON.parse(response.data.result[0].issue);
                //console.log(CHAT.arrangeData.issue);
                //CHAT.arrangeData.vote = JSON.parse(response.data.result[0].vote);
                CHAT.arrangeData.nodeNormal = JSON.parse(response.data.result[0].nodeNormal);
                CHAT.arrangeData.nodeTree = JSON.parse(response.data.result[0].nodeTree);
              }
            }).catch(function(error) {
              alert(error);
            });

          }
        }).catch(function(error) {
          alert(error);
        });
       
        
      }else{
        alert("请选择一节课程安排！");
      }

    },
    init(){
      
      var th = this;
    
      $('#chooseCourseArrangeList').modal({backdrop: 'static', keyboard: false});
      $("#chooseCourseArrangeList").modal("show")
      $("#courseArrangeList") .bootstrapTable({
            url:'/api/courseArrange/getInitCourseArrange',
            cache:false,
            toolbar:"#toolbar",
            ajaxOptions:{
                headers: {"token":window.sessionStorage.getItem("token")}
            },
            singleSelect:true,
            sortName:'gmt_modified',
            searchAlign:'left',
            sortOrder:'desc',
            striped:true,//隔行变色
            // search:true,
            searchOnEnterKey:true,
            showRefresh:true,
            method:'post',
            sidePagination:'server',
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pageList:'[10, 25, 50, 100, All]',
            showToggle:true,
            clickToSelect: true,
            singleSelect:true,
            columns:[
              {field: "checkbox", checkbox: true},
              {field: "topic", title: '课程主题', align: 'center', valign: 'middle'},
              {field: "courseInfo.name", title: '课程名称', align: 'center', valign: 'middle'},
              {field: "courseOntime", title: '上课时间', align: 'center', valign: 'middle', formatter: jsonDateFormat},
              {field: "courseDowntime", title: '下课时间', align: 'center', valign: 'middle', formatter: jsonDateFormat},
              {field: "courseClass.className", title: '上课班级', align: 'center', valign: 'middle'},
              {field: "state", title: '上课状态', align: 'center', valign: 'middle', formatter: stateCheck },
            ],queryParams:function(params) {
              params.courseId= th.courseInfo.id;
              params.searchName = $('#searchName').val();
              console.log(params)
              return params;
            }
        });
        function stateCheck(value){
          if(value==0){
            return '<div style=" background-color: #878D99;border-radius:5px;color:#fff;padding:5px">未发布</div>'
          }else if(value==1){
            return '<div style=" background-color: #EB9E05;border-radius:5px;color:#fff;padding:5px">已发布</div>'
          }else if(value==2){
            return '<div style=" background-color: #409EFF;border-radius:5px;color:#fff;padding:5px">未上课</div>'
          }else if(value==3){
            return '<div style=" background-color: #6fcfca;border-radius:5px;color:#fff;padding:5px">已上课</div>'
          }else{
            return '<div style=" background-color: #FA5555;border-radius:5px;color:#fff;padding:5px">未关闭</div>'
          }
        }
        function jsonDateFormat(jsonDate) {
              //json日期格式转换为正常格式
          var jsonDateStr = jsonDate.toString();//此处用到toString（）是为了让传入的值为字符串类型，目的是为了避免传入的数据类型不支持.replace（）方法
          try {
              var k = parseInt(jsonDateStr.replace("/Date(", "").replace(")/", ""), 10);
              if (k < 0)
                  return null;

              var date = new Date(parseInt(jsonDateStr.replace("/Date(", "").replace(")/", ""), 10));
              var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
              var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
              var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
              var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
              var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
              var milliseconds = date.getMilliseconds();
              return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
          }
          catch (ex) {
              return "时间格式转换错误";
          }
        }
    }
  },
  beforeCreate(){},
  created(){},
  beforeMount(){},
  mounted(){
    if(this.$route.params.courseInfo != undefined){
      console.log("第一次进入交互界面");
      window.sessionStorage.setItem("onLineCourse", JSON.stringify(this.$route.params.courseInfo));
      this.courseInfo = this.$route.params.courseInfo;
      this.init();
    }else{
      var a = JSON.parse(window.sessionStorage.getItem("onLineCourse"));
      console.log(a);
      console.log("交互界面刷新");
      if(a != undefined){
        this.courseInfo = {};
        this.courseInfo = a;
        this.init();
      }else{
        console.log("获取失败跳转主页面");
        this.$router.push({ name: 'Index'})
      }
    }
    

  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){
    console.log("============");
    bus.$emit('clearModalBackDrop');
  },
  destroyed(){
   
  }
}
</script>


<style scoped>
  
  .btn-custom{
    border-radius:  53px;
    height: 53px;
    width: 53px;
    color: #5cb85c;
  }
  .btnSelect{
    background-color: #6fcfca;
    color: #FFF;
  }
  .item{
    width: 100%;
    flex: 1;
    align-items:center;
    justify-content: center;
    display: flex;
  }

.bounce-enter-active {
  animation: bounce-in .5s;
}
.bounce-leave-active {
  animation: bounce-out .5s;
}
@keyframes bounce-in {
  from {width: 0px;}
  to {width: 75px;}
}
@keyframes bounce-out {
  from {width: 75px;}
  to {width: 0px;}
}
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
/* .slide-fade-enter, .slide-fade-leave-to
  transform: translateX(40px);
} */
</style>
