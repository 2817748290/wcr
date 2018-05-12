<template>
  <div class="ui">
    <div class="out_form" @keyup.enter="login()">
    <div class="form1">
      <img src="../../assets/img/loginback.png"/>
      <div class="form-group">
        <input  class="form-control" id="exampleInputEmail1" v-model="msg1" placeholder="账号">
      </div>
      <div class="form-group">
        <input type="password" class="form-control" id="exampleInputPassword1" v-model="msg2" placeholder="密码" >
      </div>
      <div class="form-group" style="color: #ffffff">
        <label >忘记密码</label>
      </div>
      <div class="form-group">
        <button class="form-control" id="login_submit" @click="login()">登录</button>
      </div>
     </div>
  </div>
  </div>
</template>

<script>
//    new Vue({
//      el:'#app',
//      data:{
//        msg1:'请输入账号',
//        msg2:'请输入密码'
//      }
//    })

    export default {
      name: 'CustomModel',
      props:{
        show:false,
        title:"",
      },
      data () {
        return {
          msg1:'',
          msg2:'',
          shows:this.show
        }
      },
      watch:{
        show(val){
          this.shows = this.show;
        },
        shows(val){
          this.$emit("on-model-change",val);
          console.log(val);
          if(val){
            $('#'+this.title).modal('show');
          }else{
            $('#'+this.title).modal('hide');
          }
        }
      },
      methods:{
        login (){
          if (this.msg1!=null&&this.msg1!=""){
            if (this.msg2!=null&&this.msg2!=""){
              axios.post('/public/login',
                {
                  username:this.msg1,
                  password:this.msg2
                }
              ).then((response) => {
                if (response.data.status === 1) {
                  var data=JSON.stringify(response.data.result);
                  window.sessionStorage.setItem("user", data);
                  this.$router.push("/");
                }
                else if (response.data.status === 0){
                  alert(response.data.message);
                }
                console.log(response);
                })

                .catch((error) => {
                   alert("网络错误");
                  console.log(error);
                });

            }
            else {
              alert("密码不能为空")

            }
          }
          else {
            alert("用户名不能为空")
          }
        },



},
      beforeCreate(){},
      created(){},
      beforeMount(){},
      mounted(){
        var th = this;
        $(function(){
          $('#'+th.title).on('hidden.bs.modal', function (e) {
            th.shows = false;
          })
        });
      },
      beforeUpdate(){},
      updated(){},
      beforeDestroy(){},
      destroyed(){}
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .out_form{
    width: 400px;
    height: 350px;
    background-color: rgba(0,0,0,0.3);
    margin:260px auto;
    border-radius: 10px;
  }
 .form1 {
   margin:200px auto;
   width: 300px;
   height: 300px;
   position: relative;
  }
 input{
   height: 50px;
 }
 #login_submit{
   height: 50px;
   width: 300px;
   line-height:40px;
   text-align: center;
   font-size: x-large;
   border-radius: 7px;
   color: #ffffff;
   background-color: #1fbba6;
 }
  h1{
    text-align: center;
    font-weight: bold;
  }

  .ui{
   background-image: url('../../assets/img/loginbackground.jpg');
   width: 100vw;
   height: 100vh;
   background-size:100%;
    position: absolute;
 }
  img{
    width: 800px;
  }



</style>
