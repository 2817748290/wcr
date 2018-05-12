<template>
    <el-row type="flex" class="row-bg hello1" justify="center" align="middle" >
      <el-col :md="12" :xs="20" :sm="20" :lg="6" style="text-align: center;">
        <img src="../assets/img/logo.png" width="100%"/>
        <h2>管理员端</h2>
        <input type="text" name="userid" id="login-email" class="input-circle" placeholder="工号" v-model="userid">
        <div class="s" :class="{ isvisibility: isActive }"><i class="el-icon-information"></i>&#12288;用户名不能为空</div>
        <input type="password" name="password" id="login-email" class="input-circle" placeholder="密码" v-model="password">
        <div class="s" :class="{ isvisibility: isActive2 }"><i class="el-icon-information" hidden></i>&#12288;密码不能为空</div>
        <button class="button-circle" @click="login()" ><i class="el-icon-loading el-icon--left" :class="{ isvisibility2: lock }"/>登录</button>
        <div class="s" :class="{ isvisibility: isActive3 }"><i class="el-icon-information" hidden></i>&#12288;用户名或者密码错误</div>
      </el-col>
    </el-row>
</template>

<script>

export default {
  name: 'hello',
  data () {
    return {
      isActive:true,
      isActive2:true,
      isActive3:true,
      userid:null,
      password:null,
      mess1:"用户名不能为空",
      mess2:"密码不能为空",
      lock:true
    }
  },watch: {
    password: function (val) {
      this.isActive3=true;
      if(this.userid == "" || this.userid == null){
        this.isActive = false;
      }else{
        this.isActive = true;
      }
    },
    userid: function (val) {
      this.isActive3=true;
      this.isActive2 = true;
    }
  },methods:{
    login:function(){

      if(this.lock){

        this.lock = false;
        if(this.userid == "" || this.userid == null){
            this.lock = true;
          this.isActive = false;
        }else if(this.password == "" || this.password == null){
            this.lock = true;
          this.isActive2 = false;
        }else{
          this.isActive = true;
          this.isActive2 = true;
          axios.post('/public/login', {
            username: this.userid,
            password: this.password
          }).then(response =>{
            this.lock = true;
            if(response.data.status == 1){
              window.sessionStorage.setItem("user", JSON.stringify(response.data.result));
              this.$router.push("/");
            }else{
              this.isActive3=false;
            }
          })
          .catch((error)=>{
            this.lock = true;
            this.$notify.error({
                title: '错误',
                message: '网络错误！'
              });
          });
        }
      }else{

      }

    }

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.isvisibility{
  visibility:hidden
}
.isvisibility2{
  display:none;
}
.s{
  margin: 2px;
  color: #FF3030;
}
h2{
  color: rgba(11,209,244,.99);
}

.hello1 {
  background: url(../assets/img/bg.jpg) 50% 50% no-repeat;
  height: 100vh;
  background-size:cover;
}

.button-circle {
  outline:none;
  width: 101%;
  height: 38px;
  margin-top: 0px;
  background-color: rgba(11,209,244,.99);
  font-size: 18px;
  color: #FFFFFF;
  text-align: center;
  -webkit-border-radius: 100px;
  -moz-border-radius: 100px;
  border-radius: 100px;
}

.input-circle {
  outline:none;
  width: 100%;
  height: 35px;
  margin-top: 0px;
  background-color: rgba(255,255,255,.75);
  font-size: 15px;
  text-align: center;
  -webkit-border-radius: 100px;
  -moz-border-radius: 100px;
  border-radius: 100px;
}

</style>
