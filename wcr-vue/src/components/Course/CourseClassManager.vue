<template>
    <div>
      <div class="container-class">
        <label class="label-classname">{{className}}</label>
        <button type="button" class="btn btn-default btn-class" data-toggle="modal" data-target="#editModal">
          重命名
        </button>
      </div>
      <div>
        <label class="label-count">该班共有{{count}}名学生</label>
      </div>
      <div class="box-manager" style="margin-left:auto;margin-right:auto;">
        <div class="box box-danger">
           <div class="box-body">
             <div id="toolbar" >
                <div class="btn-group">
                  <button id="btn_addl" type="button" class="btn btn-default"
                    v-on:click="show1">
                   <span class="glyphicon glyphicon-plus" aria-hidden="true" ></span>添加学生
                   </button>

                   <button id="btn_editl" type="button" class="btn btn-default"
                   @click="levalEdit()">
                     <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>导出名单
                   </button>

                   <button id="btn_delectl" type="button" class="btn btn-default"
                   @click="deleteClassStudent()">
                     <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                   </button>
                </div>
                <div style="display:inline-block;">
               <select id="selectSearchName" class="form-control">
                 <!-- <option value="id">学号</option> -->
                 <option value="username">学号</option>
                 <option value="classId">行政班</option>
                 <option value="phone_number">手机号码</option>
               </select>
             </div>
            </div>
             <table id="s"></table>
           </div>
         </div>
      </div>
      <ModalBox :show="customShow1" :title="'添加学生'" :classId="classId" :className="className" @on-model-change = "onModelChange1">
          Launch demo modal1
      </ModalBox>

      <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
           <div class="modal-dialog">
             <div class="modal-content">
               <div class="modal-header">
                 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                   &times;
                 </button>
                 <h4 class="modal-title" id="myModalLabel">重命名</h4>
               </div>
               <div class="modal-body">
                 <form id="addStudentForm" role="form" >
                   <div class="form-group">
                     <label for="message-text" class="control-label">班级名称:</label>
                     <input type="text" class="form-control" id="className" v-model="editClassName" placeholder="请输入班级名称">
                   </div>
                 </form>
               </div>
               <div class="modal-footer">
                 <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
                 <button type="button" class="btn btn-primary" id="addRecord" @click="updateClassName()">提交</button>
               </div>
             </div>
           </div>
         </div><!-- 重命名 -->

    </div>
</template>

<script>
import 'bootstrap-table/js/bootstrap-table.min.js'
import 'bootstrap-table/js/bootstrap-table-zh-CN.min.js'
import 'bootstrap-table/css/bootstrap-table.min.css'
import ModalBox from '../ModalBox/AddStudent'
export default {
  name: 'CustomModel',
  components:{
    'ModalBox': ModalBox,
  },
  props:{

  },
  data () {
    return {
      classId:this.$route.query.classId,
      count:0,
      className:"",
      editClassName:"",
      customShow1:false,
    }
  },
  watch:{
    classId(){
      $('#s').bootstrapTable(
        "refresh"
      );
    }
  },
  methods:{
    initClassName(classId){
      axios.post('/api/getCourseClassByClassId',{
        'classId':classId+""
      })
      .then((response) => {
        this.className= response.data.result.className;
        this.editClassName=this.className;
      })
      .catch((error) => {
        console.log(error);
      });
    },
    show1(){
      this.customShow1 = true;
    },
    onModelChange1(val){
      console.log(this.customShow1);
      this.customShow1 = val;
    },
    updateClassName(){
      axios.post('/api/editClassName',{
        classId:this.classId,
        className:this.editClassName,
      })
      .then((response) => {
        this.className= response.data.result.className;
        this.editClassName=this.className;
        bus.$emit('changeClassName')
        $("#editModal").modal('hide');
      })
      .catch((error) => {
        console.log(error);
      });
    },
    deleteClassStudent(){
      var row=$('#s').bootstrapTable('getSelections');
      if(row[0]!=null){
        if (confirm("你确定删除吗？删除后无法复原哦！")) {
          axios.post('/api/deleteClassStudent',{
            classId:this.classId,
            studentId:row[0].id,
          })
          .then((response) => {
            if(response.data.status==1){
              $('#s').bootstrapTable(
                "refresh"
              );
              alert("删除成功！");
            }
          })
          .catch((error) => {
            alert("网络出现故障！");
            console.log(error);
          });
        }
      }
    },
  },
  beforeCreate(){},
  created(){
    this.initClassName(this.classId);
  },
  beforeMount(){},
  mounted(){
    var th = this;
    bus.$on('classIdChange',function(id){
      th.classId=id;
      console.log("classId:"+th.classId);
      th.initClassName(id);
    });
    bus.$on('courseClassDataUpdate',function(){
      $('#s').bootstrapTable("refresh");
    });

    $("#s") .bootstrapTable({
          url:'/api/getAllCourseClass',
          ajaxOptions:{
              headers: {"token":window.sessionStorage.getItem("token")}
          },
          cache:false,
          toolbar:"#toolbar",
          singleSelect:true,
          sortName:'id',
          searchAlign:'left',
          sortOrder:'asc',
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
            {field: "state", checkbox: true},

            {field: "username", title: "学号/账号"},
            {field: "nickname", title: "姓名"},
            {field: "defaultClass.className", title: "行政班"},
            {field: "phoneNumber", title: "手机号码"},
          ],queryParams:function(params) {
            params.searchName = $("#selectSearchName").val();
            params.classId=th.classId;
            return params;
          },responseHandler:function(res){
            th.count=res.total;
            return res;
          }
      });
  },
  beforeUpdate(){},
  updated(){

  },
  beforeDestroy(){
    bus.$off('classIdChange');
    bus.$off('courseClassDataUpdate');
  },
  destroyed(){}
}
</script>

<style scoped>
.label-classname{
  font-size: 45px;
  margin-top:3%;
  margin-left:5%;
  margin-right:2%;
}
.btn-class{
  line-height: 30px;
}
.container-class{

}
.label-count{
  margin-left:5%;
  margin-top:2%;
  font-size: 20px;
}
.box-manager{
  margin-top: 3%;
  width: 95%;
  text-align: center;
}
</style>
