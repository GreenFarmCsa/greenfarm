const loginRouter = {
  path: '/login',
  name: 'login',
  component: () => import(/*webpackChunkName:'home'*/ '@/views/login/index.vue'),
  meta: {
    title: 'login',
    roles: ['admin', 'seler', 'user']
  }
}

export default loginRouter