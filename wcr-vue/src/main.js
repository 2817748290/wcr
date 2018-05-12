// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import router from './router'
import $ from 'jquery'
import jquery from 'jquery'
import 'bootstrap/js/bootstrap.min.js'
import 'bootstrap/css/bootstrap.min.css'


window.Vue = Vue;
window.bus = new Vue();

require("es6-promise").polyfill();
/**
 * http配置
 */
// 引入axios以及element ui中的loading和message组件
import axios from 'axios'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
// 超时时间
axios.defaults.timeout = 5000
// http请求拦截器
var loadinginstace
axios.interceptors.request.use(config => {
  config.headers.token = window.sessionStorage.getItem("token")
  return config
}, error => {
  console.log('加载超时');
  return Promise.reject(error)
})
// http响应拦截器
axios.interceptors.response.use(response => {

  if(response.data.status == -1){

    window.sessionStorage.removeItem("user")
    this.$router.push({ name: 'login'})
    alert("登录失效")
  }else if(response.data.status == -2){
    alert("权限拒绝")
  }else{
    if(response.headers.refresh != null){
      window.sessionStorage.setItem("token",response.headers.refresh)
    }
    return response
  }
}, error => {
  console.log('加载失败')
  return Promise.reject(error)
})

export default axios
window.axios = require('axios');
