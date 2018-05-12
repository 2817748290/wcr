<!--suppress ALL -->
<template>
  <div class="panel-body" style="padding-bottom:0px;">
    <div class="panel panel-default">
      <div class="panel-heading">议题</div>

      <div class="panel-body">
        <form id="formSearch" class="form-horizontal">
          <label>议题内容</label>
          <textarea class="form-control" id="txt_search_departmentname" rows="5" v-model="issues"></textarea>
        </form>
      </div>
      <div class="panel-footer">
        <button id="btn_add" type="button" class="btn btn-default"  @click="addIssues()" v-if="state === 0">
        <span class="glyphicon glyphicon-plus" aria-hidden="true" ></span>提交
         </button>
        <button id="btn_update" type="button" class="btn btn-default" @click="updateIssues()" v-if="state === 1">
          <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>修改
        </button>
        <button id="btn_reset" type="button" class="btn btn-default" @click="resetIssues()">
          <span class="glyphicon glyphicon glyphicon-repeat" aria-hidden="true"></span>重置
        </button>
      </div>
    </div>

    <div id="toolbar" class="btn-group">

  </div>
    <table id="issuesTable"></table>
  </div>
</template>

<script>
  export default {
    name: 'CustomModel',
    props:{
      nodeId:{
        type:Number,
        default:0
      }
    },
    data () {
      return {
       /*修改删除状态*/ state:0,
        issuesTem:"",
        issues:"",
        id:0,
      }
    },
    watch:{
      nodeId(){
        $('#issuesTable').bootstrapTable(
          "refresh"
        );
      }
    },
    methods:{
      addIssues (){
        if (this.issues!=null&&this.issues!=""){
            axios.post('/api/addCourseIssues',
              {
                content:this.issues,
                nodeId:this.nodeId
              }
            ).then((response) => {
              if (response.data.status=== 1) {
                bus.$emit("dialog", {
                  type: "success",//success default error warn
                  body: response.data.message ,
                  auto: true,//是否自动隐藏
                  time: 5000//毫秒
                })
                this.resetIssues();
                $('#issuesTable').bootstrapTable(
                  "refresh");
              }
              else {
                bus.$emit("dialog", {
                  type: "error",//success default error warn
                  body: response.data.value ,
                  auto: false,//是否自动隐藏
                  time: 5000//毫秒
                });
              }
              console.log(response);
            })
              .catch((error) => {
                bus.$emit("dialog", {
                  type: "warn",//success default error warn
                  body: "网络错误" ,
                  auto: false,//是否自动隐藏
                  time: 5000//毫秒
                });
              });
          }
          else {
          bus.$emit("dialog", {
            type: "error",//success default error warn
            body: "议题内容不能为空" ,
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          });
          }
        },
      deleteIssuesAfter(){
        axios({
          method: 'post',
          url: '/api/deleteIssues',
          data:{
            id:this.id,
            nodeId:this.nodeId
          }
        }).then((response) => {
          bus.$emit("dialog", {
            type: "success",//success default error warn
            body: response.data.message ,
            auto: true,//是否自动隐藏
            time: 5000//毫秒
          })
          this.resetIssues()
          $('#issuesTable').bootstrapTable('refresh');
        }).catch(function(error) {
          bus.$emit("dialog", {
            type: "warn",//success default error warn
            body: "网络错误" ,
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          });
        });
      },
      updateIssues(){
        axios({
          method: 'post',
          url: '/api/updateIssues',
          data: {
            id:this.id,
            nodeId:this.nodeId,
            content:this.issues,
            description: this.markupStr,
            coverImgUrl:this.coverImgUrl
          },
        }).then((response) => {
          if (response.data.status=== 1) {
            bus.$emit("dialog", {
              type: "success",//success default error warn
              body: response.data.message ,
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
            this.resetIssues();
            $('#issuesTable').bootstrapTable(
              "refresh");
          }
          else {
            bus.$emit("dialog", {
              type: "error",//success default error warn
              body: response.data.value ,
              auto: false,//是否自动隐藏
              time: 5000//毫秒
            });
          }
          console.log(response);
        })
          .catch((error) => {
            bus.$emit("dialog", {
              type: "warn",//success default error warn
              body: "网络错误" ,
              auto: false,//是否自动隐藏
              time: 5000//毫秒
            });
          });
      },
      detailsIssues() {
        this.issuesTem.forEach((item, index)=>{
          if(item.id == this.id){
            this.issues= item.content;
          }
        });
      },
      resetIssues() {
        this.issues="";
        this.state = 0;
      }
    },
    beforeCreate(){},
    created(){},
    beforeMount(){},
    mounted(){
      var self =this
      $('#issuesTable').bootstrapTable({
        url:'/api/getCourseIssues',
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
        search:false,
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
        columns: [
          {field: "state", checkbox: true},
          {field: "courseNode.title", title: "章节名称"},
          {field: "content", title: "议题内容"},
          {field: 'operate', title: '操作', formatter : operateFormatter},
        ],queryParams:function(params) {
          params.nodeId=self.nodeId;
          return params;
        },responseHandler:function(res){
          self.issuesTem=res.result.rows;
          return res.result;
        }
      });
      function operateFormatter(value, row, index) {
        return [
          '<button class="RoleOfinfo btn btn-info" style="margin:0 10px 0 0"  OnClick=detailsIssuesId("'+row.id+'")>查看</button>',
          '<button class="RoleOfdelete btn btn-danger" style="margin:0 10px 0 0" OnClick=deleteIssuesModel("'+row.id+'")>删除</button>'
        ]
      }
      window.detailsIssuesId= function (id) {
        self.state = 1;
        self.id = id;
        self.detailsIssues()
      },
      window.deleteIssuesModel = function (id) {
        if (confirm("确认删除？")) {
          self.id = id;
          self.deleteIssuesAfter()
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

</style>
