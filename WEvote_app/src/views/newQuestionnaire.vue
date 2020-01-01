<template>
    <div class="maindiv">
        <van-row type="flex" justify="end" id="updatebutton">
            <van-uploader :after-read="afterread">
                <van-button round type="info" color="linear-gradient(to right, #4bb0ff, #6149f6)" size="small">点击上传
                </van-button>
            </van-uploader>
        </van-row>
        <van-row type="flex" justify="center">
            <van-image
                    width="300"
                    height="170"
                    :src=coverImgUrl
            />
        </van-row>
        <van-row type="flex" justify="center" style="margin-bottom: 16px">
            <span id="updatespan">建议上传像素为600*340的封面图片</span>
        </van-row>
        <hr style="height: 7px">
        <van-cell-group class="mygroup">
            <van-field
                    v-model="title"
                    label="问卷标题"
                    placeholder="必填"
            />

            <van-field
                    v-model="description"
                    label="问卷描述"
                    placeholder="大致描述问卷内容"
            />
        </van-cell-group>
        <hr style="height: 7px">
        <div v-for="(topic,i) in topicList" :key="i">
            <van-cell-group>
                <van-field v-model="topic.title" placeholder="题目标题"/>
            </van-cell-group>
            <van-cell-group v-for="(item,j) in topic.optionList" :key="j">
                <van-row>
                    <van-col span="2">
                        <img src="../assets/images/del.png" style="margin:12px 5px;" height="24px" width="24px"
                             @click="delTable(i,j)">
                    </van-col>
                    <van-col span="22">
                        <van-field v-model="item.content" placeholder="选项内容"/>
                    </van-col>
                </van-row>
            </van-cell-group>
            <van-row>
                <van-col span="2">
                    <img src="../assets/images/add.png" style="margin:12px 5px;" height="24px" width="24px"
                         @click="addTable(i)">
                </van-col>
                <van-col span="15">
                    <span id="addtable" @click="addTable(i)">添加选项</span>
                </van-col>
                <van-col span="2">
                    <img src="../assets/images/del2.png" style="margin:14px 5px;" height="20px" width="20px"
                         @click="delTopic(i)">
                </van-col>
                <van-col span="5">
                    <span id="deltable" @click="delTopic(i)">删除题目</span>
                </van-col>
            </van-row>
        </div>
        <van-row type="flex" justify="center" style="margin: 8px 32px">
            <van-button type="info" icon="add-o" size="large" @click="addTopic">添加题目</van-button>
        </van-row>
        <hr style="height: 7px">
        <van-cell-group>
            <van-row>
                <van-col span="16">
                    <span style="display: block;margin: 14px 16px">截止时间</span>
                </van-col>
                <van-col span="8">
                    <span @click="onShow" style="display: block;margin-top: 14px">{{deadline}}</span>
                </van-col>
            </van-row>
        </van-cell-group>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-primary" style="margin-top: 20px" @click="createQuestionnaire">创建问卷
            </button>
        </div>
        <van-action-sheet v-model="timeShow">
            <van-datetime-picker
                    v-model="timeSet.currentDate"
                    type="datetime"
                    :min-date="timeSet.minDate"
                    :max-date="timeSet.maxDate"
                    :formatter="formatter"
                    @confirm="confirmTime"
                    @cancel="cancelTime"
            />
        </van-action-sheet>
    </div>
</template>

<script>
    export default {
        name: "newQuestionnaire",
        data() {
            return {
                coverImgUrl:'http://112.124.26.125/test/image/cover.png',
                isLogin: false,
                loginUser: {},
                timeShow: false,
                title: '',
                coverImgName:'',
                description: '',
                topicList: [
                    {
                        title: '',
                        optionList: [{content: ''}, {content: ''}],
                    }
                ],
                deadline: '',
                timeSet: {
                    minDate: new Date(),
                    maxDate: new Date(2100, 11, 31),
                    currentDate: new Date()
                },
                newQuestionnaire: {},
            }
        },
        methods: {
            async afterread(file) {
                this.coverImgName = await this.uploadImg(file);
                this.coverImgUrl=this.imgUrl+this.coverImgName;
            },

            delTable(idx, idy) {
                this.topicList[idx].optionList = this.topicList[idx].optionList.filter((
                    item, i) => {
                    // //console.log([item, i, idx])
                    if (i != idy) {
                        return "e"
                    }
                })
            },
            addTable(idx) {
                this.topicList[idx].optionList.push({
                    content: ''
                })
            },
            addTopic() {
                this.topicList.push({
                    title: '',
                    optionList: [{content: ''}, {content: ''}],
                })
            },
            delTopic(idx) {
                this.topicList = this.topicList.filter((
                    item, i) => {
                    // //console.log([item, i, idx])
                    if (i != idx) {
                        return "e"
                    }
                })
            },
            formatter(type, value) {
                if (type === 'year') {
                    return `${value}年`;
                } else if (type === 'month') {
                    return `${value}月`
                } else if (type === 'day') {
                    return `${value}日`
                } else if (type === 'hour') {
                    return `${value}时`
                } else if (type === 'minute') {
                    return `${value}分`
                }
                return value;
            },
            onShow() {
                this.timeShow = true
            },
            confirmTime(value) {
                this.deadline = value.format("yyyy-MM-dd hh:mm");
                this.timeShow = false
            },
            cancelTime() {
                this.timeShow = false
            },
            uploadImg: async function (imgFile) {
                // let that=this;
                let imgName = imgFile.file.name;
                let config = {
                    headers: { //添加请求头
                        "Content-Type": "multipart/form-data"
                    }
                };
                let uploadUrl = this.website + "/commons/upload";
                let params = new FormData(); //创建form对象
                params.append("file", imgFile.file); //通过append向form对象添加数据//第一个参数字符串可以填任意命名，第二个根据对象属性来找到file
                await this.axios.post(uploadUrl, params, config).then(function (response) {
                    if (response.data.code == 200) {
                        //console.log(imgName + "图片上传成功");
                        imgName = response.data.data;
                    } else {
                        this.$notify('上传失败');
                        //console.log("上传失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                });
                return imgName;
            },
            async createQuestionnaire() {
                let topicTitleCheck = true;
                let topicOptionCheck = true;
                for (let i in this.topicList) {
                    if (this.topicList[i].title == '') {
                        topicTitleCheck = false;
                    }
                    for (let j in this.topicList[i].optionList) {
                        if (this.topicList[i].optionList[j].content == '') {
                            topicOptionCheck = false;
                        }
                    }
                }
                if (this.tilte == '') {
                    this.$dialog.alert({
                        message: '请填写问卷标题'
                    }).then(() => {
                        // on close
                    });
                } else if (this.topicList.length == 0) {
                    this.$dialog.alert({
                        message: '请至少添加一个问题'
                    }).then(() => {
                        // on close
                    });
                } else if (topicTitleCheck == false) {
                    this.$dialog.alert({
                        message: '题目标题不能为空'
                    }).then(() => {
                        // on close
                    });
                } else if (topicOptionCheck == false) {
                    this.$dialog.alert({
                        message: '题目选项不能为空'
                    }).then(() => {
                        // on close
                    });
                } else {
                    let isExist = await this.checkTitle(this.tilte);
                    if (isExist == false) {
                        this.$dialog.alert({
                            message: '问卷标题已存在'
                        }).then(() => {
                            // on close
                        });
                        return;
                    }
                    let isCreateQues = false;
                    let isSaveTopic = false;
                    let questionnaire={
                        creator:this.loginUser,
                        title:this.title,
                        description:this.description,
                        coverImg:this.coverImgName,
                        deadline:this.deadline
                    };
                    isCreateQues=await this.saveQuestionnaire(questionnaire);
                    for (let i in this.topicList) {
                        isSaveTopic=await this.saveTopic(i)
                    }
                    if (isCreateQues == false) {
                        this.$toast.clear();
                        this.$notify({
                            message: '问卷创建失败',
                            duration: 1000
                        });
                    } else if (isSaveTopic == false) {
                        this.$toast.clear();
                        this.$notify({
                            message: '题目保存失败',
                            duration: 1000
                        });
                    } else {
                        this.$toast.clear();
                        // window.location.href = '/myVote';
                    }

                }
            },
            saveTopic:async function(i){
                let isSaveTopic=false;
                let saveTopicUrl = this.website + "/topic/saveTopic";
                let options=[];
                for(let j in this.topicList[i].optionList){
                    options[j]={
                        content:this.topicList[i].optionList[j].content,
                        choiceNum:0
                    }
                }
                let topic={
                    questionnaire: this.newQuestionnaire,
                    title: this.topicList[i].title,
                    optionList:options
                };
                //console.log(topic);
                await this.axios.post(saveTopicUrl,JSON.stringify(topic),{
                    headers: { //添加请求头
                        "Content-Type": "application/json;charset=utf-8",
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        //console.log(i + "选项添加成功");
                        isSaveTopic = true;
                    } else {
                        //console.log(i + "选项添加失败");
                        isSaveTopic = false;
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                    isSaveTopic = false;
                })
                return isSaveTopic;
            },
            saveQuestionnaire:async function(ques){
                let isCreateQues=false;
                let that=this;
                let createUrl=this.website + "/questionnaire/createQuestionnaire";
                await this.axios.post(createUrl, ques).then(function (response) {
                    if (response.data.code == 200) {
                        that.newQuestionnaire = response.data.data;
                        //console.log("创建问卷成功");
                        isCreateQues = true;
                    } else {
                        //console.log("创建失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                });
                return isCreateQues;
            },
            checkTitle:async function(tilte){
                let isExist=false;
                let checkUrl = this.website + "/questionnaire/checkTitle";
                await this.axios.get(checkUrl, {
                    params: {
                        voteTitle: tilte
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        isExist = response.data.data;
                    } else {
                        //console.log("检查过程出错");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                    //console.log( );
                });
                return isExist;
            },
        },
        mounted() {
            let date = new Date();
            date.setDate(date.getDate() + 7);
            this.deadline = date.format("yyyy-MM-dd hh:mm");
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

    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };

        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(
                    RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }
</script>

<style scoped>
    .maindiv {
        margin-bottom: 20px;
        margin-top: 30px;
    }

    .mygroup {
        margin-right: 8px;
    }

    #updatebutton {
        margin-right: 16px;
    }

    #updatespan {
        color: #818181;
        font-size: x-small;
    }

    #addtable {
        color: #2a89ff;
        display: block;
        margin-top: 10px
    }

    #deltable {
        color: #ff6565;
        display: block;
        margin-top: 10px
    }

    hr {
        margin-top: 0px;
        margin: 0;
        border: 0;
        color: #e0e0e0;
        background-color: #e0e0e0;
        height: 1px
    }
</style>