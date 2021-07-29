<template>
  <div class="login">
    <div class="welcomefont">
      <p>Hello,</p>
      <p>welcome to</p>
      <p>Green Farm!</p>
    </div>
    <div class="loginTop">
      <div class="title">
        <span @click="tabType='login';checked=false" :class="{'active': tabType=='login'}" >Login</span>
        <span class="mid">|</span>
        <span @click="tabType='guest';checked=true" :class="{'active': tabType=='guest'}" >Guest</span>
      </div>
      <div class="input">
        <van-form @submit="handleLogin">
          <div v-if="tabType == 'login'" class="login-type">
            <div class="block">
              <div class="icon">
                <svg-icon iconClass="login-username" style="font-size: 0.28rem"></svg-icon>
              </div>
              <div class="inner">
                <input
                  ref="userName"
                  placeholder="username"
                  type="text"
                  v-model="loginForm.userName"
                  :rules="[{ required: true, message: 'please enter your name' }]"
                >
              </div>
            </div>
            <div class="block">
              <div class="icon">
                <svg-icon iconClass="login-password" style="font-size: 0.28rem"></svg-icon>
              </div>
              <div class="inner">
                <input
                  ref="password"
                  type="password"
                  placeholder="password"
                  v-model="loginForm.password"
                  :rules="[{ required: true, message: 'please enter your name' }]"
                >
              </div>
            </div>
          </div>
          <div v-if="tabType == 'guest'" class="guest-type">
            <div class="guest-item" @click="guestName = 'Sarah'">
              <img :class="{'active': guestName == 'Sarah'}" :src="require('@/assets/images/sarah'+(guestName == 'Sarah'?'-xuanzhong':'')+'.png')" alt="">
              <div class="name" :class="{'active': guestName == 'Sarah'}">Sarah</div>
            </div>
            <div class="guest-item" @click="guestName = 'Fred'">
              <img :class="{'active': guestName == 'Fred'}" :src="require('@/assets/images/Fred'+(guestName == 'Fred'?'-xuanzhong':'')+'.png')" alt="">
              <div class="name" :class="{'active': guestName == 'Fred'}">Fred</div>
            </div>
          </div>
          <button class="button" :disabled="!checked" native-type="submit" size="large">login</button>
          <div class="privacy">
            <van-checkbox
              v-model="checked"
              shape="square"
              checked-color="#08C25E"
              icon-size="0.4rem"
            ></van-checkbox>
            <div class="info" @click="block=true">I’ve read the Terms of Use above.</div>
          </div>
        </van-form>
      </div>
    </div>

    <van-popup v-model="block" class="popup-block" closeable>
      <div class="popup-title">Privacy Policy</div>
      <div class="popup-body">
        <div class="content">
          This SERVICE is provided by Green Farm at no cost and is intended for use as is.
          <br>This page is used to inform visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service.
          <br>If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that we collect is used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.
          <br>The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at Green Farm unless otherwise defined in this Privacy Policy.
        </div>
        <div class="heading">Information Collection and Use</div>
        <div
          class="content"
        >For a better experience, while using our Service, we may require you to provide us with certain personally identifiable information. The information that we request will be retained by us and used as described in this privacy policy.</div>
        <div class="heading">Log Data</div>
        <div
          class="content"
        >We want to inform you that whenever you use our Service, in a case of an error in the app we collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, the time and date of your use of the Service, and other statistics.</div>
        <div class="heading">Cookies</div>
        <div class="content">
          Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.
          <br>This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.
        </div>
        <div class="heading">Service Providers</div>
        <div class="content">
          We may employ third-party companies and individuals due to the following reasons:
          <br>* To facilitate our Service;
          <br>* To provide the Service on our behalf;
          <br>* To perform Service-related services; or
          <br>* To assist us in analyzing how our Service is used.
          <br>We want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.
        </div>
        <div class="heading">Security</div>
        <div
          class="content"
        >We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</div>
      </div>
    </van-popup>
  </div>
</template>

<script>
import Vue from "vue";
import { Col, Row, Button, Toast } from "vant";
import { login } from "@/api/user";

export default {
  data() {
    return {
      loginForm: {
        userName: "",
        password: ""
      },
      checked: true,
      block: false,
      tabType: 'guest',
      guestName: 'Sarah'
    };
  },
  methods: {
    async handleLogin() {
      const toast = Toast.loading({
        duration: 0,
        message: "Loading...",
        forbidClick: true,
        className: "loading"
      });
      try {
        let loginParam = {};
        if (this.tabType == 'guest') {
          loginParam = {
            userName: this.guestName,
            password: ''
          }
        } else {
          loginParam = this.loginForm
        }
        const res = await login(loginParam);
        this.$store.dispatch("user/setToken", "123");
        this.$store.dispatch("user/setUserName", res.data.username);
        this.$store.dispatch("user/setRoleName", res.data.rolename);
        this.$store.dispatch("user/setCreateTime", res.data.createTime);
        this.$store.dispatch("user/setDescription", res.data.description);
        this.$store.dispatch("user/setEmail", res.data.email);
        this.$store.dispatch("user/setFullname", res.data.fullname);
        this.$store.dispatch("user/setIconUrl", res.data.iconUrl);
        this.$store.dispatch("user/setLocation", res.data.location);
        this.$store.dispatch("user/setPhone", res.data.phone);
        this.$store.dispatch("user/setRemark", res.data.remark);
        this.$store.dispatch("user/setSex", res.data.sex);
        toast.clear();
        this.$router.push({ path: "/dashboard" });
      } catch (err) {
        toast.clear();
        console.log(err);
      }
      
    }
  }
};
</script>

<style lang="scss" scoped>
.login {
  background-image: url("../../assets/images/login-background.png");
  background-repeat: no-repeat;
  background-size: 100% auto;
  width: 100%;
  height: calc(100% - 2.6rem);
  .welcomefont {
    background-color: rgba(0, 0, 0, 0);
    padding-top: 0.1rem;
    p {
      padding-left: 0.76rem;
      font-family: Montserrat-Medium;
      font-size: 0.56rem;
      color: #fcfcfc;
      line-height: 0.92rem;
    }
  }
  .loginTop {
    background: #ffffff;
    bottom: 0;
    top: 5rem;
    position: absolute;
    width: 100%;
    border-radius: 0.6rem 0.6rem 0 0;
    .title {
      width: calc(100% - 0.6rem);
      font-size: 0.36rem;
      color: #666666;
      margin-top: 0.86rem;
      margin-left: 0.6rem;
      position: relative;
      span {
        font-family: Montserrat-Regular;
        &.mid {
          margin: 0 0.22rem;
        }
        &.active {
          font-family: Montserrat-Medium;
          color: #000000;
        }
      }
    }
    .input {
      position: relative;
      text-align: center;
      margin-top: 0.44rem;
      .login-type {
        .block {
          overflow: hidden;
          width: 6.2rem;
          height: 0.92rem;
          border-radius: 0.16rem;
          border: 0.02rem solid #d0d6d5;
          margin: 0 auto;
          margin-bottom: 0.44rem;
          display: flex;
          justify-content: flex-start;
          align-items: center;
          .icon {
            padding-left: 0.42rem;
          }
          .inner {
            height: 0.92rem;
            width: 100%;
            margin: 0 0.14rem;
            button,
            input,
            textarea {
              height: 0.92rem;
              width: 100%;
              font-family: Montserrat-Light;
              color: #919191;
            }
          }
        }
      }
      .guest-type {
        padding: 0.6rem 0.4rem 0.4rem;
        display: flex;
        align-items: flex-end;
        justify-content: center;
        .guest-item {
          width: 2.4rem;
          text-align: center;
          img {
            width: 1.6rem;
            height: 1.6rem;
            border-radius: 50%;
            &.active {
              width: 2rem;
              height: 2rem;
            }
          }
          .name {
            padding-top: 0.26rem;
            font-family: Montserrat-Regular;
            font-size: 0.32rem;
            color: #999999;
            line-height: 0.32rem;
            &.active {
              padding-top: 0.04rem;
              color: #333333;
            }
          }
        }
      }
      .field {
        width: 6.2rem;
        height: 0.92rem;
        border-radius: 0.16rem;
        border: 1px solid #d0d6d5;
        margin: 0 auto;
        .van-field {
          color: #919191;
        }
      }
      .button {
        background: #00b459;
        border-radius: 0.46rem;
        width: 6.2rem;
        height: 0.92rem;
        color: #ffffff;
        border: 0;
        font-family: Montserrat-Regular;
        margin-top: 0.36rem;
        &:disabled {
          background: #bdbdbd;
        }
      }
      .privacy {
        width: 6.3rem;
        margin: 0 auto;
        display: flex;
        justify-content: center;
        padding-top: 0.38rem;
        .info {
          font-family: Montserrat-Light;
          font-size: 0.28rem;
          color: #00b459;
          line-height: 0.28rem;
          border-bottom: 0.02rem solid #00b459;
        }
        .van-checkbox {
          margin-right: 0.1rem;
          ::v-deep .van-icon.van-icon-success {
            border-radius: 0.08rem;
          }
        }
      }
    }
  }
}
.popup-block {
  width: calc(100% - 0.72rem);
  height: calc(100% - 1.16rem);
  border-radius: 0.2rem;
  ::v-deep .van-popup__close-icon {
    color: #333333;
  }
  .popup-title {
    text-align: center;
    height: 1.16rem;
    line-height: 1.16rem;
    font-size: 0.32rem;
    color: #151515;
    font-family: Montserrat-SemiBold;
    letter-spacing: 0;
  }
  .popup-body {
    max-height: 13rem;
    overflow-y: scroll;
    margin: auto;
    padding: 0 0.3rem 0.3rem 0.3rem;
    .heading {
      font-family: Montserrat-SemiBold;
      font-size: 0.28rem;
      color: #151515;
      letter-spacing: 0;
      line-height: 0.38rem;
      padding: 0.1rem 0 0.06rem 0;
    }
    .content {
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.38rem;
    }
  }
}
</style>
