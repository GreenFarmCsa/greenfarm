import HomePage from '@/views/community/parent.vue'

const communityRouter = {
  path: '/community',
  name: 'community',
  component: HomePage,
  children: [
    {
      path: 'index',
      name: 'myCommunity',
      component: () => import(/*webpackChunkName:'me.financialList'*/'@/views/community/index'),
      meta: {
        title: 'Community',
        ifFirstRoute: true
      }
    },
    {
      path: 'post-detail/:id',
      name: 'communityPosts',
      component: () => import(/*webpackChunkName:'me.communityPosts'*/'@/views/community/post-detail'),
      meta: {
        title: 'Topic'
      }
    },
    {
      path: 'addTopic',
      name: 'communityAddTopic',
      component: () => import(/*webpackChunkName:'me.communityPosts'*/'@/views/community/addTopic'),
      meta: {
        title: 'Add a topic'
      }
    },
  ]
}

export default communityRouter