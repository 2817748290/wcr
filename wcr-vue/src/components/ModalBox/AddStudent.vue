<template>
    <div class="modal fade" :id="title" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">{{title}}</h4>
          </div>
            <div class="modal-body">
                <ul class="nav nav-pills border" style="padding-left:17%">
                    <li role="presentation" id="hander"><a href="javascript:;" v-on:click="showDiv(1)">手动添加</a></li>
                    <li role="presentation"><a href="javascript:;" v-on:click="showDiv(2)">学生库添加</a></li>
                    <li role="presentation"><a href="javascript:;" v-on:click="showDiv(3)" >批量添加</a></li>
                    <li role="presentation"><a href="javascript:;" v-on:click="showDiv(4)">现场添加</a></li>
                </ul>
                <div v-show="div1">
                    <form class="form-horizontal" @submit.prevent="addStudentById">
                        <div style="margin:auto;">
                        <p style="text-align:center;font-size: 16px;color: #999999;">
                            输入学生学号即可添加学生
                        </p>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">学生学号</label>
                            <div class="col-sm-10">
                                <input type="text" v-model="studentID" class="form-control" id="inputEmail3" placeholder="学号">
                            </div>
                        </div>
                        <div>
                            <input type="submit" class="btn btn-success" style="margin-left:45%" value="添加学生">
                        </div>
                    </form>
                </div>
                <div v-show="div2">
                    <form class="form-inline">
                        <div class="form-group">
                            <select class="form-control" @change="selectCourseInfo" id="teacherId">
                                <option selected disabled>请选择老师</option>
                                <option v-for='teacher in teachers'  v-bind:value="teacher.id">
                                    {{teacher.nickname}}
                                </option>

                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" @change="selectClassInfo" id="courseId">
                                <option selected disabled>选择课程</option>
                                <option v-for="course in coursesInfo" v-bind:value="course.id">{{course.name}}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <select class="form-control" @change="getStudentInfo" id="classId">
                                <option selected disabled>选择班级</option>
                                <option v-for="item in classesInfo" v-bind:value="item.classId">{{item.className}}</option>
                            </select>
                        </div>
                        <table class="table table-bordered" style="margin-top:20px">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>学号/账号</th>
                                    <th>姓名</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-if="studentsInfo==[]?true:false">
                                    <td>未选择班级</td>
                                </tr>
                                <tr v-if="studentsInfo==null?true:false">
                                    <td>该班无学生</td>
                                </tr>
                                <tr v-if="studentsInfo!=null||studentsInfo!=[]" v-for="item in studentsInfo">
                                    <td><input type="checkbox" name='checkname'><span>1</span></td>
                                    <td>{{item.username}}</td>
                                    <td>{{item.nickname}}</td>
                                </tr>
                            </tbody>
                        </table>
                        <div>
                            <div style="display:inline">
                                <input type="checkbox" @change="clickAll()" id="checkAll" v-model='checked'><span>全选</span>
                            </div>
                        </div>
                        <div>
                        </div>
                        <div>
                            <input type="button" class="btn btn-success" value="添加"  style="margin-left:45%;margin-top:10px">
                        </div>
                    </form>
                </div>
                <div v-show="div3">
                    <div>
                        <h5>学生学号</h5>
                        <p class="pfont">填写名单时请仔细阅读模板文件中的说明文字，并严格按照其中所述规则填写。否则可能会导入失败。</p>
                    </div>
                    <div style="margin-left: 35%;">
                        <button class="btn btn-primary">下载模板</button>
                        <button class="btn btn-success">批量导入</button>
                    </div>
                </div>
                <div v-show="div4">
                    <button class="btn btn-success" :class="{closed : !allowedAdd }" style='width:60%;margin-left:20%;margin-bottom:25px' @click="toggle()">{{allowedAddMess}}</button>
                     <div id="divOne1"></div>
                        <img id='imgOne1'  style='width:60%;margin-left:20%;' />
                    <p style="margin-top:10px;font-size:30px;text-align:center;">{{className}}</p>
                </div>
            </div>
        </div>
      </div>
    </div>
</template>

<script>
require("../../assets/js/jquery.min.js");
require("../../assets/js/jquery.qrcode.min.js");
export default {
  name: 'ModalBox',
  props:{
      show:false,
      title:"",
      classId:"",
      className:""
  },
  data () {
    return {
        shows:this.show,
        div1:true,
        div2:false,
        div3:false,
        div4:false,
        checked:false,
        studentID:"",
        allStudent:[],
        class:[],
        profession:[],
        allowedAdd:false,
        allowedAddMess:"禁止加入",
        classInfo:{},
        teachers:[],
        coursesInfo:[],
        classesInfo:[],
        studentsInfo:[],
    }
  },
  watch:{
    show(val){
        //模态框打开时获取最新课程信息
        if(val){
            this.initClassInfo();
        }
        this.shows = this.show;
    },
    shows(val){
        this.$emit("on-model-change",val);
        console.log(val);
        if(val){
            this.div1 = true;
            this.div2 = false;
            this.div3 = false;
            this.div4 = false;
            console.log($("#hander"));
            $('#'+this.title).modal('show');
        }else{
            $('#'+this.title).modal('hide');
            $("#hander").removeClass("active");
        }
    }
  },
  methods:{
    getStudentInfo(ele){
        var index;
        this.classesInfo.map((item,i) => {
            if(item.classId==ele.target.value){
                index = i;
            }
        });
        this.studentsInfo = this.classesInfo[index].studentList;
        console.log(this.studentsInfo);
    },
    selectClassInfo(ele){
        axios.post('/api/addStudentGetClassByCourseId',{
            'id':ele.target.value
        }).then((response) => {
            console.log(response.data.result);
            this.classesInfo = response.data.result;
        }).catch((error) => {
            console.log(error);
        })
    },
    selectCourseInfo(ele){
        axios.post('/api/addStudentGetCourseByTeacherId',{
        'id':ele.target.value
      }).then((response) => {
          console.log(response.data);
          this.coursesInfo = response.data.result;
      }).catch((error) => {
          console.log(error);
      })
    },
    handleClick(id) {
      var a = '{"type":0,"url":"/app/addClassByScanQcode?code='+id+'"}';
      $("#divOne1").empty();
      var qrcode= $('#divOne1').qrcode(a);
      var canvas = qrcode.find('canvas').get(0);
      $('#imgOne1').attr('src',canvas.toDataURL('image/jpg'));
      $("#divOne1").empty();
    },
    toggle(){
      axios.post('/api/changeAllowedAdd',{
        classId:this.classId,
        allowedAdd:this.allowedAdd?(0):(1),
      })
      .then((response) => {
       this.classInfo= response.data.result;
        if(this.classInfo.allowedAdd == 0){
            this.allowedAdd = false;
            this.allowedAddMess = "禁止加入"
        }else{
            this.allowedAddMess = "允许加入"
            this.allowedAdd = true;
        }
      })
      .catch((error) => {
        console.log(error);
      });
    },
    initClassInfo(){
      axios.post('/api/getCourseClassByClassId',{
        'classId':this.classId+""
      })
      .then((response) => {
        this.classInfo= response.data.result;
        if(this.classInfo.allowedAdd == 0){
            this.allowedAdd = false;
            this.allowedAddMess = "禁止加入"
        }else{
            this.allowedAddMess = "允许加入"
            this.allowedAdd = true;
        }
      })
      .catch((error) => {
        console.log(error);
      });
    },
    showDiv(val){
        $("#hander").removeClass("active");
        console.log(this.classId);
        if(val==1){
            this.div2 = false;
            this.div3 = false;
            this.div4 = false;
            this.div1 = true;
        }else if(val==2){
            // axios.post('/api/getClassStudent',{'classId':1+""}).then((response)=>{
            //     console.log(response.data);
            //     this.allStudent = response.data.result;
            //     console.log(this.allStudent[0].nickname);
            // }).catch((error) => {
            //     console.log(error);
            // });
            axios.get('/api/getTeacher').then((response) => {
                console.log(response.data.result);
                this.teachers = response.data.result;
            }).catch((error) => {
                console.log(error);
            })
            this.div1 = false;
            this.div3 = false;
            this.div4 = false;
            this.div2 = true;
        }else if(val==3){
            this.div1 = false;
            this.div2 = false;
            this.div4 = false;
            this.div3 = true;
        }else{
            this.handleClick(this.classId)
            this.div1 = false;
            this.div3 = false;
            this.div2 = false;
            this.div4 = true;
        }
    },
    clickAll(){
        // console.log($("input[name='checkname']"));
        if(this.checked){
            $("input[name='checkname']").attr('checked', true);
        }else{
            $("input[name='checkname']").attr('checked', false);
        }
    },
    addStudentById(){
        console.log(this.studentID+" "+this.classId);
        axios.post('/api/addStudentIntoClass',{"username":this.studentID,"classId":this.classId+""}).then((response) => {
            if(response.data.status == 1){
              bus.$emit('courseClassDataUpdate')
            }
            alert(response.data.message);
        }).catch((error) => {
            console.log(error);
        });
    }
  },
  beforeCreate(){},
  created(){},
  beforeMount(){},
  mounted(){
    var th = this;
    $(function(){
        $('#'+th.title).on('hidden.bs.modal', function (e) {
            th.shows = false;
        })
    });
    th.div1 = true;
    $("#hander").addClass("active");
  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){},
  destroyed(){}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    /* .unactive{
        color: black;
        background-color: #f0f0f0;
    } */
    .closed{
        background-color: #d9534f;
    }
    .divActive{
        text-decoration: none;
        background-color: #337ab7;
        color: #fff;
    }
    .border{
        text-align: center;
        padding-bottom: 10px;
        /* border-bottom: 1px solid #e5e5e5; */
    }
    .nav>li>a:focus, .nav>li>a:hover {
        text-decoration: none;
        background-color: #337ab7;
        color: #fff;
    }
    li{
        font-color: #000;
        background-color: #f0f0f0;
    }
    .pfont{
        color: #999999;
        font-size: 10p;
    }
</style>
