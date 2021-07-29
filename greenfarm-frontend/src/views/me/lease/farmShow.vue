<template>
  <div class="farmShow">
    <div class="farmImg">
      <van-swipe :autoplay="3000" class="img">
        <van-swipe-item v-for="(imageUrl, index) in leaseFarmsJackList.imageUrls" :key="index">
          <img :src="showIMG(imageUrl)">
        </van-swipe-item>
      </van-swipe>
      <div class="farmName">
        <div class="farmNameBody">
          <div class="farmNameBody-left">
            <img :src="srcIcon">
          </div>
          <div class="farmNameBody-right">
            <div class="farmNameBody-right-title">{{leaseFarmsJackList.farmName}}</div>
            <div class="farmNameBody-right-body">
              <!-- <div class="farmNameBody-right-body-img">
              </div> -->
              <svg-icon iconClass="time-green" style="font-size: 0.28rem"></svg-icon>
              <span>{{leaseFarmsJackList.rentPeriod}} days lease</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="farmLocationBack">
      <div class="farmLocation">
        <div class="farmLocation-left">
          <svg-icon iconClass="location-mark-yellow" style="font-size: 0.28rem"></svg-icon>
        </div>
        <span>{{leaseFarmsJackList.location}}</span>
      </div>
    </div>
    <div class="farmIntroductionBack">
      <div class="introduction-wrapper">
        <div class="title">Introduction</div>
        <div class="detail">
          <span>{{introduction}}</span>
          <span v-if="moreShow" class="more" @click="clickMore">MORE...</span>
          <span v-if="!moreShow">{{introductionExtra}}</span>
        </div>
      </div>
    </div>
    <div class="farmDetailBack">
      <div class="farmDetailTitle">
        <span>Farm Detail</span>
        <div class="vrButton" @click="showVR()">
          <button>VR Photo</button>
        </div>
      </div>
      <div class="farmDetailLinkman">
        <span class="linkManMajor">Linkman</span>
        <span class="linkManName">{{leaseFarmsJackList.username}}</span>
      </div>
      <div class="farmDetailPhone">
        <span class="phoneMajor">Phone</span>
        <span class="phone">{{leaseFarmsJackList.phone}}</span>
      </div>
      <div class="farmDetailEmail">
        <span class="emailMajor">Email</span>
        <span class="email">{{leaseFarmsJackList.email}}</span>
      </div>
      <div class="farmDetailArea">
        <span class="areaMajor">Area</span>
        <span class="area">{{leaseFarmsJackList.totalArea}}m²</span>
      </div>
    </div>

    <div class="landDetailBack" v-for="(item,index) in leaseFarmsJackList.lands" :key="index">
      <div class="landDetailTitle">
        <span>Land Detail</span>
      </div>
      <div class="landDetailLinkman">
        <span class="landNameMajor">Name of Land</span>
        <span class="landName">NO.{{item.landId}}</span>
      </div>
      <div class="landDetailArea">
        <span class="areaMajor">Area</span>
        <span class="area">{{item.area}}m²</span>
      </div>
      <div class="landDetailPlants">
        <span class="plantsMajor">Grow Plants</span>
        <span class="plants">{{item.confirmCrops}}</span>
      </div>
      <div class="landDetailStart">
        <span class="startMajor">Start Time</span>
        <span class="start">{{item.rentStartTime}}</span>
      </div>
      <div class="landDetailEnd">
        <span class="endMajor">End Time</span>
        <span class="end">{{item.rentEndTime}}</span>
      </div>
      <div class="landDetailCost">
        <span class="costMajor">Cost</span>
        <span class="cost">${{parseFloat(item.rentPrice).toFixed(2)}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import { type } from "os";
import { request } from "http";
import { leaseFarmsJack } from "@/api/lease";
export default {
  data() {
    return {
      moreShow: true,
      leaseFarmsJackList: [],
      introduction: "",
      introductionExtra: "",
      srcIcon: ""
    };
  },
  mounted() {
    this.getleaseFarms();
  },

  methods: {
    showIMG(val) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + val;
    },
    showVR() {
      window.UMJSBridge.callHandler(
        "jumpVR",
        {
          url:
            process.env.VUE_APP_BASE_API +
            "/file/download?url=" +
            this.leaseFarmsJackList.vrUrl
        },
        response => {}
      );
    },
    getleaseFarms() {
      try {
        this.leaseFarmsJackList = this.$route.query.leaseFarmsJackList;
        this.getInfo();
      } catch (e) {
        // this.$toast({
        //   message: "get leaseFarmsJack failed!",
        //   position: "bottom"
        // });
      }
    },
    clickMore() {
      this.moreShow = false;
    },
    getInfo() {
      if (this.leaseFarmsJackList.introduction) {
        if (this.leaseFarmsJackList.introduction.length <= 200) {
          this.introduction = this.leaseFarmsJackList.introduction;
          this.introductionExtra = "";
          this.moreShow = false;
        } else {
          this.introduction = this.leaseFarmsJackList.introduction.substr(
            0,
            200
          );
          this.introductionExtra = this.leaseFarmsJackList.introduction.slice(
            200
          );
          this.moreShow = true;
        }
      }
      this.srcIcon =
        process.env.VUE_APP_BASE_API +
        "/file/download?url=" +
        this.leaseFarmsJackList.iconUrl;
      for (var i = 0; i < this.leaseFarmsJackList.imgUrl; i++) {
        this.leaseFarmsJackList.imgUrl[i] =
          process.env.VUE_APP_BASE_API +
          "/file/download?url=" +
          this.leaseFarmsJackList.imgUrl[i];
      }
      for (var i = 0; i < this.leaseFarmsJackList.lands.length; i++) {
        if (this.leaseFarmsJackList.lands[i].rentStartTime) {
          if (this.leaseFarmsJackList.lands[i].rentStartTime.length > 10) {
            this.leaseFarmsJackList.lands[
              i
            ].rentStartTime = this.leaseFarmsJackList.lands[
              i
            ].rentStartTime.substr(0, 10);
          }
        }
        if (this.leaseFarmsJackList.lands[i].rentEndTime) {
          if (this.leaseFarmsJackList.lands[i].rentEndTime.length > 10) {
            this.leaseFarmsJackList.lands[
              i
            ].rentEndTime = this.leaseFarmsJackList.lands[i].rentEndTime.substr(
              0,
              10
            );
          }
        }
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.farmShow {
  background: #f6f6f6;
  margin-bottom: 0.7rem;
  color: #333;
  .farmImg {
    width: 100%;
    background: #ffffff;
    .img {
      position: relative;
      height: 4.4rem;
      img {
        width: 100%;
        height: 4.4rem;
        object-fit: cover;
      }
    }
    .farmName {
      background: #ffffff;
      margin: 0.36rem 0.26rem 0.2rem 0.26rem;
      width: 90%;
      .farmNameBody {
        width: 100%;
        padding-bottom: 0.3rem;
        display: flex;
        align-items: center;
        .farmNameBody-left {
          flex: 1;
          height: 0.72rem;
          width: 0.72rem;
          img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
          }
        }
        .farmNameBody-right {
          flex: 8;
          padding-left: 0.3rem;
          .farmNameBody-right-title {
            font-family: Montserrat-Medium;
            font-size: 0.32rem;
            color: #333333;
            letter-spacing: 0;
          }
          .farmNameBody-right-body {
            display: flex;
            align-items: center;
            margin-top: 0.18rem;
            .farmNameBody-right-body-img {
            }
            span {
              margin-left: 0.1rem;
              font-family: Montserrat-Regular;
              font-size: 0.28rem;
              color: #00b459;
              letter-spacing: 0;
              line-height: 0.28rem;
            }
          }
        }
      }
    }
  }
  .farmLocationBack {
    background: #ffffff;
    margin-top: 0.2rem;
    .farmLocation {
      margin: 0.26rem AUTO;
      width: 90%;
      background: #ffffff;
      height: 0.84rem;
      display: flex;
      .farmLocation-left {
        flex: 1;
        margin-top: 0.28rem;
      }
      span {
        flex: 12;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        letter-spacing: 0;
        line-height: 0.9rem;
      }
    }
  }
  .farmIntroductionBack {
    padding: 0.32rem;
    margin-top: 0.2rem;
    font-family: "Montserrat-Light";
    font-size: 0.24rem;
    background: #ffffff;
    letter-spacing: 0;
    line-height: 0.36rem;
    .title {
      margin-bottom: 0.24rem;
      font-family: "Montserrat-SemiBold";
      font-size: 0.32rem;
      color: #383f45;
      letter-spacing: 0;
      text-align: left;
      line-height: 0.32rem;
    }
    .detail {
      span {
        font-family: "Montserrat-Light";
        font-size: 0.24rem;
        color: #666666;
        letter-spacing: 0;
        line-height: 0.36rem;
      }
      .more {
        padding-left: 0.1rem;
        font-size: 0.24rem;
        color: #ff9f00;
        letter-spacing: 0;
        line-height: 0.36rem;
        cursor: pointer;
      }
    }
  }
  .farmDetailBack {
    margin-top: 0.2rem;
    padding: 0.32rem;
    background: #ffffff;
    .farmDetailTitle {
      display: flex;
      span {
        flex: 5;
        font-family: Montserrat-Medium;
        font-size: 0.32rem;
        color: #333333;
        text-align: left;
        line-height: 0.7rem;
      }

      .vrButton {
        flex: 5;
        text-align: right;
        button {
          border: 0;
          border-radius: 0.4rem;
          background-color: #00b459;
          padding: 0.2rem 0.6rem;
          color: #ffffff;
        }
      }
    }
    .farmDetailLinkman {
      display: flex;
      .linkManMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .linkManName {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .farmDetailPhone {
      display: flex;
      .phoneMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .phone {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .farmDetailEmail {
      display: flex;
      .emailMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .email {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .farmDetailArea {
      display: flex;
      .areaMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .area {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
  }
  .landDetailBack {
    margin-top: 0.2rem;
    padding: 0.32rem;
    background: #ffffff;
    .landDetailTitle {
      span {
        font-family: Montserrat-Medium;
        font-size: 0.32rem;
        color: #333333;
        text-align: left;
        line-height: 0.7rem;
      }
    }
    .landDetailLinkman {
      display: flex;
      .landNameMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .landName {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .landDetailArea {
      display: flex;
      .areaMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .area {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .landDetailPlants {
      display: flex;
      .plantsMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .plants {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .landDetailStart {
      display: flex;
      .startMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .start {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .landDetailEnd {
      display: flex;
      .endMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .end {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
    .landDetailCost {
      display: flex;
      .costMajor {
        flex: 5;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
        text-align: left;
        line-height: 0.6rem;
      }
      .cost {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #00b459;
        text-align: right;
        line-height: 0.6rem;
      }
    }
  }
}
</style>