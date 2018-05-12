import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

const Index = resolve => require(['@/components/Index/Index'], resolve)
const CourseDashboard = resolve => require(['@/components/Course/CourseDashboard'], resolve)
const CourseFlow = resolve => require(['@/components/Course/CourseFlow'], resolve)
const CourseInfo = resolve => require(['@/components/Course/CourseInfo'], resolve)
const CourseClassManager = resolve => require(['@/components/Course/CourseClassManager'], resolve)
const CourseArrangement = resolve => require(['@/components/Course/CourseArrangement'], resolve)
const login = resolve => require(['@/components/login/login'], resolve)
const CourseInfoList = resolve => require(['@/components/Course/CourseInfoList'], resolve)
const CourseAddContent = resolve => require(['@/components/ModalBox/addCourseContent'], resolve)
const CourseIssues = resolve => require(['@/components/Course/CourseIssues'], resolve)
const CourseVote = resolve => require(['@/components/Course/CourseVote'], resolve)
const CourseArrangeContent = resolve => require(['@/components/Course/CourseArrangeContent'], resolve)



const OnlineDashboard = resolve => require(['@/components/online/Dashboard'],resolve)




Vue.use(Router)
const ABC = {
  template: `
        <div>
          <router-view/>
        </div>
      `
}
export default new Router({
    routes: [{
            path: '/',
            component: ABC,
            redirect: '/index',
            beforeEnter: (to, from, next) => {
                var data = JSON.parse(window.sessionStorage.getItem("user"));
                if (data != null) {
                    next();
                } else {
                    next('/login');
                }
            },
            children: [
              {
                    path: '/index',
                    name: 'Index',
                    component: Index
                },
                {
                    path: '/Course',
                    name: 'CourseDashboard',
                    component: CourseDashboard,
                    redirect: '/Course/CourseFlow',
                    children: [{
                            path: '/Course/CourseFlow',
                            name: 'CourseFlow',
                            component: CourseFlow,
                        },
                        {
                            path: '/Course/CourseArrangement',
                            name: 'CourseArrangement',
                            component: CourseArrangement,
                        },
                        {
                            path: '/Course/CourseInfo',
                            name: 'CourseInfo',
                            component: CourseInfo,
                        },
                        {
                            path: '/Course/CourseClassManager',
                            name: 'CourseClassManager',
                            component: CourseClassManager,
                        },
                        {
                            path: '/Course/CourseIssues',
                            name: 'CourseIssues',
                            component: CourseIssues,
                        },
                        {
                            path: '/Course/CourseVote',
                            name: 'CourseVote',
                            component: CourseVote,
                        },
                        // {
                        //     path: '/ModelBox/text',
                        //     name: 'HelloWorld',
                        //     component: HelloWorld
                        // },
                        {
                            path: '/Course/CourseInfoList',
                            name: 'CourseInfoList',
                            component: CourseInfoList
                        },
                        {
                            path: '/Course/CourseArrangeContent',
                            name: 'CourseArrangeContent',
                            component: CourseArrangeContent,
                        }
                    ]
                }
            ]
        },
        {
            path: '/login',
            name: 'login',
            component: login
        }, {
            path: '/abc',
            name: 'login',
            component: CourseAddContent
        },
        {
          path: '/online',
            name: 'OnlineDashboard',
          component: OnlineDashboard,
          children: []
        }

    ]
})
