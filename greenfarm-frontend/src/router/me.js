
const routes =
{
  path: '/me',
  name: 'me',
  component: () => import(/*webpackChunkName:'me.parent'*/'@/views/me/parent'),
  redirect: '/me/index',
  children: [
    {
      path: 'index',
      name: 'meIndex',
      component: () => import(/*webpackChunkName:'me.meIndex'*/'@/views/me/index'),
      meta: {
        title: 'Me',
        ifFirstRoute: true
      }
    },
    {// consumer order list
      path: 'profile',
      name: 'myProfile',
      component: () => import(/*webpackChunkName:'me.goodList'*/'@/views/me/profile'),
      meta: {
        title: 'Profile'
      }
    },
    {// consumer order list
      path: 'goods/consumer-list',
      name: 'myGoodsConsumerList',
      component: () => import(/*webpackChunkName:'me.goodList'*/'@/views/me/goods/consumerList'),
      meta: {
        title: 'Order'
      }
    },
    {// consumer order details
      path: 'goods/consumer-detail',
      name: 'myGoodsConsumerDetail',
      component: () => import(/*webpackChunkName:'me.goodList'*/'@/views/me/goods/consumerDetail'),
      meta: {
        title: 'Order Detail'
      }
    },
    {// consumer evaluation
      path: 'goods/evaluation',
      name: 'myGoodsEvaluation',
      component: () => import(/*webpackChunkName:'me.goodList'*/'@/views/me/goods/evaluation'),
      meta: {
        title: 'Evaluation'
      }
    },
    {// farmer order list
      path: 'goods/farmer-list',
      name: 'myGoodsFarmerList',
      component: () => import(/*webpackChunkName:'me.goodList'*/'@/views/me/goods/farmerList'),
      meta: {
        title: 'Seller center'
      }
    },
    {
      path: 'carbon-account',
      name: 'carbonAccount',
      component: () => import(/*webpackChunkName:'me.carbonAccount'*/'@/views/me/carbonAccount'),
      meta: {
        title: 'Carbon Account'
      }
    },
    {
      path: 'farm-map',
      name: 'farmMap',
      component: () => import(/*webpackChunkName:'me.honorWall'*/'@/views/me/honorWall'),
      meta: {
        title: 'Farm Map'
      }
    },
    {
      path: 'lease/farmList',
      name: 'leaseList',
      component: () => import(/*webpackChunkName:'me.leaseList'*/'@/views/me/lease/farmList'),
      meta: {
        title: 'My CSA'
      }
    },
    {
      path: 'lease/farmShow',
      name: 'farm-show',
      component: () => import(/*webpackChunkName:'me.leaseList'*/'@/views/me/lease/farmShow'),
      meta: {
        title: 'My CSA'
      }
    },
    {
      path: 'lease/listFarmers',
      name: 'leaseListFarmers',
      component: () => import(/*webpackChunkName:'me.leaseList'*/'@/views/me/lease/listFarmers'),
      meta: {
        title: 'Registerd Farm'
      }
    },
    {
      path: 'lease/add',
      name: 'leaseAdd',
      component: () => import(/*webpackChunkName:'me.leaseAdd'*/'@/views/me/lease/add'),
      meta: {
        title: 'Add the Farm',
      }
    },
    {
      path: 'lease/edit',
      name: 'leaseEdit',
      component: () => import(/*webpackChunkName:'me.leaseEdit'*/'@/views/me/lease/edit'),
      meta: {
        title: 'Edit the Farm',
      }
    },
    {
      path: 'lease/confirm',
      name: 'leaseConfirm',
      component: () => import(/*webpackChunkName:'me.leaseConfirm'*/'@/views/me/lease/confirm'),
      meta: {
        title: 'Confirm',
      }
    },
    {
      path: 'lease/result',
      name: 'leaseResult',
      component: () => import(/*webpackChunkName:'me.leaseResult'*/'@/views/me/lease/result'),
      meta: {
        title: 'Lease Result',
      }
    },
    {
      path: 'lease/land-edit',
      name: 'leaseLandEdit',
      component: () => import(/*webpackChunkName:'me.leaseEdit'*/'@/views/me/lease/landedit'),
      meta: {
        title: 'Edit the Land',
      }
    },
    {
      path: 'lease/land-add',
      name: 'leaseLandAdd',
      component: () => import(/*webpackChunkName:'me.leaseEdit'*/'@/views/me/lease/landadd'),
      meta: {
        title: 'Add the Land',
      }
    },
    {
      path: 'plant/field-list',
      name: 'fieldList',
      component: () => import(/*webpackChunkName:'me.fieldList'*/'@/views/me/plant/fieldList'),
      meta: {
        title: 'My green planting',
      }
    },
    {
      path: 'plant/task-list',
      name: 'taskList',
      component: () => import(/*webpackChunkName:'me.taskList'*/'@/views/me/plant/taskList'),
      meta: {
        title: 'My green planting task'
      }
    },
    {
      path: 'plant/task-detail',
      name: 'taskDetail',
      component: () => import(/*webpackChunkName:'me.taskDetail'*/'@/views/me/plant/taskDetail'),
      meta: {
        title: 'Planting detail'
      }
    },
    {
      path: 'plant/overview',
      name: 'overview',
      component: () => import(/*webpackChunkName:'me.overview'*/'@/views/me/plant/overview'),
      meta: {
        title: 'Overview of planting tasks'
      }
    },
    {
      path: 'plant/goods/edit',
      name: 'goodsEdit',
      component: () => import(/*webpackChunkName:'me.goodsEdit'*/'@/views/me/plant/goods/edit'),
      meta: {
        title: 'Input'
      }
    },
    {
      path: 'plant/goods/box',
      name: 'goodsBox',
      component: () => import(/*webpackChunkName:'me.goodsEdit'*/'@/views/me/plant/goods/box'),
      meta: {
        title: 'Box'
      }
    },
    {
      path: 'plant/goods/result',
      name: 'goodsResult',
      component: () => import(/*webpackChunkName:'me.goodsResult'*/'@/views/me/plant/goods/result'),
      meta: {
        title: 'Submit completed'
      }
    },
    {
      path: 'community/posts',
      name: 'MycommunityPosts',
      component: () => import(/*webpackChunkName:'me.MycommunityPosts'*/'@/views/me/community/posts'),
      meta: {
        title: 'My Community'
      }
    },
    {
      path: 'community/postinfo',
      name: 'communityPostInfo',
      component: () => import('@/views/me/community/postinfo'),
      meta: {
        title: 'Hot Springs Base 1 Community'
      }
    },
    {
      path: 'financial-products/list',
      name: 'financialList',
      component: () => import(/*webpackChunkName:'me.financialList'*/'@/views/me/financial-products/list'),
      meta: {
        title: 'My Green Finance'
      }
    },
    {
      path: 'financial-products/detail/:id/:isSigned',
      name: 'financialDetail',
      component: () => import(/*webpackChunkName:'me.financialList'*/'@/views/me/financial-products/detail'),
      meta: {
        title: 'Green Agricultural Credit'
      }
    },
  ]
}



export default routes