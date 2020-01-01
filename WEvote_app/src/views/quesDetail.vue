<template>
    <div class="quesDetail">
问卷结果统计功能开发中
    </div>
</template>

<script>
    export default {
        name: "quesDetail",
        data() {
            return {
                voteId: '',

            }
        },
        methods: {},
        mounted() {
            this.voteId = this.$route.query.voteId;
        }
    }
</script>

<style scoped>
    .quesDetail {
    margin-top: 30px;
    }
</style>