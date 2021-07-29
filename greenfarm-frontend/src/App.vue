<template>
  <div id="app" class="app">
    <div v-if="!($route.name == 'login')" style="position:fixed;height:1rem;width:7.5rem;z-index:1999;border-bottom:0.01rem solid #F7F8FA;">
      <van-nav-bar style="height:1rem"
          v-show="this.$store.getters['user/getToken'] != ''" 
          :title="$route.meta.title"
          left-text="back"
          left-arrow
          @click-left="onClickLeft"
      >
        <template #left>
          <div ><div/>
            <div  v-if=" !($route.meta.ifFirstRoute == undefined ? false : $route.meta.ifFirstRoute) " >
              <van-icon name="arrow-left" color="#333333" size="0.4rem" style="padding-left: 0.06rem;" />
            </div>
          </div>
          </template>
        <template #right><div/></template>
      </van-nav-bar>
    </div>
    <transition name="fade" mode="out-in">
      <router-view style="padding-top:1rem;padding-bottom:1.6rem;min-height:calc(100vh - 2.6rem);"/>
    </transition>
    <div class="tab-con" v-if="!($route.name == 'login')">
      <div class="tab-item"  >
        <div class="block" @click="active='dashboard';$router.replace('/dashboard');"></div>
        <img class="active" v-show="active=='dashboard'" :src="require('@/assets/images/bottomIcon/Activity2@3x.png')" alt="">
        <img class="normal" :class="{'iconhide':active=='dashboard'}" style="" :src="require('@/assets/images/bottomIcon/Activity@3x.png')" alt="">
        <div class="txt" :class="{'active': active=='dashboard'}">Activity</div>
      </div>
      <div class="tab-item" >
        <div class="block" @click="active='farm';$router.replace('/farm/index');"></div>
        <img class="active" v-show="active=='farm'" :src="require('@/assets/images/bottomIcon/Farm2@3x.png')" alt="">
        <img class="normal" :class="{'iconhide':active=='farm'}" style="" :src="require('@/assets/images/bottomIcon/Farm@3x.png')" alt="">
        <div class="txt" :class="{'active': active=='farm'}">Farm</div>
      </div>
      <div class="tab-item"  >
        <div class="block" @click="active='community';$router.replace('/community/index');"></div>
        <img class="active" v-show="active=='community'" :src="require('@/assets/images/bottomIcon/Community2@3x.png')" alt="">
        <img class="normal" :class="{'iconhide':active=='community'}" style="" :src="require('@/assets/images/bottomIcon/Community@3x.png')" alt="">
        <div class="txt" :class="{'active': active=='community'}">Community</div>
      </div>
      <div class="tab-item"  >
        <div class="block" @click="active='mall';$router.replace('/mall/index');"></div>
        <img class="active" v-show="active=='mall'" :src="require('@/assets/images/bottomIcon/Product2@3x.png')" alt="">
        <img class="normal" :class="{'iconhide':active=='mall'}" style="" :src="require('@/assets/images/bottomIcon/Product@3x.png')" alt="">
        <div class="txt" :class="{'active': active=='mall'}">Produce</div>
      </div>
      <div class="tab-item" >
        <div class="block" @click="active='me';$router.replace('/me/index');"></div>
        <img class="active" v-show="active=='me'" :src="require('@/assets/images/bottomIcon/Me2@3x.png')" alt="">
        <img class="normal" :class="{'iconhide':active=='me'}" style="" :src="require('@/assets/images/bottomIcon/Me@3x.png')" alt="">
        <div class="txt" :class="{'active': active=='me'}">Me</div>
      </div>
    </div>
  </div>
</template>

<script>
import { setFontSize, getCookies, setBgColor, resetVantStyle } from '@/utils/preference'
import { getFontSize, getBgColor } from './api/demo'

import Vue from 'vue'
import { Tabbar, TabbarItem, Col, Row, Button, Toast } from 'vant'
Vue.use(Tabbar)
Vue.use(TabbarItem)

export default {
  name: 'App',
  computed: {
    ifdev() {
      return process.env.NODE_ENV != 'production'
    }
  },
  data() {
    return {
      active: ''
    }
  },
  watch: {
  },
  async mounted() {
    const size = getCookies('fontSize') || '30'
    setFontSize(size)
    if (process.env.VUE_APP_VANT_RESET == 'true') {
      resetVantStyle(true)
    } else {
      resetVantStyle(false)
    }
  },
  methods: {
    onClickLeft() {
      if ( this.$route.meta.ifFirstRoute ) {
      } else {
        if(this.$route.name == "searchResult") {
          this.$router.replace({ path: "/mall/index" });
        } else if(this.$route.name == "farmResult") {
          this.$router.replace({ path: "/farm/index" });
        } else if(this.$route.name == "orderResult" || this.$route.name == "farmOrderResult") {
          this.$router.go(-2)
        } else {
          this.$router.go(-1)
        }
      }
    }
  },
  created(){
    if(sessionStorage.getItem("userInfo")) {
      let tempuser = this.$store.state.user.username
      this.$store.state.user = (tempuser != "" && tempuser != undefined) ? this.$store.state.user :  JSON.parse(sessionStorage.getItem("userInfo"))
      sessionStorage.removeItem("userInfo")
    }
    window.addEventListener('beforeunload',()=>{
      sessionStorage.setItem("userInfo", JSON.stringify(this.$store.state.user))
    })
  },
  watch: {
    $route: {
      immediate: true,
      handler(val) {  
        if(val.fullPath.indexOf("/dashboard") == 0){
          this.active = "dashboard"
        } else if(val.fullPath.indexOf("/farm/") == 0){
          this.active = "farm"
        } else if(val.fullPath.indexOf("/community/") == 0){
          this.active = "community"
        } else if(val.fullPath.indexOf("/mall/") == 0){
          this.active = "mall"
        } else if(val.fullPath.indexOf("/me/") == 0){
          this.active = "me"
        }
      }
    }
  }
}
</script>

<style  lang="scss">
#app {
  font-family: "Helvetica", "Pingfang SC", "Hiragino Sans GB", "Arial", "Droid Sans", "Microsoft YaHei", "sans-serif";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
.fade-enter{
  opacity:0
}
.fade-leave{
  opacity:1;
}
.fade-enetr-active{
  transition:opacity .2s
}
.fade-leave-active{
 opacity:0;
 transition:opacity .2s
}
.van-nav-bar__title.van-ellipsis {
  font-family: Montserrat-Medium;
  font-size: 0.32rem;
  color: #333333;
  letter-spacing: 0;
  text-align: center;
  line-height: 0.32rem;
}
.van-nav-bar__left {
  width: 1rem; 
}
.tab-con {
  position: fixed;
  z-index: 999;
  bottom: 0;
  left: 0;
  right: 0;
  background: #fff;
  padding: 0.2rem 0.3rem 0.55rem 0.3rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .tab-item {
    text-align: center;
    width: 1.3rem;
    position: relative;
    .block {
      z-index: 1001;
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
    }
    img.active {
      height: 0.9rem;
      position: absolute;
      top: -0.45rem;
      left: 50%;
      transform: translate(-50%, 0);
      z-index: 1000;
    }
    img.normal {
      width: 0.5rem;
    }
    img.iconhide {
      opacity: 0;
    }
    .txt {
      margin-top: 0.06rem;
      font-family: Montserrat-Light;
      font-size: 0.2rem;
      color: #323232;
      letter-spacing: 0;
      text-align: center;
      line-height: 0.2rem;
      &.active {
        color: #00B459;
      }
    }
  }
}
</style>
