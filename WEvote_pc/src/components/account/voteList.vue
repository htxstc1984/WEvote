<template>
    <div>
        <h1 class="page-header text-overflow">投票列表</h1>
        <ol class="breadcrumb">
            <li>用户中心</li>
            <li><i class="el-icon-arrow-right"></i>投票列表</li>
        </ol>
        <div class="card mycard">
            <h5 class="card-header">投票列表</h5>
            <div class="card-body">
                <el-button type="primary" icon="el-icon-edit" size="small " @click="tocreateVote">创建投票</el-button>
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
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <!--                            <el-button-->
                            <!--                                    size="mini"-->
                            <!--                                    type="warning"-->
                            <!--                                    @click="handleEdit(scope.$index, scope.row)">编辑-->
                            <!--                            </el-button>-->

                            <el-button
                                    size="mini"
                                    type="primary"
                                    data-toggle="modal" data-target="#exampleModalCenter"
                                    @click="handleShare(scope.row)">分享
                            </el-button>
                            <el-button
                                    size="mini"
                                    type="danger"
                                    @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalCenterTitle">分享</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="d-flex justify-content-center"><img :src=QRcode></div>
                        <div class="d-flex justify-content-center">复制链接：{{shortUrl}}</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "voteList",
        data() {
            return {
                isLogin: false,
                loginUser: {},
                voteList: [],
                QRcode: '',
                shortUrl: '',
            }
        },
        methods: {
            // handleEdit(index, row) {
            //
            //     console.log(index, row);
            // },
            async handleShare(row) {
                this.QRcode = await this.getQRcode(row.id);
                this.shortUrl = await this.getShortUrl(row.id);
            },
            handleDelete(index, row) {
                let that = this;
                this.$confirm('此操作将删除该投票信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(async () => {
                    let isDel = false;
                    for (let i in that.voteList) {
                        if (that.voteList[i].id == row.id) {
                            that.voteList[i].flag = true;
                            isDel = await that.deleteVote(that.voteList[i]);
                        }
                    }
                    if (isDel == true) {
                        that.Refresh();
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    } else {
                        this.$message({
                            type: 'error',
                            message: '删除失败!'
                        });
                    }
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
                console.log(index, row);
            },
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
                            if (resultList[i].flag == true) {
                                resultList.splice(i, 1)
                            }
                        }
                        that.voteList = resultList;
                    } else {
                        // console.log("查询失败");
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                })
            },
            async getOptionNum(voteId) {
                let optionNum = 0;
                let url = this.website + "/option/getOptionNum";
                await this.axios.get(url, {
                    params: {
                        voteId: voteId
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        optionNum = response.data.data;
                    } else {
                        // console.log("查询失败");
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                });
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
            async deleteVote(vote) {
                let isDel = false;
                let url = this.website + "/vote/delVote";
                await this.axios.post(url, vote).then(function (response) {
                    if (response.data.code == 200) {
                        isDel = true;
                    } else {
                        isDel = false;
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                })
                return isDel;
            },
            async getQRcode(id) {
                let QRcode = '';
                let longUrl = this.website2 + '/detail?voteId=' + id;
                let url = 'pc/QRcode';
                await this.axios.get(url, {
                    params: {
                        text: longUrl,
                        key: 'd6e93df58937b9995c52d7998b8513b8'
                    }
                },).then(function (response) {
                    if (response.data.error_code == 0) {
                        QRcode = "data:image/png;base64," + response.data.result.base64_image;
                    } else {
                        // console.log(response.data);
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                });
                return QRcode;
            },
            async getShortUrl(id) {
                let shortUrl = '';
                let longUrl = this.website2 + '/detail?voteId=' + id;
                let url = "pc/shortUrl";
                await this.axios.get(url, {
                    params: {
                        format: 'json',
                        url: longUrl,
                        key: '5e09e1efb1b63c2d90541992@01d1f947328e5aa20680c754c12eef90',
                        expireDate: '2020-03-31'
                    }
                }).then(function (response) {
                    if (response.data.err == "") {
                        shortUrl = response.data.url;
                    } else {
                        // console.log(response.data)
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                })
                return shortUrl;
            },
            Refresh() {
                for (let i in this.voteList) {
                    this.voteList[i].vid = parseInt(i) + 1;
                    if (this.voteList[i].flag == true) {
                        this.voteList.splice(i, 1)
                    }
                }
            },
            tocreateVote() {
                this.$router.push({name:'createVote'})
            },
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