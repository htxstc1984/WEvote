<template>
    <div class="detail">
        <van-row>
            <van-col span="2">
                <img id="titleImg" src="../assets/images/h1.png" height="24px" width="24px"/>
            </van-col>
            <van-col span="22">
                <span id="title">{{vote.title}}</span>
            </van-col>
        </van-row>
        <div class="d-flex" id="creatorInfo">
            <van-image
                    round
                    fit="none"
                    width="3rem"
                    height="3rem"
                    :src=avatarUrl
                    style="margin-right: 12px"
            />
            <div>
                <span id="s1">投票发起人</span>
                <span id="s2">{{vote.creator.userName}}</span>
            </div>
        </div>
        <div id="deadline">截止时间：{{vote.deadline}}</div>
        <div id="prompt">(提示：点击选项可查看详情，投票结果于投票后可见)</div>
        <hr>
        <img :src=descriptionImgUrl width="360px">
        <div class="description" v-if="vote.description!=null">{{vote.description}}</div>
        <div class="description" v-else>暂无说明</div>
        <hr>
        <van-tabs v-model="active" id="tab1">
            <van-tab title="默认排序" style="width: 400px">
                <div class="options d-flex" v-for="(item,i) in optionList" :key=i>
                    <img height="64px" width="64px" :src=item.img>
                    <div class="option-content">
                        <div>{{item.content}}</div>
                        <div class="d-flex justify-content-end">
                            <div class="progress" style="width: 240px;margin-top: 8px">
                                <div class="progress-bar" role="progressbar" :style="{width: item.percent+'%'}"
                                     aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">{{item.percent}} %
                                </div>
                            </div>
                            <div class="votenum">{{item.votes}}票</div>
                        </div>
                    </div>
                </div>
            </van-tab>
            <van-tab title="票数排序" style="width: 400px">
                <div class="options d-flex" v-for="(item,i) in optionListSort" :key=i>
                    <img height="64px" width="64px" :src=item.img>
                    <div class="option-content">
                        <div>{{item.content}}</div>
                        <div class="d-flex justify-content-end">
                            <div class="progress" style="width: 240px;margin-top: 8px">
                                <div class="progress-bar" role="progressbar" :style="{width: item.percent+'%'}"
                                     aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">{{item.percent}} %
                                </div>
                            </div>
                            <div class="votenum">{{item.votes}}票</div>
                        </div>
                    </div>
                </div>
            </van-tab>
        </van-tabs>
        <hr>
        <van-tabs v-model="active" id="tab2">
            <van-tab title="参与投票" style="width: 400px">
                <van-radio-group v-model="isSelect">
                    <van-grid :border="false" :column-num="2">
                        <van-grid-item v-for="(item,i) in optionList" :key=i>
                            <van-image
                                    width="160"
                                    height="160"
                                    lazy-load
                                    :src=item.img
                                    fit="cover"
                                    style="margin: 5px"
                                    radius="5px"
                            />
                            <div class="d-flex justify-content-start" style="width: 160px">
                                <van-radio :name=item.id>{{item.content}}</van-radio>
                            </div>
                        </van-grid-item>
                    </van-grid>
                </van-radio-group>
                <div class="d-flex justify-content-between" style="margin: 8px">
                    <span>选择投票数</span>
                    <van-stepper v-model="onceVoteNum" integer :max="vote.onceMaxVote"/>
                </div>
            </van-tab>
        </van-tabs>

        <div class="d-flex justify-content-center">
            <van-button class="votebutton" type="info" @click="doVote()">投票</van-button>
        </div>
        <div class="d-flex justify-content-center">
            <van-button class="votebutton" plain type="info">分享投票</van-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "detail",
        data() {
            return {
                isLogin:false,
                loginUser:{},
                isSelect: '',
                voteId: '',
                vote: {},
                descriptionImgUrl: '',
                active: 0,
                optionList: [],
                optionListSort: [],
                onceVoteNum: '',
                avatarUrl:''
            }
        },
        methods: {
            async getVote(voteId) {
                let that = this;
                let url = this.website+"/vote/getVoteById"
                await this.axios.get(url, {
                    params: {
                        voteId: voteId
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        that.vote = response.data.data;
                        // //console.log(that.vote);
                    } else {
                        //console.log("投票信息查询失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
            },
            async getOption(voteId) {
                let that = this;
                let url = this.website+"/option/getOption"
                await this.axios.get(url, {
                    params: {
                        voteId: voteId
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        that.optionList = response.data.data;
                    } else {
                        //console.log("选项查询失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
            },
            doVote() {
                // //console.log(this.isSelect)
                // //console.log(this.onceVoteNum)
                if(this.isLogin==false){
                    this.$dialog.confirm({
                        message: '登录之后才能进行投票，点击确认立即前往登录'
                    }).then(() => {
                        // on confirm
                        window.location.href = '/login';
                    }).catch(() => {
                        // on cancel
                    });
                }else {
                    if(this.isSelect==''){
                        this.$dialog.alert({
                            message: '请选择一项'
                        }).then(() => {
                            // on close
                        });
                    }else{
                        let updateVoterUrl=this.website+"/vote/doVote";
                        let config = {
                            headers: { //添加请求头
                                "Content-Type": "application/x-www-form-urlencoded"
                            }
                        };
                        let params = new FormData(); //创建form对象
                        params.append("userId", this.loginUser.id);
                        params.append("voteId", this.voteId);
                        params.append("optionId", this.isSelect);
                        params.append("voteNum", this.onceVoteNum);
                        this.axios.put(updateVoterUrl,params,config).then(function (response) {
                            if (response.data.code == 200) {
                                this.$dialog.alert({
                                    message: '投票成功'
                                }).then(() => {
                                    // on close
                                    window.location.href = '/detail';
                                });
                                //console.log("投票信息修改成功");
                            } else {
                                //console.log("投票信息修改失败");
                            }
                        }).catch(function ( ) {
                            //console.log( );
                        })

                    }
                }
            }
        },
        async mounted() {
            // this.voteId = this.$route.params.voteId;
            this.voteId=this.$route.query.voteId;
            await this.getVote(this.voteId);
            await this.getOption(this.voteId);
            for (let i in this.optionList) {
                this.optionListSort[i] = this.optionList[i];
            }
            // this.descriptionImgUrl='D:/uploadFiles/'+this.vote.descriptionImg;
            let voteSum = 0;
            for (let i in this.optionList) {
                voteSum += this.optionList[i].votes;
                this.optionList[i].img=this.imgUrl+this.optionList[i].img;
            }
            // for (let i in this.optionListSort) {
            //     this.optionListSort[i].img=this.imgUrl+this.optionListSort[i].img;
            // }
            if(voteSum==0){
                for (let j in this.optionList) {
                    this.optionList[j].percent = 0;
                }
            }else {
                for (let j in this.optionList) {
                    let a = (this.optionList[j].votes / voteSum) * 100;
                    this.optionList[j].percent = a.toFixed(2);
                }
            }
            this.optionList.sort(function (x, y) {
                if (x.content < y.content) {
                    return -1;
                }
                if (x.content > y.content) {
                    return 1;
                }
                return 0;
            })
            this.optionListSort.sort(function (x, y) {
                if (x.votes < y.votes) {
                    return 1;
                }
                if (x.votes > y.votes) {
                    return -1;
                }
                return 0;
            })
            /*获取当前登录用户信息*/
            if (sessionStorage.getItem("loginUser") != null || localStorage.getItem("loginUser") != null) {
                this.isLogin = true;
                if (sessionStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
                }
                if (localStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(localStorage.getItem("loginUser"));
                }
            }
            this.avatarUrl=this.imgUrl+this.loginUser.avatar;
            this.descriptionImgUrl=this.imgUrl+this.vote.descriptionImg;
            console.log(this.optionList)
        }
    }
</script>

<style scoped>
    .detail {
        margin-top: 30px;
        margin-bottom: 90px;
    }

    .detail img {
        margin: 8px 8px;
    }

    .description {
        margin: 8px 8px;
        font-size: small;
    }

    .options {
        margin: 3px 8px;
    }

    .option-content {
        margin: 8px 8px;
    }

    .votenum {
        margin: 3px;
    }

    .votebutton {
        width: 160px;
        display: block;
        margin-top: 12px;

    }

    #titleImg {
        margin-left: 8px;
        margin-top: 6px
    }

    #title {
        display: block;
        margin: 6px 0px
    }

    #creatorInfo {
        margin: 5px 8px
    }

    #creatorInfo #s1 {
        color: #969799;
        display: block;
        font-size: x-small;
        margin-top: 3px
    }

    #creatorInfo #s1 {
        display: block;
    }

    #deadline {
        font-size: x-small;
        margin-left: 8px;
    }

    #prompt {
        font-size: x-small;
        color: #969799;
        margin-left: 8px;
        margin-bottom: 8px;
    }

    #tab1 {
        width: 160px;
    }

    #tab2 {
        width: 80px;
    }

    hr {
        margin: 0px 8px;
        border: 0;
        color: #e0e0e0;
        background-color: #e0e0e0;
        height: 1px
    }
</style>