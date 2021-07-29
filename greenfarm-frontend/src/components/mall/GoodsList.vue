<template>
  <div class="card goods-card">
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
        <span class="like sale">{{item.saleNumber}} Sales</span>
      </div>
      <div class="like" @click.stop="likeClick(item)">
        <svg-icon iconClass="like" style="font-size: 0.28rem"></svg-icon>
        {{item.likeNumber}}
      </div>
    </div>
  </div>
</template>
<script>
import { productLike } from "@/api/mall";
export default {
  name: "GoodsList",
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
    likeClick(item) {
      productLike({
        isLike: true,
        productId: item.productId,
        username: this.$store.state.user.username
      })
        .then(res => {
          this.$toast({ position: "bottom", message: "success!" });
          item.likeNumber = 0 + item.likeNumber + 1;
        })
        .catch(e => {
          console.log(e);
        });
    },
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    }
  }
};
</script>
<style lang="scss" scoped>
.goods-card {
  .img {
    margin: -0.3rem -0.3rem 0.3rem;
    height: 3rem;
    overflow: hidden;
    border-radius: 0.15rem 0.15rem 0 0;
    .van-image {
      height: 3rem;
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
    }
    .like {
      font-family: Montserrat-Light;
      font-size: 0.24rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.24rem;
    }
    .sale {
      margin-left: 0.2rem;
    }
  }
}
</style>
