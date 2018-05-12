<template>
  <div class="hello">
    <button type="button" class="btn btn-primary btn-lg" v-on:click="show">
      点我！
    </button>
    <button type="button" class="btn btn-primary btn-lg" v-on:click="show1">
      添加学生！
    </button>
    <button type="button" class="btn btn-primary btn-lg" v-on:click="show2">
      添加班级！
    </button>
    <ModalBox :show="customShow" :title="'第一个'" @on-model-change = "onModelChange">
      <button type="button" class="btn btn-primary btn-lg" v-on:click="show">
        Launch demo modal
      </button>
    </ModalBox>
    <ModalBox :show="customShow1" :title="'添加学生'" @on-model-change = "onModelChange1">
        Launch demo modal1
    </ModalBox>
    <ModalBox :show="customShow2" :title="'添加班级'" @on-model-change = "onModelChange1">
        Launch demo modal2
    </ModalBox>
  </div>
</template>

<script>
import ModalBox from './ModalBox/AddStudent'

export default {
  name: 'HelloWorld',
  components:{
    'ModalBox': ModalBox,
  },
  data () {
    return {
      customShow:false,
      customShow1:false,
      customShow2:false
    }
  },
  watch:{

  },
  methods:{
    show1(){
      this.customShow1 = true;
    },
    show(){
      axios.get('/api/test')
      .then((response) => {
        this.msg = response.data.message + response.data.result;
        this.customShow = true;
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
    },
    show2(){

    },
    onModelChange(val){
      this.customShow = val;
    },
    onModelChange1(val){
      console.log(this.customShow1);
      this.customShow1 = val;
    }

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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
