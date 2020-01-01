<template>
    <div>
        <h1 class="page-header text-overflow">用户首页</h1>
        <ol class="breadcrumb">
            <li>用户中心</li>
            <li><i class="el-icon-arrow-right"></i>用户首页</li>
        </ol>
        <div class="card mycard">
            <h5 class="card-header">最近的投票</h5>
            <div class="card-body">
                <el-button type="primary" icon="el-icon-edit" size="small " @click="tocreateVote">创建投票</el-button>
                <el-button type="success" icon="el-icon-s-fold" size="small " @click="tovoteList">管理投票</el-button>
                <el-table
                        :data="voteList"
                        stripe
                        style="width: 100%"
                        type=index>
                    <el-table-column
                            prop="vid"
                            label="序号"
                            width="80">
                    </el-table-column>
                    <el-table-column
                            prop="title"
                            label="标题"
                            width="180">
                    </el-table-column>
                    <el-table-column label="状态" width="120">
                        <template slot-scope="scope">
                            <span v-if="scope.row.isDead" class="badge badge-pill badge-success">进行中</span>
                            <span v-else class="badge badge-pill badge-danger">已结束</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="optionNum"
                            label="选项数量"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="partnerNum"
                            label="参与人数"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="deadline"
                            label="结束时间"
                            width="200">
                    </el-table-column>
                </el-table>
            </div>
        </div>

        <div class="card mycard">
            <h5 class="card-header">最新公告</h5>
            <div class="card-body">
                <el-table
                        :data="tableData"
                        style="width: 100%">
                    <el-table-column
                            prop="date"
                            label="序号"
                            width="60">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="标题"
                            width="960">
                    </el-table-column>
                    <el-table-column
                            prop="address"
                            label="时间">
                    </el-table-column>
                </el-table>
            </div>
        </div>

    </div>
</template>
<script>
    export default {
        name: "accountIndex",
        data() {
            return {
                tableData: [],
                isLogin: false,
                loginUser: {},
                voteList: []
            }
        },
        methods: {
            getVoteList() {
                let that = this;
                let url = this.website + "/vote/getVote";
                this.axios.get(url, {
                    params: {
                        creatorId: this.loginUser.id
                    }
                }).then(async function (response) {
                    if (response.data.code == 200) {
                        let resultList = response.data.data;
                        for (let i in resultList) {
                            resultList[i].vid = parseInt(i) + 1;
                            resultList[i].isDead = that.compareTime(resultList[i].deadline);
                            resultList[i].optionNum = await that.getOptionNum(resultList[i].id);
                            if (resultList[i].voter != null) {
                                resultList[i].partnerNum = resultList[i].voter.length;
                            } else {
                                resultList[i].partnerNum = 0;
                            }
                            if (resultList[i].flag == 1) {
                                resultList.splice(i, 1);
                            }
                        }
                        that.voteList = resultList;
                        console.log(that.voteList)
                    } else {
                        console.log("查询失败");
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                })
            },
            async getOptionNum(voteId) {
                let optionNum = 0;
                let url = this.website + "/option/getOptionNum"
                await this.axios.get(url, {
                    params: {
                        voteId: voteId
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        optionNum = response.data.data;
                        console.log(optionNum);
                    } else {
                        console.log("查询失败");
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                })
                return optionNum;
            },
            compareTime(dateFormat) {
                let date = new Date(dateFormat);
                let nowDate = new Date();
                if (date.getTime() > nowDate.getTime()) {
                    return true;
                } else {
                    return false;
                }
            },
            tocreateVote() {
                this.$router.push({name:'createVote'})
            },
            tovoteList(){
                this.$router.push({name:'voteList'})
            }
        },
        async mounted() {
            if (sessionStorage.getItem("loginUser") != null || localStorage.getItem("loginUser") != null) {
                this.isLogin = true;
                if (sessionStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
                }
                if (localStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(localStorage.getItem("loginUser"));
                }
            }
            // this.loginUser=this.$route.params.loginUser;
            this.getVoteList();

        }
    }
</script>

<style scoped>
    .mycard {
        margin-top: 30px
    }
</style>