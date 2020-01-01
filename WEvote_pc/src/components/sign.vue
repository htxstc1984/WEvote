<template>
    <div id="app" class="base" style="margin: auto;margin-top: 116px">
        <div v-if="signUp">
            <div class="aim">
                <h2>Hello mate!</h2>
                <h6>Enter your personal details and start journey with me</h6>
                <button @click="signUp = !signUp" class="btn ">Sign up</button>
            </div>
            <div class="formArea">
                <h2>Sign in to WEvote</h2>
                <h6>Fill in the required fields</h6>

                <div class="form-group">
                    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                           placeholder="Your email" v-model="userName">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password"
                           v-model="passWord">
                </div>
                <div class="form-group d-flex">
                    <input type="text" class="form-control" id="exampleInput" aria-describedby="emailHelp"
                           placeholder="Verification code" v-model="validateCode">
                    <img class="rounded float-right" style="cursor:pointer;height: 40px" title="看不清换一张"
                         :src="randomCodeUrl" @click="changeRandomCode">
                </div>
                <button tabindex="0" role="button" class="btn" id="signin" @click="doLogin">Sign in</button>

            </div>
        </div>

        <div v-else>
            <div class="aim in">
                <h2>Welcome back!</h2>
                <h6>To keep connected with me, please login with your personal infos</h6>
                <button @click="signUp = !signUp" class="btn">Sign in</button>
            </div>
            <div class="formArea in">
                <h2>Create an account</h2>
                <h6>Fill in the required fields</h6>
                <div class="form-group">
                    <input type="text" class="form-control" id="exampleInputName1" placeholder="Name"
                           v-model="regUser.userName">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password"
                           v-model="regUser.passWord">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" id="exampleInputPassword3" placeholder="Password Again"
                           v-model="passWordAgain">
                    <span class="badge badge-danger" v-if="seen">两次填写的密码不一致</span>
                </div>
                <button class="btn" @click="register">Sign up</button>
            </div>

        </div>
    </div>

</template>
<script>
    // import mySlider from '../components/commons/slider.vue'

    export default {
        name: "sign",
        data() {
            return {
                userName: '',
                passWord: '',
                signUp: true,
                loginUser: {},
                randomCodeUrl: '',
                validateCode: '',
                token: '',
                regUser: {
                    userName: '',
                    passWord: '',
                    phoneNum: '',
                    email: '',
                    token:'',
                    validateCode:''
                },
                passWordAgain: '',
                seen: false
            }
        },
        components: {
            // mySlider
        },
        mounted() {
            this.changeRandomCode();
            this.signUp=this.$route.params.signUp
        },
        methods: {
            doLogin() {
                /*滑块验证*/
                // let confirmSuccess = this.$refs.isSuccess.confirmSuccess;
                // if (confirmSuccess){
                //     // console.log(true);
                //     let url="http://localhost:8080/user/Login";
                //     let that=this;
                //     this.axios.get(url,{
                //         params: {
                //             userName: this.userName,
                //             passWord: this.passWord
                //         }
                //     }).then(function (response) {
                //         if(response.data.code==200){
                //             that.loginUser=response.data.data;
                //             // console.log("登陆成功");
                //         }else {
                //             // console.log("登陆失败");
                //         }
                //     }).catch(function (err) {
                //         // eslint-disable-next-line no-console
                //         // console.log(err);
                //     })
                // }else {
                //     // console.log(false);
                //     this.$message({
                //         message: '请先通过验证！',
                //         type: 'warning'
                //     });
                // }

                /*公式验证 后端*/
                // let url = 'http://localhost:8080/user/Login';
                // let that = this;
                // this.axios.get(url, {
                //     params: {
                //         userName: this.userName,
                //         passWord: this.passWord,
                //         validateCode: this.validateCode,
                //         token: this.token
                //     }
                // }).then(function (response) {
                //     if (response.data.code == 200) {
                //         // console.log("登录成功用户对象->" + response.data.data);
                //         that.$message({
                //             message: response.data.msg,
                //             type: 'success'
                //         });
                //     } else {
                //         that.$message({
                //             message: response.data.msg,
                //             type: 'error'
                //         });
                //     }
                // }).catch(function (error) {
                //     that.$message({
                //         message: '登录失败！',
                //         type: 'error'
                //     });
                //     // console.log(error);
                // })

                /*hutool生成验证码 后端*/
                let url = this.website + '/user/Login';
                let that = this;
                this.axios.get(url, {
                    params: {
                        userName: this.userName,
                        passWord: this.passWord,
                        validateCode: this.validateCode,
                        token: this.token
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        // // console.log("登录成功用户对象->" + response.data.data);
                        that.loginUser = response.data.data;
                        that.$message({
                            message: response.data.msg,
                            type: 'success'
                        });
                        localStorage.setItem("loginUser",JSON.stringify(that.loginUser));
                        window.location.href = '/';
                    } else {
                        that.$message({
                            message: response.data.msg,
                            type: 'error'
                        });
                    }
                }).catch(function () {
                    that.$message({
                        message: '登录失败！',
                        type: 'error'
                    });
                    // console.log(error);
                })
            },

            register: function () {
                if (this.regUser.passWord != this.passWordAgain) {
                    // console.log('两次密码不匹配');
                    this.seen = true;
                } else {
                    this.seen = false;

                    // this.$alert('<strong>这是 <i>HTML</i> 片段</strong>', '身份验证', {
                    //     dangerouslyUseHTMLString: true
                    //
                    // });
                    this.$prompt('请输入手机号', '身份验证', {
                        confirmButtonText: '发送验证码',
                        cancelButtonText: '取消',
                        // inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
                        inputPattern: /^1[3456789]\d{9}$/,
                        inputErrorMessage: '手机号格式不正确',
                        beforeClose: (action, instance, done) => {
                            if (action === 'confirm') {
                                instance.confirmButtonLoading = true;
                                instance.confirmButtonText = '发送中...';
                                setTimeout(() => {
                                    done();
                                    setTimeout(() => {
                                        instance.confirmButtonLoading = false;
                                    }, 300);
                                }, 3000);
                            } else {
                                done();
                            }
                        }
                    }).then(({value}) => {
                        this.regUser.phoneNum = value;
                        this.sendRandomCode();
                        this.$prompt('请输入验证码', '身份验证', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                        }).then(({value}) => {
                            this.regUser.validateCode = value;
                            let url = this.website + '/user/register';
                            let that = this;
                            this.axios.post(url, this.regUser).then(function (response) {
                                if (response.data.code == 200) {
                                    that.$message({
                                        message: response.data.msg,
                                        type: 'success'
                                    });
                                    localStorage.setItem("loginUser",JSON.stringify(that.regUser));
                                    window.location.href = '/';
                                } else {
                                    that.$message({
                                        message: response.data.msg,
                                        type: 'error'
                                    });
                                }
                            }).catch(function () {
                                that.$message({
                                    message: '注册失败！',
                                    type: 'error'
                                });
                                // console.log(error)
                            })
                        }).catch(() => {
                            this.$message({
                                type: 'info',
                                message: '取消输入'
                            });
                        });
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '取消输入'
                        });
                    });
                }
            },
            //给手机发送验证码
            sendRandomCode: function () {
                this.changeToken();
                let url = this.website + '/commons/getPhoneRandomCode?phoneNum=' + this.regUser.phoneNum;
                let that = this;
                this.axios.get(url)
                    .then(function (response) {
                        //// console.log(response.data);
                        if (response.data.code == 200) {
                            that.regUser.token = response.data.data;
                            that.$message({
                                message: response.data.msg,
                                type: 'success'
                            });
                        } else {
                            that.$message({
                                message: response.data.msg,
                                type: 'error'
                            });
                        }
                    }).catch(function () {
                    that.$message({
                        message: '验证码发送失败！',
                        type: 'error'
                    });
                    // console.log(error)
                })
            },

            changeRandomCode: function () {
                const jwt = require('jsonwebtoken');
                let payload = new Date().getTime() + ""; //当前时间的毫秒数
                let secret = 'ray';
                this.token = jwt.sign(payload, secret);//那你的公钥+私钥，生成唯一token
                this.randomCodeUrl = this.website + '/commons/hutoolRandomCodeImage?token=' + this.token
            },
            changeToken: function () {
                const jwt = require('jsonwebtoken');
                let payload = new Date().getTime() + ""; //当前时间的毫秒数
                let secret = 'ray';
                this.token = jwt.sign(payload, secret);//那你的公钥+私钥，生成唯一token
            }
        }
    }
</script>

<style scoped>
    @import "../assets/css/style.css";

</style>