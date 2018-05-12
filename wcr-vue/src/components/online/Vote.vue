<template>
  <div style="width: 100%;height: 100%">
    <div style="margin-top: -1px;height: 1px"></div>
    <h1>表决</h1>
    <div class="col-md-12">
      <div class="col-md-8">
        <div class="panel panel-info">
          <div class="panel-heading">
            <h3 class="panel-title">表决库</h3>
          </div>
          <div class="panel-body">
            <table id="voteTable"></table>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="panel panel-primary">
      <div class="panel-heading">表决</div>
      <div class="panel-body">
        <form id="formSearch" class="form-horizontal">
          <label>表决题目</label>
          <textarea  class="form-control" id="txt_search_departmentname" rows="5" v-model="voteContentsForm.voteTitle"></textarea>
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
                <!-- <textarea v-model="voteContent.value" style="border-radius: 3px;"/> -->
                <input  type="text" class="form-control" v-model="voteContent.value">
              <button type="button" class="btn btn-danger" @click="deleteDomain(voteContent)">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
              </button>
              </div>
            </form>
          </tr>
      </div>
      <div class="panel-footer">
        <button type="button" class="btn btn-primary" @click="push()">
          <span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>&nbsp;  推送
        </button>
        <button type="button" class="btn btn-default" @click="addDomain()">
          <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp;  新增选项
        </button>
        <button type="button" class="btn btn-default" @click="test()">
          <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>&nbsp; 查看具体回答
        </button>
      </div>
      
      </div>
      <div class="panel panel-danger">
        <div class="panel-heading">
          <h3 class="panel-title">表决结果</h3>
        </div>
        <div class="panel-body" style="witdh:100%">
          <chart :options="dataOption" ref="resize" auto-resize></chart>
        </div>
      </div>
        
      </div>
    </div>
    
  </div>
</template>

<script>
import CHAT from "./client"
export default {
  name: "Vote",
  components: {},
  props:{
    vote:""
  },
  data() {
    return {
      voteContentsForm: {
          voteTitle: "",
          voteContents:[
          ],
          nodeId:this.nodeId,
          id:0
        },
      voteList:[],
      dataOption: {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "cross" // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
         
        },
        xAxis: [
          {
            type: "category",
            data: [],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: "value"
          }
        ],
        series: [
          {
            name: "表决结果",
            type: "bar",
            label: {
              normal: {
                  show: true,
                  position: 'inside',
                  fontSize:25,
                  color:"#FFF",
                  formatter:function(params){
                    if(params.value==0){
                        return '';
                    }else
                    {
                        return params.value;
                    }
                  }
              }
            },
            barWidth: "60%",
            data: [0, 0, 0, 0, 0, 0, 0]
          }
        ],
        color:['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3']
      }
    };
  },
  watch: {
    vote(val){
    var th = this;
    let data = CHAT.arrangeData.nodeNormal;
    $('#voteTable').bootstrapTable({
        url:'/public/getCourseVoteByNodeIdList',
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
        sidePagination:'client',
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pageList:'[10, 25, 50, 100, All]',
        showToggle:true,
        clickToSelect: true,
        columns: [
          {field: "title", title: "表决题目"},
          {field: 'operate', title: '操作', align:'center', width:"100px", formatter : operateFormatter},
        ],
        queryParams:function(params) {
          params = data;
          return params;
        },responseHandler:function(res){
          th.voteList = res.result;
          return res.result;
        }
      });
      function operateFormatter(value, row, index) {
        return [
          '<button class="RoleOfinfo btn btn-info"  OnClick=detailsVote("'+row.id+'")>查看</button>',
        ]
          .join('');
      }
      /*查看具体表决*/
      window.detailsVote= function (id) {
        th.voteContentsForm.voteTitle="";
        th.voteContentsForm.voteContents = [];
        th.dataOption.xAxis[0].data = [];
        th.dataOption.series[0].data = [];
        th.changeVote(id);
      }
  
    }
  },
  methods: {
    changeVote(id){
      this.voteList.forEach((e,index)=>{
          if(e.id == id){
            var content = JSON.parse(e.content);
            for (var i=0;i<content.length;i++){ 
              this.dataOption.xAxis[0].data.push(content[i].id)
              this.dataOption.series[0].data.push(0)
              this.voteContentsForm.voteTitle=e.title;
              this.voteContentsForm.voteContents.push({
                id:content[i].id,
                value: content[i].value,
              });
            }
          }

        })
    },
    test(){
       this.$refs.resize.resize();
    },
    addDomain() {
      var data = 
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
    push(){
      
      this.dataOption.xAxis[0].data = [];
      this.dataOption.series[0].data = [];
      this.voteContentsForm.voteContents.forEach((e,index)=>{
        this.dataOption.xAxis[0].data.push(String.fromCharCode(index+65))  
        this.dataOption.series[0].data.push(0)
      });
      this.$refs.resize.resize();
      CHAT.submit({
        type: CHAT.VOTE,
        allSend:1,
        studentId: 0,
        classId: 0,
        result: this.voteContentsForm,
      })
    }
  },
  beforeCreate() {},
  created() {},
  beforeMount() {},
  mounted() {
    var th = this;
    bus.$on("responVote",(e)=>{
      this.voteContentsForm.voteContents.forEach((element,index) => {
        if(e.result.toLowerCase() == element.id.toLowerCase()){
          this.dataOption.series[0].data[index]++;
          Vue.set(this.dataOption.series[0].data, index, this.dataOption.series[0].data[index]);
          console.log(index)
        }
      });
      
    });
    },
  beforeUpdate() {
   
  },
  updated() {},
  beforeDestroy() {
    bus.$off("responVote");
  },
  destroyed() {}
};
</script>


<style scoped>
 .echarts {
        width: 100%;
        height: 400px;
    }
</style>
