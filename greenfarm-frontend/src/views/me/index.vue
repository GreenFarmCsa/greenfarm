<template>
  <div class="meIndex">
    <div class="header">
      <div class="user">
        <div class="img" @click="$router.push({name:'myProfile'})">
          <!-- <img :src="imgurl(this.$store.state.user.iconUrl)" alt> -->
          <van-image  
            round
            width="1.6rem"
            height="1.6rem" 
            :src="imgurl(this.$store.state.user.iconUrl)"
          >
            <template v-slot:loading>
              <van-loading type="spinner" size="20" />
            </template>
          </van-image>
        </div>
        <div class="right">
          <div class="name">{{this.$store.state.user.username}}</div>
        </div>
      </div>
      <div class="credits">
        <div class="left">
          <div class="img">
            <img :src="require('@/assets/images/carbon-credits-1.png')" alt>
          </div>
          <div class="info">
            <div class="title">Carbon Credit</div>
            <div class="num">{{carbonCredit}}</div>
          </div>
        </div>
        <div class="btn" @click="$router.push({name:'carbonAccount'})">Detail</div>
      </div>
    </div>
    <div class="information">
      <div class="title">My Information</div>
      <div class="info-list">
        <div class="info-item" @click="$router.push({name:'fieldList'})">
          <div class="img">
            <svg-icon iconClass="green-plant" style="font-size: 0.88rem;"></svg-icon>
          </div>
          <div class="txt">
            <p>Green</p>
            <p>Plant</p>
          </div>
        </div>
        <div class="info-item" @click="$router.push({name:'farmMap'})">
          <div class="img">
            <svg-icon iconClass="carbon-footprint" style="font-size: 0.88rem;"></svg-icon>
          </div>
          <div class="txt">
            <p>Farm</p>
            <p>Map</p>
          </div>
        </div>
        <div
          class="info-item"
          @click="$router.push({name: $store.state.user.rolename=='farmer' ? 'leaseListFarmers' : 'leaseList'})"
        >
          <div class="img">
            <svg-icon iconClass="my-lease" style="font-size: 0.88rem;"></svg-icon>
          </div>
          <div class="txt">
            <p>My</p>
            <p>CSA</p>
          </div>
        </div>
        <div
          class="info-item"
          @click="$router.push({name: $store.state.user.rolename=='consumer' ? 'myGoodsConsumerList' : 'myGoodsFarmerList'})"
        >
          <div class="img">
            <svg-icon iconClass="my-product" style="font-size: 0.88rem;"></svg-icon>
          </div>
          <div class="txt">
            <p>My</p>
            <p>Produce</p>
          </div>
        </div>
      </div>
    </div>
    <div class="entry">
      <div class="entry-item" @click="$router.push({name:'financialList'})">
        <div class="name">
          <img src="@/assets/images/green finaince.png" alt>
          <span>Green Finance</span>
        </div>
        <div class="arrow">
          <svg-icon iconClass="more" style="font-size: 0.28rem;"></svg-icon>
        </div>
      </div>
      <div class="entry-item" @click="$router.push({name:'MycommunityPosts'})">
        <div class="name">
          <img src="@/assets/images/my community.png" alt>
          <span>My Community</span>
        </div>
        <div class="arrow">
          <svg-icon iconClass="more" style="font-size: 0.28rem;"></svg-icon>
        </div>
      </div>
      <div class="entry-item" @click="logout()">
        <div class="name">
          <img src="@/assets/images/exit.png" style="width:0.36rem" alt>
          <span>Exit</span>
        </div>
        <div class="arrow"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { queryUserInfo, logout } from "@/api/user";

export default {
  name: "MeIndex",
  data() {
    return {
      carbonCredit: ""
    };
  },
  mounted() {
    this.queryUserInfo();
  },
  methods: {
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    async queryUserInfo() {
      try {
        const res = await queryUserInfo({
          username: this.$store.state.user.username
        });
        this.carbonCredit = res.data.carbonCredit;
      } catch (err) {
        console.log(err);
      } finally {
      }
    },
    async logout() {
      try {
        const res = await logout({ username: this.$store.state.user.username });
        this.$store.dispatch("user/clearUserInfo", "");
        this.$router.push({ path: "/login" });
      } catch (err) {
        console.log(err);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.meIndex {
  min-height: calc(100vh - 2.6rem);
  color: #333;
  font-family: Montserrat-Medium;
  background: #f6f6f6;
  .header {
    .user {
      background-image: url("../../assets/images/me-background-1.png");
      background-size: cover;
      padding: 1.2rem 0.56rem 1.5rem;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      .img {
        img {
          width: 1.6rem;
          height: 1.6rem;
          border-radius: 50%;
        }
      }
      .right {
        margin-left: 0.4rem;
        .name {
          font-family: Montserrat-Medium;
          font-size: 0.52rem;
          color: #fefefe;
          letter-spacing: 0;
          line-height: 0.52rem;
        }
      }
    }
    .credits {
      margin: -0.8rem 0.36rem 0.4rem;
      padding: 0.2rem;
      background: #ffffff;
      box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
      border-radius: 0.2rem;

      display: flex;
      justify-content: space-between;
      align-items: center;
      .left {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        .img {
          img {
            width: 1.2rem;
            height: 1.2rem;
          }
        }
        .info {
          margin-left: 0.2rem;
          .title {
            font-family: Montserrat-Regular;
            font-size: 0.28rem;
            color: #333333;
            letter-spacing: 0;
            line-height: 0.28rem;
          }
          .num {
            margin-top: 0.18rem;
            font-family: Montserrat-Medium;
            font-size: 0.4rem;
            color: #00b459;
            letter-spacing: 0;
            line-height: 0.4rem;
          }
        }
      }
      .btn {
        background: #ff9f00;
        border-radius: 0.4rem;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #ffffff;
        letter-spacing: 0;
        line-height: 0.76rem;
        height: 0.76rem;
        padding: 0 0.42rem;
      }
    }
  }
  .information {
    background: #fff;
    .title {
      padding: 0.4rem;
      border-bottom: 0.02rem solid #eeeff1;
      font-family: Montserrat-Medium;
      font-size: 0.32rem;
      color: #333333;
      letter-spacing: 0;
      line-height: 0.32rem;
    }
    .info-list {
      padding: 0.4rem 0.6rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .info-item {
        .img {
          margin-bottom: 0.1rem;
        }
        .txt {
          font-family: PingFangSC-Regular;
          font-size: 0.24rem;
          color: #333333;
          letter-spacing: 0;
          text-align: center;
          line-height: 0.28rem;
        }
      }
    }
  }
  .entry {
    background: #fff;
    margin-top: 0.32rem;
    .entry-item {
      padding: 0.34rem 0.42rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .name {
        display: flex;
        align-items: center;
        img {
          width: 0.42rem;
        }
        span {
          margin-left: 0.3rem;
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #333333;
          letter-spacing: 0;
          line-height: 0.28rem;
        }
      }
      &:not(:last-child) {
        border-bottom: 0.02rem solid #eeeff1;
      }
    }
  }
}
</style>
