import Vue from 'vue'
import VueRouter from 'vue-router'
import meRouter from './me'
import loginRouter from './login'
import mallRouter from './mall'
import farmRouter from './farm'
import communityRouter from './community'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/login',
    component: () => import(/*webpackChunkName:'home'*/ '@/views/home.vue'),
    meta: {
      title: 'home'
    }
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import(/*webpackChunkName:'dashboard'*/ '@/views/dashboard/index.vue'),
    meta: {
      title: 'Activity',
      ifFirstRoute: true
    }
  },
  meRouter,
  loginRouter,
  mallRouter,
  farmRouter,
  communityRouter
]

const router = new VueRouter({ routes })

const originalReplace = VueRouter.prototype.replace;
VueRouter.prototype.replace = function replace(location) {
  return originalReplace.call(this, location).catch(err => err);
};
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

export default router
