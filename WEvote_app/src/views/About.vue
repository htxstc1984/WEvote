<template>
    <div class="about">
        <van-row style="margin: 30px;">
            <van-col span="6" v-if="loginUser.avatar==null">
                <van-image
                        round
                        fit="none"
                        width="4.4rem"
                        height="4.4rem"
                        src="http://112.124.26.125/test/image/avatar.png"
                        @click="showPopup"
                />
            </van-col>
            <van-col span="6" v-else>
                <van-image
                        round
                        fit="none"
                        width="4.4rem"
                        height="4.4rem"
                        :src=avatarUrl
                        @click="showPopup"
                />
            </van-col>
            <van-popup v-model="show" closeable style="height:170px;width: 200px">
                <van-uploader v-model="avatarList" :max-count="1" style="margin-top: 30px;margin-left: 56px"/>
                <van-button v-if="!showLoading" type="info" @click="uploadAvatar" loading-text="上传中..." style="margin-left: 38px;width: 120px">点击上传头像</van-button>
                <van-button v-else type="info" loading loading-text="上传中..." style="margin-left: 38px;width: 120px"></van-button>
            </van-popup>
            <van-col span="8">
                <span v-if="!isLogin" style="display:block;margin: 20px 0px">未登录</span>
                <span v-else style="display:block;margin: 20px 0px">{{loginUser.userName}}</span>
            </van-col>
            <van-col span="8">
                <van-button v-if="!isLogin" color="linear-gradient(to right, #4bb0ff, #6149f6)"
                            style="margin: 12px 10px;width: 100px" to="login">登录/注册
                </van-button>
                <van-button v-else color="linear-gradient(to right, #4bb0ff, #6149f6)" style="margin: 12px 10px"
                            @click="doLogout">注销
                </van-button>
            </van-col>
        </van-row>

        <van-row type="flex" justify="space-around" style="margin-bottom: 16px">
            <van-col span="6">
                <div>
                    <span style="display: block;font-weight:bold;text-align: center">0</span>
                    <span style="display: block;font-size: x-small;color: #969799;text-align: center">报名</span>
                </div>
            </van-col>
            <van-col span="6">
                <div @click="toMyVote">
                    <span style="display: block;font-weight:bold;text-align: center">{{voteNum}}</span>
                    <span style="display: block;font-size: x-small;color: #969799;text-align: center">发起</span>
                </div>
            </van-col>
            <van-col span="6">
                <div>
                    <span style="display: block;font-weight:bold;text-align: center">0</span>
                    <span style="display: block;font-size: x-small;color: #969799;text-align: center">投票</span>
                </div>
            </van-col>
        </van-row>
        <hr style="height: 5px">
        <van-cell-group>
            <div class="d-flex bd-highlight">
                <div class="p-2 w-100 bd-highlight d-flex">
                    <img src="../assets/images/h2.png" style="margin: 4px" height="30"/>
                    <span style="display:block;font-weight:bold;margin-top: 6px">客服/合作</span>
                </div>
                <div class="p-2 flex-shrink-1 bd-highlight">
                    <van-icon name="arrow" style="margin-top: 10px"/>
                </div>
            </div>
        </van-cell-group>
        <van-cell-group>
            <div class="d-flex bd-highlight">
                <div class="p-2 w-100 bd-highlight d-flex">
                    <img src="../assets/images/h3.png" style="margin: 4px" height="30"/>
                    <span style="display:block;font-weight:bold;margin-top: 6px">常见问题</span>
                </div>
                <div class="p-2 flex-shrink-1 bd-highlight">
                    <van-icon name="arrow" style="margin-top: 10px"/>
                </div>
            </div>
        </van-cell-group>
        <van-cell-group>
            <div class="d-flex bd-highlight">
                <div class="p-2 w-100 bd-highlight d-flex">
                    <img src="../assets/images/h4.png" style="margin: 4px" height="30"/>
                    <span style="display:block;font-weight:bold;margin-top: 6px">我的消息</span>
                </div>
                <div class="p-2 flex-shrink-1 bd-highlight">
                    <van-icon name="arrow" style="margin-top: 10px"/>
                </div>
            </div>
        </van-cell-group>
    </div>
</template>
<script>
    export default {
        name: 'about',
        components: {},
        data() {
            return {
                show: false,
                showLoading:false,
                isLogin: false,
                loginUser: {},
                voteNum: 0,
                avatarList: [],
                avatarUrl:''
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
                window.location.href = '/about'
            },
            toMyVote() {
                window.location.href = '/myVote'
            },
            getVoteNum() {
                let that = this;
                let url =this.website+ "/vote/getVoteNum";
                this.axios.get(url, {
                    params: {
                        creatorId: this.loginUser.id
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        //console.log("查询成功");
                        that.voteNum = response.data.data;
                    } else {
                        //console.log("查询失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
            },
            showPopup() {
                this.show = true;
            },
            uploadAvatar() {
                let that=this;
                this.showLoading=true;
                let config = {
                    headers: { //添加请求头
                        "Content-Type": "multipart/form-data"
                    }
                };
                let uploadUrl = this.website+"/user/uploadAvatar";
                let params = new FormData(); //创建form对象
                params.append("file", this.avatarList[0].file); //通过append向form对象添加数据
                params.append("userId",this.loginUser.id);
                this.axios.post(uploadUrl, params, config).then(function (response) {
                    if (response.data.code == 200) {
                        //console.log("头像上传成功");
                        that.show=false;
                        that.loginUser.avatar=response.data.data;
                    } else {
                        //console.log("上传失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
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
            this.avatarUrl=this.imgUrl+this.loginUser.avatar;
            this.getVoteNum();
        }
    }
</script>
<style scoped>
    hr {
        margin-top: 0px;
        margin: 0;
        border: 0;
        color: #e0e0e0;
        background-color: #e0e0e0;
        height: 1px
    }
</style>