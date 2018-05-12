<template>
    <div style="width:auto">
        <div class="btn-group col-md-offset-4" style="width:auto;margin-top:20px;">
                <button class="btn btn-success btn-sm" v-on:click="showModel" style="margin-right:10px">添加父节点</button>
                <button class="btn btn-success btn-sm" v-on:click="removeList(1)" style="margin-right:10px">上移(选中节点)</button>
                <button class="btn btn-success btn-sm" v-on:click="removeList(2)">下移(选中节点)</button>
        </div>
        <div style="width:900px; margin: auto auto;color:black">
            <Tree
                :showModel="showModel"
                ref='tree'
                :treeData="treeData1"
                :options="options1"
                @node-click="itemClick1"
                @add-node="addNode"
                :courseId="courseId"
            >
            </Tree>
        </div>
        <div class="modal fade" id="addCourseList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">创建主节点</h4>
                    </div>

                    <div class="modal-body">
                        <form class="form-horizontal" @submit.prevent="addNewNode">
                            <div style="margin:auto;">
                            <p style="text-align:center;font-size: 16px;color: #999999;">
                                输入节点名称
                            </p>
                            </div>
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-2 control-label">节点名称</label>
                                <div class="col-sm-10">
                                    <input type="text" v-model="listName" class="form-control" id="inputCourseInfo" placeholder="节点名称">
                                </div>
                            </div>
                            <div>
                                <input type="submit" class="btn btn-success" style="margin-left:45%" value="添加节点">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

      <!-- 添加课程内容 -->
      <div class="modal fade" id="addCourseContent" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title" id="myModalLabel">添加内容</h4>
                  </div>
                  <addCourseContent :nodeId="nodeId"></addCourseContent>
              </div>
          </div>
      </div>
      <!-- 添加课程议题 -->
      <div class="modal fade" id="addCourseIssues" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title" id="myModalLabel">添加议题</h4>
                  </div>
                  <addCourseIssues :nodeId="nodeId"></addCourseIssues>
              </div>
          </div>
      </div>
      <!-- 添加课程表决 -->
      <div class="modal fade" id="addCourseVote" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title" id="myModalLabel">添加表决</h4>
                  </div>
                  <addCourseVote :nodeId="nodeId"></addCourseVote>
              </div>
          </div>
      </div>
      <!-- 重命名 -->
        <div class="modal fade" id="updateName" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">修改节点名称</h4>
                    </div>

                    <div class="modal-body">
                        <form class="form-horizontal" @submit.prevent="updateNodeName">
                            <div style="margin:auto;">
                            <p style="text-align:center;font-size: 16px;color: #999999;">
                                输入节点名称
                            </p>
                            </div>
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-2 control-label">节点名称</label>
                                <div class="col-sm-10">
                                    <input type="text" v-model="newName" class="form-control" id="newName" placeholder="节点名称">
                                </div>
                            </div>
                            <div>
                                <input type="submit" class="btn btn-success" style="margin-left:45%" value="修改名称">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
  import Tree from '../tree/tree.vue';
  import 'vue2-lazy-tree/dist/vue2-tree.min.css';
  import addCourseContent from '../ModalBox/addCourseContent.vue'
  import addCourseIssues from './CourseIssues.vue'
  import addCourseVote from  './CourseVote.vue'
  import Vue from 'Vue'
//   import bus from './eventBus.js';
  export default{
    name: 'CouseInfoList',
    data () {
        return {
            nodeId:0,
            options1: {
                showCheckbox: false,
                halfCheckedStatus: false,
                lazy: true,
                load: this.loadingChild,
                rootName: this.$route.query.courseName,
                iconClass: {
                    close: 'icon-youjiantou',
                    open: 'icon-xiajiantou',
                    add: 'icon-add'
                },
                iconStyle: {
                    color: 'black'
                },
                dynamicAdd: true,
                dynamicAddFilter: (node) => {
                    if (node.type === 1 || node.type === 2) {
                        return true
                    }
                    return false
                },
                dynamicAddNode: this.addNode,
                dynamicSaveNode: this.saveNode,
                leafIcon: this.leafIcon,
                treeType:0,
            },
            treeData1: [],
            listId: 0,
            listName: "",
            clickNode:[],
            courseId: this.$route.query.courseId,
            newName:"",
            checkNode:[],
        }
    },
    watch:{

    },
    methods:{
        updateNodeName(){
            this.checkNode.label = this.newName;
            axios.post("/api/updateCourseNodeInfoTitle",{
                id:this.checkNode.id,
                title:this.newName
            }).then((response) =>{
                console.log(response.data);
                $("#updateName").modal('hide');
                bus.$emit("dialog", {
                    type: "success",//success default error warn
                    body: " 修改成功！",
                    auto: true,//是否自动隐藏
                    time: 2000//毫秒
                })
            }).catch((error) =>{
                $("#updateName").modal('hide');
                bus.$emit("dialog", {
                    type: "error",//success default error warn
                    body: " 修改失败！",
                    auto: true,//是否自动隐藏
                    time: 2000//毫秒
                })
            })
            
        },
        showModel2(node,index){
          this.nodeId = node.id;
        //   console.log(this.nodeId+"=============="+index);
          if(index == 1){
            $('#addCourseContent').modal('show');
          }else if(index == 2){
            // $('#addCourseContent').modal('show');
          }else if(index == 3){
            $('#addCourseIssues').modal('show');
          }else if(index == 4){
            $('#addCourseVote').modal('show');
          }else{
            // console.log(node);
            this.newName = node.label;
            this.checkNode = node;
            $("#updateName").modal('show');
          }
        },
        generateKey (treeData = [], parentKey) {
            treeData = treeData.map((item, i) => {
                item.key = parentKey + '-' + i.toString();
                if (item.hasOwnProperty('children') && item.children.length > 0) {
                    this.generateKey(item.children, item.key)
                }
                return item;
            })
            return treeData;
        },
        getParentNode (node, treeData) {
            let tem;
            let postions = node.key.split('-');
            for (let [index, item] of postions.entries()) {
                // console.log("index:"+index+",item:"+item)
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        tem = treeData[item];
                        break;
                    default:
                        tem = tem.children[item];
                }
            }
            return tem
        },
        loadTreeData: function () {
            let treeData = [];
            var parent=[];
            axios.post("/api/getCourseNodeByCourseId",{'id':this.courseId}).then((response)=>{
                console.log(response.data.result);
                response.data.result.map((item,index) => {
                    parent.push(item.id);
                    if(item.level == 1){
                        treeData.push(
                            {
                                id:item.id,
                                label: item.title,
                                type: 1,
                                open: false,
                                checked: false,
                                nodeSelectNotAll: false,
                                parentId: null,
                                visible: true,
                                searched: false,
                                loaded: true,
                                children:[]
                            }
                        )
                        // var key = item.levelOrder-1;
                        Vue.set(item,'key',"0-"+(item.levelOrder));
                        // console.log(item.key);
                    }else{
                        var parentNode = response.data.result[parent.indexOf(item.parentId)];
                        if(parentNode.parentId==1){
                            treeData.map((fix, i) => {
                                if (fix.id == parentNode.id) {
                                    fix.children.push({
                                        id: item.id,
                                        label: item.title,
                                        open: false,
                                        type: 1,
                                        checked: false,
                                        nodeSelectNotAll: false,
                                        parentId: fix.id,
                                        visible: true,
                                        searched: false,
                                        loaded:true,
                                        children:[]
                                    })
                                }
                                Vue.set(item,'key',parentNode.key+"-"+(item.levelOrder));
                                // console.log(item.key);
                            });

                        }else{
                            var parentNode = response.data.result[parent.indexOf(item.parentId)];
                            var tem = [];
                            let postions = parentNode.key.split('-');
                            console.log(postions);
                            for(var i=1;i<postions.length;i++){
                                // console.log("i:"+i);
                                if(i==1){
                                    tem = treeData[parseInt(postions[i])];
                                }else if(i>1){
                                    tem = tem.children[parseInt(postions[i])];
                                }
                            }
                            tem.children.push({
                                id: item.id,
                                label: item.title,
                                open: false,
                                type: 1,
                                checked: false,
                                nodeSelectNotAll: false,
                                parentId: parentNode.id,
                                visible: true,
                                searched: false,
                                loaded:true,
                                children:[]
                            })
                            Vue.set(item,'key',parentNode.key+"-"+(item.levelOrder));
                        }
                    }
                })

            }).catch((error)=>{
                console.log(error);
            })
            this.treeData1 = treeData;

        },
        async loadingChild (node, index) {
            try {
                let data = await new Promise((resolve, reject) => {
                    setTimeout(() => {
                        let d = [
                            {
                                "id": 1,
                                "label": "新增加结点",
                                "open": false,
                                "checked": false,
                                "nodeSelectNotAll": false,
                                "parentId": null,
                                "visible": false,
                                "searched": false
                            },
                        ]
                        resolve(d)
                    }, 100)
                })
                let tem = this.getParentNode(node, this.treeData1)
                Vue.set(tem, 'children', data);
                // console.log(data);
                Promise.resolve(data);
            } catch (e) {
                Promise.reject(e);
            }
        },
        itemClick1 (node) {
            this.clickNode = node;
            console.log(this.clickNode.id);
        },
        async addNode (item) {
            let parent = this.getParentNode(item, this.treeData1)
            var th = this;
            var courseid = th.courseId;
            let postions = parent.key.split('-');
            let node = {
                id: 10000,
                label: " ",
                open: false,
                type: 1,
                checked: false,
                nodeSelectNotAll: false,
                parentId: parent.id,
                visible: true,
                searched: false,
                level: postions.length,
                levelOrder:parent.children.length,
                children:[]
            }
            if (!item.hasOwnProperty('children') || item.children.length === 0) {
                await this.loadingChild(parent)
            }
            parent.open = true
            parent.children.splice(parent.children.length, 0, Object.assign({}, { dynamicAdd: true, loaded: true }, node))
            return Promise.resolve(parent.children)
        },
        async saveNode (item, e) {
            if (!e.target.value) {
                return
            }
            try {
                // todo sent data to sever
                delete item.dynamicAdd // 删除属性

                Vue.set(item, 'label', e.target.value)

                e.target.value = ''
                // console.log(item);
                axios.post("/api/addCourseNodeInfo",{
                    'title':item.label,
                    'level':item.level,
                    'levelOrder':item.levelOrder,
                    'parentId':item.parentId,
                    'courseId':this.courseId
                }).then((response) => {
                    item.id = response.data.result.id;
                }).catch((error) => {
                    console.log(error);
                })
                return Promise.resolve(item) // server return data with id
            } catch (e) {
                return Promise.reject(e)
            }
        },
        leafIcon (node) {
            if (node.type === 1 || node.type === 2) {
                return ''
            }
            return 'icon-square'
        },
        addNewNode(){
            var th = this;
            var courseid = th.courseId;
            var len = th.treeData1.length;
            axios.post("/api/addCourseNodeInfo",{
                'title':this.listName,'level':1,'levelOrder':len,'parentId':1,'courseId':courseid
            }).then((response) => {
                // console.log(response.data.result);
                 let node = {
                    id: response.data.result.id,
                    label: response.data.result.title,
                    type: 1,
                    open: false,
                    checked: false,
                    nodeSelectNotAll: false,
                    parentId: null,
                    visible: true,
                    searched: false,
                    loaded: true
                }
                this.treeData1.push(node);
                this.listName= "";
                $("#addCourseList").modal('hide');
            }).catch((error) => {
                console.log(error);
            })
        },
        removeList(type){
            var index,tem;
            if(type==1){
                if(this.clickNode.parentId == null){
                    this.treeData1.map((item, i) => {
                        if (item.label == this.clickNode.label) {
                            index = i;
                        }
                    })
                    axios.post("/api/updateCourseNodeInfo",{
                        'id':this.clickNode.id,'levelOrder':index-1,'courseId':this.courseId
                    }).then((response) => {
                        console.log(response.data);
                    }).catch((error) => {
                        console.log(error);
                    })
                    this.treeData1.splice(index-1, 0, this.clickNode);
                    this.treeData1.splice(index+1,1);
                    // console.log();
                    axios.post("/api/updateCourseNodeInfo",{
                        'id':this.treeData1[index].id,'levelOrder':index,'courseId':this.courseId
                    }).then((response) => {
                        console.log(response.data);
                    }).catch((error) => {
                        console.log(error);
                    })
                }else{
                    let postions = this.clickNode.key.split('-');
                    for(var i=1;i<postions.length-1;i++){
                        if(i==1){
                            tem = this.treeData1[parseInt(postions[i])];
                        }else if(i>1){
                            tem = tem.children[parseInt(postions[i])];
                        }
                    }
                    index = parseInt(postions[postions.length-1]);
                    axios.post("/api/updateCourseNodeInfo",{
                        'id':this.clickNode.id,'levelOrder':index-1,'courseId':this.courseId
                    }).then((response) => {
                        console.log(response.data);
                    }).catch((error) => {
                        console.log(error);
                    })
                    tem.children.splice(index-1, 0, this.clickNode);
                    tem.children.splice(index+1,1);
                    axios.post("/api/updateCourseNodeInfo",{
                        'id':tem.children[index].id,'levelOrder':index,'courseId':this.courseId
                    }).then((response) => {
                        console.log(response.data);
                    }).catch((error) => {
                        console.log(error);
                    })
                }
            }else{
                if(this.clickNode.parentId == null){
                    this.treeData1.map((item, i) => {
                        if (item.label == this.clickNode.label) {
                            index = i;
                        }
                    })
                    console.log(index);
                    if(index == this.treeData1.length-1){
                        alert("已经到底了，不能在移动了");
                    }else{
                        axios.post("/api/updateCourseNodeInfo",{
                            'id':this.clickNode.id,'levelOrder':index+1,'courseId':this.courseId
                        }).then((response) => {
                            console.log(response.data);
                        }).catch((error) => {
                            console.log(error);
                        })
                        this.treeData1[index] = this.treeData1.splice(index+1,1,this.treeData1[index])[0];
                        axios.post("/api/updateCourseNodeInfo",{
                            'id':this.treeData1[index].id,'levelOrder':index,'courseId':this.courseId
                        }).then((response) => {
                            console.log(response.data);
                        }).catch((error) => {
                            console.log(error);
                        })
                    }
                }else{
                    let postions = this.clickNode.key.split('-');
                    for(var i=1;i<postions.length-1;i++){
                        if(i==1){
                            tem = this.treeData1[parseInt(postions[i])];
                        }else if(i>1){
                            tem = tem.children[parseInt(postions[i])];
                        }
                    }
                    index = parseInt(postions[postions.length-1]);
                    if(index == tem.children.length-1){
                        alert("已经到底了，不能在移动了");
                    }else{
                        axios.post("/api/updateCourseNodeInfo",{
                            'id':this.clickNode.id,'levelOrder':index+1,'courseId':this.courseId
                        }).then((response) => {
                            console.log(response.data);
                        }).catch((error) => {
                            console.log(error);
                        })
                        tem.children[index] = tem.children.splice(index+1,1,tem.children[index])[0];
                        axios.post("/api/updateCourseNodeInfo",{
                            'id':tem.children[index].id,'levelOrder':index,'courseId':this.courseId
                        }).then((response) => {
                            console.log(response.data);
                        }).catch((error) => {
                            console.log(error);
                        })
                    }
                }
            }
        },
        showModel(){
            $("#addCourseList").modal('show');
        }
    },
    beforeCreate(){},
    created(){},
    beforeMount(){},
    mounted(){
      var th = this;
        bus.$on('courseInfoModal',function(node,index){
          th.showModel2(node,index);
        });
        this.loadTreeData();
        this.listId = 2;
        console.log(this.courseId);
    },
    beforeUpdate(){},
    updated(){},
    beforeDestroy(){
      bus.$off('courseInfoModal');
    },
    destroyed(){},
    components:{
      'addCourseContent': addCourseContent,
      'addCourseIssues': addCourseIssues,
      'addCourseVote': addCourseVote,
      'Tree':Tree
    }
  }
</script>


<style scoped>
    .halo-tree li span{
        color: black
    }
</style>

