import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import store from './store'
import * as VeeValidate from 'vee-validate'

import Vuex from 'vuex';
Vue.config.productionTip = false;
Vue.use(VeeValidate);
Vue.use(Vuex);

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
