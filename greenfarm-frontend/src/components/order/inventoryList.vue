<template>
  <div class="inventory">
    <div class="img">
      <img :src="img">
    </div>
    <div class="bottom">
      <div class="left">
        <div class="name">{{item.productName}}*{{item.number}}KG</div>
        <div class="detail">
          <div class="price">${{parseFloat(item.price).toFixed(2)}}</div>
          <div class="sales">&nbsp;&nbsp;{{item.saleNumber}} Sales</div>
        </div>
      </div>
      <div class="right">
        <div class="key">Remaining</div>
        <div class="value">&nbsp;{{item.number}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { productLike } from "@/api/mall";
export default {
  name: "RecommendGoods",
  data() {
    return {
      img: ""
    };
  },
  props: {
    item: {
      type: Object,
      default: () => {}
    }
  },
  mounted() {
    this.img =
      process.env.VUE_APP_BASE_API +
      "/file/download?url=" +
      this.item.imageUrl[0];
  },
  methods: {
    likeClick(item) {
      productLike({
        productId: item.productId,
        username: this.$store.state.user.username
      })
        .then(res => {
          this.$toast({ position: "bottom", message: "liked!" });
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
.inventory {
  background: #fff;
  box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
  border-radius: 0.16rem;
  margin: 0.3rem;
  .img {
    img {
      width: 100%;
      height: 2.8rem;
      border-radius: 0.15rem 0.15rem 0 0;
      object-fit: cover;
    }
  }
  .bottom {
    padding: 0.16rem 0.3rem;
    display: flex;
    .left {
      padding: 0 0.1rem;
      .name {
        font-family: Montserrat-Regular;
        font-size: 0.32rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 0.32rem;
      }
      .detail {
        padding-top: 0.2rem;
        display: flex;
        .price {
          font-family: Montserrat-Regular;
          font-size: 0.26rem;
          color: #08c25e;
          letter-spacing: 0;
          line-height: 0.32rem;
        }
        .sales {
          font-family: Montserrat-Light;
          font-size: 0.24rem;
          color: #909090;
          letter-spacing: 0;
          line-height: 0.32rem;
        }
      }
    }
    .right {
      align-items: center;
      display: flex;
      margin-left: auto;
      .key {
        font-family: Montserrat-Light;
        font-size: 0.28rem;
        color: #666666;
        letter-spacing: 0;
        line-height: 0.28rem;
      }
      .value {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #ff9f00;
        letter-spacing: 0;
        line-height: 0.28rem;
      }
    }
  }
  .foot {
    display: flex;
    justify-content: space-between;
    align-items: center;

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




