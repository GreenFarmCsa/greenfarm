
const mallRouter = {
    path: '/mall',
    name: 'mall',
    component: () => import(/*webpackChunkName:'mall.parent'*/ '@/views/mall/index.vue'),
    children: [
        {
            path: 'index',
            component: () => import(/*webpackChunkName:'mall.homepage'*/ '@/views/mall/homepage.vue'),
            name: 'homePage',
            meta: {
                title: 'Produce',
                ifFirstRoute: true
            }
        },
        {
            path: 'goods/list/:id',
            component: () => import(/*webpackChunkName:'mall.goods'*/ '@/views/mall/goods/list.vue'),
            name: 'goodsList',
            meta: {
                title: 'Classification',
            }
        },
        {
            path: 'goods/result',
            component: () => import(/*webpackChunkName:'mall.goods'*/ '@/views/mall/goods/result.vue'),
            name: 'searchResult',
            meta: {
                title: 'Search Result',
            }
        },
        {
            path: 'goods/detail/:goodsId',
            component: () => import(/*webpackChunkName:'mall.goods'*/ '@/views/mall/goods/detail.vue'),
            name: 'goodsDetail',
            meta: {
                title: 'Produce Detail',
            }
        },
        {
            path: 'shopping-cart/list',
            component: () => import(/*webpackChunkName:'mall.shopping'*/ '@/views/mall/shopping-cart/list.vue'),
            name: 'shoppingCart',
            meta: {
                title: 'Cart',
            }
        },
        {
            path: 'order/edit',
            component: () => import(/*webpackChunkName:'mall.order'*/ '@/views/mall/order/edit.vue'),
            name: 'orderEdit',
            meta: {
                title: 'Complete the order',
            }
        },
        {
            path: 'order/result',
            component: () => import(/*webpackChunkName:'mall.order'*/ '@/views/mall/order/result.vue'),
            name: 'orderResult',
            meta: {
                title: 'Successful Tip',
            }
        },
    ]
}

export default mallRouter