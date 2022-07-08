import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import User from "../views/User";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/book",  //重定向至某个路由
    //二次路由的写法如下
    children: [
      {
        path: '/user',
        name: 'User',
        component: User,
      },
      {
        path: '/book',
        name: 'Book',
        component: () => import("../views/Book")
      },
      {
        path: '/information',
        name: 'Information',
        component: () => import("../views/Information")
      },
      {
        path: '/news',
        name: 'News',
        component: () => import("../views/News")
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("../views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("../views/Register")
  },
  {
    path: '/reSetPassword',
    name: 'ReSetPassword',
    component: () => import("../views/ReSetPassword")
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
