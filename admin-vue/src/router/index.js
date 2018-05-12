import Vue from 'vue'
import Router from 'vue-router'

const Login = resolve => require(['@/components/Login'], resolve)
const Dashboard = resolve => require(['@/components/Dashboard'], resolve)

const IndexManage = resolve => require(['@/components/page/IndexManage'], resolve)
const StudentManage = resolve => require(['@/components/page/StudentManage'], resolve)
const CollegeManage = resolve => require(['@/components/page/CollegeManage'], resolve)
const MajorManage = resolve => require(['@/components/page/MajorManage'], resolve)
const DefaultClassManage = resolve => require(['@/components/page/DefaultClassManage'], resolve)
const TeacherManage = resolve => require(['@/components/page/TeacherManage'], resolve)
const CourseManage = resolve => require(['@/components/page/CourseManage'], resolve)
const CourseClassManage = resolve => require(['@/components/page/CourseClassManage'], resolve)

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },{
      path: '/',
      name: 'Dashboard',
      component: Dashboard,
      redirect: 'IndexManage',
      beforeEnter: (to, from, next) => {
        var data = JSON.parse(window.sessionStorage.getItem("user"));
        if (data != null) {
          if(data.roles[0] == "ROLE_ADMIN"){
            next();
          }else{
            bus.$notify.error({
              title: '错误',
              message: '拒绝访问！'
            });
            next('/login');
          }
        } else {
          bus.$notify.error({
            title: '错误',
            message: '请先登录！'
          });
            next('/login');
        }
      },
      children: [
        {
          path: 'IndexManage',
          component: IndexManage,
        },
        {
          path: 'StudentManage',
          component: StudentManage,
        },
        {
          path: 'CollegeManage',
          component: CollegeManage,
        },
        {
          path: 'MajorManage',
          component: MajorManage,
        },
        {
          path: 'DefaultClassManage',
          component: DefaultClassManage,
        },
        {
          path: 'TeacherManage',
          component: TeacherManage,
        },
        {
          path: 'CourseManage',
          component: CourseManage,
        },
        {
          path: 'CourseClassManage',
          component: CourseClassManage,
        }
      ]
    }
  ]
})
