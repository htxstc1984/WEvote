<template>
    <div style="margin-top: 56px">
        <!--轮播图-->
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="width: 100%">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            </ol>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="../assets/images/b1.jpg" class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="../assets/images/b2.jpg" class="d-block w-100" alt="...">
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>

        <!--案例展示卡片-->
        <div class="d-flex justify-content-center">
            <div class="d-flex flex-column align-items-center" style="height: 230px;padding-top: 90px">
                <div style="color:#333;font-size: 32px;font-family: Arial;">案例展示</div>
                <div style="color:rgb(153, 153, 153);font-family:Arial;font-size:15px;">优秀案例分享</div>
            </div>
        </div>
        <div class="d-flex justify-content-center ">
            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded" style="max-width: 720px;">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="../assets/images/5.png" class="card-img" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">创建图文投票</h5>
                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to
                                additional content. This content is a little bit longer.</p>
                            <div style="width: 160px">
                                <img src="../assets/images/QR1.png" height="160px" width="160px" class="card-img" alt="...">
                            </div>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                </div>
            </div>
            <div style="width: 20px;"></div>
            <div class="card mb-3 shadow p-3 mb-5 bg-white rounded" style="max-width: 720px;">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="../assets/images/6.png" class="card-img" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">创建问卷调查</h5>
                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to
                                additional content. This content is a little bit longer.</p>
                            <div style="width: 160px">
                                <img src="../assets/images/QR2.png" height="160px" width="160px" class="card-img" alt="...">
                            </div>
                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--活动展示-->
        <div class="d-flex justify-content-center">
            <div class="d-flex flex-column align-items-center" style="height: 230px;padding-top: 50px">
                <div style="color:#333;font-size: 32px;font-family: Arial;">大型活动，政企，电视台，银行，重要奖项评选 都在使用我们的投票平台</div>
                <div style="color:rgb(153, 153, 153);font-family:Arial;font-size:15px;">超1000家行业佼佼者优先的选择</div>
            </div>
        </div>
        <div class="d-flex justify-content-center">
            <div class="row row-cols-1 row-cols-md-3" style="width: 1500px">
                <div v-for="(item,i) in voteList" :key="i">
                    <div class="col mb-4" v-if="item.flag==false">
                        <div class="card">
                            <img :src=item.descriptionImg class="card-img-top" height="270px">
                            <div class="card-body">
                                <h5 class="card-title">{{item.title}}</h5>
                                <p class="card-text detail-text" v-if="item.description!=''">{{item.description}}</p>
                                <p class="card-text detail-text" v-else>暂无说明</p>
                                <p class="card-text"><small class="text-muted">{{item.startTime}}</small></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--优势展示-->
        <div class="d-flex justify-content-center">
            <div class="d-flex flex-column align-items-center" style="height: 230px;padding-top: 50px">
                <div style="color:#333;font-size: 32px;font-family: Arial;">我们的优势</div>
                <div style="color:rgb(153, 153, 153);font-family:Arial;font-size:15px;">极速建立投票活动</div>
            </div>
        </div>
        <div class="d-flex justify-content-center" style="margin-bottom: 50px">
            <div class="d-flex flex-row bd-highlight" style="padding-right: 40px">
                <img src="../assets/images/1.png"/>
                <div>
                    <span style="display: block;text-align: left;padding-top: 19px;color: #333;font-size: 25px;">高性能</span>
                    <span style="display: block;text-align: left;padding-top: 18px;color: #5b5b5b;font-size: 20px;letter-spacing: 2px;line-height: 24px;width: 400px">超过100万+场投票活动验证</span>
                </div>
            </div>
            <div class="d-flex flex-row bd-highlight" style="padding-left: 40px">
                <img src="../assets/images/2.png"/>
                <div>
                    <span style="display: block;text-align: left;padding-top: 19px;color: #333;font-size: 25px;">易上手</span>
                    <span style="display: block;text-align: left;padding-top: 18px;color: #5b5b5b;font-size: 20px;letter-spacing: 2px;line-height: 24px;width: 400px">只需要简单4步即可创建你的投票活动</span>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center">
            <div class="d-flex flex-row bd-highlight" style="padding-right: 40px">
                <img src="../assets/images/3.png"/>
                <div>
                    <span style="display: block;text-align: left;padding-top: 19px;color: #333;font-size: 25px;">安全稳定</span>
                    <span style="display: block;text-align: left;padding-top: 18px;color: #5b5b5b;font-size: 20px;letter-spacing: 2px;line-height: 24px;width: 400px">华为云合作伙伴，高端服务器，<br/>1000M的带宽助力您的投票活动</span>
                </div>
            </div>
            <div class="d-flex flex-row bd-highlight" style="padding-left: 40px">
                <img src="../assets/images/4.png"/>
                <div>
                    <span style="display: block;text-align: left;padding-top: 19px;color: #333;font-size: 25px;">7*12小时售后服务</span>
                    <span style="display: block;text-align: left;padding-top: 18px;color: #5b5b5b;font-size: 20px;letter-spacing: 2px;line-height: 24px;width: 400px">专业的售后，让你用得更放心</span>
                </div>
            </div>
        </div>

        <!--页面底部-->
        <div class="jumbotron" style="margin-top: 100px">
            <h1 class="display-4">欢迎使用!</h1>
            <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention
                to featured content or information.</p>
            <hr class="my-4">
            <p>It uses utility classes for typography and spacing to space content out within the larger container.</p>
            <a class="btn btn-primary btn-lg" href="#" role="button">加入我们</a>
        </div>


    </div>
</template>

<script>
    export default {
        name: "index",
        data() {
            return {
                bImageList: [
                    "../assets/images/b1.jpg",
                    "../assets/images/b2.jpg"
                ],
                isLogin: false,
                voteList: [],
            }
        },
        methods: {
            getVoteList: async function () {
                let voteList = [];
                let getVoteUrl = this.website + '/vote/getAllVote';
                await this.axios.get(getVoteUrl).then(function (response) {
                    if (response.data.code == 200) {
                        voteList = response.data.data;
                    } else {
                        // console.log("所有投票信息查询失败");
                    }
                }).catch(function () {
                    // console.log(err);
                });
                return voteList;
            },
        },
        async mounted() {
            this.voteList = await this.getVoteList();
            for (let i in this.voteList) {
                this.voteList[i].descriptionImg = this.imgUrl + this.voteList[i].descriptionImg;
            }
            // console.log(this.voteList)
        }
    }
</script>

<style scoped>
    /*.el-header, .el-footer {*/
    /*    background-color: #E9EEF3;*/
    /*    color: #333;*/
    /*    text-align: center;*/
    /*    line-height: 60px;*/
    /*}*/
    /*.el-main {*/
    /*    background-color: #545c64;*/
    /*    color: #333;*/
    /*    text-align: center;*/
    /*    line-height: 160px;*/
    /*}*/

    /*body > .el-container {*/
    /*    margin-bottom: 40px;*/
    /*}*/

    /*.el-container:nth-child(5) .el-aside,*/
    /*.el-container:nth-child(6) .el-aside {*/
    /*    line-height: 260px;*/
    /*}*/

    /*.el-container:nth-child(7) .el-aside {*/
    /*    line-height: 320px;*/
    /*}*/
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