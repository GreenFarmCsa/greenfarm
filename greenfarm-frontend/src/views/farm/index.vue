<template>
  <div>
    <Header @onSearch="onSearch"></Header>
    <div class="homepage">
      <div class="card kind-card">
        <div
          class="tab"
          v-for="item in tabList"
          :key="item.name"
          @click="goClassification(item.name)"
        >
          <div class="img-wrapper">
            <img :src="item.src" alt="item.name">
          </div>
          <p>{{item.name}}</p>
        </div>
      </div>
      <div class="recommend-wrapper">
        <span></span>
        <span class="recommend-title">
          <svg-icon iconClass="recommend farm" style="font-size: 0.38rem"></svg-icon>Recommended Farm
        </span>
        <div class="recommend-list">
          <RecommendFarm
            v-for="(item,index) in dataList"
            :key="index"
            :item="item"
            @click.native="goDetail(item)"
          ></RecommendFarm>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Header from "@/components/farm/FarmSearchHeader";
import RecommendFarm from "@/components/recommendFarm";
import { queryAllFarm } from "@/api/farm";
export default {
  name: "HomePage",
  components: { Header, RecommendFarm },
  data() {
    return {
      dataList: [],
      tabList: [
        {
          name: "Map",
          src: require("@/assets/images/map.png")
        },
        {
          name: "Crop",
          src: require("@/assets/images/crops.png")
        },
        {
          name: "Area",
          src: require("@/assets/images/area.png")
        }
      ]
    };
  },
  computed: {},
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      queryAllFarm().then(res => {
        this.dataList = res.data;
      });
    },
    goDetail(item) {
      this.$router.push({
        path: "/farm/detail/" + item.farmId
      });
    },
    goClassification(name) {
      if (name === "Map") {
        let infos = [];
        this.dataList.forEach(item => {
          if (
            item.latitudeLongitude &&
            item.latitudeLongitude.split(",").length > 0
          ) {
            infos.push({
              farmId: item.farmId,
              name: item.farmName,
              loc: item.latitudeLongitude.split(",")
            });
          }
        });
        if (infos.length == 0) {
          this.$toast({ message: "no location data!", position: "bottom" });
          return;
        }
        window.UMJSBridge.callHandler(
          "jumpMap",
          {
            infos: infos
          },
          response => {
            if (response.farmId) {
              this.$router.push({ path: "/farm/detail/" + response.farmId });
            } else {
              this.$toast({ message: "selete failed!", position: "bottom" });
            }
          }
        );
      } else if (name === "Crop") {
        this.$router.push({
          path: "/farm/crop"
        });
      } else if (name === "Area") {
        this.$router.push({
          path: "/farm/area"
        });
      }
    },
    onSearch(keyword) {
      this.$router.push({
        path: "/farm/result/" + keyword
      });
      this.getData();
    }
  }
};
</script>

<style lang="scss" scoped>
.homepage {
  background: #fdfdfd;
  padding: 1.14rem 0.3rem 0.3rem 0.3rem;
  .card {
    padding: 0.3rem;
    box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
  }
  .kind-card {
    margin: 0.15rem 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    height: 1.72rem;
    .tab {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      width: 33%;
      .img-wrapper {
        width: 0.96rem;
        height: 0.96rem;
        border: 0.02rem solid #dedede;
        border-radius: 1.06rem;
        border-radius: 0.16rem;
        box-sizing: border-box;
        display: flex;
        align-items: center;
        justify-content: center;
        img {
          width: 0.62rem;
        }
      }
      p {
        font-size: 0.28rem;
        margin-top: 0.28rem;
        font-weight: bold;
        font-family: "Montserrat-Regular";
      }
    }
  }
  .recommend-wrapper {
    .recommend-title {
      display: inline-block;
      margin: 0.23rem 0;
      line-height: 0.32rem;
      font-family: "Montserrat-Medium";
      font-size: 0.32rem;
      color: #333333;
      letter-spacing: 0;
      font-weight: bold;
    }
  }
  .footer {
    margin: 1rem 0;
  }
}
</style>




