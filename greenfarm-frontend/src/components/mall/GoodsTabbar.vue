<!--tabbar for mall-->
<template>
  <div class="tabbar">
    <van-goods-action-icon @click="goCart">
      <template #icon>
        <van-icon name="cart-o" size="0.5rem" :badge="goodsCount"/>
      </template>
    </van-goods-action-icon>
    <van-goods-action-button
      color="#F7B500"
      type="warning"
      text="Add to Cart"
      @click="addToCart"
    />
    <van-goods-action-button
      color="#00B459"
      type="primary"
      text="Buy it Now"
      @click="createOrder"
    />
  </div>
</template>
<script>
export default {
  name: "GoodsTabbar",
  props: {
    goodsCount: {
      type: Number,
      default: 0
    }
  },
  methods: {
    addToCart() {
      this.$emit("add");
    },
    goCart() {
      this.$router.push({ name: "shoppingCart" });
    },
    createOrder() {
      this.$emit("buy");
    }
  }
};
</script>
<style lang="less" scoped>
.tabbar {
  box-shadow: 0 0 0.2rem 0 rgba(0,0,0,0.08);
  background-color: #fff;
  height: 1.15rem;
  padding-left: 0.3rem;
  padding-right: 0.3rem;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 1.46rem;
  display: flex;
  justify-content: space-between;
  align-items: center; 
  /deep/ .van-goods-action-icon {
    margin-right: 0.8rem;
    .van-info {
      background-color: #00b459;
      min-width: 0.34rem;
    }
    .van-goods-action-icon__icon {
      margin-top: 0.1rem;
    }
  }
  /deep/ .van-button .van-button__content .van-button__text{
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    color: #FCFCFC;
    letter-spacing: 0;
    line-height: 0.28rem;
  }
}
</style>
