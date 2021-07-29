<template>
  <div class="card goods-card" @click="goDetails(item.productId)">
    <div class="img">
      <van-image :src="imgurl((item.imageUrl && item.imageUrl.length > 0) ? item.imageUrl[0] : '')"  fit="cover" >
        <template v-slot:loading>
          <van-loading type="spinner" size="20" />
        </template>
      </van-image>
    </div>
    <div class="name">{{item.productName}}*{{item.weight}}KG</div>
    <div class="foot">
      <div class="price">
        <span class="dolar">$</span>
        {{parseFloat(item.price).toFixed(2)}}
      </div>
      <div class="sales">{{item.saleNumber}} Sales</div>
    </div>
  </div>
</template>
<script>
export default {
  name: "GoodsHot",
  data() {
    return {};
  },
  props: {
    item: {
      type: Object,
      default: () => {}
    }
  },

  methods: {
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    goDetails(val) {
      this.$router.push({ name: "goodsDetail", params: { goodsId: val } });
    }
  },
  mounted() {}
};
</script>
<style lang="scss" scoped>
.goods-card {
  margin: 0.1rem 0;
  width: 40%;
  .img {
    height: 2.5rem;
    margin: -0.3rem -0.3rem 0.3rem;
    overflow: hidden;
    border-radius: 0.15rem 0.15rem 0 0;
    .van-image {
      height: 2.5rem;
      width: 100%;
    }
  }
  .name {
    margin: 0.3rem 0;
    font-family: Montserrat-Regular;
    font-size: 0.32rem;
    letter-spacing: 0;
    line-height: 0.32rem;
  }
  .foot {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .price {
      font-family: Montserrat-Regular;
      font-size: 0.26rem;
      color: #00b459;
      letter-spacing: 0;
      line-height: 0.32rem;
      .dolar {
        font-family: Montserrat-Regular;
        font-size: 0.32rem;
      }
    }
    .sales {
      font-family: Montserrat-Light;
      font-size: 0.24rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.24rem;
    }
  }
}
</style>

