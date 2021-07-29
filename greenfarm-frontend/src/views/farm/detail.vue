<template>
  <div class="homepage">
    <div class="genera-situation-wrapper">
      <div class="img-wrapper">
        <van-swipe :autoplay="3000" indicator-color="white">
          <van-swipe-item v-for="(image, index) in dataObj.imageUrls" :key="index">
            <img :src="image">
          </van-swipe-item>
        </van-swipe>
      </div>
      <div class="title-wrapper">
        <div class="title">{{dataObj.farmName}}</div>
        <div class="other-wrapper">
          <div class="cost">        
            <svg-icon iconClass="time-green" style="font-size: 0.28rem"></svg-icon>
          </div>
          <div class="lease">{{dataObj.rentPeriod}} days lease</div>
        </div>
      </div>
      <div class="location-wrapper">
        <svg-icon iconClass="location-mark-yellow" style="font-size: 0.28rem"></svg-icon>
        {{dataObj.location}}
      </div>
      <div class="introduction-wrapper">
        <div class="title">
          Introduction
          <div class="btn-list"></div>
        </div>
        <div class="detail">
          <span v-html="introduction"></span>
          <span v-if="moreShow" class="more" @click="clickMore">MORE...</span>
          <span v-if="!moreShow" v-html="introductionExtra"></span>
        </div>
      </div>
      <div class="select-land-wrapper">
        <div class="land-title-warpper">
          <span class="title">Select The Land</span>
          <span class="remain">Remaining&nbsp;{{isRentNum}}/{{isRentNum+isNotRentNum}}</span>
        </div>
        <div class="tag-list-wrapper">
          <div class="tag-list-item" v-for="item in tagList" :key="item.value">
            <div
              class="block"
              :class="[item.value=='Selected'?'selected':item.value=='Leased'?'leased':'']"
            ></div>
            <div class="block-txt">{{item.value}}</div>
          </div>
        </div>
        <div class="land-list-wrapper">
          <div
            class="land-list-item"
            v-for="item in landList"
            :key="item.landId"
            @click="selectLand(item)"
          >
            <div
              class="ractangle"
              :class="[item.isRent?'leased':'',selLandId===item.landId?'selected':'']"
            >{{item.landId}}</div>
            <div class="ractangle-txt" :class="[item.isRent?'leased':'',selLandId===item.landId?'selected':'']">{{item.area}}m²</div>
          </div>
        </div>
      </div>
      <div class="seed-list-wrapper">
        <div class="title">Seed List</div>
        <div class="seed-list">
          <div v-for="item in seedList" :key="item.seedId" class="seed-list-item">
            <div class="img">
              <img :src="item.seedImageUrl" alt>
            </div>
            <div class="other">
              <div class="name">{{item.seedName}}</div>
              <div class="output-txt">Estimated output:</div>
              <div class="output">{{item.production}}kg/m²</div>
            </div>
          </div>
        </div>
      </div>
      <div class="btn-list-wrapper">
        <div class="title-wrapper">
          <div class="title">Detail</div>
          <div class="btn" @click="showVR()">
            VR Photo
            <van-icon name="arrow" color="#00B459" style="top: 0.05rem;left: 0.05rem;"/>
          </div>
        </div>
        <div class="title-wrapper">
          <div class="title">Community</div>
          <div class="btn" @click="onCommunity()">
            More
            <van-icon name="arrow" color="#00B459" style="top: 0.05rem;left: 0.05rem;"/>
          </div>
        </div>
        <div class="title-wrapper">
          <div class="title">Produce</div>
          <div class="btn" @click="onFarmGoods()">
            More
            <van-icon name="arrow" color="#00B459" style="top: 0.05rem;left: 0.05rem;"/>
          </div>
        </div>
      </div>
      <LiveList :location="dataObj.location"></LiveList>
      <div class="button-list-wrapper">
        <van-button @click="onSubscribe" round type="primary" color="#F7B500" class="button-list-item">Subscribe</van-button>
        <van-button @click="onLease" round type="primary" class="button-list-item">Lease</van-button>
      </div>
    </div>
  </div>
</template>
<script>
import { queryFarmById, querySeed } from "@/api/farm";
import { queryLandByFarmId } from "@/api/land";
import { queryCommunityByFarmId } from "@/api/community";
import LiveList from "@/components/liveList";
export default {
  name: "HomePage",
  components: { LiveList },
  data() {
    return {
      liveId: -1,
      dataObj: {},
      seedList: [],
      introduction: "",
      introductionExtra: "",
      moreShow: "",
      landList: [],
      isRentNum: 0,
      isNotRentNum: 0,
      tagList: [
        {
          value: "Optional",
          name: "Optional"
        },
        {
          value: "Selected",
          name: "Selected"
        },
        {
          value: "Leased",
          name: "Leased"
        }
      ],
      selLandId: ""
    };
  },
  computed: {},
  mounted() {
    this.getData();
  },
  methods: {
    showVR() {
      window.UMJSBridge.callHandler(
        "jumpVR",
        {
          url:
            process.env.VUE_APP_BASE_API +
            "/file/download?url=" +
            this.dataObj.vrUrl
        },
        response => {}
      );
    },
    getData() {
      let params = {
        id: this.$route.params.id
      };
      queryFarmById(params).then(res => {
        let tmp = res.data;
        if(tmp.introduction){
          tmp.introduction = tmp.introduction.replace(/\n/g, "<br/>");
        }
        this.commentsList = tmp;
        this.dataObj = tmp;
        if (this.dataObj.imageUrls && this.dataObj.imageUrls.length) {
          for (let i = 0; i < this.dataObj.imageUrls.length; i++) {
            this.dataObj.imageUrls[i] =
              process.env.VUE_APP_BASE_API +
              "/file/download?url=" +
              this.dataObj.imageUrls[i];
          }
        }
        this.getIntroduction();
      });
      querySeed().then(res => {
        this.seedList = res.data;
        for (let i = 0; i < this.seedList.length; i++) {
          let id =
            this.seedList[i].seedId % 5 == 0 ? 5 : this.seedList[i].seedId % 5;
          this.$set(
            this.seedList[i],
            "seedImageUrl",
            require("@/assets/images/seed-" + id + ".png")
          );
        }
      });

      queryLandByFarmId(params).then(res => {
        this.landList = res.data;
        this.isRentNum = 0;
        this.isNotRentNum = 0;
        for (let i = 0; i < this.landList.length; i++) {
          if (this.landList[i].isRent) {
            this.isRentNum++;
          } else {
            this.isNotRentNum++;
          }
        }
      });
    },
    goDetail() {
      this.$router.push({
        path: "/farm/detail"
      });
    },
    getIntroduction() {
      if (this.dataObj.introduction) {
        if (this.dataObj.introduction.length <= 200) {
          this.introduction = this.dataObj.introduction;
          this.introductionExtra = "";
          this.moreShow = false;
        } else {
          this.introduction = this.dataObj.introduction.substr(0, 200);
          this.introductionExtra = this.dataObj.introduction.slice(200);
          this.moreShow = true;
        }
      }
    },
    clickMore() {
      this.moreShow = false;
    },
    async onCommunity() {
      try {
        let farmId = this.$route.params.id;
        const res = await queryCommunityByFarmId({ id: farmId });
        if (
          res &&
          res.data &&
          JSON.stringify(res.data) != "[]"
        ) {
          this.$store.dispatch("title/setTitle", res.data.communityName);
          this.$router.push({
            path: "/me/community/postinfo",
            query: { id: JSON.stringify(res.data) }
          });
        } else {
          this.$toast({ message: "no data", position: "bottom" });
        }
      } catch (err) {
        console.log(err);
      }
    },
    onFarmGoods() {
      let farmId = this.$route.params.id;
      this.$router.push({
        path: "/mall/goods/result",
        query: { farmId: this.$route.params.id }
      });
    },
    onSubscribe (){
      let farmId = this.$route.params.id;
      let landId = this.selLandId;
      if (landId) {
        this.$router.push({
          path: "/farm/subscribe/result",
          query: { farmId, land: this.land, type:"subscribe" }
        });
      } else {
        this.$toast({ message: "cannot subscribe, please select the land!" });
      }
    },
    onLease() {
      let farmId = this.$route.params.id;
      let landId = this.selLandId;
      if (landId) {
        this.$router.push({
          path: "/farm/order/edit",
          query: { farmId, land: this.land, type:"order" }
        });
      } else {
        this.$toast({ message: "cannot lease, please select the land!" });
      }
    },
    selectLand(item) {
      if (item.isRent) {
        return;
      }
      this.land = JSON.stringify(item);
      this.selLandId = item.landId;
    }
  }
};
</script>

<style lang="scss" scoped>
.homepage {
  background: #F6F6F6;
  .genera-situation-wrapper {
    .img-wrapper {
      width: 100%;
      height: 4.54rem;
      img {
        width: 100%;
        height: 4.54rem;
      }
    }
    .title-wrapper {
      background: #FFFFFF;
      height: 1.2rem;
      padding: 0.32rem 0.32rem 0 0.32rem;
      margin-bottom: 0.24rem;
      font-family: "Montserrat-Medium";
      .title {
        font-family: Montserrat-Medium;
        margin-bottom: 0.24rem;
        font-size: 0.32rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 0.32rem;
      }
      .other-wrapper {
        display: flex;
        .cost {
          font-size: 0.28rem;
          color: #00b459;
          letter-spacing: 0;
          line-height: 0.28rem;
        }
        .lease {
          padding-left: 0.08rem;
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #00B459;
          letter-spacing: 0;
          line-height: 0.28rem;
        }
      }
    }
    .location-wrapper {
      background: #FFFFFF;
      margin-bottom: 0.24rem;
      padding: 0.32rem;
      font-family: "Montserrat-Regular";
      font-size: 0.28rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.28rem;
    }
    .introduction-wrapper {
      background: #FFFFFF;
      margin-bottom: 0.24rem;
      padding: 0.32rem;
      font-family: "Montserrat-Light";
      font-size: 0.24rem;
      color: #666666;
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
        .btn-list {
          float: right;
          .btn-list-item {
            margin-right: 0.2rem;
          }
        }
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
          font-size: 0.24rem;
          color: #ff9f00;
          letter-spacing: 0;
          line-height: 0.36rem;
          cursor: pointer;
        }
      }
    }
    .select-land-wrapper {
      background: #FFFFFF;
      margin-bottom: 0.24rem;
      padding: 0.32rem;
      .land-title-warpper {
        .title {
          margin-bottom: 0.24rem;
          font-family: "Montserrat-SemiBold";
          font-size: 0.32rem;
          color: #383f45;
          letter-spacing: 0;
          text-align: left;
          line-height: 0.32rem;
        }
        .remain {
          float: right;
          margin-top: 0.04rem;
          font-family: "Montserrat-Light";
          font-size: 0.24rem;
          color: #6c7b8a;
          text-align: right;
          line-height: 0.24rem;
        }
      }
      .tag-list-wrapper {
        display: flex;
        margin: 0.36rem 0 0.44rem 0;
        .tag-list-item {
          display: flex;
          margin-right: 0.58rem;
          .block {
            width: 0.3rem;
            height: 0.3rem;
            margin-right: 0.08rem;
            background: rgba(5, 184, 133, 0.51);
            border-radius: 0.08rem;
            &.selected {
              background: #05b885;
            }
            &.leased {
              background: #dddddd;
            }
          }
          .block-txt {
            font-family: "Montserrat-Light";
            font-size: 0.24rem;
            color: #333333;
            text-align: right;
            line-height: 0.3rem;
          }
        }
      }
      .land-list-wrapper {
        &::-webkit-scrollbar {
          height: 0;
          display: none;
        }
        overflow-x: scroll;
        overflow-y: hidden;
        scrollbar-width: none;
        -ms-overflow-style: none;
        display: flex;
        .land-list-item {
          margin-right: 0.18rem;
          width: 1rem;
          .ractangle {
            width: 0.66rem;
            height: 2.5rem;
            margin: 0 auto;
            padding: 0.2rem 0.08rem;
            box-sizing: border-box;
            margin-bottom: 0.1rem;
            background: rgba(0, 180, 89, 0.51);
            border-radius: 0.08rem;
            font-family: "Montserrat-Light";
            font-size: 0.2rem;
            color: #ffffff;
            text-align: right;
            line-height: 0.24rem;
            word-break: break-all;
            text-align: center;
            &.selected {
              background: #00b459;
            }
            &.leased {
              background: #dadada;
              color: #999999;
            }
          }
          .ractangle-txt {
            font-family: "Montserrat-Light";
            font-size: 0.2rem;
            color: #00b459;
            letter-spacing: 0;
            text-align: center;
            line-height: 0.2rem;
            &.leased {
              color: #999999;
            }
          }
        }
      }
    }
    .seed-list-wrapper {
      background: #FFFFFF;
      padding: 0.32rem 0 0 0.32rem;
      margin-bottom: 0.24rem;
      .title {
        margin-bottom: 0.24rem;
        font-family: "Montserrat-SemiBold";
        font-size: 0.32rem;
        color: #383f45;
        letter-spacing: 0;
        text-align: left;
        line-height: 0.32rem;
      }
      .seed-list {
        overflow: hidden;
        .seed-list-item {
          float: left;
          width: 45%;
          margin: 0 5% 5% 0;
          box-sizing: border-box;
          background: #ffffff;
          box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
          border-radius: 0.16rem;
          border-radius: 0.16rem;
          text-align: center;
          .img {
            padding: 0.2rem 0;
            border-bottom: 0.02rem solid #f0f0f0;
            img {
              width: 1.5rem;
              height: 1.5rem;
              border-radius: 0.3rem;
              object-fit: cover;
            }
          }
          .other {
            padding: 0.24rem;
            text-align: left;
            .name {
              font-family: "Montserrat-Medium";
              font-size: 0.28rem;
              color: #333333;
              letter-spacing: 0;
              line-height: 0.28rem;
            }
            .output-txt {
              margin: 0.1rem 0;
              font-family: "Montserrat-Light";
              font-size: 0.24rem;
              color: #6c7b8a;
              letter-spacing: 0;
              line-height: 0.24rem;
            }
            .output {
              font-family: "Montserrat-Regular";
              font-size: 0.24rem;
              color: #00b459;
              letter-spacing: 0;
              line-height: 0.24rem;
            }
          }
        }
      }
    }
    .btn-list-wrapper {
      background: #FFFFFF;
      margin-bottom: 0.24rem;
      .title-wrapper {
        padding: 0.38rem 0.32rem;
        height: auto;
        margin-bottom: 0;
        display: flex;
        justify-content: space-between;
        box-shadow: none;
        &:not(:last-child) {
          border-bottom: 1px solid #F0F0F0;
        }
        .title {
          font-family: "Montserrat-SemiBold";
          font-size: 0.32rem;
          color: #333;
          letter-spacing: 0;
          text-align: left;
          margin-bottom: 0;
        }
        .btn {
          font-family: "Montserrat-Regular";
          font-size: 0.28rem;
          color: #00b459;
          letter-spacing: 0;
          line-height: 0.28rem;
        }
      }
    }
    .button-list-wrapper {
      background: #FFFFFF;
      display: flex;
      justify-content: center;
      padding: 0.24rem 0.3rem;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.08);
      .button-list-item {
        width: 3.2rem;
        height: 0.8rem;
        margin: 0 0.16rem;
      }
    }
  }
  .footer {
    margin: 1rem 0;
  }
}
</style>
<style lang="scss">
.button-list-wrapper {
  .van-button--primary {
    .van-button__text {
      font-family: "Montserrat-Regular";
      font-size: 0.26rem;
      color: #fcfcfc;
      letter-spacing: 0;
      line-height: 0.72rem;
    }
  }
  .van-button--plain {
    color: #fff;
    background-color: rgba(0, 180, 89, 0.1);
    border: 0.01rem solid #07c160;
    .van-button__text {
      color: #00b459;
    }
  }
}
.title {
  .btn-list {
    .btn-list-item.van-button {
      height: 0.5rem;
      margin-top: -0.08rem;
    }
  }
}
</style>


