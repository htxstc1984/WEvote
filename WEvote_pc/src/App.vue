<template>
    <div id="app" style="width: 100%; height: 100%;">

        <!--    <div id="nav">-->
        <!--      <router-link to="/">Home</router-link> |-->
        <!--      <router-link to="/about">About</router-link>-->
        <!--    </div>-->
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <a class="navbar-brand" href="#">WE投票</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="/">首页 <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">案例中心</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" tabindex="-1" aria-disabled="true">功能介绍</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            关于我们
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#">员工风采</a>
                            <a class="dropdown-item" href="#">招纳贤士</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#">关于我们</a>
                        </div>
                    </li>
                </ul>
                <form class="form-inline my-2 my-lg-0">
                    <button type="button" class="btn btn-primary" style="margin-right: 10px">+创建投票</button>
                    <div v-if="!isLogin">
                        <router-link :to="{name:'sign',params:{signUp:true}}">
                            <button class="btn btn-outline-success" type="button" style="margin-right: 5px">sign in</button>
                        </router-link>
                        <router-link :to="{name:'sign',params:{signUp:false}}">
                            <button class="btn btn-outline-success" type="button">sign up</button>
                        </router-link>
                    </div>
                    <div v-else>
                        <router-link to="/mine">
                            <button class="btn btn-outline-success" type="button" style="margin-right: 5px">个人中心</button>
                        </router-link>
                        <button class="btn btn-outline-warning" type="button" @click="doLogout">注销</button>
                    </div>

                </form>
            </div>
        </nav>
        <router-view/>
    </div>
</template>

<script>
    export default {
        name: "App",
        data() {
            return {
                isLogin: false,
                loginUser: {}
            }
        },
        methods: {
            doLogout: function () {
                if (this.isLogin) {
                    if (sessionStorage.getItem("loginUser") != null) {
                        sessionStorage.removeItem("loginUser");
                    }
                    if (localStorage.getItem("loginUser") != null) {
                        localStorage.removeItem("loginUser");
                    }
                }
                window.location.href = '/'
            }
        },
        mounted() {
            if (sessionStorage.getItem("loginUser") != null || localStorage.getItem("loginUser") != null) {
                this.isLogin = true;
                if (sessionStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
                }
                if (localStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(localStorage.getItem("loginUser"));
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
