<template>
    <div style="margin-top: 56px;height: 100%;padding-bottom: 56px">
        <el-container style="height: 100%">
            <el-aside width="200px" style="background-color:#f1f4f7">
                <el-menu
                        default-active="1"
                        class="el-menu-vertical-demo"
                        @open="handleOpen"
                        @close="handleClose"
                        background-color="#545c64"
                        text-color="#fff"
                        active-text-color="#ffd04b">
                    <router-link :to="{name:'accountIndex',params:{loginUser:loginUser}}">
                        <el-menu-item index="1">
                            <i class="el-icon-location"></i>
                            <span>用户首页</span>
                        </el-menu-item>
                    </router-link>
                    <router-link :to="{name:'createVote',params:{loginUser:loginUser}}">
                        <el-menu-item index="2">
                            <i class="el-icon-menu"></i>
                            <span slot="title">创建投票</span>
                        </el-menu-item>
                    </router-link>
                    <router-link :to="{name:'voteList'}">
                        <el-menu-item index="3">
                            <i class="el-icon-document"></i>
                            <span slot="title">投票列表</span>
                        </el-menu-item>
                    </router-link>
                    <router-link :to="{name:'accountInfo',params:{loginUser:loginUser}}">
                        <el-menu-item index="4">
                            <i class="el-icon-setting"></i>
                            <span slot="title">账户信息</span>
                        </el-menu-item>
                    </router-link>
                </el-menu>
            </el-aside>
            <el-container >
                <el-main style="padding: 15px 15px">
                    <router-view/>
                </el-main>
                <el-footer class="footer" height="30px">
                    <span class="footer">© 2019 we投票</span>
                </el-footer>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "mine",
        data() {
            return {
                isLogin: false,
                loginUser: {},
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        },
        mounted(){
            if (sessionStorage.getItem("loginUser") != null || localStorage.getItem("loginUser") != null) {
                this.isLogin = true;
                if (sessionStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
                }
                if (localStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(localStorage.getItem("loginUser"));
                }
            }
            if(this.isLogin==false){
                window.location.href = '/sign';
            }
        }
    }
</script>

<style scoped>

    .footer{
    color:#758697;
    font-size:13px;
    }
</style>