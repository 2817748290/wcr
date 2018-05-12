<template>
  <div style="width: 100%;height: 100%;background-color:#f2f2f2;">

    <div class="row">
      <div class="col-sm-12 col-md-8" style="margin-top: 3%">
        <div v-for="(item,index) in CHAT.groups" class="col-sm-6 col-md-6 col-lg-4" style="padding-left:2%;margin-bottom:5%;">
          <div v-if="item.group.length>0" class="row" :id="item.groupId" >
            <div class="col-xs-4 col-sm-4 col-md-4" :id="item.groupId" >
              <label v-for="(item1,index1) in item.group" v-if="index1<item.group.length/2" :id="item1.id" class="lab_name" :style="{backgroundColor: item1.online ==2 ? ('#d9534f') : item1.online ==1 ? ('#1FBBA6') : ('#E0E0E0'),borderStyle: index1 ==0 ? ('solid') : (null)}" >{{item1.nickname}}</label>
            </div>
            <div class="col-xs-4 col-sm-4 col-md-4" :id="item.groupId" >
              <label class="lab_ball" :id="item.groupId" :style="{backgroundColor:color[index%10]}">{{index+1}}</label>
            </div>
            <div class="col-xs-4 col-sm-4 col-md-4" :id="item.groupId" >
              <label v-for="(item1,index1) in item.group" v-if="index1>=item.group.length/2" class="lab_name" :style="{backgroundColor: item1.online ==2 ? ('#d9534f') : item1.online ==1 ? ('#1FBBA6') : ('#E0E0E0')}" :id="item1.id">{{item1.nickname}}</label>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-4" style="margin-top: 3%">
        <div id="divOne"></div>
        <img id='imgOne'  style='width:90%;margin-left:5%;' />
        <button class="btn btn-success" :class="{closed : allowedAdd }" style='width:60%;margin-left:20%;margin-top:25px' @click="toggle()">{{allowedAddMess}}</button>
      </div>
    </div>
  </div>
</template>

<script>
  require("../../assets/js/jquery.min.js");
  require("../../assets/js/jquery.qrcode.min.js");

import CHAT from "./client"
export default {
  name: 'SignIn',
  components:{

  },
  props:{

  },
  data () {
    return {
      CHAT,
      ballWitdh:0,
      color:['#CE0000',
      '#0000E3',
      '#009393',
      '#009100',
      '#8C8C00',
      '#804040',
      '#808040',
      '#408080',
      '#5A5AAD',
      '#8F4586'],
      allowedAdd:false,
      allowedAddMess:"允许加入",
      lunxunObject:{},
      classId:1,
    }
  },
  watch:{

  },
  methods:{
    initGroup(id){

    },
    toggle(){
      if(this.allowedAdd){
        this.allowedAdd = false;
        this.allowedAddMess = "允许加入";
        clearInterval(this.lunxunObject);
        bus.$emit("dialog",{
          type:"warn",
          body:"已关闭签到！",
          auto:true,
          time:5000
        })
      }else{
        this.allowedAddMess = "禁止加入";
        this.allowedAdd = true;
        this.lunxun();
        this.lunxunObject = setInterval(this.lunxun,20000);
        bus.$emit("dialog",{
          type:"success",
          body:"开始签到！",
          auto:true,
          time:5000
        })
      }
      CHAT.submit({
        type:CHAT.SIGN_IN_CHANGE,
        studentId:0,
        classId:0,
        allSend:1,
        result:this.allowedAdd,
      });
    },
    lunxun(){
      var a = +new Date().getTime()+""+Math.floor(Math.random()*899+100)+"!";
      var domain = window.location.host;
      var b = '{"type":1,"url":"ws://10.10.3.113/studentwebsocket?code='+a+'&classId='+CHAT.courseArrange.id+'&token="}';
      //var b = '{"type":1,"url":"ws://'+domain+'/studentwebsocket?code='+a+'&classId='+CHAT.courseArrange.id+'&token="}';
      CHAT.submit({
        type:CHAT.SIGN_IN_REFRESH,
        studentId:0,
        classId:0,
        result:a,
      });
      this.handleClick(b);
    },
    handleClick(id) {
      var a = id;
      $("#divOne").empty();
      var qrcode= $('#divOne').qrcode(a);
      var canvas = qrcode.find('canvas').get(0);
      $('#imgOne').attr('src',canvas.toDataURL('image/jpg'));
      $("#divOne").empty();
    },
  },
  beforeCreate(){},
  created(){},
  beforeMount(){},
  mounted(){
  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){},
  destroyed(){}
}
</script>


<style scoped>
  .closed{
    background-color: #d9534f;
  }
  .signin{
    background-color: #1FBBA6;
  }
  .lab_ball{
  width: 5vw;
  height: 5vw;
  font-size: 3vw;
  color: #FFF;
  line-height: 5vw;
  text-align: center;
  border-radius: 50%;

}
.lab_name{
  width: 6vw;
  display:block;
  font-size: 1.2vw;
  height: 2vw;
  background-color: #E0E0E0;
  border:0.05vw;
  text-align: center
}

</style>
