(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["me.MycommunityPosts"],{"0fac":function(t,e,i){"use strict";i.d(e,"a",(function(){return o})),i.d(e,"b",(function(){return s})),i.d(e,"f",(function(){return a})),i.d(e,"d",(function(){return c})),i.d(e,"e",(function(){return r})),i.d(e,"c",(function(){return u})),i.d(e,"g",(function(){return d}));var n=i("b775");function o(t){return Object(n["a"])({url:"/topic/add",method:"post",data:t})}function s(t){return Object(n["a"])({url:"/topic/addComment",method:"post",data:t})}function a(t){return Object(n["a"])({url:"/topic/queryComment",method:"get",params:t})}function c(t){return Object(n["a"])({url:"/topic/queryByCommunityId",method:"get",params:t})}function r(t){return Object(n["a"])({url:"/topic/queryByUserName",method:"get",params:t})}function u(t){return Object(n["a"])({url:"/topic/queryByTopicId",method:"get",params:t})}function d(t){return Object(n["a"])({url:"/topic/like",method:"put",params:t})}},7998:function(t,e,i){"use strict";i.d(e,"a",(function(){return o})),i.d(e,"b",(function(){return s}));var n=i("b775");function o(t){return Object(n["a"])({url:"/community/queryByFarmId",method:"get",params:t})}function s(t){return Object(n["a"])({url:"/community/queryByUserName",method:"get",params:t})}},8084:function(t,e,i){"use strict";i("f49c")},d594:function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"postbody"},[i("div",{staticClass:"postbody-body"},t._l(t.dataList,(function(e,n){return i("div",{key:n,staticClass:"postbody-list"},[i("div",{staticClass:"post-list-top",on:{click:function(i){return t.toInfo(e)}}},[i("div",{staticClass:"post-list-top-left"},[i("svg-icon",{staticStyle:{"font-size":".4rem"},attrs:{iconClass:"house-1"}})],1),i("div",{staticClass:"post-list-top-title"},[t._v(t._s(e.communityName))])]),i("div",{staticClass:"post-list-body"},[i("div",{staticClass:"post-list-img",on:{click:function(i){return t.toInfo(e)}}},[i("img",{attrs:{src:t.getImg(e.communityImageUrl),alt:""},on:{click:function(i){return t.toInfo(e)}}})]),i("div",{staticClass:"post-list-community"},[i("ul",t._l(e.topic,(function(e,n){return i("li",{key:n,on:{click:function(i){return t.$router.push("/community/post-detail/"+e.topicId)}}},[t._v(t._s(e.topicName))])})),0)])])])})),0),i("div",{staticClass:"div"})])},o=[],s=i("7998"),a=i("0fac"),c={name:"HomePage",data(){return{dataList:[],topicList:[]}},mounted(){this.getData()},methods:{toInfo(t){this.$store.dispatch("title/setTitle",t.communityName),this.$router.push({path:"/me/community/postinfo",query:{id:JSON.stringify(t)}})},getImg(t){return"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/download?url="+t},getData(){this.$route.params.farmId;let t=this.$store.state.user.username||"jack";Object(s["b"])({name:t}).then(t=>{this.dataList=t.data,this.getTopic()}).catch(t=>{console.log(t)})},getTopic(){if(this.dataList)for(let t=0;t<this.dataList.length;t++){let e={username:this.$store.state.user.username,id:this.dataList[t].communityId};Object(a["d"])(e).then(e=>{this.$set(this.dataList[t],"topic",e.data)}).catch(t=>{console.log(t)})}}}},r=c,u=(i("8084"),i("2877")),d=Object(u["a"])(r,n,o,!1,null,"a64ea852",null);e["default"]=d.exports},f49c:function(t,e,i){}}]);