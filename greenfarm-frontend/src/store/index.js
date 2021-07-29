import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import indexNav from './modules/indexNav'
import user from './modules/user'
import cartGoods from './modules/cartGoods'
import title from './modules/title'
Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    indexNav,
    user,
    cartGoods,
    title
  },
  getters
})

export default store