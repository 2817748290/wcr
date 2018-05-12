<template>
    <div>
      <div>
        <label class="label-head">备课流程</label><br>
        <label class="label-content">备课流程说明：************************************************************</label>
        <br>
      </div>
      <div>
        <div class="but-content" style="margin-left:5%;"><strong>课程信息管理</strong></div>
        <div class="but-content1"><strong>-></strong></div>
        <div class="but-content"><strong>课程班级管理</strong></div>
        <div class="but-content1"><strong>-></strong></div>
        <div class="but-content"><strong>课程内容管理</strong></div>
        <div class="but-content1"><strong>-></strong></div>
        <div class="but-content"><strong>课程题库管理</strong></div>
        <div class="but-content1"><strong>-></strong></div>
        <div class="but-content"><strong>课程安排管理</strong></div>
      </div>
    </div>
</template>

<script>
export default {
  name: 'CustomModel',
  props:{

  },
  data () {
    return {

    }
  },
  watch:{

  },
  methods:{

  },
  beforeCreate(){},
  created(){},
  beforeMount(){},
  mounted(){
    var th = this;
    $(function(){

    });

  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){},
  destroyed(){}
}
</script>

<style scoped>
.label-head{
  font-size: 70px;
  margin-left: 5%;
  margin-top: 3%;
}
.label-content{
  font-size: 30px;
  margin-left: 8%;
  margin-top: 2%;
}
.but-content{
  margin-top: 12%;
  text-align: center;
  line-height: 120px;
  background-color: #1FBBA6;
  width: 200px;
  height: 120px;
  font-size: 20px;
  border-radius: 15px;
  float: left;
}
.but-content1{
  margin-top: 12%;
  text-align: center;
  line-height: 120px;
  width: 80px;
  height: 120px;
  font-size: 50px;
  float: left;
}
</style>
