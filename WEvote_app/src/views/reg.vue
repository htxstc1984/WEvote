<template>
    <div class="reg">
        <van-cell-group>
            <van-field
                    v-model="regUser.userName"
                    label="用户名"
                    placeholder="请输入用户名"
            />

            <van-field
                    v-model="regUser.passWord"
                    type="password"
                    label="密码"
                    placeholder="请输入密码"
            />
            <van-field
                    v-model="regUser.phoneNum"
                    label="手机号"
                    placeholder="请输入手机号"
                    :error-message=errormessage
            />
            <van-field
                    v-model="regUser.validateCode"
                    center
                    clearable
                    label="短信验证码"
                    placeholder="请输入短信验证码"
            >
                <van-button slot="button" size="small" type="primary" @click="sendRandomCode">发送验证码</van-button>
            </van-field>
        </van-cell-group>
        <div class="d-flex justify-content-center">
            <van-button color="#7232dd" style="margin-top: 50px" plain @click="doReg">前往注册</van-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "reg",
        data(){
            return{
                errormessage:'',
                token: '',
                regUser: {
                    userName: '',
                    passWord: '',
                    phoneNum: '',
                    email: '',
                    token:'',
                    validateCode:''
                },
            }
        },
        methods:{
            doReg(){
                let url = this.website + '/user/register';
                let that = this;
                this.axios.post(url, this.regUser).then(function (response) {
                    if (response.data.code == 200) {
                        that.$dialog.alert({
                            message: '注册成功'
                        }).then(() => {
                            // on close
                        });
                        localStorage.setItem("loginUser",JSON.stringify(that.regUser));
                        window.location.href = '/';
                    } else {
                        that.$dialog.alert({
                            message: '注册失败'
                        }).then(() => {
                            // on close
                        });
                    }
                }).catch(function () {
                    that.$dialog.alert({
                        message: '注册失败'
                    }).then(() => {
                        // on close
                    });
                })
            },
            //给手机发送验证码
            sendRandomCode: function () {
                let reg=/^1[3456789]\d{9}$/;
                if(this.regUser.phoneNum==''){
                    this.errormessage='请填写手机号'
                }else if(!reg.test(this.regUser.phoneNum)){
                    this.errormessage='手机号格式不正确'
                }else {
                    this.errormessage='';
                    let url = this.website + '/commons/getPhoneRandomCode?phoneNum=' + this.regUser.phoneNum;
                    let that = this;
                    this.axios.get(url)
                        .then(function (response) {
                            //console.log(response.data);
                            if (response.data.code == 200) {
                                that.regUser.token = response.data.data;
                                that.$notify({
                                    message: response.data.msg,
                                    type: 'success'
                                });
                            } else {
                                that.$notify({
                                    message: response.data.msg,
                                    type: 'danger'
                                });
                            }
                        }).catch(function () {
                        that.$notify({
                            message: '验证码发送失败！',
                            type: 'danger'
                        });
                    })
                }
            },
        },
        mounted(){

        }
    }
</script>

<style scoped>
.reg{
    margin-top: 30px;
}
</style>