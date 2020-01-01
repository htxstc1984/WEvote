<template>
    <div id="app">
        <!--    <div id="nav">-->
        <!--      <router-link to="/">Home</router-link> |-->
        <!--      <router-link to="/about">About</router-link>-->
        <!--    </div>-->
        <router-view/>
        <van-tabbar v-model="active" @change="onChange" v-show="(path=='/'||path=='/about') ">
            <van-tabbar-item icon="home-o">首页</van-tabbar-item>
            <van-tabbar-item icon="user-o">个人中心</van-tabbar-item>
        </van-tabbar>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                isLogin:false,
                active: 0,
                loginUser:{},
                path:''
            }
        },
        methods: {
            onChange(index) {
                if (index == 0) {
                    this.$router.push('/')
                } else {
                    this.$router.push('/about')
                }
            }
        },
        mounted(){
            this.path = this.$route.path;
            if (sessionStorage.getItem("loginUser") != null || localStorage.getItem("loginUser") != null) {
                this.isLogin = true;
                if (sessionStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
                }
                if (localStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(localStorage.getItem("loginUser"));
                }
            }
        },
        watch:{
            $route(to){
                this.path = to.path;

                if (this.path=='/'){
                    this.active=0
                }else if(this.path=='/about'){
                    this.active=1
                }
            }
        }

    }
</script>

<style lang="scss">
    /*#app {*/
    /*  font-family: 'Avenir', Helvetica, Arial, sans-serif;*/
    /*  -webkit-font-smoothing: antialiased;*/
    /*  -moz-osx-font-smoothing: grayscale;*/
    /*  text-align: center;*/
    /*  color: #2c3e50;*/
    /*}*/

    /*#nav {*/
    /*  padding: 30px;*/

    /*  a {*/
    /*    font-weight: bold;*/
    /*    color: #2c3e50;*/

    /*    &.router-link-exact-active {*/
    /*      color: #42b983;*/
    /*    }*/
    /*  }*/
    /*}*/
</style>
