<template>
  <div style="width: 100%;height: 100%">
    <div style="margin-top: -1px;height: 1px"></div>
    <h1>议题</h1>
    <div class="col-md-12">
      <div class="col-md-8">
        <div class="panel panel-info">
          <div class="panel-heading">
            <h3 class="panel-title">议题库</h3>
          </div>
          <div class="panel-body">
            <table id="issuesTable"></table>
          </div>
        </div>
          
      </div>
      <div class="col-md-4" style="height:90vh;display: flex;flex-direction: column;">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">议题</h3>
          </div>
          <div class="panel-body">
            <form id="formSearch" class="form-horizontal">
              <label>议题内容</label>
              <textarea class="form-control" id="txt_search_departmentname" rows="5" v-model="issues"></textarea>
            </form>
          </div>
          <div class="panel-footer">
            <button id="btn_add" type="button" class="btn btn-default"  @click="pushIssues()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true" ></span>推送
            </button>
            <button id="btn_add" type="button" class="btn btn-danger"  @click="messageList=[]">
            <span class="glyphicon glyphicon-trash" aria-hidden="true" ></span>清空回复
            </button>
          </div>
        </div>

        <div class="panel panel-danger" style="flex:1;display:flex;flex-direction: column;">
          <div class="panel-heading">
            <h3 class="panel-title">议题回复</h3>
          </div>
          <div class="panel-body" id="scrolldIV" style="overflow:auto;">
            <div id="scrolldIV" ref="scrolldIV" style=" width:100%;height:100%;background-color:#FFF" >
              <div v-for="o in messageList"  >
                <div style="clear:both;">
                  <div class="col-md-1">
                    <img  v-bind:src="o.user.headImgUrl" style="border-radius: 50%;margin-left:-20px;float:left;margin-top:20px;" height="55px"/>
                  </div>
                  <div class="col-md-11">
                    <div style="overflow:hidden;float:left;line-height:55px;margin-top:20px;margin-left:5px;font-size:20px">{{o.user.nickname}}:</div>
                    <div style="overflow:hidden;float:left;line-height:55px;margin-top:20px;margin-left:7px">{{o.message}}</div>
                  </div>
                  <div style="float:left;line-height:55px;margin-top:20px;margin-left:0px">时间:{{o.time}}</div>   
                  <div style="height:2px;background-color:#f2dede"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
        
      </div>
    </div>
  </div>
</template>

<script>
import CHAT from "./client"
export default {
  name: "Issues",
  components: {},
  props:{
    issue : "",
  },
  data() {
    return {
      issues: "",
      issuesList:[],
      id: 0,
      /**{
        user:{
          nickname:"abc",
          headImgUrl:"http://i-3.shouji56.com/2016/5/14/513aeef4-640f-419f-b5ff-acbf2d9f3220.png"
        },
        message:"askdaskldjal",
        time:""
      }*/
      messageList:[]
    };
  },
  watch: {
    messageList(){
      //监听数据，等待数据对应的dom操作完成后。执行
      this.$nextTick(() => {
        var div = document.getElementById('scrolldIV');
        div.scrollTop = div.scrollHeight;
      })
      
    },
    issue(val){
      var th = this;
      let data = CHAT.arrangeData.nodeNormal;
      console.log(data)
      $("#issuesTable").bootstrapTable({
            url: "/public/getCourseIssuesByNodeIdList",
            ajaxOptions: {
              headers: { token: window.sessionStorage.getItem("token") }
            },
            cache: false,
            toolbar: "#toolbar",
            singleSelect: true,
            sortName: "id",
            searchAlign: "left",
            sortOrder: "asc",
            striped: true, //隔行变色
            search: false,
            searchOnEnterKey: true,
            showRefresh: true,
            method: "post",
            sidePagination: "client",
            pagination: true,
            pageNumber: "1",
            pageSize: "10",
            pageList: "[10, 25, 50, 100, All]",
            showToggle: true,
            clickToSelect: true,
            columns: [
              { field: "content", title: "议题内容" },
              { field: "operate", title: "操作",align:'center', width:"100px",  formatter: operateFormatter }
            ],
            queryParams: function(params) {
              params = data;
              return params;
            },
            responseHandler: function(res) {
              th.issuesList = res.result;
              return res.result;
            }
          });
          function operateFormatter(value, row, index) {
            return [
              '<button class="RoleOfinfo btn btn-info"  OnClick=detailsIssues("' + row.id +'")>查看</button>'
            ];
          }
          window.detailsIssues = function(id) {
            th.issuesList.forEach(e => {
              if(e.id == id){
                th.issues = e.content;
              }
            });
            
          };
          }
  },
  methods: {
    pushIssues(){
      CHAT.submit({
        type: CHAT.ISSUES,
        allSend:1,
        studentId: 0,
        classId: 0,
        result: this.issues,
      })
    }
  },
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {
    var th = this;
    bus.$on("responIssues",(e)=>{
      CHAT.forEachGroups((e1,e2)=>{
        if(e.studentId == e2.id){
          var now = new Date();
          let a = new Object();
          a.user = e2;
          a.message = e.result;
          a.time = CHAT.formatTime(now.getTime());
          th.messageList.push(a);
        }
      })
    });
    
   
  },
  beforeUpdate() {},
  updated() {},
  beforeDestroy() {
    bus.$off("responIssues");
  },
  destroyed() {}
};
</script>


<style scoped>
.echarts {
  height: 300px;
}
</style>
