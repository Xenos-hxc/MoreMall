import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import VueRouter from 'vue-router'
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/index'

import axios from 'axios';
import Qs from 'qs';


import '../filters.js'
import globle from './globle';

import * as echarts from 'echarts';
import 'echarts-gl';




Vue.prototype.$globle=globle;
Vue.prototype.qs = Qs;
Vue.use(VueRouter)
Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.prototype.$axios=axios
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
