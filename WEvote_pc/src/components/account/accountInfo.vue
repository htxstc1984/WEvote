<template>
    <div>
        <h1 class="page-header text-overflow">账户设置</h1>
        <ol class="breadcrumb">
            <li>用户中心</li>
            <li><i class="el-icon-arrow-right"></i>账户设置</li>
        </ol>
        <div class="card mycard">
            <h5 class="card-header">基本信息</h5>
            <div class="card-body">
                <div class="info">用户名：<span style="color: #758697">{{loginUser.userName}}</span></div>
                <div class="info">手机号：<span style="color: #758697">{{loginUser.phoneNum}}</span></div>
                <div class="info">邮 箱：<span style="color: #758697" v-if="loginUser.email!=null">{{loginUser.email}}</span>
                    <span style="color: #758697" v-else>未绑定</span></div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "accountInfo",
        data() {
            return {
                loginUser: {},
            }
        },
        methods: {},
        mounted() {
            this.loginUser = this.$route.params.loginUser;
        }
    }
</script>

<style scoped>
    .mycard {
        margin-top: 30px
    }
    .info{
        margin:8px;
    }
</style>