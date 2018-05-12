<template>
  <div id="app" style="position: relative;">
    <router-view/>
      <div class="customDialog">
        <transition-group name="slide-fade" >
            <div class="customDialogItem" v-for="(item, index) in dailogList" :key="item.key"> 
              <div class="customDialogItemHeader" :class="{bggreen:item.type == 'success',bgblue:item.type == 'default',bgred:item.type == 'error',bgyellow:item.type == 'warn'}">
                <button type="button" class="btn_close" @click="closeDialog(item.key)">&times;</button>
              </div>
              <div class="customDialogItemBody">
                {{item.body}}
              </div>
            </div>
        </transition-group>
      </div>
  </div>
</template>

<script>
export default {
  name: 'app',
  components:{

  },
  data () {
    return {
      dailogList:[
        // {
        //   type:"success",
        //   body:"dialog测试"
        // },
        // {
        //   type:"default",
        //   body:"dialog测试"
        // },
        // {
        //   type:"error",
        //   body:"dialog测试"
        // },
        // {
        //   type:"warn",
        //   body:"dialog测试"
        // }
      ],
      dailogCount:0,
    }
  },
  watch:{
  },
  methods:{
    closeDialog(val){

      this.dailogList.forEach((value, index, array) => {
        if(value.key == val){
           this.dailogList.splice(index,1);
        }
      });
    }
  },
  beforeCreate(){},
  created(){},
  beforeMount(){},
  mounted(){
    var th = this;
    bus.$on('clearModalBackDrop',function(){
        console.log("clearModalBackDrop")
        $(".modal-backdrop").remove();
      });
    bus.$on('dialog',function(val){
        val.key=th.dailogCount;
        th.dailogList.push(val)
        let a = th.dailogCount;
        if(val.auto){
          setTimeout(() => {
                           th.closeDialog(a)
                        }, val.time);
          
        }
        th.dailogCount++;
      });
  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){
     bus.$off('clearModalBackDrop');
     bus.$off('dialog');
  },
  destroyed(){}
}
</script>

<style>
.customDialog{
    display: block;
    z-index: 2000;
    width: 280px;
    position: fixed;
    right: 40px;
    bottom: 120px;
  }
.customDialogItem{
    padding-left: 5px;
    padding-right: 5px;
    display: block;
    min-height: 60px;
    width: 280px;
    margin-bottom: 10px;
    background-color: #FFF;
    border : 1px solid;
    border-radius:5px;
    border-color: #1FBBA6;
    overflow: hidden;
  }
  .customDialogItemHeader{
    margin-left: -5px;
    display: block;
    height: 4px;
    width: 280px;
    margin-bottom: 10px;
    background-color: #1FBBA6;
  
  }
  
  .customDialogItemBody{
    word-wrap:break-word;
    margin-left: 5px;
    margin-bottom: 5px;
    font-size: 18px;
    width: 280px;
    margin-bottom: 5px;
  }
  .btn_close{
    float: right;
    font-size: 21px;
    font-weight: 700;
    line-height: 1;
    margin-right: 2px;
    color: #000;
    text-shadow: 0 1px 0 #fff;
    filter: alpha(opacity=20);
    opacity: .2;
    padding: 0;
    cursor: pointer;
    background: 0 0;
    border: 0;
  }
  .btn_close:focus, .btn_close:hover {
    color: #000;
    text-decoration: none;
    cursor: pointer;
    filter: alpha(opacity=50);
    opacity: .5;
  }
  .bggreen{
   background-color:#00A65A
  }
  .bgblue{
    background-color:#00C0EF
  }
  .bgred{
    background-color:#DD4B39
  }
  .bgyellow{
    background-color:#F39C12
  }
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all .8s ease;
}
.slide-fade-leave-active {
  transition: all .6s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(40px);
  opacity: 0;
}
</style>
