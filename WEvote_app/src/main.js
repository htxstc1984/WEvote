import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vant from 'vant';
import 'vant/lib/index.css';
import { Lazyload } from 'vant';
import "bootstrap/dist/css/bootstrap.css"
import "bootstrap/dist/js/bootstrap.min.js"
import axios from 'axios';
import VueAxios from 'vue-axios';
import qs from 'qs';
Vue.use(qs);
Vue.use(VueAxios,axios);
Vue.use(Lazyload);
Vue.use(Vant);
Vue.config.productionTip = false;
Vue.prototype.website='http://112.124.26.125:8080';
Vue.prototype.website2='http://112.124.26.125:80';
Vue.prototype.imgUrl='http://112.124.26.125/test/image/'
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
