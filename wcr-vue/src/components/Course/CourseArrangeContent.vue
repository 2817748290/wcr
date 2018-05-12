<template>
    <div>
        <div class="form-inline">
            <div style="width:900px; margin: auto auto;color:black;font-size:15px;font-weight:600">
                课程名称：
                <span>{{course}}</span>
            </div>
            <div style="width:900px; margin: auto auto;color:black;font-size:15px;font-weight:600;margin-top:20px">
                <label for="courseTitle">课程名称：</label>
                <div class="form-group">
                    <v-select :debounce="250" :onChange="consoleCallback" :value.sync="selected" :options="list1"></v-select>
                </div>

            </div>
            <div style="width:900px; margin: auto auto;color:black;font-size:15px;font-weight:600;margin-top:20px">
                <label for="courseTitle">上课时间：</label>
                <input class="form-control select-box-input" v-model="courseArrange.courseOntime" type="text"
                                      id="data_datetime">
                <label for="courseTitle">下课时间：</label>
                <input class="form-control select-box-input" v-model="courseArrange.courseDowntime" type="text"
                                      id="data_datetime1">
            </div>
        </div>
        <div style="width:900px; margin: auto auto;margin;margin-top:20px;background-color:rgb(243,243,243)">
            <ZTree
                ref='tree'
                :treeData="treeData"
                :options="options"
                @node-click="itemClick"
                :key="1"
                @on-checked-change = "onCheckChange"
            />
        </div>
        <!-- <div>
            <button class="btn btn-lg btn-success" v-on:click="getAllData()">获取数据测试</button>
        </div> -->
    </div>
</template>

<script>
    import Vue from 'vue';
    import {ZTree} from '../../treeindex'
    import 'vue2-lazy-tree/dist/vue2-tree.min.css';
    import vSelect from "vue-select"
    Vue.use(ZTree)

    const Tree1 = {
        name: 'Tree1',
        props:{
            IfShow:"",
            status:"",
            course:"",
            courseId:0,
        },
        data: function () {
            return {
                treeData: [],
                options: {
                    labelKey: 'name',
                    showCheckbox: true,
                    halfCheck: false,//控制父框是否需要半钩状态
                    search: {
                        useInitial: true,
                        useEnglish: false,
                        customFilter: null
                    },
                    treeType:1,
                    // rootName: this.course,
                },
                courseArrangeTitle:"",
                courseOnClass:0,
                list1:[],
                selected:null,
                courseArrange:{
                    topic: '',
                    classId: 0,
                    state: 0,
                    courseId: 0,
                    courseOntime: '',
                    courseDowntime: '',
                    groups:'',
                    arrangeData:{
                        vote:"",
                        nodeNormal:"",
                        issue:"",
                        nodeTree:"",
                    }
                },
            }
        },
        watch:{
            IfShow(val){
                if(val==2){
                    this.getAllData();
                    console.log(this.courseArrange);
                    this.$emit("course-arrange-content-data",this.courseArrange);
                }
            }
        },
        methods: {
            onCheckChange(val){
                console.log(val);
            },
            itemClick (node) {
                console.log(node.id);
            },
            addCourseOnClass(ele){
                this.courseOnClass = ele.target.value;
            },
            addCourseOnTime(ele){
                console.log(this.courseOnTime)
            },
            consoleCallback(val) {
                this.courseArrange.topic = val;
            },
            getAllData(){
                var checkNodeId=[];
                var checkNodeTree = [];
                var count = 0;
                this.treeData.map((tem,index) => {
                    var arr =[];
                    arr.push(tem);
                    while(arr.length>0){
                        tem = arr.shift();
                        if(tem.checked==true&&checkNodeId.indexOf(tem.id)==-1){
                            checkNodeId.push(tem.id);
                            checkNodeTree.push({'nodeId':tem.id,'parentId':tem.parentId});
                        }
                        if(tem.hasOwnProperty('children')){
                            if(tem.children.length){
                                for(var i = 0;i<tem.children.length;i++){
                                    if(tem.children[i].checked){
                                        checkNodeId.push(tem.children[i].id);
                                        checkNodeTree.push({'nodeId':tem.children[i].id,'parentId':tem.children[i].parentId});
                                    }
                                    arr.push(tem.children[i]);
                                }
                            }
                        }
                    }
                });
                this.findData(checkNodeId);
                this.createNodeTree(checkNodeTree);
            },
            findData(checkNodeId){
                axios.post( '/api/courseArrange/getCourseArrangeNodeId',checkNodeId).then((response)=>{
                    this.courseArrange.arrangeData.vote = response.data.result.vote;
                    this.courseArrange.arrangeData.issue = response.data.result.issue;
                    this.courseArrange.arrangeData.nodeNormal = response.data.result.nodeNormal;
                }).catch((error)=>{
                    console.log(error);
                })
            },
            createNodeTree(nodeIdTree){
                var map = {};
                nodeIdTree.forEach(function (item){
                    map[item.nodeId] = item;
                });
                var val=[];
                nodeIdTree.forEach(function(item){
                    var parent = map[item.parentId];
                    if(parent){
                        (parent.children || ( parent.children = [] )).push(item);
                    }else {
                        val.push(item);
                    }
                });
                this.courseArrange.arrangeData.nodeTree = JSON.stringify(val);
            },
            dateDefault(){
                var d, s;
                var self = this;
                d = new Date();
                s = d.getFullYear() + "-";       //取年份
                s = s + (d.getMonth() + 1) + "-";   //取月份,date生成的月份为0-11
                s += d.getDate() + " ";        //取日期
                s += d.getHours() + ":";        //取小时
                s += d.getMinutes() + ":";       //取分
                s += d.getSeconds();          //取秒
                self.courseArrange.courseOntime = s;
                $('#data_datetime').datetimepicker({
                    startDate: s,
                    language: 'zh-CN',
                    format: 'yyyy-mm-dd hh:ii:ss',
                    todayBtn: 1,
                    autoclose: 1
                });
                $('#data_datetime').datetimepicker()
                .on('hide', function (ev) {
                var value = $("#data_datetime").val();
                self.courseArrange.courseOntime = value;
                });

            },
            dateDefault1(){
                var d, s;
                var self = this;
                d = new Date();
                s = d.getFullYear() + "-";       //取年份
                s = s + (d.getMonth() + 1) + "-";   //取月份,date生成的月份为0-11
                s += d.getDate() + " ";        //取日期
                s += d.getHours() + ":";        //取小时
                s += d.getMinutes() + ":";       //取分
                s += d.getSeconds();          //取秒
                self.courseArrange.courseDowntime = s;
                $('#data_datetime1').datetimepicker({
                    startDate: s,
                    language: 'zh-CN',
                    format: 'yyyy-mm-dd hh:ii:ss',
                    todayBtn: 1,
                    autoclose: 1
                });
                $('#data_datetime1').datetimepicker()
                .on('hide', function (ev) {
                var value = $("#data_datetime1").val();
                self.courseArrange.courseDowntime = value;
                });

            },
        },
        mounted(){
            var th = this;
            th.dateDefault1();
            th.dateDefault();
            this.courseArrange.courseId = this.courseId;
            let treeData1 = [];
            var parent=[];
            var allParentId=[];
            axios.post("/api/getCourseNodeByCourseId",{'id':this.courseId}).then((response)=>{
                // console.log(response.data.result);
                response.data.result.map((item,index) => {
                    if(item.parentId!=1){
                        allParentId.push(item.parentId);
                    }

                })
                response.data.result.map((item,index) => {
                    parent.push(item.id);
                    if(item.level == 1){
                        this.list1.push(item.title);
                        if(allParentId.indexOf(item.id)!=-1){
                            treeData1.push(
                                {
                                    id:item.id,
                                    name: item.title,
                                    open: true,
                                    checked: false,
                                    nodeSelectNotAll: false,
                                    parentId: null,
                                    visible: true,
                                    searched: false,
                                    children:[],
                                }
                            )
                            Vue.set(item,'key',"0-"+(item.levelOrder));
                        }else{
                            treeData1.push(
                                {
                                    id:item.id,
                                    name: item.title,
                                    open: true,
                                    checked: false,
                                    nodeSelectNotAll: false,
                                    parentId: null,
                                    visible: true,
                                    searched: false,
                                }
                            )
                            Vue.set(item,'key',"0-"+(item.levelOrder));
                        }

                    }else{
                        var parentNode = response.data.result[parent.indexOf(item.parentId)];
                        if(parentNode.parentId==1){
                            treeData1.map((fix, i) => {
                                if (fix.id == parentNode.id) {
                                    if(allParentId.indexOf(item.id)!=-1){
                                        fix.children.push({
                                            id: item.id,
                                            name: item.title,
                                            open: true,
                                            nodeSelectNotAll: false,
                                            parentId: fix.id,
                                            visible: true,
                                            searched: false,
                                            children:[]
                                        })
                                    }else{
                                        fix.children.push({
                                            id: item.id,
                                            name: item.title,
                                            open: true,
                                            nodeSelectNotAll: false,
                                            parentId: fix.id,
                                            visible: true,
                                            searched: false,
                                        })
                                    }
                                }
                                Vue.set(item,'key',parentNode.key+"-"+(item.levelOrder));
                            });
                        }else{
                            var parentNode = response.data.result[parent.indexOf(item.parentId)];
                            var tem = [];
                            let postions = parentNode.key.split('-');
                            // console.log(postions);
                            for(var i=1;i<postions.length;i++){
                                // console.log("i:"+i);
                                if(i==1){
                                    tem = treeData1[parseInt(postions[i])];
                                }else if(i>1){
                                    tem = tem.children[parseInt(postions[i])];
                                }
                            }
                            if(allParentId.indexOf(item.id)!=-1){
                                tem.children.push({
                                    id: item.id,
                                    name: item.title,
                                    open: true,
                                    nodeSelectNotAll: false,
                                    parentId: parentNode.id,
                                    visible: true,
                                    searched: false,
                                    children:[]
                                })
                                Vue.set(item,'key',parentNode.key+"-"+(item.levelOrder));
                            }else{
                                tem.children.push({
                                    id: item.id,
                                    name: item.title,
                                    open: true,
                                    nodeSelectNotAll: false,
                                    parentId: parentNode.id,
                                    visible: true,
                                    searched: false,
                                })
                                Vue.set(item,'key',parentNode.key+"-"+(item.levelOrder));
                            }

                        }
                    }
                })

            }).catch((error)=>{
                console.log(error);
            })
            this.treeData = treeData1;
        },
        components:{
            vSelect,
        }
    }

    export default Tree1
</script>

<style scoped>

</style>
