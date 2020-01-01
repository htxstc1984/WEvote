<template>
    <div class="quesStart">
        <div v-if="!start">
            <van-row type="flex" justify="center" style="margin-top: 120px">
                <van-image
                        width="300"
                        height="170"
                        :src=coverImgUrl
                />
            </van-row>
            <van-row type="flex" justify="center" style="margin: 16px 0px">
                <span>{{questionnaire.title}}</span>
            </van-row>
            <van-row type="flex" justify="center" style="margin-bottom: 16px">
                <van-col span="16">
                    <span id="descspan" class="detail-text">{{questionnaire.description}}</span>
                </van-col>
            </van-row>
            <van-row type="flex" justify="center" style="margin-top: 24px">
                <van-button color="linear-gradient(to right, #4bb0ff, #6149f6)" @click="startQues">开始答题</van-button>
            </van-row>
        </div>
        <div v-else>
            <van-tabs v-model="active">
                <van-tab  v-for="(item,i) in questionnaire.topicList" :title="'第' + i+'题'" :key="i">
                    <van-row style="margin-top: 24px">
                        <van-col>
                            <span id="topicId">{{i+1}}</span>
                        </van-col>
                        <van-col>
                            <span style="line-height: 50px">{{questionnaire.topicList[i].title}}</span>
                        </van-col>
                    </van-row>
                    <van-radio-group v-model="radio">
                    <van-row v-for="(option,j) in questionnaire.topicList[i].optionList" :key="j" style="margin: 16px">
                        <van-radio :name="j">{{questionnaire.topicList[i].optionList[j].content}}</van-radio>
                    </van-row>
                    </van-radio-group>
                </van-tab>
            </van-tabs>
            <van-row type="flex" justify="center" style="margin-top: 48px">
                <van-button color="linear-gradient(to right, #4bb0ff, #6149f6)" @click="submit" v-if="active==questionnaire.topicList.length-1">提交</van-button>
                <van-button color="linear-gradient(to right, #4bb0ff, #6149f6)" @click="nextTopic" v-else>下一题</van-button>
            </van-row>
        </div>
    </div>
</template>

<script>
    export default {
        name: "quesStart",
        data() {
            return {
                radio:'',
                active:0,
                start: false,
                quesId: '',
                questionnaire: {},
                coverImgUrl: 'http://112.124.26.125/test/image/cover.png',
            }
        },
        methods: {
            getQuesById(quesId) {
                let that = this;
                let url = this.website + "/questionnaire/getQuesById"
                this.axios.get(url, {
                    params: {
                        id: quesId
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        that.questionnaire = response.data.data;
                        that.coverImgUrl=that.imgUrl+that.questionnaire.coverImg;
                    } else {
                        //console.log("投票信息查询失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                })
            },
            startQues(){
                this.start=true;
            },
            nextTopic(){
                this.active+=1;
            },
            submit(){
                window.location.href = '/';
            }
        },
        mounted() {
            this.quesId = this.$route.query.voteId;
            this.getQuesById(this.quesId);
            if (sessionStorage.getItem("loginUser") != null || localStorage.getItem("loginUser") != null) {
                this.isLogin = true;
                if (sessionStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(sessionStorage.getItem("loginUser"));
                }
                if (localStorage.getItem("loginUser") != null) {
                    this.loginUser = JSON.parse(localStorage.getItem("loginUser"));
                }
            }
        }
    }
</script>

<style scoped>
    .quesStart {
        margin-top: 60px;
    }

    #descspan {
        color: #818181;
        font-size: x-small;
    }
    .detail-text {
        overflow: hidden;
        text-overflow: ellipsis;
        display: block;
        display: -webkit-box;
        -webkit-line-clamp: 5;
        /*! autoprefixer: off */
        -webkit-box-orient: vertical;
        /* autoprefixer: on */
    }
    #topicId{
        margin-left: 16px;
        margin-right: 8px;
        color: #2a89ff;
        font-size: xx-large;
    }
</style>