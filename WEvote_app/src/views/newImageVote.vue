<template>
    <div style="margin-bottom: 100px">
        <van-cell-group>
            <van-field v-model="voteTilte" placeholder="投票标题"/>
            <van-field
                    v-model="description"
                    rows="4"
                    type="textarea"
                    placeholder="请输入描述（图片可选）"
                    show-word-limit
            />
        </van-cell-group>
        <van-uploader v-model="descriptionImgList" :max-count="1" :max-size=maxsize :oversize="oversize"
                      style="margin: 8px 8px"/>
        <hr style="height: 7px">
        <van-cell-group v-for="(item,i) in optionList" :key="i">
            <van-row>
                <van-col span="2">
                    <img src="../assets/images/del.png" style="margin:12px 5px;" height="24px" width="24px"
                         @click="delTable(i)">
                </van-col>
                <van-col span="3">
                    <van-uploader v-model="item.img" preview-size="42px" :max-count="1"
                                  style="margin-top: 5px"/>
                </van-col>
                <van-col span="19">
                    <van-field v-model="item.content" placeholder="选项内容" style="margin-top: 5px"/>
                </van-col>
            </van-row>
        </van-cell-group>
        <van-row>
            <van-col span="2">
                <img src="../assets/images/add.png" style="margin:12px 5px;" height="24px" width="24px"
                     @click="addTable()">
            </van-col>
            <van-col span="22">
                <span style="color: #2a89ff;display: block;margin-top: 10px" @click="addTable()">添加选项</span>
            </van-col>
        </van-row>
        <hr style="height: 7px">
        <van-cell-group>
            <van-field v-model="maxVote" placeholder="每人最多可投(默认为1)" type="number"/>
            <van-row>
                <van-col span="21">
                    <span style="display: block;margin: 14px 16px">自定义分享图（选填）</span>
                </van-col>
                <van-col span="3">
                    <van-uploader v-model="shareImg" preview-size="42px" :max-count="1"
                                  style="margin-top: 5px"/>
                </van-col>
            </van-row>
        </van-cell-group>
        <van-cell-group>
            <van-row>
                <van-col span="16">
                    <span style="display: block;margin: 14px 16px">开始时间</span>
                </van-col>
                <van-col span="8">
                    <span @click="onShow1" style="display: block;margin-top: 14px">{{startTime}}</span>
                </van-col>
            </van-row>
        </van-cell-group>
        <van-cell-group>
            <van-row>
                <van-col span="16">
                    <span style="display: block;margin: 14px 16px">截止时间</span>
                </van-col>
                <van-col span="8">
                    <span @click="onShow2" style="display: block;margin-top: 14px">{{deadline}}</span>
                </van-col>
            </van-row>
        </van-cell-group>
        <van-action-sheet v-model="timeShow1">
            <van-datetime-picker
                    v-model="timeSet.currentDate"
                    type="datetime"
                    :min-date="timeSet.minDate"
                    :max-date="timeSet.maxDate"
                    :formatter="formatter"
                    @confirm="confirmTime1"
                    @cancel="cancelTime"
            />
        </van-action-sheet>
        <van-action-sheet v-model="timeShow2">
            <van-datetime-picker
                    v-model="timeSet.currentDate"
                    type="datetime"
                    :min-date="timeSet.minDate"
                    :max-date="timeSet.maxDate"
                    :formatter="formatter"
                    @confirm="confirmTime2"
                    @cancel="cancelTime"
            />
        </van-action-sheet>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-primary" style="margin-top: 20px" @click="createVote">立即发起投票</button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "newImageVote",
        data() {
            return {
                maxsize:1000000,
                isLogin: false,
                loginUser: {},
                timeShow1: false,
                timeShow2: false,
                voteTilte: '',
                optionList: [{
                    img: [],
                    content: ''
                }, {
                    img: [],
                    content: ''
                }
                ],
                description: '',
                //简介图片
                descriptionImgList: [],
                //分享图片
                shareImg: [],
                maxVote: '',
                timeSet: {
                    minDate: new Date(),
                    maxDate: new Date(2100, 11, 31),
                    currentDate: new Date()
                },
                startTime: new Date().format("yyyy-MM-dd hh:mm"),
                deadline: '',
                newVote: {},
            }
        },
        methods: {

            // afterRead(file) {
            //     console.log(file)
            // },
            // test(){
            //     this.$notify({
            //         message: '图片上传失败',
            //         duration: 1000
            //     });
            // },
            oversize(file){
                console.log(file)
                this.$dialog.alert({
                    message: '图片大小不能超过1M'
                }).then(() => {
                    // on close
                });
            },
            createVote: async function () {
                if (this.voteTilte == '') {
                    this.$dialog.alert({
                        message: '请填写标题'
                    }).then(() => {
                        // on close
                    });
                } else if (this.optionList.length == 0) {
                    this.$dialog.alert({
                        message: '请至少添加一个选项'
                    }).then(() => {
                        // on close
                    });
                } else {
                    for (let i in this.optionList) {
                        if (this.optionList[i].img[0] == null) {
                            this.$dialog.alert({
                                message: '请上传选项图片'
                            }).then(() => {
                                // on close
                            });
                            return;
                        } else if (this.optionList[i].content == '') {
                            this.$dialog.alert({
                                message: '请填写标题选项内容'
                            }).then(() => {
                                // on close
                            });
                            return;
                        }
                    }
                    //检查标题唯一性
                    let isExist=await this.checkTitle(this.voteTilte);
                    if (isExist == false) {
                        console.log("标题已存在");
                        this.$dialog.alert({
                            message: '该标题已存在'
                        }).then(() => {
                            // on close
                        });
                        return;
                    }
                    this.$toast.loading({
                        message: '创建中...',
                        forbidClick: true,
                        duration: 0,
                    });
                    let isUpload = true;
                    let isCreateVote = false;
                    let isSaveOption = false;
                    //将最大投票数默认置为1
                    if (this.maxVote == null) {
                        this.maxVote = 1;
                    }
                    let vote = {
                        creator: this.loginUser,
                        title: this.voteTilte,
                        description: this.description,
                        descriptionImg: "",
                        shareImg: "",
                        startTime: this.startTime,
                        deadline: this.deadline,
                        onceMaxVote: this.maxVote,
                    };
                    //上传图片
                    if (this.descriptionImgList[0] != null) {
                        //同步执行 需等待文件上传完毕
                        vote.descriptionImg = await this.uploadImg(this.descriptionImgList[0]);
                        if (vote.descriptionImg == null) {
                            isUpload = false;
                        }
                    }
                    if (this.shareImg[0] != null) {
                        vote.shareImg = await this.uploadImg(this.shareImg[0]);
                        if (vote.shareImg == null) {
                            isUpload = false;
                        }
                    }
                    // console.log(vote)
                    //保存投票信息
                    isCreateVote=await this.saveVote(vote);
                    //保存选项信息
                    for (let i in this.optionList) {
                        let imgName = await this.uploadImg(this.optionList[i].img[0]);
                        if (imgName == null) {
                            isUpload = false;
                        }
                        isSaveOption=await this.saveOption(imgName,i)
                    }
                    if (isUpload == false) {
                        this.$toast.clear();
                        this.$notify({
                            message: '图片上传失败',
                            duration: 1000
                        });
                    } else if (isCreateVote == false) {
                        this.$toast.clear();
                        this.$notify({
                            message: '投票创建失败',
                            duration: 1000
                        });
                    } else if (isSaveOption == false) {
                        this.$toast.clear();
                        this.$notify({
                            message: '选项保存失败',
                            duration: 1000
                        });
                    } else {
                        this.$toast.clear();
                        window.location.href = '/myVote';
                    }
                }
            },
            checkTitle:async function(tilte){
                let isExist=false;
                let checkUrl = this.website + "/vote/checkTitle";
                await this.axios.get(checkUrl, {
                    params: {
                        voteTitle: tilte
                    }
                }).then(function (response) {
                    if (response.data.code == 200) {
                        isExist = response.data.data;
                    } else {
                        console.log("检查过程出错");
                    }
                }).catch(function (err) {
                    // eslint-disable-next-line no-console
                    console.log(err);
                });
                return isExist;
            },
            saveVote:async function(vote){
                let isCreateVote=false;
                let that=this;
                let createVoteUrl = this.website + "/vote/createVote";
                await this.axios.post(createVoteUrl, vote).then(function (response) {
                    if (response.data.code == 200) {
                        that.newVote = response.data.data;
                        console.log("创建投票成功");
                        isCreateVote = true;
                    } else {
                        console.log("创建失败");
                    }
                }).catch(function (err) {
                    // eslint-disable-next-line no-console
                    console.log(err);
                });
                return isCreateVote;
            },
            saveOption:async function(imgName,i){
                let isSaveOption=false;
                let saveOptionUrl = this.website + "/option/saveOption";
                await this.axios.post(saveOptionUrl, {
                    img: imgName,
                    content: this.optionList[i].content,
                    vote: this.newVote,
                    votes: 0
                }).then(function (response) {
                    if (response.data.code == 200) {
                        console.log(i + "选项添加成功");
                        isSaveOption = true;
                    } else {
                        console.log(i + "选项添加失败");
                        isSaveOption = false;
                    }
                }).catch(function (err) {
                    // eslint-disable-next-line no-console
                    console.log(err);
                })
                return isSaveOption;
            },
            delTable(idx) {
                this.optionList = this.optionList.filter((
                    item, i) => {
                    // console.log([item, i, idx])
                    if (i != idx) {
                        return "e"
                    }
                })
            },
            addTable() {
                this.optionList.push({
                    img: [],
                    content: ''
                })
            },
            onShow1() {
                this.timeShow1 = true
            },
            onShow2() {
                this.timeShow2 = true
            },
            confirmTime1(value) {
                this.startTime = value.format("yyyy-MM-dd hh:mm")
                this.timeShow1 = false
            },
            confirmTime2(value) {
                this.deadline = value.format("yyyy-MM-dd hh:mm")
                this.timeShow2 = false
            },
            cancelTime() {
                this.timeShow1 = false
                this.timeShow2 = false
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
                        // if(imgFile==that.descriptionImgList[0]){
                        //     that.descriptionImgName=response.data.data;
                        //     // console.log(that.descriptionImgName)
                        // }else if(imgFile==that.shareImg[0]){
                        //     that.shareImgName=response.data.data;
                        //     // console.log(that.shareImgName)
                        // }
                        console.log(imgName + "图片上传成功");
                        imgName = response.data.data;
                    } else {
                        console.log("上传失败");
                    }
                }).catch(function ( ) {
                    // eslint-disable-next-line no-console
                     //console.log(err);
                })
                return imgName;
            }
        },
        mounted() {
            let date = new Date();
            date.setDate(date.getDate() + 7)
            this.deadline = date.format("yyyy-MM-dd hh:mm")
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
    hr {
        margin-top: 0px;
        margin: 0;
        border: 0;
        color: #e0e0e0;
        background-color: #e0e0e0;
        height: 1px
    }
</style>