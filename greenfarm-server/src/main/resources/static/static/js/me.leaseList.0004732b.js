(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["me.leaseList"],{"06ed":function(t,a,s){},"087d":function(t,a,s){"use strict";s("06ed")},1:function(t,a){},2:function(t,a){},"402f":function(t,a,s){},"4c2d":function(t,a,s){"use strict";s("deb5")},"527b":function(t,a,s){t.exports=s.p+"static/img/add-one-placeholder.d28a17e4.jpg"},6756:function(t,a,s){"use strict";s.r(a);var e=function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"farmShow"},[s("div",{staticClass:"farmImg"},[s("van-swipe",{staticClass:"img",attrs:{autoplay:3e3}},t._l(t.leaseFarmsJackList.imageUrls,(function(a,e){return s("van-swipe-item",{key:e},[s("img",{attrs:{src:t.showIMG(a)}})])})),1),s("div",{staticClass:"farmName"},[s("div",{staticClass:"farmNameBody"},[s("div",{staticClass:"farmNameBody-left"},[s("img",{attrs:{src:t.srcIcon}})]),s("div",{staticClass:"farmNameBody-right"},[s("div",{staticClass:"farmNameBody-right-title"},[t._v(t._s(t.leaseFarmsJackList.farmName))]),s("div",{staticClass:"farmNameBody-right-body"},[s("svg-icon",{staticStyle:{"font-size":"0.28rem"},attrs:{iconClass:"time-green"}}),s("span",[t._v(t._s(t.leaseFarmsJackList.rentPeriod)+" days lease")])],1)])])])],1),s("div",{staticClass:"farmLocationBack"},[s("div",{staticClass:"farmLocation"},[s("div",{staticClass:"farmLocation-left"},[s("svg-icon",{staticStyle:{"font-size":"0.28rem"},attrs:{iconClass:"location-mark-yellow"}})],1),s("span",[t._v(t._s(t.leaseFarmsJackList.location))])])]),s("div",{staticClass:"farmIntroductionBack"},[s("div",{staticClass:"introduction-wrapper"},[s("div",{staticClass:"title"},[t._v("Introduction")]),s("div",{staticClass:"detail"},[s("span",[t._v(t._s(t.introduction))]),t.moreShow?s("span",{staticClass:"more",on:{click:t.clickMore}},[t._v("MORE...")]):t._e(),t.moreShow?t._e():s("span",[t._v(t._s(t.introductionExtra))])])])]),s("div",{staticClass:"farmDetailBack"},[s("div",{staticClass:"farmDetailTitle"},[s("span",[t._v("Farm Detail")]),s("div",{staticClass:"vrButton",on:{click:function(a){return t.showVR()}}},[s("button",[t._v("VR Photo")])])]),s("div",{staticClass:"farmDetailLinkman"},[s("span",{staticClass:"linkManMajor"},[t._v("Linkman")]),s("span",{staticClass:"linkManName"},[t._v(t._s(t.leaseFarmsJackList.username))])]),s("div",{staticClass:"farmDetailPhone"},[s("span",{staticClass:"phoneMajor"},[t._v("Phone")]),s("span",{staticClass:"phone"},[t._v(t._s(t.leaseFarmsJackList.phone))])]),s("div",{staticClass:"farmDetailEmail"},[s("span",{staticClass:"emailMajor"},[t._v("Email")]),s("span",{staticClass:"email"},[t._v(t._s(t.leaseFarmsJackList.email))])]),s("div",{staticClass:"farmDetailArea"},[s("span",{staticClass:"areaMajor"},[t._v("Area")]),s("span",{staticClass:"area"},[t._v(t._s(t.leaseFarmsJackList.totalArea)+"m²")])])]),t._l(t.leaseFarmsJackList.lands,(function(a,e){return s("div",{key:e,staticClass:"landDetailBack"},[t._m(0,!0),s("div",{staticClass:"landDetailLinkman"},[s("span",{staticClass:"landNameMajor"},[t._v("Name of Land")]),s("span",{staticClass:"landName"},[t._v("NO."+t._s(a.landId))])]),s("div",{staticClass:"landDetailArea"},[s("span",{staticClass:"areaMajor"},[t._v("Area")]),s("span",{staticClass:"area"},[t._v(t._s(a.area)+"m²")])]),s("div",{staticClass:"landDetailPlants"},[s("span",{staticClass:"plantsMajor"},[t._v("Grow Plants")]),s("span",{staticClass:"plants"},[t._v(t._s(a.confirmCrops))])]),s("div",{staticClass:"landDetailStart"},[s("span",{staticClass:"startMajor"},[t._v("Start Time")]),s("span",{staticClass:"start"},[t._v(t._s(a.rentStartTime))])]),s("div",{staticClass:"landDetailEnd"},[s("span",{staticClass:"endMajor"},[t._v("End Time")]),s("span",{staticClass:"end"},[t._v(t._s(a.rentEndTime))])]),s("div",{staticClass:"landDetailCost"},[s("span",{staticClass:"costMajor"},[t._v("Cost")]),s("span",{staticClass:"cost"},[t._v("$"+t._s(parseFloat(a.rentPrice).toFixed(2)))])])])}))],2)},i=[function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"landDetailTitle"},[s("span",[t._v("Land Detail")])])}],r=(s("3c43"),s("9490"),s("696f"),{data(){return{moreShow:!0,leaseFarmsJackList:[],introduction:"",introductionExtra:"",srcIcon:""}},mounted(){this.getleaseFarms()},methods:{showIMG(t){return"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/download?url="+t},showVR(){window.UMJSBridge.callHandler("jumpVR",{url:"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/download?url="+this.leaseFarmsJackList.vrUrl},t=>{})},getleaseFarms(){try{this.leaseFarmsJackList=this.$route.query.leaseFarmsJackList,this.getInfo()}catch(t){}},clickMore(){this.moreShow=!1},getInfo(){this.leaseFarmsJackList.introduction&&(this.leaseFarmsJackList.introduction.length<=200?(this.introduction=this.leaseFarmsJackList.introduction,this.introductionExtra="",this.moreShow=!1):(this.introduction=this.leaseFarmsJackList.introduction.substr(0,200),this.introductionExtra=this.leaseFarmsJackList.introduction.slice(200),this.moreShow=!0)),this.srcIcon="http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/download?url="+this.leaseFarmsJackList.iconUrl;for(var t=0;t<this.leaseFarmsJackList.imgUrl;t++)this.leaseFarmsJackList.imgUrl[t]="http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/download?url="+this.leaseFarmsJackList.imgUrl[t];for(t=0;t<this.leaseFarmsJackList.lands.length;t++)this.leaseFarmsJackList.lands[t].rentStartTime&&this.leaseFarmsJackList.lands[t].rentStartTime.length>10&&(this.leaseFarmsJackList.lands[t].rentStartTime=this.leaseFarmsJackList.lands[t].rentStartTime.substr(0,10)),this.leaseFarmsJackList.lands[t].rentEndTime&&this.leaseFarmsJackList.lands[t].rentEndTime.length>10&&(this.leaseFarmsJackList.lands[t].rentEndTime=this.leaseFarmsJackList.lands[t].rentEndTime.substr(0,10))}}}),n=r,c=(s("ca84"),s("2877")),l=Object(c["a"])(n,e,i,!1,null,"0c50859a",null);a["default"]=l.exports},"696f":function(t,a,s){"use strict";s.d(a,"b",(function(){return i})),s.d(a,"a",(function(){return r})),s.d(a,"c",(function(){return n}));var e=s("b775");function i(t){return Object(e["a"])({url:"/rent/queryRentLands",method:"get",params:t})}function r(t){return Object(e["a"])({url:"/rent/add",method:"post",data:t})}function n(t){return Object(e["a"])({url:"/rent/querySubscriber",method:"get",params:t})}},"9d18":function(t,a,s){},bc0e:function(t,a,s){"use strict";var e=function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"card farm-card"},[s("div",{staticClass:"title"},[t._v(t._s(t.item.farmName))]),s("div",{staticClass:"img"},[s("img",{attrs:{src:t.imgurl(t.item.iconUrl),alt:""}}),s("div",{staticClass:"area"},[t._v(t._s(t.item.totalArea)+"m²")])]),s("div",{staticClass:"desc"},[t._v(t._s(t.item.introduction))]),s("div",{staticClass:"location"},[s("svg-icon",{staticStyle:{"font-size":"0.28rem"},attrs:{iconClass:"location-mark"}}),t._v(" "+t._s(t.item.location)+" ")],1)])},i=[],r={name:"RecommendFarm",data(){return{}},props:{item:{type:Object,default:()=>{}}},mounted(){},methods:{imgurl(t,a){return"http://ec2-52-80-243-127.cn-north-1.compute.amazonaws.com.cn:8081/file/download?url="+t}}},n=r,c=(s("4c2d"),s("2877")),l=Object(c["a"])(n,e,i,!1,null,"b13c4e12",null);a["a"]=l.exports},ca84:function(t,a,s){"use strict";s("9d18")},d415:function(t,a,s){"use strict";s.d(a,"a",(function(){return i})),s.d(a,"c",(function(){return r})),s.d(a,"b",(function(){return n})),s.d(a,"e",(function(){return c})),s.d(a,"d",(function(){return l})),s.d(a,"f",(function(){return o})),s.d(a,"h",(function(){return m})),s.d(a,"g",(function(){return u})),s.d(a,"k",(function(){return d})),s.d(a,"i",(function(){return f})),s.d(a,"j",(function(){return h}));var e=s("b775");function i(t){return Object(e["a"])({url:"/farm/add",method:"post",data:t})}function r(t){return Object(e["a"])({url:"/farm/query",method:"get",params:t})}function n(t){return Object(e["a"])({url:"/farm/queryAll",method:"get",params:t})}function c(t){return Object(e["a"])({url:"/farm/queryByLocation",method:"get",params:t})}function l(t){return Object(e["a"])({url:"/farm/queryById",method:"get",params:t})}function o(t){return Object(e["a"])({url:"/farm/queryByProductName",method:"get",params:t})}function m(t){return Object(e["a"])({url:"/farm/queryByUserName",method:"get",params:t})}function u(t){return Object(e["a"])({url:"/farm/queryByTotalArea",method:"get",params:t})}function d(t){return Object(e["a"])({url:"/farm/revise",method:"put",data:t})}function f(t){return Object(e["a"])({url:"/seed/query",method:"get",params:t})}function h(t){return Object(e["a"])({url:"/seed/queryById",method:"get",params:t})}},db41:function(t,a,s){"use strict";s.r(a);var e=function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"mylease"},t._l(t.leaseFarmsJackList,(function(a,e){return s("div",{key:e,attrs:{item:a},on:{click:function(s){return t.viewFarmDetail(a)}}},[s("RecommendFarm",{attrs:{item:a}})],1)})),0)},i=[],r=s("bc0e"),n=s("696f"),c=(s("3c43"),s("9490"),{components:{RecommendFarm:r["a"]},data(){return{leaseFarmsJackList:[]}},mounted(){this.getleaseFarms()},methods:{getleaseFarms(){Object(n["b"])({username:this.$store.state.user.username}).then(t=>{this.leaseFarmsJackList=t.data}).catch(t=>{console.log(t)})},viewFarmDetail(t){this.$router.push({path:"farmShow",query:{leaseFarmsJackList:t}})}}}),l=c,o=(s("e58f"),s("2877")),m=Object(o["a"])(l,e,i,!1,null,"4b4e0164",null);a["default"]=m.exports},deb5:function(t,a,s){},e58f:function(t,a,s){"use strict";s("402f")},fd8d:function(t,a,s){"use strict";s.r(a);var e=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"lease-farmers"},[t.ifListNull?e("div",{staticClass:"nodata"},[e("img",{attrs:{src:s("527b"),alt:""}}),t._m(0)]):t._e(),t._l(t.dataList,(function(a,s){return e("RecommendFarm",{key:s,attrs:{item:a},nativeOn:{click:function(s){return t.$router.push({name:"leaseEdit",query:{id:a.farmId}})}}})})),e("div",{staticClass:"op-container"},[e("div",{staticClass:"btn",on:{click:function(a){return t.$router.push({name:"leaseAdd"})}}},[t._v("Add a Farm")])])],2)},i=[function(){var t=this,a=t.$createElement,s=t._self._c||a;return s("div",{staticClass:"text"},[s("p",[t._v("Farms that have not been leased,")]),s("p",[t._v("Please create a farm!")])])}],r=s("d415"),n=s("bc0e"),c={components:{RecommendFarm:n["a"]},data(){return{dataList:[],ifListNull:!1}},mounted(){this.getData()},methods:{getData(){Object(r["h"])({username:this.$store.state.user.username}).then(t=>{this.dataList=t.data,0==t.data.length?this.ifListNull=!0:this.ifListNull=!1}).catch(t=>{console.log(t)})}}},l=c,o=(s("087d"),s("2877")),m=Object(o["a"])(l,e,i,!1,null,"762f72fa",null);a["default"]=m.exports}}]);