import Vue from 'vue'
import store from './store'
import router from './router'
import mockArr from '../mock/jsapi'   //JSAPI MOCK 
import packageConfig from '../package.json'
import vConsole from 'vconsole'

// local&dev use vConsole
// if (process.env.NODE_ENV === 'development' || process.env.VUE_APP_ENV === 'staging') {
//   new vConsole();
// }

// 
router.afterEach((to, from, next) => {
  window.scrollTo(0, 1);
  window.scrollTo(0, 0);
})
router.beforeEach((to, from, next) => {
  let token = store.state.user.token
  if (process.env.NODE_ENV === 'development') {
    if (store.state.user.token == '') {
      const localtoken = localStorage.getItem('token')
      if (localtoken == undefined || localtoken == null) {
        token = ''
      } else {
        token = localtoken
      }
    }
  }
  if (token == '' && to.path != '/login') {
    if (from.path != '/login') {
      next({ path: '/login' })
    }
  } else {
    window.document.title = to.meta.title + ' - ' + 'Green Farm'
    if (to.name == "communityPostInfo") {
      to.meta.title = store.state.title.title
    }
    next()
  }

})
