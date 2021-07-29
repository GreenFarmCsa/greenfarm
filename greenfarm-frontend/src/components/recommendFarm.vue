<template>
  <div class="card farm-card">
    <div class="title">{{item.farmName}}</div>
    <div class="img">
      <img :src="imgurl(item.iconUrl)" alt>
      <div class="area">{{item.totalArea}}m²</div>
    </div>
    <div class="desc">{{item.introduction}}</div>
    <div class="location">
      <svg-icon iconClass="location-mark" style="font-size: 0.28rem"></svg-icon>
      {{item.location}}
    </div>
  </div>
</template>

<script>
export default {
  name: "RecommendFarm",
  data() {
    return {};
  },
  props: {
    item: {
      type: Object,
      default: () => {}
    }
  },
  mounted() {},
  methods: {
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/styles/commonC.scss";
.farm-card {
  .title {
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    letter-spacing: -0.006rem;
    line-height: 0.28rem;
  }
  .img {
    position: relative;
    margin: 0.2rem -0.3rem 0;
    height: 2.24rem;
    img {
      width: 100%;
      height: 2.24rem;
      object-fit: cover;
    }
    .area {
      position: absolute;
      bottom: 0;
      right: 0;
      color: #fff;
      background: #fda541;
      font-size: 0.2rem;
      height: 0.36rem;
      line-height: 0.36rem;
      padding: 0 0.2rem;
      border-radius: 0.08rem 0 0 0;
      font-family: PingFangSC-Regular;
      letter-spacing: -0.5px;
    }
  }
  .desc {
    margin: 0.2rem 0;
    font-family: Montserrat-Light;
    font-size: 0.24rem;
    color: #666666;
    letter-spacing: 0;
    line-height: 0.32rem;
  }
  .location {
    font-family: Montserrat-Light;
    font-size: 0.24rem;
    color: #00b459;
    letter-spacing: 0;
    line-height: 0.24rem;
  }
}
</style>
