<template>
  <div style="position:fixed;top:1rem;z-index:1888;width:7.5rem; height:1.14rem;">
    <van-search shape="round" v-model="goodsName" placeholder="Search" show-action>
      <template #right-icon>
        <van-button @click="onSearch" round type="primary">Search</van-button>
      </template>
      <template #action>
        <van-icon @click="openCart" name="cart-o" size="0.5rem" :badge="goodsNumInCart"/>
      </template>
    </van-search>
  </div>
</template>
<script>
import { mapState } from "vuex";
import { queryCart } from "@/api/mall";
export default {
  name: "GoodsHeaders",
  data() {
    return {
      goodsName: "",
      goodsNumInCart: 0
    };
  },
  methods: {
    onSearch() {
      if (this.$route.query.productName != this.goodsName) {
        this.$router.push({
          path: "/mall/goods/result",
          query: { productName: this.goodsName }
        });
      }
    },
    openCart() {
      this.$router.push({ name: "shoppingCart" });
    },
    //loading cart
    loadCart() {
      queryCart({ username: this.username })
        .then(res => {
          this.goodsNumInCart = res.data.length;
        })
        .catch(e => {
          this.goodsNumInCart = 0;
          console.log(e);
        });
    }
  },
  mounted() {
    if (this.$route.query.productName !== undefined) {
      this.goodsName = this.$route.query.productName;
    }
    this.loadCart();
  },
  computed: {
    ...mapState({
      username: state => state.user.username || "jack"
    })
  }
};
</script>
<style lang="less" scoped>
/deep/ .van-search {
  .van-button {
    height: 0.5rem;
    width: 100%;
  }
  .van-field__control {
    padding: 0.1rem;
    color: #9a9a9a;
    font-size: 0.24rem;
  }
  .van-field__left-icon {
    margin-right: 0;
    margin-left: 0.08rem;
    .van-icon {
      line-height: 0.68rem;
      color: #9a9a9a;
    }
  }
  .van-icon-clear {
    font-size: 0.28rem;
    margin-right: 0.08rem;
  }
}
/deep/ .van-info {
  background-color: #00b459;
  min-width: 0.36rem;
}
/deep/ .van-button.van-button--primary.van-button--normal.van-button--round {
  height: 0.54rem;
  width: 1.34rem;
}
/deep/ .van-button__text {
  font-family: Montserrat-Light;
}
/deep/ .van-field__right-icon {
  padding: 0;
}
/deep/ .van-cell.van-cell--borderless.van-field {
  padding: 0 0.14rem 0 0;
}
/deep/ .van-search__action {
  margin-top: 0.1rem;
}
</style>

