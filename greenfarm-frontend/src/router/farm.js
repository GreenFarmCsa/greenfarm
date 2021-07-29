const farmRouter = {
    path: '/farm',
    name: 'farm',
    component: () => import(/*webpackChunkName:'farm.parent'*/ '@/views/farm/parent.vue'),
    children: [
        {
            path: '/farm/index',
            component: () => import(/*webpackChunkName:'farm.index'*/ '@/views/farm/index.vue'),
            name: 'farmList',
            meta: {
                title: 'Farm',
                ifFirstRoute: true
            }
        },
        {
            path: '/farm/detail/:id',
            component: () => import(/*webpackChunkName:'farm.detail'*/ '@/views/farm/detail.vue'),
            name: 'farmDetail',
            meta: {
                title: 'Farm Detail',
            }
        },
        {
            path: '/farm/region',
            component: () => import(/*webpackChunkName:'farm.classification'*/ '@/views/farm/region.vue'),
            name: 'farmRegion',
            meta: {
                title: 'Classification',
            }
        },
        {
            path: '/farm/area',
            component: () => import(/*webpackChunkName:'farm.classification'*/ '@/views/farm/area.vue'),
            name: 'farmArea',
            meta: {
                title: 'Classification',
            }
        },
        {
            path: '/farm/crop',
            component: () => import(/*webpackChunkName:'farm.classification'*/ '@/views/farm/crop.vue'),
            name: 'farmCrop',
            meta: {
                title: 'Classification',
            }
        },
        {
            path: '/farm/result/:keyword',
            component: () => import(/*webpackChunkName:'farm.classification'*/ '@/views/farm/result.vue'),
            name: 'farmResult',
            meta: {
                title: 'Classification',
            }
        },
        {
            path: '/farm/order/edit',
            component: () => import(/*webpackChunkName:'farm.order'*/ '@/views/farm/order/edit.vue'),
            name: 'farmOrderEdit',
            meta: {
                title: 'Confirm an order'
            }
        },
        {
            path: '/farm/order/result',
            component: () => import(/*webpackChunkName:'farm.order'*/ '@/views/farm/order/result.vue'),
            name: 'farmOrderResult',
            meta: {
                title: 'Successful Tip'
            }
        },
        {
            path: '/farm/subscribe/result',
            component: () => import(/*webpackChunkName:'farm.subscribe'*/ '@/views/farm/order/edit.vue'),
            name: 'farmSubscribeEdit',
            meta: {
                title: 'Subscribe'
            }
        }
    ]
}

export default farmRouter