import Vue from 'vue'
import App from '@/App'
import router from '@/router'
import store from '@/store'
import Vant from 'vant'
import '@/cube-ui'
import Echarts from 'echarts'
import Axios from 'axios'

import '@/styles/font.css'
import '@/styles/base.scss'
import '@/styles/common.scss'
import '@/styles/public.scss'
import '@/assets/font/vant_font/index.css'

import '@/permission'
import '@/utils/rem-flexible'
import '../mock'
import "@/assets/icons"

Vue.prototype.$echarts = Echarts
Vue.prototype.$axios = Axios
Vue.prototype.$axios.options.emulateJSON = true
Vue.use(Vant)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  render: h => h(App)
})

