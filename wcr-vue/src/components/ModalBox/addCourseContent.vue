<template>

  <div class="modal-body" style="height:auto">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">上传内容</a></li>
      <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">内容库</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">

      <div role="tabpanel" class="tab-pane active" id="home" >

        <div class="line line-dashed line-lg pull-in"></div>
        <div class="form-group">
          <div class="form-group" style="width:100%;">
            <input id="JSh_ZhP" type="file" class="file" name="file" multiple>
          </div>
        </div>
      </div>

      <div role="tabpanel" class="tab-pane" id="profile">

        <div class="btn-group" data-toggle="buttons" style="margin-top:2%;float:left">
          <label @click="typeChange(0)" class="btn btn-default">
            <input type="radio" name="options" id="option1" autocomplete="off" checked>全部
          </label>
          <label @click="typeChange(1)" class="btn btn-default">
            <input type="radio" name="options" id="option2" autocomplete="off">视频
          </label>
          <label @click="typeChange(2)" class="btn btn-default">
            <input type="radio" name="options" id="option3" autocomplete="off">课件
          </label>
          <label @click="typeChange(3)" class="btn btn-default">
            <input type="radio" name="options" id="option4" autocomplete="off" checked>文件
          </label>
          <label @click="typeChange(4)" class="btn btn-default">
            <input type="radio" name="options" id="option5" autocomplete="off" checked>其他
          </label>
        </div>

        <table class="table table-striped" id="tab">
        </table>

      </div>

    </div>

  </div>

</template>

<script>

  import ModalBox from '../ModalBox/AddStudent'
  import 'bootstrap-table/js/bootstrap-table.min.js'
  import 'bootstrap-table/js/bootstrap-table-zh-CN.min.js'
  import 'bootstrap-table/css/bootstrap-table.min.css'
  import '../../../static/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js'
  import 'bootstrap-fileinput/js/fileinput.min.js'
  import 'bootstrap-fileinput/css/fileinput.min.css'
  import 'bootstrap-fileinput/js/zh.js'
  import  'jquery'


  export default {
    name: 'addContent',

    data () {
      return {

        id:"",//设置文件在数据库中所对应的id的容器
        type:'0',//设置文件分类按钮在触发时所对应的值的容器

      }
    },
    watch:{
      //携带nodeId值
      nodeId(val){
        $("#tab").bootstrapTable('refresh');
        $('#JSh_ZhP').fileinput('clear');
        $('#JSh_ZhP').fileinput('destroy');
        $("#JSh_ZhP").fileinput({
          showUpload: true,//是否显示上传总按钮
          showRemove: true,//是否显示移除总按钮
          uploadAsync: true,//默认异步上传
          showCaption: true,//是否显示标题
          language: "zh",//配置语言
          uploadUrl: "/api/addContent/upload",
          ajaxSettings:{headers :{token:window.sessionStorage.getItem("token")}},
          maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
          maxFileCount: 20, /*允许最大上传数，可以多个，当前设置单个*/
          msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
          dropZoneTitle: "请通过拖拽文件放到这里",
          dropZoneClickTitle: "或者点击此区域添加文件",
          showBrowse: false,
          showPreview: true,
          browseOnZoneClick: true,
          dropZoneEnabled: true,//是否显示拖拽区域
          layoutTemplates: {
            actionZoom: ''
          },uploadExtraData:{
            fileName:"fileName",
            nodeId:val
          },
        });
      }
    },

    props: {
      nodeId: {
        type: Number,
        default: 0
      }
    },

    methods:{

      //文件分类按钮触发时改变对应的值并刷新table表
      typeChange(val){
        this.type = val;
        $("#tab").bootstrapTable('refresh');
      },

      //从数据库删除文件并在执行后刷新表格
      deleteAfter(){
        axios({
          method: 'post',
          url: '/api/deleteFile',
          data:{
            id:this.id,
          }
        }).then((response) => {
          $("#tab").bootstrapTable('refresh');
        }).catch(function(error) {
          bus.$emit("dialog",{
            type:"false",
            body:"删除失败",
            auto:false,
          })
        });
      },

    },
    beforeCreate(){},
    created(){},
    beforeMount(){},
    mounted() {
      var th = this;

      $("#tab") .bootstrapTable({
        url:'/api/getFileInfoById',
        ajaxOptions:{
          headers: {"token":window.sessionStorage.getItem("token")}
        },
        cache:false,
        toolbar:'#toolbar',
        striped:true,//隔行变色
        showRefresh:true,
        method:'post',
        sortOrder:'desc',
        sortName:'id',
        sidePagination:'server',
        pagination:true,
        paginationLoop:false,
        pageNumber:'1',
        pageSize:'10',
        pageList:'[10, 25, 50, 100, All]',
        clickToSelect: true,
        showToggle:true,
        columns:[
          {field: "fileName", title: "文件名"},
          {field: "fileType", title: "类型"},
          {field: "gmtCreate", title: "上传时间",formatter: format},
          {field:"operation", title:"操作",align: 'center', valign: 'middle',width:'50',formatter: operateFormatter}
        ],queryParams:function(params) {
          params.tags = th.type;
          params.nodeId=th.nodeId;
          return params;
        },responseHandler:function(res){
          return res.result;
      }
      });

      $("#JSh_ZhP").fileinput({

        showUpload: true,//是否显示上传总按钮
        showRemove: true,//是否显示移除总按钮
        uploadAsync: true,//默认异步上传
        showCaption: true,//是否显示标题
        language: "zh",//配置语言
        uploadUrl: "/api/addContent/upload",
        ajaxSettings:{headers :{token:window.sessionStorage.getItem("token")}},
        maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
        maxFileCount: 20, /*允许最大上传数，可以多个，当前设置单个*/
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        dropZoneTitle: "请通过拖拽文件放到这里",
        dropZoneClickTitle: "或者点击此区域添加文件",
        showBrowse: false,
        showPreview: true,
        browseOnZoneClick: true,
        dropZoneEnabled: true,//是否显示拖拽区域
        layoutTemplates: {
          actionZoom: ''
        },uploadExtraData:{
          fileName:"fileName",
          nodeId:th.nodeId
        },
      });

      //判断是否上传成功
      $('#JSh_ZhP').on("fileuploaded", function (event, data) {
        var result = data.response; //后台返回的json
        if(data.response.status==1)
        {
          $("#tab").bootstrapTable('refresh');
          bus.$emit("dialog",{
            type:"success",
            body:"上传成功",
            auto:true,
            time:3000   //毫秒
          });
        }else{
          bus.$emit("dialog",{
            type:"false",
            body:"上传失败",
            auto:false,
          })
        }
      });

      //table表中的删除按钮
      function operateFormatter(value, row, index) {//赋予的参数
        return [
          '<button class="RoleOfdelete btn btn-danger" style="margin:0 10px 0 0" OnClick="deleteModel('+row.id+')">删除</button>'
        ]
      }
      window.deleteModel = function (value) {
        th.id = value;
        var r=confirm("是否删除");
        if (r==true)
        {
          th.deleteAfter();
          bus.$emit("dialog",{
            type:"false",
            body:"删除成功",
            auto:true,
            time:3000
          })
        }
      };

      //将时间戳格式改成yyyy-MM-dd hh-mm-ss显示格式
      function add0(m){return m<10?'0'+m:m }
      function format(timeStamp)
      {
        //timeStamp，否则要parseInt转换
        var time = new Date(timeStamp);
        var y = time.getFullYear();
        var m = time.getMonth()+1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
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

</style>
