<template>
    <div class="modal fade" :id="title" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">{{title}}</h4>
          </div>
          <div class="modal-body">
            <slot></slot>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Save changes</button>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: 'CustomModel',
  props:{
      show:false,
      title:"",
  },
  data () {
    return {
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

</style>
