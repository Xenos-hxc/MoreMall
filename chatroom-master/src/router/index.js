import VueRouter from 'vue-router'
import Vue from "vue";

Vue.use(VueRouter)

const routes = [
    {path: '/', redirect: "/login"},
    {
        path: '/login',
        component: () => import("@/view/login")
    },
    {
        path: '/admin',
        component: () => import("@/head/adminHeadView"),
        redirect: "/admin/head",
        children: [{
            path: "/admin/head",
            name:"head",
            component: () => import("@/view/admin/head")
        },
            {
                path: "/admin/ProductDetail",
                name:"productDetail",
                component: () => import("@/view/admin/ProductDetail")
            },{
                path: "/admin/product",
                name:"product",
                component: () => import("@/view/admin/product")
            },{
                path: "/admin/person",
                name:"person",
                component: () => import("@/view/admin/person")
            }
            ,{
                path: "/admin/user",
                name:"user",
                component: () => import("@/view/admin/user")
            }
        ]

    }
]


const router = new VueRouter({
    routes
})

//     router.beforeEach((to, from, next) => {
//     //to 将要访问的路径
//     //from 代表从哪个路径而来
//     //next() 代表放行 next('xxx') 强制放行的xxx的路径
//     if(to.path==='/login'){
//         next();
//     }else{
//         const tokenStr = window.localStorage.getItem('token');
//         console.log(tokenStr);
//         if(!tokenStr){
//             return next('/login')
//         }
//         next();
//     }
// })

export default router
