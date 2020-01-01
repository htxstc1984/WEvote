<template>
    <div>
        <h1 class="page-header text-overflow">创建投票</h1>
        <ol class="breadcrumb">
            <li>用户中心</li>
            <li><i class="el-icon-arrow-right"></i>创建投票</li>
        </ol>
        <div class="card mycard">
            <h5 class="card-header">创建投票</h5>
            <div class="card-body ">
                <div class="d-flex justify-content-center">
                    <img src="../../assets/images/QR1.png" height="200px">
                </div>
                <div class="d-flex justify-content-center">
                    <span>(目前只支持手机端创建)</span>
                </div >
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "createVote",
        data() {
            return {

            }
        },
        methods: {

        },
        mounted() {

        }
    }
</script>

<style scoped>
    .mycard {
        margin-top: 30px
    }
</style>