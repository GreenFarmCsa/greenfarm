<template>
  <div class="product">
    <div class="left">
      <img :src="img">
    </div>
    <div class="detail">
      <div class="title">{{dataObj.productName}}</div>
      <div class="description">{{dataObj.remark}}</div>
    </div>
    <div class="right">
      <div class="prize">${{parseFloat(dataObj.price).toFixed(2)}}</div>
      <div class="count">X {{dataObj.count}}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      img: ""
    };
  },
  props: {
    dataObj: {
      default: () => {},
      type: Object
    }
  },
  computed: {},
  mounted() {
    this.img =
      process.env.VUE_APP_BASE_API +
      "/file/download?url=" +
      this.dataObj.imageUrl;
  },
  methods: {}
};
</script>

<style lang="scss" scoped>
.product {
  display: flex;
  padding: 0.32rem 0.32rem 0 0.32rem;
  .left {
    img {
      object-fit: cover;
      border-radius: 0.1rem;
      width: 2rem;
      height: 2rem;
    }
  }
  .detail {
    padding: 0.2rem 0.3rem;
    width: 2.66rem;
    .title {
      height: 0.32rem;
      font-size: 0.32rem;
      font-family: Montserrat-Regular;
    }
    .description {
      margin-top: 0.16rem;
      font-family: Montserrat-Light;
      font-size: 0.28rem;
      color: #7a7f83;
    }
  }
  .right {
    margin-left: auto;
    padding: 0.2rem 0;
    .prize {
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #00b459;
      line-height: 0.28rem;
      text-align: right;
    }
    .count {
      margin-top: 0.18rem;
      font-family: Monaco;
      font-size: 0.24rem;
      color: #999999;
      letter-spacing: 0;
      text-align: right;
      line-height: 0.24rem;
    }
  }
}
</style>
