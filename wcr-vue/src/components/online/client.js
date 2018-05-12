
const CHAT = {
  error: -2, //错误
  NORMAL: -1, //连接成功
  SIGN_IN_CHANGE: 0, //签到开关
  SIGN_IN_REFRESH: 1, //签到码刷新
  SEND_ONE: 2, //单独推送
  SEND_ALL: 3, //全体推送
  FEED_BACK: 4, //学生回馈
  ISSUES: 5, //议题
  VOTE: 6, //表决
  RESPONDER: 7, //抢答
  SELECT_PERSON: 8, //选人
  DISCUSSION: 9, //讨论
  INFORMATION: 10, //信息
  LOGIN: 11, //学生加入
  LOGOUT: 12, //学生离开
  RECONNECTION: 13, //学生重连
  HEART_BEAT: 14, //心跳包

  ws: Object,
  status: false,
  groups: [],
  courseArrange:{
    id:0,
    classId:0,
    courseId:0,
    topic:"",
    courseOntime:"",
    courseDowntime:"",
    courseInfo:{},
    courseClass:{},
    state:0,
    arrangeDataId:0,
    groups:""
  },
  arrangeData:{
    id:0,
    issue:"",
    vote:"",
    nodeNormal:"",
    nodeTree:"",
  },
  keywords: ["艹"],//敏感词过滤
  init: function (value) {
    var domain = window.location.host;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
      //CHAT.ws = new WebSocket("ws://"+domain+"/teacherwebsocket?token=" + window.sessionStorage.getItem("token") + "&classId=" + CHAT.courseArrange.id);
      CHAT.ws = new WebSocket("ws://10.10.3.113/teacherwebsocket?token=" + window.sessionStorage.getItem("token") + "&classId=" + CHAT.courseArrange.id);
      CHAT.ws.onopen = function (evt) {
        console.log("Connection open ...");
      };

      CHAT.ws.onmessage = function (evt) {
        let data = JSON.parse(evt.data);

        console.log("Received Message: " + evt.data);

        switch (data.type) {
          case CHAT.RESPONDER:
            bus.$emit("responder",data);
            break;
          case CHAT.ISSUES:
            bus.$emit("responIssues",data);
            break;
          case CHAT.VOTE:
            bus.$emit("responVote",data);
            break;
          case CHAT.HEART_BEAT:
            CHAT.forEachGroups((e, e1) => {
              if (data.studentId == e1.id) {
                e1.heart = false;
                e1.online = 1;
              }
            });
            break;
            //学生登录
          case CHAT.LOGIN:
            CHAT.forEachGroups((e, e1) => {
              if (data.studentId == e1.id) {
                if (e1.online != 1) {
                  e1.online = 1;
                  bus.$emit("dialog", {
                    type: "success",
                    body: e1.nickname + " 加入课堂",
                    auto: true,
                    time: 5000
                  })
                }

              }
            });
            break;
            //学生断开连接
          case CHAT.LOGOUT:
            CHAT.forEachGroups((e, e1) => {
              if (data.studentId == e1.id) {
                if (e1.online != 2) {
                  e1.online = 2;
                  bus.$emit("dialog", {
                    type: "warn",
                    body: e1.nickname + " 离开课堂",
                    auto: true,
                    time: 5000
                  })
                }
              }
            });
            break;
            //学生重新连接
          case CHAT.RECONNECTION:
            CHAT.forEachGroups((e, e1) => {
              if (data.studentId == e1.id) {
                if (e1.online != 1) {
                  e1.online = 1;
                  bus.$emit("dialog", {
                    type: "success",
                    body: e1.nickname + " 重新连接",
                    auto: true,
                    time: 5000
                  })
                }

              }
            });
            break;
          case CHAT.NORMAL:
            bus.$emit("dialog", {
              type: "success",
              body: data.allSend == 0?("创建成功"):("重连成功!"),
              auto: true,
              time: 5000
            });
            if(data.allSend == 1){
              for(var key in data.result){
                CHAT.forEachGroups((e,e1)=>{
                  console.log(e1.id)
                  if(e1.id == key){
                    e1.online = data.result[key];
                    console.log(e1.online)
                  }
                });
              }
            }
            CHAT.status = true;
            CHAT.sendHeartBeat();
            break;
          default:
            break;
        };
      }
      CHAT.ws.onclose = function (evt) {
        console.log("Connection closed." + evt.reason);
        if (CHAT.status) {
          bus.$emit("dialog", {
            type: "error",
            body: "服务器已断开！刷新重连！" + evt.reason,
            auto: false,
            time: 5000
          })
        } else {
          bus.$emit("dialog", {
            type: "warn",
            body:  "连接已中断！"+evt.reason,
            auto: false,
            time: 5000
          })
        }

      };

    } else {
      alert('Not support websocket')
    }

  },

  forEachGroups(callback) {
    CHAT.groups.forEach(e => {
      e.group.forEach(e1 => {
        callback(e, e1)
      });
    });
  },

  submit: function (jsonObject) {
    console.log("------------send:"+JSON.stringify(jsonObject))
    CHAT.ws.send(JSON.stringify(jsonObject));
  },

  filter_method: function (obj) {
    for (var i = 0; i < CHAT.keywords.length; i++) {
      if (obj.indexOf(CHAT.keywords[i]) != -1) {
        return false;
      }
    }
    return true;
  },
  sendHeartBeat() {
    console.log("开始轮询")
    //每30秒发送一次心跳包
    setInterval(() => {

      CHAT.forEachGroups((e, e1) => {
        //判断现有状态是否在线
        if (e1.online == 1) {
          //临时把heart拿来判断心跳包
          //每一次发送完，学生需在30秒内回复，并把heart改为false
          if (e1.heart == true) {
            bus.$emit("dialog", {
              type: "warn",
              body: e1.nickname + " 离开课堂",
              auto: true,
              time: 5000
            })
            e1.online = 0;
          } else {
            //上一次心跳包检测通过，重新改为true，等待学生回复
            e1.heart = true;
          }
        }
      });
      CHAT.submit({
        type: CHAT.HEART_BEAT,
        studentId: 0,
        classId: 0,
        result: null,
      });
    }, 30000)
  },formatTime:function(time) {
    var date = new Date(time);
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
      month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
      strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
    return currentdate;
  }
}
export default CHAT
