<template>
    <div style="margin-top: 30px">
        <van-row>
            <van-col span="2">
                <img src="../assets/images/h1.png" height="24px" width="24px" style="margin-left: 8px;margin-top: 6px"/>
            </van-col>
            <van-col span="22">
                <span style="display: block;margin: 6px 0px">用户登录</span>
            </van-col>
        </van-row>
        <van-cell-group>
            <van-field
                    v-model="userName"
                    required
                    clearable
                    label="用户名"
                    right-icon="question-o"
                    placeholder="请输入用户名"
                    @click-right-icon="$toast('question')"
            />

            <van-field
                    v-model="passWord"
                    type="password"
                    label="密码"
                    placeholder="请输入密码"
                    required
            />
        </van-cell-group>
        <div class="d-flex justify-content-center">
            <div style="width: 70%;margin-top: 16px">
                <mySlider ref="isSuccess"></mySlider>
            </div>
        </div>

        <div class="d-flex justify-content-center">
            <van-button color="#7232dd" style="margin-top: 50px;margin-right: 50px" plain @click="doLogin">立即登录</van-button>
            <van-button color="#7232dd" style="margin-top: 50px" plain @click="toReg">前往注册</van-button>
        </div>
    </div>
</template>

<script>
    import mySlider from '../components/mslider.vue'
    export default {
        name: "login",
        components: {
            mySlider
        },
        data() {
            return {
                userName: '',
                passWord: '',
                loginUser:{}
            }
        },
        methods:{
            doLogin(){
                // 滑块验证
                let confirmSuccess = this.$refs.isSuccess.confirmSuccess;
                if (confirmSuccess){
                    let url=this.website+"/user/mLogin";
                    let that=this;
                    this.axios.get(url, {
                        params: {
                            userName: this.userName,
                            passWord: this.passWord
                        }
                    }).then(function (response) {
                        if (response.data.code == 200) {
                            that.loginUser = response.data.data;
                            // //console.log("登陆成功");
                            localStorage.setItem("loginUser",JSON.stringify(that.loginUser));
                            window.location.href = '/about';
                        } else {
                            //console.log("登陆失败");
                        }
                    }).catch(function ( ) {
                        // eslint-disable-next-line no-console
                        //console.log( );
                    })
                }else {
                    this.$dialog.alert({
                        message: '请先通过验证'
                    }).then(() => {
                        // on close
                    });
                }
            },
            toReg(){
                window.location.href = '/reg';
            },
        }
    }
</script>

<style scoped>

</style>