import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from "@/components/index.vue";
import Sign from "@/components/sign.vue";
import Mine from "@/components/mine.vue";
import AccountInfo from "@/components/account/accountInfo.vue";
import AccountIndex from "@/components/account/accountIndex.vue";
import CreateVote from "@/components/account/createVote.vue";
import VoteList from "@/components/account/voteList.vue";
Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'index',
        component: Index
    },
    {
        path: '/sign',
        name: 'sign',
        component: Sign
    },
    {
        path: '/mine',
        name: 'mine',
        component: Mine,
        children:[
            {
                path: '',
                name: 'accountIndex',
                component: AccountIndex
            },
            {
                path: 'accountInfo',
                name: 'accountInfo',
                component: AccountInfo
            },
            {
                path: 'createVote',
                name: 'createVote',
                component: CreateVote
            },
            {
                path: 'voteList',
                name: 'voteList',
                component: VoteList
            },
        ]
    },

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
