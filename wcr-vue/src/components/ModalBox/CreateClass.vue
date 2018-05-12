<template>
    <div class="modal fade" :id="title" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">{{title}}</h4>
          </div> 
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: 'CreateClass',
  props:{
      show:false,
      title:"",
  },
  data () {
    return {
        shows:this.show,
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
   1
  },
  beforeUpdate(){},
  updated(){},
  beforeDestroy(){},
  destroyed(){}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    /* .unactive{
        color: black;
        background-color: #f0f0f0;
    } */
    .divActive{
        text-decoration: none;
        background-color: #337ab7;
        color: #fff;
    }
    .border{
        text-align: center;
        padding-bottom: 10px;
        /* border-bottom: 1px solid #e5e5e5; */
    }
    .nav>li>a:focus, .nav>li>a:hover {
        text-decoration: none;
        background-color: #337ab7;
        color: #fff;
    }
    li{
        font-color: #000;
        background-color: #f0f0f0;
    }
    .pfont{
        color: #999999;
        font-size: 10p;
    }
</style>
