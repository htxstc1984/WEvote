(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["about"],{"2a5f":function(t,a,i){t.exports=i.p+"img/h3.30695578.png"},"8d32":function(t,a,i){t.exports=i.p+"img/h4.097e2126.png"},"8ff1":function(t,a,i){t.exports=i.p+"img/h2.afe4123c.png"},a96c:function(t,a,i){"use strict";var s=i("f12c"),o=i.n(s);o.a},f12c:function(t,a,i){},f820:function(t,a,i){"use strict";i.r(a);var s=function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"about"},[s("van-row",{staticStyle:{margin:"30px"}},[null==t.loginUser.avatar?s("van-col",{attrs:{span:"6"}},[s("van-image",{attrs:{round:"",fit:"none",width:"4.4rem",height:"4.4rem",src:"http://112.124.26.125/test/image/avatar.png"},on:{click:t.showPopup}})],1):s("van-col",{attrs:{span:"6"}},[s("van-image",{attrs:{round:"",fit:"none",width:"4.4rem",height:"4.4rem",src:t.avatarUrl},on:{click:t.showPopup}})],1),s("van-popup",{staticStyle:{height:"170px",width:"200px"},attrs:{closeable:""},model:{value:t.show,callback:function(a){t.show=a},expression:"show"}},[s("van-uploader",{staticStyle:{"margin-top":"30px","margin-left":"56px"},attrs:{"max-count":1},model:{value:t.avatarList,callback:function(a){t.avatarList=a},expression:"avatarList"}}),t.showLoading?s("van-button",{staticStyle:{"margin-left":"38px",width:"120px"},attrs:{type:"info",loading:"","loading-text":"上传中..."}}):s("van-button",{staticStyle:{"margin-left":"38px",width:"120px"},attrs:{type:"info","loading-text":"上传中..."},on:{click:t.uploadAvatar}},[t._v("点击上传头像")])],1),s("van-col",{attrs:{span:"8"}},[t.isLogin?s("span",{staticStyle:{display:"block",margin:"20px 0px"}},[t._v(t._s(t.loginUser.userName))]):s("span",{staticStyle:{display:"block",margin:"20px 0px"}},[t._v("未登录")])]),s("van-col",{attrs:{span:"8"}},[t.isLogin?s("van-button",{staticStyle:{margin:"12px 10px"},attrs:{color:"linear-gradient(to right, #4bb0ff, #6149f6)"},on:{click:t.doLogout}},[t._v("注销 ")]):s("van-button",{staticStyle:{margin:"12px 10px",width:"100px"},attrs:{color:"linear-gradient(to right, #4bb0ff, #6149f6)",to:"login"}},[t._v("登录/注册 ")])],1)],1),s("van-row",{staticStyle:{"margin-bottom":"16px"},attrs:{type:"flex",justify:"space-around"}},[s("van-col",{attrs:{span:"6"}},[s("div",[s("span",{staticStyle:{display:"block","font-weight":"bold","text-align":"center"}},[t._v("0")]),s("span",{staticStyle:{display:"block","font-size":"x-small",color:"#969799","text-align":"center"}},[t._v("报名")])])]),s("van-col",{attrs:{span:"6"}},[s("div",{on:{click:t.toMyVote}},[s("span",{staticStyle:{display:"block","font-weight":"bold","text-align":"center"}},[t._v(t._s(t.voteNum))]),s("span",{staticStyle:{display:"block","font-size":"x-small",color:"#969799","text-align":"center"}},[t._v("发起")])])]),s("van-col",{attrs:{span:"6"}},[s("div",[s("span",{staticStyle:{display:"block","font-weight":"bold","text-align":"center"}},[t._v("0")]),s("span",{staticStyle:{display:"block","font-size":"x-small",color:"#969799","text-align":"center"}},[t._v("投票")])])])],1),s("hr",{staticStyle:{height:"5px"}}),s("van-cell-group",[s("div",{staticClass:"d-flex bd-highlight"},[s("div",{staticClass:"p-2 w-100 bd-highlight d-flex"},[s("img",{staticStyle:{margin:"4px"},attrs:{src:i("8ff1"),height:"30"}}),s("span",{staticStyle:{display:"block","font-weight":"bold","margin-top":"6px"}},[t._v("客服/合作")])]),s("div",{staticClass:"p-2 flex-shrink-1 bd-highlight"},[s("van-icon",{staticStyle:{"margin-top":"10px"},attrs:{name:"arrow"}})],1)])]),s("van-cell-group",[s("div",{staticClass:"d-flex bd-highlight"},[s("div",{staticClass:"p-2 w-100 bd-highlight d-flex"},[s("img",{staticStyle:{margin:"4px"},attrs:{src:i("2a5f"),height:"30"}}),s("span",{staticStyle:{display:"block","font-weight":"bold","margin-top":"6px"}},[t._v("常见问题")])]),s("div",{staticClass:"p-2 flex-shrink-1 bd-highlight"},[s("van-icon",{staticStyle:{"margin-top":"10px"},attrs:{name:"arrow"}})],1)])]),s("van-cell-group",[s("div",{staticClass:"d-flex bd-highlight"},[s("div",{staticClass:"p-2 w-100 bd-highlight d-flex"},[s("img",{staticStyle:{margin:"4px"},attrs:{src:i("8d32"),height:"30"}}),s("span",{staticStyle:{display:"block","font-weight":"bold","margin-top":"6px"}},[t._v("我的消息")])]),s("div",{staticClass:"p-2 flex-shrink-1 bd-highlight"},[s("van-icon",{staticStyle:{"margin-top":"10px"},attrs:{name:"arrow"}})],1)])])],1)},o=[],e={name:"about",components:{},data:function(){return{show:!1,showLoading:!1,isLogin:!1,loginUser:{},voteNum:0,avatarList:[],avatarUrl:""}},methods:{doLogout:function(){this.isLogin&&(null!=sessionStorage.getItem("loginUser")&&sessionStorage.removeItem("loginUser"),null!=localStorage.getItem("loginUser")&&localStorage.removeItem("loginUser")),window.location.href="/about"},toMyVote:function(){window.location.href="/myVote"},getVoteNum:function(){var t=this,a=this.website+"/vote/getVoteNum";this.axios.get(a,{params:{creatorId:this.loginUser.id}}).then((function(a){200==a.data.code&&(t.voteNum=a.data.data)})).catch((function(){}))},showPopup:function(){this.show=!0},uploadAvatar:function(){var t=this;this.showLoading=!0;var a={headers:{"Content-Type":"multipart/form-data"}},i=this.website+"/user/uploadAvatar",s=new FormData;s.append("file",this.avatarList[0].file),s.append("userId",this.loginUser.id),this.axios.post(i,s,a).then((function(a){200==a.data.code&&(t.show=!1,t.loginUser.avatar=a.data.data)})).catch((function(){}))}},mounted:function(){null==sessionStorage.getItem("loginUser")&&null==localStorage.getItem("loginUser")||(this.isLogin=!0,null!=sessionStorage.getItem("loginUser")&&(this.loginUser=JSON.parse(sessionStorage.getItem("loginUser"))),null!=localStorage.getItem("loginUser")&&(this.loginUser=JSON.parse(localStorage.getItem("loginUser")))),this.avatarUrl=this.imgUrl+this.loginUser.avatar,this.getVoteNum()}},n=e,l=(i("a96c"),i("2877")),r=Object(l["a"])(n,s,o,!1,null,"59c6c268",null);a["default"]=r.exports}}]);
//# sourceMappingURL=about.dc657abb.js.map