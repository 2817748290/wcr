<template>

  <div id="CourseInfo" style="margin-left:1%">

      <div class="col-md-12 ">
            <p class="text-left"><font size="12" color="#000000" face="微软雅黑">课程信息</font><p>
            <p class="text-left"><font size="4" color="#000000" face="微软雅黑">课程名称：  {{ name }}</font><button class="btn" style="margin:-5px 0px 0px 10px; border-radius:100px" data-toggle="modal" data-target="#showCourseName">✚</button></p>
            <p class="text-left"><font size="4" color="#000000" face="微软雅黑">授课教师：  {{ teacher }}</font></p>

            <div>
              <p class="text-left" style="float:left;text-align:center;" ><font size="4" color="#000000" face="微软雅黑">课程图片： </font></p>
                <form id="uploadForm" enctype="multipart/form-data">
                  <p class="text-left">
                  <img v-bind:src="coverImgUrl" id='show'  style="float:left;text-align:center;"><br>
                  <button id="cropper-vue" type="button" class="btn input-file" data-toggle="modal" data-target="#myModal">∧选择课程方面</button></p>
                </form>
                
            </div>

            <!-- 课程名称修改 -->
            <div class="modal fade" id="showCourseName" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" >
                    <div class="modal-content">
                      <div class="modal-header">
                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                          <h4 class="modal-title" style="text-align:center;" id="showCourseName">课程名称</h4>
                      </div>
                      <div class="modal-body" style="text-align:center;">
                        <input type="text" class="form-control" v-model="name">
                      </div>
                      <div class="modal-footer"  style="text-align:center;">
                          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                          <button id="" type="button" class="btn btn-danger" data-dismiss="modal" @click="updateCourseInfo">保存</button>
                      </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>

            <!-- 图片上传 -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" style="width:85%">
                    <div class="modal-content" style="height:650px">
                        <cropper @transfer="transfer" @isModelShow="isModelShow" style="margin: 1% auto auto auto"></cropper>
                        <button id="model-close" type="button" class="btn btn-default btn-close" data-dismiss="modal" style="display:none">关闭</button>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>
            <div style="margin-top:10%;height:200px">
              <p class="text-left"><font size="4" color="#000000" face="微软雅黑">课程简介：</font></p>
              <div id="summernote"  class="summernote">

              </div>
              <button id="save" class="btn btn-success" style='margin-left:45%' @click="save">保存</button>
              <button id="edit" class="btn btn-info" @click="edit">编辑</button>
            </div>

    </div>
  </div>
</template>


<script>

  // import CustomModel from './model'

  //summernote富文本
  import 'summernote/dist/summernote.css'
  import 'summernote/dist/summernote.js'
  import '../../../static/summernote/lang/summernote-zh-CN.js'
  import 'jquery'
  import cropper from './cropper'

  export default {
    name: 'classname',
    components:{
      //  'CustomModel': CustomModel,
      cropper
    },
    data () {
      return {
        name: 'classname',
        teacher: '老师',
        coverImgUrl:'',
        uploadcoverImgUrl:'',
        markupStr: '',
        courseId:0,
        modelSrc:'',
        isShow: false,
      }
    },
    watch:{

    },
  methods:{
    null(){
      $('#summernote').summernote('destroy');
    },
    isModelShow (...data) {
      this.isShow = data[0]
      if(!data[0]){
        $('#model-close').click()
        this.updateCourseInfo();
      }
      console.log(data[0])
    },
    transfer (...data) {
      this.coverImgUrl = data[0]
    },
    updateCourseInfo(){
      axios({
        method: 'post',
        url: '/api/updateCourseInfo',
        data: {
          courseId: this.courseId,
          name: this.name,
          description: this.markupStr,
          coverImgUrl:this.coverImgUrl
        },
        }).then((response) => {
           bus.$emit('coverImgUrlChange',this.coverImgUrl);
           bus.$emit('nameChange',this.name);
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
          this.name = response.data.result.name
          this.teacher = response.data.result.teacher.nickname
          this.coverImgUrl = response.data.result.coverImgUrl
          this.markupStr = response.data.result.description
          $('#summernote').summernote('code', this.markupStr);
          console.log(response.data.result.description)
          console.log(response.data.result)
          this.null()
          
        }).catch(function(error) {
            bus.$emit("dialog", {
            type: "error",//success default error warn
            body: " 课程信息获取失败！",
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          })
        });
    },
    save(){
      this.markupStr = $('#summernote').summernote('code');
      $('#summernote').summernote('destroy')
      this.updateCourseInfo()
      console.log(this.markupStr)
      bus.$emit('coverImageUrlChange',this.coverImgUrl);
      bus.$emit("dialog", {
        type: "success",//success default error warn
        body: " 课程信息已保存！",
        auto: true,//是否自动隐藏
        time: 5000//毫秒
      })
    },
    edit(){
      $('#summernote').summernote({focus:true})


    },
   
    upload(){
      var formData = new FormData();
      formData.append('file', $('#file')[0].files[0]);
      let config = {
        headers: {'Content-Type': 'multipart/form-data'}
      }
      axios.post("/api/courseInfo/upload",formData,config).then(response => {
        this.coverImgUrl = response.data.result
        console.log(this.coverImgUrl)
      }).catch(function (error) {
    　　alert(error);
      });

      alert("操作成功！");
    },

    C(){
      var  r= new FileReader();
      var f=document.getElementById('file').files[0];
      r.readAsDataURL(f);
      r.onload=function  (e) {
      document.getElementById('show').src=this.result;
      };
    },
  },
  beforeCreate(){},
  created(){
    
    var a = window.sessionStorage.getItem("courseId");
    console.log(a);
    if(a != undefined){
      this.courseId = 0;
      this.courseId = a;
    }else{
      this.$router.push({ name: 'Index'})
    }
  },
  beforeMount(){},
  mounted(){
   
    this.getCourseInfo();
   
    $(document).ready(function () {
       
        $('#summernote').summernote({
            height: 250,
            tabsize: 2,
            lang: 'zh-CN',
            htmlMode: true,
            disableDragAndDrop: false,
            focus: true, 
            toolbar: [
                // [groupName, [list of button]]
                ['style', ['bold', 'italic', 'underline', 'clear']],
                ['font', ['strikethrough', 'superscript', 'subscript']],
                ['fontsize', ['fontsize']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['height', ['height']],
                ['insert', ['picture']]
              ],
            callbacks: {
              onImageUpload: function(files) {
                console.log("1111")
                sendFile(files[0])
                }
            }
        });
         
    });
    
   function sendFile(file) {
    console.log("222")
    var data = new FormData();
    data.append("file", file);
    let config = {
      headers: {'Content-Type': 'multipart/form-data'}
    }
    axios.post("/api/courseInfo/upload",data,config).then(response => {
      $("#summernote").summernote('insertImage',response.data.result, 'image name'); // the insertImage API
    }).catch(function (error) {
　　 alert(error);
    });

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

  img{
    height: 150px;
    width: 180px;
    margin: 1% 1% 0% 1%;
  }
  .m{ width: 800px; margin-left: auto; margin-right: auto; }
  .input-file{
    display: inline-block;
    position: relative;
    overflow: hidden;
    text-align: center;
    width: auto;
    background-color: #2c7;
    border: solid 1px #ddd;
    border-radius: 4px;
    padding: 5px 10px;
    font-size: 12px;
    font-weight: normal;
    line-height: 18px;
    color:#fff;
    text-decoration: none;
  }
  .input-file input[type="file"] {
    position: absolute;
    top: 0;
    right: 0;
    font-size: 14px;
    background-color: #fff;
    transform: translate(-300px, 0px) scale(4);
    height: 40px;
    opacity: 0;
    filter: alpha(opacity=0);
  }
</style>
