<template>
<el-container direction="vertical">
  <el-breadcrumb separator="/">
    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>学生管理</el-breadcrumb-item>
  </el-breadcrumb>
  <el-card class="box-card" style="margin-top:20px">
   
    <div slot="header" class="clearfix" >
      <span>学生用户管理面板</span>
      <el-button style="float: right; padding: 7px 7px" type="primary" icon="el-icon-refresh" @click="refresh">刷新</el-button>
    </div>
    <el-col :span="24" style="margin-bottom:5px;">
      <el-col :span="6">
        <el-button-group>
          <el-button type="primary" icon="el-icon-plus">添加</el-button>
          <el-button type="primary" icon="el-icon-tickets">批量导入</el-button>
          <el-button type="danger" icon="el-icon-delete">批量删除</el-button>
        </el-button-group>
      </el-col> 
      <el-col :span="6">
        <el-container >
        <el-dropdown @command="changeSearchName">
          <el-button type="primary" >
            {{searchNameTitle}}<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="username">学号</el-dropdown-item>
            <el-dropdown-item command="nickname">姓名</el-dropdown-item>
            <el-dropdown-item command="phone_number">联系电话</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-input v-model="search" placeholder="请输入内容" suffix-icon="el-icon-search" style="witdh:20%"><el-button slot="append" icon="el-icon-search" @click="refresh"></el-button></el-input>
        </el-container>
      </el-col> 
    </el-col> 
    <el-table @sort-change="sortChange" :data="tableData.rows" border style="width: 100%;">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column sortable="custom" prop="username" label="学号" width="180"></el-table-column>
      <el-table-column sortable="custom" prop="nickname" label="姓名"></el-table-column>
      <el-table-column sortable="custom" prop="phoneNumber" label="联系电话"></el-table-column>
      <el-table-column sortable="custom" prop="gmtCreate" label="创建时间" :formatter="formatterTime"></el-table-column>
      <el-table-column sortable="custom" prop="gmtCreate" label="账号状态" width="120" >
        <template slot-scope="scope">
          <div style="padding-left:15px;padding-right:15px;text-align: center">
            <div v-if="scope.row.locked == 0" style="background-color:#6fcfca;border-radius:5px;color:#fff;padding:5px">正常</div>
            <div v-if="scope.row.locked == 1" style="background-color:#FA5555;border-radius:5px;color:#fff;padding:5px">冻结</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        background
        style="float:right;margin-top:8px;margin-bottom:8px;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10,20,30,40,50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.total">
      </el-pagination>
 </el-card>
</el-container>
</template>

<script>

export default {
  name: 'HelloWorld',
  components:{
  },
  data () {
    return {
      searchName:"username",//模糊搜索字段名
      searchNameTitle:"学号",//模糊搜索的值
      search:"",//模糊搜索的值
      sort:"gmt_create",//排序的字段名
      order:"DESC",//排序的方式 ASC DESC
      currentPage:1,//第几页
      pageSize:10,//每页几条
      totalCount:100,//总数
      tableData:{
        total:100,
        rows: []
      }
    }
  },
  methods:{
    refresh(){
      axios.post('/admin/getAllstudent', 
          {
            searchName:this.searchName,
            search:this.search==""?(null):("%"+this.search+"%"),
            sort:this.sort,
            order:this.order,
            offset:(this.currentPage-1)*this.pageSize,
            limit:this.pageSize,
          }).then(response =>{
            if(response.data.status == 1){
              this.tableData = response.data.result;
            }else{
              this.$notify.error({
                title: '错误',
                message: response.data.message
              });
            }
          })
          .catch((error)=>{
            this.$notify.error({
              title: '错误',
              message: '未知错误！'
            });
          });
    },
    sortChange(column, prop, order){
      console.log(column, prop, order);
      if(column.column != null){
        if(column.order == "ascending"){
          this.order = "ASC";
        }else{
          this.order = "DESC";
        }
        
        if(column.prop == "phoneNumber"){
          this.sort = "phone_number";
        }else if(column.prop == "gmtCreate"){
          this.sort = "gmt_create";
        }else{
          this.sort = column.prop;
        }
        
        this.refresh();
      }
      
    },
    //每页显示几条-改变事件
    handleSizeChange:function(size){
      this.pageSize = size;
      this.refresh();
      console.log(size);
    },
    //显示第几页-改变事件
    handleCurrentChange:function(currentPage){
      this.currentPage = currentPage;
      this.refresh();
      console.log(currentPage);
    },
    changeSearchName(val){
      if(val == "username"){
        this.searchNameTitle = "学号";
      }else if(val == "nickname"){
        this.searchNameTitle = "姓名";
      }else if(val == "phone_number"){
        this.searchNameTitle = "联系电话";
      }
      this.searchName = val;
    },
    formatterTime(row, column, cellValue){
      var date = new Date(cellValue);
      var seperator1 = "-";
      var seperator2 = ":";
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
      return currentdate;
    }
  },mounted(){
    this.refresh();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
