<template>
  <div class="panel-body" style="padding-bottom:0px;">
    <div class="panel panel-default">
      <div class="panel-heading">表决</div>
      <div class="panel-body">
        <form id="formSearch" class="form-horizontal">
          <label>表决题目</label>
          <textarea class="form-control" id="txt_search_departmentname" rows="5" v-model="voteContentsForm.voteTitle"></textarea>
        </form>
      </div>
      <div class="panel-body">
        <label>表决选项</label>
            <tr v-for="(voteContent , index) in voteContentsForm.voteContents">
              <form class="form-inline">
                 <div class="form-group">
              <label class="col-md-1">
                   <p class="form-control-static">{{String.fromCharCode(index+65)}}</p>
              </label>
                 </div>
                 <div class="form-group">
              <input type="text" class="form-control" v-model="voteContent.value">
                 </div>
              <button type="button" class="btn btn-default" @click="deleteDomain(voteContent)">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
              </button>
              </form>

            </tr>
      </div>


      </div>
      <div class="panel-footer">
        <button id="btn_add" type="button" class="btn btn-default" @click="addVote()" v-if="state === 0">
          <span class="glyphicon glyphicon-plus" aria-hidden="true" ></span>提交
        </button>

        <button id="btn_update" type="button" class="btn btn-default" @click="updateVote()" v-if="state === 1">
          <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>修改
        </button>

        <button id="btn_edit" type="button" class="btn btn-default" @click="addDomain()">
          <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>新增选项
        </button>

        <button id="btn_reset" type="button" class="btn btn-default" @click="resetVote()">
          <span class="glyphicon glyphicon glyphicon-repeat" aria-hidden="true"></span>重置
        </button>

    </div>
    <div id="toolbar" class="btn-group">

    </div>
    <!--表决库-->
    <div>
      <table id="voteTable"></table>
    </div>
  </div>




</template>

<script>
 // import index from "../../router/index";
//  import * as json from "autoprefixer";

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
        state:0,
        voteTem:"",
        voteContentsForm: {
          voteTitle: "",
          voteContents:[
          ],
          nodeId:this.nodeId,
          id:0
        }
      }
    },
    watch:{
      nodeId(){
        $('#voteTable').bootstrapTable(
          "refresh"
        );
      }
    },
    methods:{
      /*提交表决*/
      addVote: function () {
        /*遍历表决选项并检测*/
        var arrayContents=new  Array();
        var bl=true
        if(this.voteContentsForm.voteContents.length===0){
          bus.$emit("dialog", {
            type: "error",//success default error warn
            body: "无表决选项" ,
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          });
        }
        else {
        for(var i=0;i<this.voteContentsForm.voteContents.length;i++){
          if(this.voteContentsForm.voteContents[i].value!==null&&this.voteContentsForm.voteContents[i].value!==""){
            arrayContents.push({"id":String.fromCharCode(i+65),"value":this.voteContentsForm.voteContents[i].value})
          }
          else {
            bus.$emit("dialog", {
              type: "error",//success default error warn
              body: "表决选项不能为空" ,
              auto: false,//是否自动隐藏
              time: 5000//毫秒
            })
            bl=false
            return false
          }
          }
        if (!bl){
          return false
        }
        else {
        if (this.voteContentsForm.voteTitle!=null&&this.voteContentsForm.voteTitle!="") {
            axios.post('/api/addCourseVote',
              {
                title:this.voteContentsForm.voteTitle,
                nodeId:this.nodeId,
                content:JSON.stringify(arrayContents)
              }
            ).then((response) => {
              if (response.data.status === 1) {
                bus.$emit("dialog", {
                  type: "success",//success default error warn
                  body: response.data.message ,
                  auto: true,//是否自动隐藏
                  time: 5000//毫秒
                })
                this.resetVote();
                $('#voteTable').bootstrapTable(
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
        else
          {
            bus.$emit("dialog", {
              type: "error",//success default error warn
              body: "表决题目不能为空" ,
              auto: false,//是否自动隐藏
              time: 5000//毫秒
            });
          }
        }
      }
      }
      ,
      /*从数据库删除并在执行后刷新表格*/
      deleteVoteAfter(){
        axios({
          method: 'post',
          url: '/api/deleteVote',
          data:{
            id:this.id,
            nodeId:this.nodeId
          }
        }).then((response) => {
          this.resetVote();
          $('#voteTable').bootstrapTable('refresh');
        }).catch(function(error) {
          bus.$emit("dialog", {
            type: "warn",//success default error warn
            body: "网络错误" ,
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          });
        });
      },
      addDomain() {
            this.voteContentsForm.voteContents.push({
              id:"",
              value: "",
            });
          },
      /*删除表决选项*/
      deleteDomain (item) {
        var index = this.voteContentsForm.voteContents.indexOf(item)
        if (index !== -1) {
          this.voteContentsForm.voteContents.splice(index, 1)
        }
      },
      /*修改表决内容*/
      updateVote(){
        var arrayContents=new  Array();
        for(var i=0;i<this.voteContentsForm.voteContents.length;i++) {
          if (this.voteContentsForm.voteContents[i].value !== null && this.voteContentsForm.voteContents[i].value !== "") {
            arrayContents.push({
              "id": String.fromCharCode(i + 65),
              "value": this.voteContentsForm.voteContents[i].value
            })
          }
          else {
            bus.$emit("dialog", {
              type: "error",//success default error warn
              body: "表决选项不能为空" ,
              auto: false,//是否自动隐藏
              time: 5000//毫秒
            })
          }
        }
        axios({
          method: 'post',
          url: '/api/updateVote',
          data: {
            id:this.id,
            title:this.voteContentsForm.voteTitle,
            nodeId:this.nodeId,
            content:JSON.stringify(arrayContents),
            description: this.markupStr,
            coverImgUrl:this.coverImgUrl
          },
        }).then((response) => {
          if (response.data.status === 1) {
            bus.$emit("dialog", {
              type: "success",//success default error warn
              body: response.data.message ,
              auto: true,//是否自动隐藏
              time: 5000//毫秒
            })
            this.resetVote();
            $('#voteTable').bootstrapTable(
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
        }).catch(function(error) {
          bus.$emit("dialog", {
            type: "warn",//success default error warn
            body: "网络错误" ,
            auto: false,//是否自动隐藏
            time: 5000//毫秒
          });
        });
      },
      detailsVote() {
        this.voteTem.forEach((item, index)=>{
          if(item.id == this.id){
            this.voteContentsForm.voteTitle =  item.title;
            this.voteContentsForm.voteContents = JSON.parse(item.content)
          }
        });
      },
      /*清空内容*/
      resetVote(){
        this.voteContentsForm.voteTitle="";
        this.voteContentsForm.voteContents.splice(0, this.voteContentsForm.voteContents.length);
        this.state = 0;
      }

    },
    beforeCreate(){},
    created(){},
    beforeMount(){},
    mounted(){
      var self = this
      $('#voteTable').bootstrapTable({
        url:'/api/getCourseVote',
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
          {field: "title", title: "表决题目"},
          {field: 'operate', title: '操作', formatter : operateFormatter},
        ],
        queryParams:function(params) {
          params.nodeId=self.nodeId;
          return params;
        },responseHandler:function(res){
          self.voteTem=res.result.rows;
          return res.result;
        }
      });
      function operateFormatter(value, row, index) {
        return [
          '<button class="RoleOfinfo btn btn-info" style="margin:0 10px 0 0"  OnClick=detailsVoteId("'+row.id+'")>查看</button>',
          '<button class="RoleOfdelete btn btn-danger" style="margin:0 10px 0 0" OnClick=deleteVoteModel("'+row.id+'")>删除</button>'
          ]
      }
      /*确认删除并执行删除方法*/
      window.deleteVoteModel = function (id) {
        if (confirm("确认删除？")){
          self.id = id;
          self.deleteVoteAfter()
        }

      };
      /*查看具体表决*/
      window.detailsVoteId= function (id) {
        //self.voteContentsForm.voteContents.splice(0, self.voteContentsForm.voteContents.length)
        self.id=id;
        self.state=1;
        self.detailsVote();
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
