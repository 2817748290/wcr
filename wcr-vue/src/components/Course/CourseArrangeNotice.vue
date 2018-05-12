<template>
    <div id="CourseArrangeNotice" class="container">
        <div style="margin-top:1%">
            <label for="beforeCourse"><font size="4" color="#000000" face="微软雅黑">课前通知</font></label>
            <div >
                <textarea name="beforeCourse" id="beforeCourse" cols="150" rows="7"></textarea>
            </div>
            <label for="afterCourse" style="margin-top:1%"><font size="4" color="#000000" face="微软雅黑">课后通知</font></label>
            <div >
                <textarea name="afterCourse" id="afterCourse" cols="150" rows="7"></textarea>
            </div>
            <!-- <button id="upstep" class="btn btn-default" style='margin-left:40%'>上一步</button>
            <button id="save" class="btn btn-info" >完成</button> -->
        </div>
    </div>

</template>


<script>

  import 'jquery'

  export default {
    name: '',
    components:{},
    data () {
      return {
        name: '',
        course: '',
      }
    },
    watch:{},
    methods:{},
    beforeCreate(){},
    created(){},
    beforeMount(){},
    mounted(){},
    beforeUpdate(){},
    updated(){},
    beforeDestroy(){},
    destroyed(){}
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>


</style>
