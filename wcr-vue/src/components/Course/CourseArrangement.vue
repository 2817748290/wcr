<template>

  <div id="CourseArrangement" style="margin:1% 2.5% 0 2.5%;" class="">
            <p class="text-left"><font size="12" color="#000000" face="微软雅黑">课程安排</font><p>

            <div id="toolbar" >
              <div id="operationBtn" class="" style="margin:-1px 5px 0 0;float:left" >
                <button id="btn_addCourseArrange" type="button" class="btn btn-default" data-toggle="modal" data-target="#addCourseArrange">
                  <span class="glyphicon glyphicon-plus" aria-hidden="true" ></span>添加课程安排
                </button>

              </div>
              <div style="float:left;margin:0 5px 0 0;">
                <select id="searchName" class="form-control">
                  <option value="topic">课程主题</option>
                  <option value="class_name">上课班级</option>
                  <option value="state_info">上课状态</option>
                </select>
              </div>

            </div>
            <table id="courseList"></table>

          <!-- 添加课程安排（addCourseArrangeModal） -->
          <div class="modal fade" id="addCourseArrange" tabindex="-1" role="dialog" aria-labelledby="addCourseArrangeLabel" aria-hidden="true">
              <div class="modal-dialog" style="width:90%">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                          <h4 class="modal-title" style="text-align:center;" id="addCourseArrangeLabel">添加课程安排</h4>
                      </div>
                      <div class="modal-body">
                        <CourseArrangeContent v-show="IfShow==1" :IfShow="IfShow" :status="status" :course="course" :courseId="courseId"  @course-arrange-content-data = "onCourseArrangeAdd"></CourseArrangeContent>
                        <CourseArrangeGroup v-show="IfShow==2" :classInfos="classInfos" :IfShow="IfShow" :status="status" :CourseArrangeIssue="courseArrangeAdd.arrangeData.issue" @course-arrange-content-data = "onCourseArrangeGroupsAdd"></CourseArrangeGroup>
                        <CourseArrangeNotice v-show="IfShow==3" ></CourseArrangeNotice>
                      </div>
                      <div class="modal-footer"  style="text-align:center;">
                          <button type="button" v-if="IfShow!=1" class="btn btn-default" @click="function(){if(IfShow>1){IfShow--;status='up';}}">上一步</button>
                          <button type="button" v-if="IfShow==2" class="btn btn-danger" @click="function(){IfShow++}">跳过</button>
                          <button type="button" v-if="IfShow!=3" class="btn btn-primary" @click="downToStep">下一步</button>
                          <button type="button" v-if="IfShow==3" class="btn btn-primary" @click="addCourseArrangeToSQL">完成</button>
                      </div>
                  </div><!-- /.modal-content -->
              </div><!-- /.modal -->
          </div>

          <!-- 发布（isPublishModal） -->
          <div class="modal fade" id="isPublish" tabindex="-1" role="dialog" aria-labelledby="isPublishLabel" aria-hidden="true">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                          <h4 class="modal-title" style="text-align:center;" id="isPublishLabel">发布</h4>
                      </div>
                      <div class="modal-body" style="text-align:center;">确定发布该课程安排？</div>
                      <div class="modal-footer"  style="text-align:center;">
                          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                          <button id="publish-btn" type="button" class="btn btn-primary" data-dismiss="modal" @click="publisheAfter">发布</button>
                      </div>
                  </div><!-- /.modal-content -->
              </div><!-- /.modal -->
          </div>

          <!-- 修改（isUpdateModal） -->
          <div class="modal fade" id="isUpdate" tabindex="-1" role="dialog" aria-labelledby="isUpdateLabel" aria-hidden="true">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                          <h4 class="modal-title" style="text-align:center;" id="isUpdateLabel">修改</h4>
                      </div>
                      <div class="modal-body" style="text-align:center;">
                        <div class="form-group">
                          <label class="col-sm-3 control-label">课程主题：</label>
                            <div class="col-sm-8">
                              <input class="form-control" type="text" placeholder="课程主题" v-model="courseArrange.topic"/>
                            </div>
                        </div>
                        <div class="form-group">
                          <label class="col-sm-3 control-label">上课时间：</label>
                            <div class="col-sm-8">
                                <input class="form-control select-box-input" v-model="courseArrange.courseOntime" type="text"
                                      id="form_datetime">
                            </div>
                        </div>
                        <div class="form-group">
                          <label class="col-sm-3 control-label">下课时间：</label>
                            <div class="col-sm-8">
                                <input class="form-control select-box-input" v-model="courseArrange.courseDowntime" type="text"
                                      id="form_datetime1">
                            </div>
                        <div class="form-group">
                          <label class="col-sm-3 control-label">课程班级：</label>
                            <div class="col-sm-8">

                              <select id="classSelect" class="form-control" v-model="selected" @change="selectVal">
                                  <option v-for="classInfo in classInfos" :value="classInfo.classId">{{ classInfo.className }}</option>
                              </select>
                            </div>
                        </div>

                        </div>
                        <div class="form-group">
                          <label class="col-sm-3 control-label">上课状态：</label>
                            <div class="col-sm-8">
                               <select id="stateSelect" class="form-control" v-model="stateSelected" @change="stateVal">
                                  <option v-for="state in states" :value="state.value">{{ state.message }}</option>
                              </select>
                            </div>
                        </div>

                      </div>
                      <div class="modal-footer"  style="text-align:center;">
                          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                          <button id="update-btn" type="button" class="btn btn-info " data-dismiss="modal" @click="updateAfter">修改</button>
                      </div>
                  </div><!-- /.modal-content -->
              </div><!-- /.modal -->
          </div>

          <!-- 查看（isCheckModal） -->
          <div class="modal fade" id="isCheck" tabindex="-1" role="dialog" aria-labelledby="isCheckLabel" aria-hidden="true">
              <div class="modal-dialog modal-lg">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                          <h4 class="modal-title" style="text-align:center;" id="isCheckLabel"><font size="6" color="#000000" face="微软雅黑">查看</font></h4>
                      </div>
                      <div class="modal-body" style="text-align:center;">
                        <p class="text-left"><font size="5" color="#000000" face="微软雅黑">课程名称：  {{ courseArrange.course }}</font></p>
                        <p class="text-left"><font size="5" color="#000000" face="微软雅黑">课程主题：  {{ courseArrange.topic }}</font></p>
                        <p class="text-left"><font size="5" color="#000000" face="微软雅黑">课程时间：  {{ courseArrange.courseOntime }} ~ {{courseArrange.courseDowntime}}</font></p>
                        <p class="text-left"><font size="5" color="#000000" face="微软雅黑">上课班级：  {{ className }}</font></p>
                        <p class="text-left"><font size="5" color="#000000" face="微软雅黑">上课状态：  {{ stateMsg }}</font></p>
                      </div>
                      <div class="modal-footer"  style="text-align:center;">
                          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                          <!-- <button type="button" class="btn btn-success" data-dismiss="modal">查看</button> -->
                      </div>
                  </div><!-- /.modal-content -->
              </div><!-- /.modal -->
          </div>

          <!-- 删除（isDeleteModal） -->
          <div class="modal fade" id="isDelete" tabindex="-1" role="dialog" aria-labelledby="isDeleteLabel" aria-hidden="true">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                          <h4 class="modal-title" style="text-align:center;" id="isDeleteLabel">删除</h4>
                      </div>
                      <div class="modal-body" style="text-align:center;">删除查看该课程安排？</div>
                      <div class="modal-footer"  style="text-align:center;">
                          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                          <button id="delete-btn" type="button" class="btn btn-danger" data-dismiss="modal" @click="deleteAfter">删除</button>
                      </div>
                  </div><!-- /.modal-content -->
              </div><!-- /.modal -->
          </div>
  </div>
</template>


<script>
  import CourseArrangeContent from './CourseArrangeContent.vue'
  import CourseArrangeGroup from './CourseArrangeGroup.vue'
  import CourseArrangeNotice from './CourseArrangeNotice.vue'
  //bootstrap-table
  import 'bootstrap-table/js/bootstrap-table.min.js'
  import 'bootstrap-table/js/bootstrap-table-zh-CN.min.js'
  import 'bootstrap-table/css/bootstrap-table.min.css'
  import '../../../static/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js'
  import 'jquery'


  export default {
    name: 'CourseArrangement',
    components:{
      'CourseArrangeContent':CourseArrangeContent,
      'CourseArrangeNotice':CourseArrangeNotice,
      'CourseArrangeGroup':CourseArrangeGroup,
    },
    data () {
      return {
        name: '',
        course: '',
        status:'down',
        courseName: '',
        courseId: 0,
        stateMsg:'',
        className:'',
        IfShow:1,
        courseArrange:{
          topic: '',
          // stateId: 0,
          classId: 0,
          state: 0,
          id: 0,
          courseId: 0,
          courseOntime: '',
          courseDowntime: '',
          courseClass:{
            classId:'',

          }
        },
        selected: 0,
        classInfos: [],
        stateSelected: 0,
        states:[
          {'value':0,'message':'未上'},
          {'value':1,'message':'已发布'},
          {'value':2,'message':'已上'},
        ],
        courseArrangeAdd:{
          topic: '',
          classId: 0,
          state: 0,
          courseId: 0,
          courseOntime: '',
          courseDowntime: '',
          groups:'',
          arrangeData:{
              vote:"",
              nodeNormal:"",
              issue:"",
              nodeTree:"",
          }
        }
      }
    },
    watch:{
    },
    methods:{
      downToStep(){
        // if(this.courseArrangeAdd.topic!='' && this.courseArrangeAdd.arrangeData.nodeNormal!=""){
          this.IfShow++;
          this.status='down';
        // }
      },
      onCourseArrangeGroupsAdd(val){
        this.courseArrangeAdd.groups=val.group;
        this.courseArrangeAdd.classId=val.classId;
      },
      onCourseArrangeAdd(val){
          this.courseArrangeAdd = val;
      },
      addCourseArrangeToSQL(){
        var issue=JSON.parse(this.courseArrangeAdd.arrangeData.issue);
        var vote=JSON.parse(this.courseArrangeAdd.arrangeData.vote);
        for(var i=0;i<issue.length;i++){
          issue[i]={id:issue[i].id};
        }
        this.courseArrangeAdd.arrangeData.issue=JSON.stringify(issue)+"";
        for(var i=0;i<vote.length;i++){
          vote[i]={id:vote[i].id};
        }
        this.courseArrangeAdd.arrangeData.vote=JSON.stringify(vote)+"";
        axios.post('/api/courseArrange/addCourseArrange',this.courseArrangeAdd)
        .then((response) => {
          if(response.data.status==1){
            $("#addCourseArrange").model("hide");
            bus.$emit("dialog", {
              type: "success",//success default error warn
              body: " 添加成功！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
          }
        })
        .catch((error) => {
          bus.$emit("dialog", {
           type: "error",//success default error warn
           body: " 添加失败！",
           auto: false,//是否自动隐藏
           time: 5000//毫秒
         })
        });
      },
      deleteCourseArrange(value){
        this.getRowInfo(value)
      },
      deleteAfter(){
        axios({
          method: 'post',
          url: '/api/courseArrange/deleteCourseArrange',
          data:{
            id:this.courseArrange.id,
          }
          }).then((response) => {
            $("#courseList").bootstrapTable('refresh',{ url:'/api/courseArrange/getInitCourseArrange'})
            bus.$emit("dialog", {
              type: "success",//success default error warn
              body: " 删除成功！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
          }).catch(function(error) {
             bus.$emit("dialog", {
              type: "error",//success default error warn
              body: " 删除失败！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
          });

      },

      dateDefault(){
        var d, s;
        var self = this;
        d = new Date();
        s = d.getFullYear() + "-";       //取年份
        s = s + (d.getMonth() + 1) + "-";   //取月份,date生成的月份为0-11
        s += d.getDate() + " ";        //取日期
        s += d.getHours() + ":";        //取小时
        s += d.getMinutes() + ":";       //取分
        s += d.getSeconds();          //取秒
        self.courseArrange.courseOntime = s;
        $('#form_datetime').datetimepicker({
            startDate: s,
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            todayBtn: 1,
            autoclose: 1
          });
        $('#form_datetime').datetimepicker()
          .on('hide', function (ev) {
          var value = $("#form_datetime").val();
          self.courseArrange.courseOntime = value;
          });

      },
      dateDefault1(){
        var d, s;
        var self = this;
        d = new Date();
        s = d.getFullYear() + "-";       //取年份
        s = s + (d.getMonth() + 1) + "-";   //取月份,date生成的月份为0-11
        s += d.getDate() + " ";        //取日期
        s += d.getHours() + ":";        //取小时
        s += d.getMinutes() + ":";       //取分
        s += d.getSeconds();          //取秒
        self.courseArrange.courseDowntime = s;
        $('#form_datetime1').datetimepicker({
            startDate: s,
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            todayBtn: 1,
            autoclose: 1
          });
        $('#form_datetime1').datetimepicker()
          .on('hide', function (ev) {
          var value = $("#form_datetime1").val();
          self.courseArrange.courseDowntime = value;
          });

      },
      selectVal: function(ele) {
            this.selected = ele.target.value;
            this.courseArrange.classId = this.selected
            this.courseArrange.courseClass.classId = this.selected
            console.log("selectVal"+this.selected+ this.courseArrange.courseClass.classId )
      },

      stateVal: function(e) {
            this.stateSelected = e.target.value;
            this.courseArrange.state = this.stateSelected
            // this.courseArrange.courseClass.classId = this.selected
            console.log("selectVal"+this.stateSelected )
      },

      getClassList(){
         axios({
          method: 'post',
          url: '/api/courseArrange/getCourseClasses',
          data:{
            courseId: this.courseId
          },
          }).then((response) => {
            bus.$emit("dialog", {
              type: "success",//success default error warn
              body: " 获取课程安排列表成功！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
            this.classInfos = response.data.result
          }).catch(function(error) {
            alert(error);
          });
      },

      publisheCourseArrange(value){
         this.getRowInfo(value)
      },

      publisheAfter(){

         axios({
          method: 'post',
          url: '/api/courseArrange/updateStateCourseArrange',
          data:{
            id:this.courseArrange.id,
          }

          }).then((response) => {
            $("#courseList").bootstrapTable('refresh',{ url:'/api/courseArrange/getInitCourseArrange'})
            bus.$emit("dialog", {
              type: "success",//success default error warn
              body: " 发布成功！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
          }).catch(function(error) {
            bus.$emit("dialog", {
              type: "error",//success default error warn
              body: " 发布失败！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
          });
      },

      updateAfter(){
         this.courseArrange.classId = this.selected
         this.courseArrange.courseClass.classId = this.selected
         axios({
          method: 'post',
          url: '/api/courseArrange/updateCourseArrange',
          data:this.courseArrange,
          }).then((response) => {
            $("#courseList").bootstrapTable('refresh',{ url:'/api/courseArrange/getInitCourseArrange'})
            bus.$emit("dialog", {
              type: "success",//success default error warn
              body: " 修改成功！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
          }).catch(function(error) {
            bus.$emit("dialog", {
              type: "error",//success default error warn
              body: " 修改失败！",
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
          });
      },

      updateCourseArrange(value){
        this.getRowInfo(value)
        console.log(this.arrangeId)
        console.log(this.courseArrange)

      },
    jsonDateFormat(jsonDate) {
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
       },
    stateCheck(value){
          if(value==0){
            return '未上'
          }else if(value==1){
            return '已发布'
          }else if(value==2){
            return '已上'
          }else{
            return '异常'
          }
    },
    getRowInfo(value){
      var th = this
      th.arrangeId = value
      th.courseArrange.id = value
      axios({
          method: 'post',
          url: '/api/courseArrange/getCourseArrangeById',
          data: {
            arrangeId: th.arrangeId,
          },
          }).then((response) => {
            th.courseArrange.topic = response.data.result.topic
            th.courseArrange.courseOntime = th.jsonDateFormat(response.data.result.courseOntime)
            th.courseArrange.courseDowntime = th.jsonDateFormat(response.data.result.courseDowntime)
            th.selected = response.data.result.classId
            th.stateSelected = response.data.result.state
            th.className = response.data.result.courseClass.className
            th.stateMsg = th.stateCheck(response.data.result.state)
            console.log(response.data.result.state+"sdsd"+th.stateSelectVal)
          }).catch(function(error) {
            alert(error);
          });
    },
     getCourseInfo(){
      axios({
        method: 'post',
        url: '/api/getInitCourseInfo',
        data: {
          courseId: this.courseId,
        },
        }).then((response) => {
          this.course = response.data.result.name
          this.courseArrange.course = response.data.result.name
          console.log(response.data.result.description)
          console.log(response.data.result)
        }).catch(function(error) {
          alert(error);
        });
    },
    initCourseArrange(courseId){
      axios.post({
        method: 'post',
        url: '',
        data: {
          courseId: this.courseId,
        },
      })
      .then((response) => {
         console.log(this.courseId)
      })
      .catch((error) => {
        console.log(error);
      });
    },
  },
  beforeCreate(){},
  created(){
    var a = window.sessionStorage.getItem("courseId");
    console.log(a);
    if(a != undefined){
      this.courseId = 0;
      this.courseId = a;
      this.courseArrange.courseId = a;
    }else{
      this.$router.push({ name: 'Index'})
    }
  },
  beforeMount(){},
  mounted(){

    var th = this;
    th.dateDefault1();
    th.dateDefault();
    th.getClassList()
    th.getCourseInfo()

    $("#courseList") .bootstrapTable({
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
          search:true,
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
          columns:[
            {field: "topic", title: '课程主题', align: 'center', valign: 'middle'},
            {field: "courseInfo.name", title: '课程名称', align: 'center', valign: 'middle'},
            {field: "courseOntime", title: '上课时间', align: 'center', valign: 'middle', formatter: jsonDateFormat},
            {field: "courseDowntime", title: '下课时间', align: 'center', valign: 'middle', formatter: jsonDateFormat},
            {field: "courseClass.className", title: '上课班级', align: 'center', valign: 'middle'},
            {field: "state", title: '上课状态', align: 'center', valign: 'middle', formatter: stateCheck },
            {field: "operation", title: '操作', align: 'center', valign: 'middle', formatter: operateFormatter},
          ],queryParams:function(params) {
            params.courseId=th.courseId;
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
      var th = this;
      window.lookInfo = function (value) {//赋予的参数
          th.getRowInfo(value);
          $("#isCheck").modal("show");
      };

      window.update = function (value) {
          th.updateCourseArrange(value);
          $("#isUpdate").modal("show");
      };

      window.publish = function (value) {
          th.publisheCourseArrange(value);
          $("#isPublish").modal("show");
      };

      window.deleteModel = function (value) {
          th.deleteCourseArrange(value);
          $("#isDelete").modal("show");
      };

      function operateFormatter(value, row, index) {//赋予的参数
          return  '<button class="btn btn-primary"  style="margin:0 10px 0 0" OnClick="publish('+row.id+')" >发布</button>'+
                  '<button class="btn btn-success" style="margin:0 10px 0 0" OnClick="lookInfo('+row.id+')" >查看</button>'+
                  '<button class="btn btn-info" style="margin:0 10px 0 0" OnClick="update('+row.id+')">修改</button>'+
                        '<button class="btn btn-danger" style="margin:0 10px 0 0" OnClick="deleteModel('+row.id+')">删除</button>'
              ;
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


  },

    beforeUpdate(){},
    updated(){},
    beforeDestroy(){},
    destroyed(){}
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    @import '../../../static/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.css';
</style>
