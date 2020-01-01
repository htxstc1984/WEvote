<template>
    <div class="home">
        <!--    <img alt="Vue logo" src="../assets/logo.png">-->
        <!--    <HelloWorld msg="Welcome to Your Vue.js App"/>-->
        <van-row>
            <van-col span="4">
                <van-icon size="34" name="https://b.yzcdn.cn/vant/icon-demo-1126.png"
                          style="padding-top: 10px;padding-left: 16px"/>
            </van-col>
            <van-col span="20">
                <van-search
                        v-model="searchValue"
                        placeholder="请输入搜索关键词"
                        show-action
                        shape="round"
                        @search="onSearch"
                        style="padding-right: 10px"
                >
                    <div slot="action" @click="onSearch">搜索</div>
                </van-search>
            </van-col>
        </van-row>

        <!---->
        <van-row>
            <van-col span="2">
                <img src="../assets/images/h1.png" height="24px" width="24px" style="margin-left: 8px;margin-top: 6px"/>
            </van-col>
            <van-col span="22">
                <span style="display: block;margin: 6px 0px">创建投票</span>
            </van-col>
        </van-row>
        <van-row type="flex" justify="center">
            <van-col span="9">
                <img src="../assets/images/b3.png" class="img-fluid" @click="newQuestionnaire"/>
            </van-col>
            <van-col span="9">
                <img src="../assets/images/b2.png" class="img-fluid" @click="toNewVote"/>
            </van-col>
        </van-row>
        <!--        <van-row type="flex" justify="center">-->
        <!--            <van-col span="9">-->
        <!--                <img src="../assets/images/b1.png" class="img-fluid"/>-->
        <!--            </van-col>-->
        <!--            <van-col span="9">-->
        <!--                <img src="../assets/images/b4.png" class="img-fluid"/>-->
        <!--            </van-col>-->
        <!--        </van-row>-->
        <!-- -->
        <van-row>
            <van-col span="2">
                <img src="../assets/images/h1.png" height="24px" width="24px" style="margin-left: 8px;margin-top: 6px"/>
            </van-col>
            <van-col span="22">
                <span style="display: block;margin: 6px 0px">精选话题</span>
            </van-col>
        </van-row>
        <h6></h6>
        <van-row style="margin: 8px">
            <van-col>
                <img src="../assets/images/1172324.png" height="110px">
            </van-col>
            <van-col>
                <img src="../assets/images/1577870279.png" height="110px">
            </van-col>
        </van-row>
        <van-tag plain type="warning" style="margin-left: 8px">官方话题</van-tag>(所有图片上传限制1M一下)

        <!-- -->
        <van-row>
            <van-col span="2">
                <img src="../assets/images/h1.png" height="24px" width="24px" style="margin-left: 8px;margin-top: 6px"/>
            </van-col>
            <van-col span="22">
                <span style="display: block;margin: 6px 0px">热门评选</span>
            </van-col>
        </van-row>
        <div v-for="(item,i) in voteList" :key="i">
            <div class="card border-light mb-3 shadow-sm p-3 mb-5 bg-white rounded"
                 style="max-width: 30rem;margin: 6px 16px" v-if="item.flag==false">
                <div class="card-header">{{item.title}}</div>
                <div class="card-body">

                    <div class="detail-text" v-if="item.description!=''">{{item.description}}</div>
                    <div class="detail-text" v-else>暂无描述</div>
                    <van-image
                            width="305"
                            height="176"
                            lazy-load
                            :src=item.descriptionImg
                            fit="cover"
                            radius="5px"
                    />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    // @ is an alias to /src


    export default {
        name: 'home',
        components: {},
        data() {
            return {
                searchValue: '',
                imageList: [
                    '../assets/images/h1.png',
                ],
                voteList: [],
            }
        },
        methods: {
            onSearch() {
                //console.log(this.searchValue)
            },
            toNewVote() {
                this.$router.push('newImageVote')
            },
            newQuestionnaire() {
                this.$router.push('newQuestionnaire')
            },
            getVoteList: async function () {
                let voteList = [];
                let getVoteUrl = this.website + '/vote/getAllVote';
                await this.axios.get(getVoteUrl).then(function (response) {
                    if (response.data.code == 200) {
                        voteList = response.data.data;
                    } else {
                        //console.log("所有投票信息查询失败");
                    }
                }).catch(function () {
                    //console.log( );
                });
                return voteList;
            }
        },
        async mounted() {
            this.voteList = await this.getVoteList();
            for (let i in this.voteList) {
                this.voteList[i].descriptionImg = this.imgUrl + this.voteList[i].descriptionImg;
            }
            // //console.log(this.voteList)
        }
    }
</script>
<style scoped>
    .detail-text {
        overflow: hidden;
        text-overflow: ellipsis;
        display: block;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        /*! autoprefixer: off */
        -webkit-box-orient: vertical;
        /* autoprefixer: on */
    }

</style>
