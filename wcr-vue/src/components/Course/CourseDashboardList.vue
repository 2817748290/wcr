<template>
    <div>
      <div class="list-head">
        <img class="img-head" :src="coverImgUrl"/>
      </div>
      <div class="text-head">
        <h4><strong>课程名称：{{courseName}}</strong></h4>
        <!-- <h4><strong>专业名称：{{majorName}}</strong></h4> -->
      </div>
      <div><hr style="border-bottom: 2px solid #00CA79;margin-left:5px;margin-right:5px"></div>
      <div id="tree"></div>

      <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
           <div class="modal-dialog">
             <div class="modal-content">
               <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                   &times;
                 </button>
                 <h4 class="modal-title" id="myModalLabel">创建</h4>
               </div>
               <div class="modal-body">
                 <form id="addStudentForm" role="form" >
                   <div class="form-group">
                     <label for="message-text" class="control-label">班级名称:</label>
                     <input type="text" class="form-control" id="className" v-model="newClassName" placeholder="请输入班级名称">
                   </div>
                 </form>
               </div>
               <div class="modal-footer">
                 <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
                 <button type="button" class="btn btn-primary" id="addRecord" @click="addCourseClass()">提交</button>
               </div>
             </div>
           </div>
         </div><!-- 重命名 -->
    </div>
</template>

<!-- <script src="/static/js/bootstrap-treeview.js"></script> -->
<script>
import 'bootstrap-treeview/js/bootstrap-treeview.js'
import 'bootstrap-treeview/css/bootstrap-treeview.css'
export default {
  name: 'CourseDashboardList',
  props:{
    courseid:0
  },
  data () {
    return {
      classId:0,
      courseName:"绘画",
      newClassName:"",
      coverImgUrl:"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509985624686&di=165749e8741df3a3552081bf0e301bb5&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D3990563143%2C2312333604%26fm%3D214%26gp%3D0.jpg",
      // CourseClass:{},
      tree:[
        {
          text: "课程信息",
          id:0,
          isParent:0, //判断是否是子节点
          icon:'glyphicon glyphicon-list-alt'
        },
        {
          text: "课程班级",
          id:1,
          isParent:0,
          icon:'glyphicon glyphicon-th-list',
          nodes: []
        },
        {
          text: "课程内容",
          id:2,
          isParent:0,
          icon:'glyphicon glyphicon-book'
        },
        {
          text: "课程题库",
          icon:'glyphicon glyphicon-file'
        },
        {
          id:4,
          isParent:0,
          text: "课程安排",
          icon:'glyphicon glyphicon-indent-right'
        }
      ],
    }
  },
  watch:{
    courseid(val){
      this.init();
      this.initTree();
    }
  },
  methods:{
    init(){
      //this.courseid = this.courseInfo.id;
      //console.log(this.courseInfo.id)
      axios.post('/api/getCourseByCourseId',{
        'courseId':this.courseid+""
      })
      .then((response) => {
        this.courseName=response.data.result.name;
        this.coverImgUrl = response.data.result.coverImgUrl;
      })
      .catch((error) => {
        console.log(error);
      });
    },
    initTree(){
      var th=this;
      var courseid=this.courseid;
      th.tree[1].nodes=[];
      axios.post('/api/getCourseClassByCourseId',{
        'courseId':courseid+""
      })
      .then((response) => {
        for (var result in response.data.result) {
          th.tree[1].nodes.push({
            text: response.data.result[result].className,
            id:response.data.result[result].classId,
            isParent:1,
          });
        }
        th.tree[1].nodes.push({
          text: "创建新班级",
          id:0,
          isParent:1,
        });
        $('#tree').treeview({
          data: th.tree,
          onhoverColor:'#1FBBA6',
          selectedBackColor:'#1FBBA6',
        });
        $('#tree').on('nodeSelected', function(event, data) {
          var states={
            state:"",
            stateUrl:"",
          };
          if(data.isParent==0){
            if(data.id==0){
              th.$router.replace('/Course/CourseInfo');
              states.state="课程信息";
              states.stateUrl="#/Course/CourseInfo";
            }else if(data.id==2){
              th.$router.replace({path:'/Course/CourseInfoList',query:{'courseId':courseid,'courseName':th.courseName}});
              states.state="课程内容";
            }else if(data.id==3){
              th.$router.replace('/Course/CourseInfoList');
              states.state="课程题库";
            }else if(data.id==1){
              states.state="课程班级";
            }else if(data.id==4){
              th.$router.replace('/Course/CourseArrangement');
              states.state="课程安排";
            }
          }else{
            states.state="课程班级";
            if(data.id==0){
              $("#addModal").modal('show');
            }
            else{
              th.classId=data.id;
              bus.$emit('classIdChange',th.classId)
              th.$router.replace({path:'/Course/CourseClassManager',query:{classId:th.classId}});
            }
          }
          bus.$emit('changeState',states)
        });
      })
      .catch((error) => {
        console.log(error);
      });
    },
    addCourseClass(){
      axios.post('/api/addCourseClass',{
        couseId:this.courseid,
        className:this.newClassName,
        type:0,
      })
      .then((response) => {
        this.initTree();
        $("#addModal").modal('hide');
      })
      .catch((error) => {
        console.log(error);
      });
    }
  },
  beforeCreate(){},
  created(){
    this.init();
    // this.init2(this.courseid);
  },
  beforeMount(){},
  mounted(){
    var th=this;
    var courseid=this.courseid;
    // console.log(this.courseid);
    bus.$on('changeClassName',function(){
      th.initTree();
    });
    bus.$on('coverImageUrlChange',function(val){
      th.coverImgUrl = val
    });
    bus.$on('nameChange',function(val){
      th.courseName = val
    });
    bus.$on('coverImgUrlChange',function(val){
      th.coverImgUrl = val
    });
    $(function(){
      th.initTree();
    });
  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){
    bus.$off('changeClassName')
    bus.$off('coverImageUrlChange')
    bus.$off('coverImgUrlChange')
    bus.$off('nameChange')
  },
  destroyed(){
  }
}
</script>

<style scoped>
/*@import "/static/css/bootstrap-treeview.css";*/
.list-head{
  text-align:center;
}
.img-head{
  margin-top: 15px;
  /*margin-left: 15px;*/
  width:90%;
}
.text-head{
  /*float: right;*/
  margin-top: 5%;
  margin-left:6%;
}
</style>
