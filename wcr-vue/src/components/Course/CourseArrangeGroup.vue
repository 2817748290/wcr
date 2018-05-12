<template>
  <div style="text-align:center;">
    <div class="row">
      <div class="form-group col-md-4">
        <label for="message-text" class="control-label">课程班级:</label>
        <select id="GroupOfClass" v-model="SelectClassInfo" class="form-control " data-live-search="true">
          <option value="-1">请选择班级</option>
          <option v-for="item in classInfos" :value="item">{{ item.className }}</option>
        </select>
      </div>
      <div class="form-group col-md-4">
        <label for="message-text" class="control-label">分组形式:</label>
        <select id="GroupOfSend" v-model="SelectGroupInfo" class="form-control " data-live-search="true">
          <option value="-1">随机分组</option>
          <option v-for="item in groupInfos" :value="item">{{ item.groupName }}(历史分组)</option>
        </select>
      </div>
      <div class="form-group col-md-3" v-if="oldSelectIf">
        <label for="message-text" class="control-label">布局形式:</label>
        <select id="GroupOfState" v-model="sel_layout" class="form-control " data-live-search="true">
          <option value="0">请改变布局形式</option>
          <option value="4">4人一组</option>
          <option value="5">5人一组</option>
          <option value="6">6人一组</option>
        </select>
      </div>
      <div class="form-group col-md-1" v-if="oldSelectIf">
        <button id="button" type="button" class="btn btn-danger" style="height:50px;" @click="openAddModel()">保存分组</button>
      </div>
    </div>
    <div class="form-group">
      <label for="message-text" v-if="SelectClassInfo!=-1" class="control-label">课程班级:{{classInfo.className}}  共：{{total}}人  共{{groupNum}}个组 每组{{groupStuNum}}人</label>
      <div class="row" v-if="!show">
        <div v-for="(item,index) in groups" class="col-md-4" style="padding-left:2%;margin-top:1%;">
          <div class="row" :id="item.groupId" @drop='dropRow($event)' @dragover='allowDrop($event)'>
            <div class="col-md-4" :id="item.groupId" @drop='dropMd($event)' @dragover='allowDrop($event)'>
              <label v-for="(item1,index1) in item.group" v-if="index1<item.group.length/2" draggable="true" :id="item1.id" @dragstart='drag($event)' @drop='drop($event)' @dragover='allowDrop($event)' :style="{borderStyle: index1 ==0 ? ('solid') : (null)}" class="lab_name">{{item1.nickname}}</label>
              <!-- <label draggable="true" :id="item.group[0].key" @dragstart='drag($event)' @drop='drop($event)' @dragover='allowDrop($event)' v-if="item.group[0]!=null" class="lab_name" style="border-style:solid;">{{item.group[0].name}}</label> -->
            </div>
            <div class="col-md-4" :id="item.groupId" @drop='drop($event)' @dragover='allowDrop($event)'>
              <label class="lab_ball" :id="item.groupId" :style="{backgroundColor:color[index%10]}">{{index+1}}</label>
              <button type="button" :id="item.groupId" class="btn btn-danger" :style="{'backgroundColor':(item.issueId=='' || item.issueId==null) ?(''):('green')}" @click="openIssueModal(item.groupId)">{{(item.issueId=="" || item.issueId==null) ? ("未派题"):("已派题")}}</button>
            </div>
            <div class="col-md-4" :id="item.groupId" @drop='dropMd($event)' @dragover='allowDrop($event)'>
              <label v-for="(item1,index1) in item.group" v-if="index1>=item.group.length/2" draggable="true" :id="item1.id" @dragstart='drag($event)' @drop='drop($event)' @dragover='allowDrop($event)' class="lab_name">{{item1.nickname}}</label>
            </div>
          </div>
        </div>
      </div>
      <div class="row" v-if="show">
        <div class="col-md-2">
          <label v-for="(item,index) in AllStudent" v-if="index<total/4" class="lab_name">{{item.nickname}}</label>
        </div>
        <div class="col-md-2">
          <label v-for="(item,index) in AllStudent" v-if="index<total/2 && index>=total/4" class="lab_name">{{item.nickname}}</label>
        </div>
        <div class="col-md-4">
          <label class="lab_ballOne" style="background-color:orange;margin-top:10%;">1</label>
        </div>
        <div class="col-md-2">
          <label v-for="(item,index) in AllStudent" v-if="index>=total/2 && index<3*total/4" class="lab_name">{{item.username}}</label>
        </div>
        <div class="col-md-2">
          <label v-for="(item,index) in AllStudent" v-if="index>=3*total/4" class="lab_name">{{item.username}}</label>
        </div>
      </div>
    </div>

    <div class="modal fade" id="addGroupModal" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">保存分组</h4>
          </div>
          <div class="modal-body">
            <form id="addGroupForm" role="form" >
              <div class="form-group">
                <label for="message-text" class="control-label">分组名称:</label>
                <input type="text" v-model="groupName" class="form-control" id="groupName" placeholder="请输入名称">
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" @click="modelHide()" >取消</button>
            <button type="button" class="btn btn-primary"  @click="addClassGroup()">确定</button>
          </div>
        </div>
      </div>
    </div><!-- 保存分组 -->

    <div class="modal fade" id="addIssueModal" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">分派议题</h4>
          </div>
          <div class="modal-body">
            <form id="addGroupForm" role="form" >
              <div class="form-group">
                <div class="panel-body" style="padding-bottom:0px;">
                  <table id="issuesTableForSend"></table>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <!-- <button type="button" class="btn btn-default" @click="modelHide()" >取消</button> -->
            <button type="button" class="btn btn-primary"  @click="sendIssue">确定</button>
          </div>
        </div>
      </div>
    </div><!-- 分派议题 -->

  </div><!-- 分组 -->
</template>

<script>
export default {
  name: 'CourseArrangeGroup',
  props:{
    classInfos:"",
    IfShow:"",
    status:"",
    CourseArrangeIssue:"",
  },
  data () {
    return {
      datas:[],
      sendIssueIf:'未派题',
      sel_layout:'0', //选择的几人一个小组
      total:0,  //学生总数
      AllStudent:[],  //放返回学生数组
      groups:[],  //AllStudent数组经过分组算法后分组，并放入groups中
      show:false,
      color:['#CE0000', //分组号圆的颜色
      '#0000E3',
      '#009393',
      '#009100',
      '#8C8C00',
      '#804040',
      '#808040',
      '#408080',
      '#5A5AAD',
      '#8F4586'],
      stuId1:"",  //放置移动的学生ID
      stuId2:"",  //放置移动到的学生ID
      groupId:"", //放置移动到的小组ID
      groupNum:1, //小组数量
      groupStuNum:0,  //小组人数
      groupName:"", //保存弹框中的小组名称
      classInfo:{className:""}, //选择的班级
      SelectClassInfo:'-1', //选择框默认值
      groupInfos:{groupName:""},  //选择的分组
      SelectGroupInfo:'-1', //选择框默认值
      oldSelectIf:true, //是否选择了历史分组
    }
  },
  watch:{
    //监听班级选择
    SelectClassInfo(val){
      //绑定分组和学生先置空
      this.AllStudent=[];
      this.groups=[];
      //布局形式回到默认值
      this.sel_layout='0';
      //班级选择不为默认值时进行下一步操作
      if(val!=-1){
        var classId=val.classId;
        this.getStudentList(classId);
        this.show=true;
        this.classInfo=val;
        this.SelectGroupInfo='-1';
        this.getClassGroupByClassId();
      }else{
        this.show=false;
      }
    },
    //监听分组选择
    SelectGroupInfo(val){
      this.groups=[];
      if(val!=-1){
        this.getStudentListByGroupId(val.id);
        // this.groups=JSON.parse(val.groups);
        this.show=false;
        this.oldSelectIf=false;
      }else{
        this.oldSelectIf=true;
        this.show=true;
        this.sel_layout='0';
        this.getClassGroupByClassId();
      }
    },
    //监听布局形式选择
    sel_layout(val){
      //不为一组，不显示一个圆
      this.show=false;
      if(val==4){
        this.sendGroup(4)
        this.groupStuNum=4;
      }
      else if(val==5){
        this.sendGroup(5)
        this.groupStuNum=5;
      }
      else if(val==6){
        this.sendGroup(6)
        this.groupStuNum=6;
      }
      else{
        this.show=true;
        this.groups=[];
        //不选布局形式时为一个组，每组人数为全班总人数
        this.groupNum=1;
        this.groupStuNum=this.total;
      }
    },
    IfShow(val){
      if(this.status=='down' && val==3){
        this.groupsHandle();
        this.$emit("course-arrange-content-data",{group:JSON.stringify(this.groups),classId:this.classInfo.classId});
      }
    },
  },
  methods:{
    getStudentListByGroupId(val){
      axios.post('/public/getStudentListByGroupId',{
        id:val,
      }
       ).then((response) => {
         if(response.data.status==0){
           bus.$emit("dialog", {
            type: "error",//success default error warn
            body: " 网络出错啦！",
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          })
         }else{
           // console.log(response.data.result);
           this.groups=response.data.result;
         }
       }).catch(function(error) {
         alert(error);
       });
    },
    //返回前将学生数据处理成只有id
    groupsHandle(){
      for(var i=0;i<this.groups.length;i++){
        for(var j=0;j<this.groups[i].group.length;j++){
          this.groups[i].group[j]={id:this.groups[i].group[j].id};
        }
      }
    },
    openIssueModal(id){
      // console.log(this.CourseArrangeIssue);
      if(this.groups[id-1].issueId!=""){
        $('#issuesTableForSend').bootstrapTable("check",1);
      }
      this.groupId=id;
      var Issue=JSON.parse(this.CourseArrangeIssue);
      $('#issuesTableForSend').bootstrapTable("load",Issue);
      $("#addIssueModal").modal('show');
    },
    sendIssue(){
      var row=$('#issuesTableForSend').bootstrapTable('getSelections');
      if(row[0]!=null){
        this.groups[this.groupId-1].issueId=row[0].id;
        var da=this.groups;
        this.groups=[];
        this.groups=da;
        // console.log(this.groups[this.groupId].issueId);
        $("#addIssueModal").modal('hide');
      }
    },
    //打开保存分组的模态框，写成方法是为了打开时groupName为空
    openAddModel(){
      this.groupName="";
      $("#addGroupModal").modal('show');
    },
    modelHide(){
      $("#addGroupModal").modal('hide');
    },
    //选择班级后取该班级所有的学生
    getStudentList(classId){
       axios({
        method: 'post',
        url: '/api/courseArrange/getClassStudentsForGroup',
        data:{
          classId: classId+""
        },
        }).then((response) => {
          this.AllStudent = response.data.result;
          this.total=this.AllStudent.length;
          this.groupStuNum=this.total;
        }).catch(function(error) {
          alert(error);
        });
    },
    //保存分组到数据库
    addClassGroup(){
      var groups=this.groups;
      this.groupsHandle();
      var groups=JSON.stringify(this.groups);
      var ClassGroup={groupName:this.groupName,groups:groups,classId:this.SelectClassInfo.classId};
      axios.post('/api/classGroup/addClassGroup',ClassGroup
       ).then((response) => {
         this.groups=groups;
         if(response.data.status==0){
           // alert(response.data.message);
           bus.$emit("dialog", {
            type: "error",//success default error warn
            body: " 保存失败！",
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          })
         }else{
           // alert(response.data.message);
           bus.$emit("dialog", {
             type: "success",//success default error warn
             body: " 保存成功！",
             auto: true,//是否自动隐藏
             time: 5000//毫秒
           })
           this.getClassGroupByClassId();
           $("#addGroupModal").modal('hide');
         }
       }).catch(function(error) {
         alert(error);
       });
    },
    //选择班级后取该班级的历史分组
    getClassGroupByClassId(){
      axios.post('/api/classGroup/getClassGroupByClassId',{classId:this.SelectClassInfo.classId}
       ).then((response) => {
         if(response.data.status==0){
           alert(response.data.message);
         }else{
           this.groupInfos=response.data.result;
         }
       }).catch(function(error) {
         alert(error);
       });
    },
    //选择布局形式后进行的随机分组算法
    sendGroup(count){
      var da=this.AllStudent;
      var len=da.length;
      this.groups=[];
      var id=0;
      while (len>0) {
        var s=[];
        for(var j=0;j<count;j++){
          var i=Math.ceil(Math.random()*da.length-1); //0-数组总数之间的随机数
          if(da[i]!=null){
            s.push(da[i]);
          }
          //以下为数组删除一个item的蠢办法
          var copy=[];
          for(var k=0;k<da.length;k++){
            if(k!=i){
              copy.push(da[k]);
            }
          }
          da=copy;
          len--;
        }
        id++;
        this.groups.push({group:s,groupId:id,issueId:''});
      }
      this.groupNum=id;
    },
    //移动学生时触发的方法
    drag(event){
       this.stuId1=event.target.id;
    },
    //移动到某分组空白处时触发的方法
    dropRow(event){
      if(event.target.className == 'row'){
        this.groupRemoveToAdd(event);
      }
    },
    //移动到某分组序号圆和派题按钮时触发的方法
    dropMd(event){
      if(event.target.className == 'col-md-4'){
        this.groupRemoveToAdd(event);
      }
    },
    //学生互换位置时触发的方法
    drop(event){
      event.preventDefault();
      if(event.target.className == 'lab_name'){
        this.stuId2=event.target.id;
        var stu="",stu1="",stu2="",stu3="";
        this.groupId=event.target.id;
        for(var i=0;i<this.groups.length;i++){
          for(var j=0;j<this.groups[i].group.length;j++){
            if(this.groups[i].group[j].id==this.stuId1){
              stu=i;stu1=j;
            }
            if(this.groups[i].group[j].id==this.stuId2){
              stu2=i;stu3=j;
            }
          }
        }
        var move="";
        var groupMove=this.groups;
        this.groups=[];
        move=groupMove[stu].group[stu1];
        groupMove[stu].group[stu1]=groupMove[stu2].group[stu3];
        groupMove[stu2].group[stu3]=move;
        this.groups=groupMove;
      }else{
        this.groupRemoveToAdd(event);
      }
    },
    //移动中触发的方法
    allowDrop(event){
      event.preventDefault();
    },
    //移动到新的小组时真正执行的方法
    groupRemoveToAdd(event){
      var stu="",null_group=0;
      this.groupId=event.target.id;
      //循环所有小组中的所有人
      for(var i=0;i<this.groups.length;i++){
        var g=[];
        for(var j=0;j<this.groups[i].group.length;j++){
          //将移动的学生在该分组中删除
          if(this.groups[i].group[j].id!=this.stuId1){
            g.push(this.groups[i].group[j]);
          }else{
            stu=this.groups[i].group[j];
          }
        }
        if(g.length==0){
          null_group=i;
        }
        this.groups[i].group=g;
      }
      //添加该学生到移动到的新分组中
      this.groups[this.groupId-1].group.push(stu);
      //当移动后原分组为空时，则去掉这个分组
      if(null_group!=0){
        var groupCopy=[];
        for(var i=0;i<this.groups.length;i++){
          if(i!=null_group){
            groupCopy.push(this.groups[i]);
          }
        }
        this.groups=groupCopy;
      }
    },
  },
  beforeCreate(){},
  created(){},
  beforeMount(){},
  mounted(){
    var th=this;
    $("#issuesTableForSend") .bootstrapTable({
        cache:false,
        searchAlign:'left',
        sortOrder:'asc',
        striped:true,//隔行变色
        search:false,
        searchOnEnterKey:true,
        showRefresh:true,
        sidePagination:'client',
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        clickToSelect: true,
        pageList:'[10, 25, 50, 100, All]',
        showToggle:true,
        columns:[
          {field: "state", checkbox: true},
          {field: "content", title: "议题内容",sortable:true},
          // {field: "phone", title: "联系方式",sortable:true},
        ],
        // data:th.CourseArrangeIssue,
    });
  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){},
  destroyed(){}
}
</script>

<style scoped>
.lab_ball{
  width: 100px;
  height:100px;
  font-size: 80px;
  color: #FFF;
  line-height: 100px;
  text-align: center;
  border-radius: 50px;
}
.lab_name{
  width: 120px;
  display:block;
  font-size: 20px;
  height: 35px;
  background-color: #E0E0E0;
  border:5px;
}
.lab_ballOne{
  width: 200px;
  height:200px;
  font-size: 160px;
  color: #FFF;
  line-height: 200px;
  text-align: center;
  border-radius: 100px;
}
</style>
