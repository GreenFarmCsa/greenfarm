<template>
  <div class="card goods-card">
    <div class="img">
      <img :src="imgurl((item.imageUrl && item.imageUrl.length > 0) ? item.imageUrl[0] : '')" alt>
    </div>
    <div class="name">{{item.productName}}*{{item.weight}}KG</div>
    <div class="foot">
      <div class="price">
        <span class="dolar">$</span>
        {{parseFloat(item.price).toFixed(2)}}
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
  name: "RecommendGoods",
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
    },
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
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/styles/commonC.scss";
.goods-card {
  .img {
    margin: -0.3rem -0.3rem 0.3rem;
    height: 2.8rem;
    img {
      width: 100%;
      height: 2.8rem;
      border-radius: 0.15rem 0.15rem 0 0;
      object-fit: cover;
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
    .like {
      font-family: Montserrat-Light;
      font-size: 0.24rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.24rem;
    }
  }
}
</style>
