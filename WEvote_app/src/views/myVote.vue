<template>
    <div class="myVote">
        <van-row>
            <van-col span="2">
                <img src="../assets/images/h1.png" height="24px" width="24px" style="margin-left: 8px;margin-top: 6px"/>
            </van-col>
            <van-col span="22">
                <span style="display: block;margin: 6px 0px">我发起的投票</span>
            </van-col>
        </van-row>
        <van-tabs v-model="active">
            <van-tab title="图文投票">
                <div v-for="(item,i) in voteList" :key=i>
                    <div v-if="item.flag==false">
                        <van-panel :title=item.title desc="标题" :status=item.status>
                            <div class="detail-text" style="margin: 10px 16px">{{item.description}}</div>
                            <div slot="footer" class="d-flex justify-content-end">
                                <van-button size="small" style="margin-right: 10px"
                                            :to="{path:'/detail',query: { voteId: item.id }}">详情
                                </van-button>
                                <van-button size="small" style="margin-right: 10px" @click="toShare(item.id,'/detail')">
                                    分享
                                </van-button>
                                <van-button size="small" type="danger" @click="doDelete(item.id)">删除</van-button>
                            </div>
                        </van-panel>
                        <hr style="height: 7px">
                    </div>
                </div>
            </van-tab>
            <van-tab title="问卷调查">
                <div v-for="(item,i) in quesList" :key=i>
                    <div v-if="item.flag==false">
                        <van-panel :title=item.title desc="标题" :status=item.status>
                            <div class="detail-text" style="margin: 10px 16px">{{item.description}}</div>
                            <div slot="footer" class="d-flex justify-content-end">
                                <van-button size="small" style="margin-right: 10px"
                                            :to="{path:'/quesStart',query: { voteId: item.id }}">预览
                                </van-button>
                                <van-button size="small" style="margin-right: 10px"
                                            :to="{path:'/quesDetail',query: { voteId: item.id }}">详情
                                </van-button>
                                <van-button size="small" style="margin-right: 10px"
                                            @click="toShare(item.id,'/quesStart')">分享
                                </van-button>
                                <van-button size="small" type="danger" @click="doDelete(item.id)">删除</van-button>
                            </div>
                        </van-panel>
                        <hr style="height: 7px">
                    </div>
                </div>
            </van-tab>
        </van-tabs>
        <van-dialog
                v-model="showShare"
                title="手机扫一扫"
        >
            <van-row type="flex" justify="center">
                <van-col><img :src=QRcode></van-col>
            </van-row>
            <van-row type="flex" justify="center">
                <van-col>
                    <span>复制链接：{{shortUrl}}</span>
                </van-col>
            </van-row>
        </van-dialog>
    </div>
</template>

<script>
    export default {
        name: "myVote",
        data() {
            return {
                showShare: false,
                isLogin: false,
                loginUser: {},
                active: 0,
                voteList: [],
                QRcode: '',
                shortUrl: '',
                quesList: []
            }
        },
        methods: {
            getQuesList() {
                let that = this;
                let url = this.website + "/questionnaire/getQues"
                this.axios.post(url, this.loginUser).then(function (response) {
                    if (response.data.code == 200) {
                        that.quesList = response.data.data;
                        //console.log(that.quesList)
                    } else {
                        //console.log("查询失败");
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
            },
            getVoteList() {
                let that = this;
                let url = this.website + "/vote/getVote"
                this.axios.get(url, {
                    params: {
                        creatorId: this.loginUser.id
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        that.voteList = response.data.data;
                    } else {
                        //console.log("查询失败");
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
            },
            deleteVote(vote) {
                let that = this;
                let url = this.website + "/vote/delVote"
                this.axios.post(url, vote).then(function (response) {
                    if (response.data.code == 200) {
                        that.$dialog.alert({
                            message: '删除成功'
                        }).then(() => {
                            // on close
                        });
                    } else {
                        that.$dialog.alert({
                            message: '删除失败'
                        }).then(() => {
                            // on close
                        });
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
            },
            doDelete(id) {
                let that = this;
                this.$dialog.confirm({
                    message: '确认删除'
                }).then(() => {
                    // on confirm
                    for (let i in that.voteList) {
                        if (that.voteList[i].id == id) {
                            that.voteList[i].flag = true;
                            that.deleteVote(that.voteList[i]);
                        }
                    }
                }).catch(() => {
                    // on cancel
                });
            },
            getQRcode(id, type) {
                let that = this;
                let longUrl = this.website2 + type + '?voteId=' + id;
                let url2 = 'api/getQRcode';
                this.axios.get(url2, {
                    params: {
                        text: longUrl,
                        key: 'd6e93df58937b9995c52d7998b8513b8'
                    }
                },).then(function (response) {
                    if (response.data.error_code == 0) {
                        that.QRcode = "data:image/png;base64," + response.data.result.base64_image;
                    } else {
                        console.log(response.data);
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    // console.log(err);
                })
            },
            getShortUrl(id, type) {
                let that = this;
                let shareUrl = this.website2 + type + '?voteId=' + id;
                let url = "api/getShortUrl"
                this.axios.get(url, {
                    params: {
                        format: 'json',
                        url: shareUrl,
                        key: '5e09e1efb1b63c2d90541992@01d1f947328e5aa20680c754c12eef90',
                        expireDate: '2020-03-31'
                    }
                }).then(function (response) {
                    if (response.data.err == "") {
                        that.shortUrl = response.data.url;
                    } else {
                        console.log(response.data)
                    }
                }).catch(function () {
                    // eslint-disable-next-line no-console
                    console.log();
                })
            },
            toShare(id, type) {
                this.getQRcode(id, type);
                this.getShortUrl(id, type);
                this.showShare = true;
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
            this.getVoteList();
            this.getQuesList();
        }

    }
</script>

<style scoped>
    .myVote {
        margin-top: 30px;
        margin-bottom: 90px;
    }

    hr {
        margin-top: 0px;
        margin: 0;
        border: 0;
        color: #e0e0e0;
        background-color: #e0e0e0;
        height: 1px
    }

    .detail-text {
        overflow: hidden;
        text-overflow: ellipsis;
        display: block;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        /*! autoprefixer: off */
        -webkit-box-orient: vertical;
        /* autoprefixer: on */
    }
</style>