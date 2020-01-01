import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import NewImageVote from '../views/newImageVote.vue'
import Login from '../views/login.vue'
import MyVote from '../views/myVote.vue'
import Detail from '../views/detail.vue'
import NewQuestionnaire from '../views/newQuestionnaire.vue'
import QuesDetail from '../views/quesDetail.vue'
import QuesStart from '../views/quesStart.vue'
import Reg from '../views/reg.vue'


Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path:'/newQuestionnaire',
    name: 'newQuestionnaire',
    component:NewQuestionnaire
  },
  {
    path:'/newImageVote',
    name: 'newImageVote',
    component:NewImageVote
  },
  {
    path:'/login',
    name: 'login',
    component:Login
  },
  {
    path:'/reg',
    name: 'reg',
    component:Reg
  },
  {
    path:'/myVote',
    name: 'myVote',
    component:MyVote
  },
  {
    path:'/detail',
    name: 'detail',
    component:Detail
  },
  {
    path:'/quesDetail',
    name: 'quesDetail',
    component:QuesDetail
  },
  {
    path:'/quesStart',
    name: 'quesStart',
    component:QuesStart
  }
]

const router = new VueRouter({
  mode: 'history',
  base: '/pc/',
  routes
})

export default router
