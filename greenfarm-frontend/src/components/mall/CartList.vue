<template>
  <div style="width: calc(100% - 1rem);">
  <!-- <van-swipe-cell> -->
    <van-card>
      <template #thumb>
        <img
          class="avatar"
          @click="viewDetail"
          :src="imgurl((goodsInfo.imageUrl) ? goodsInfo.imageUrl: '')"
          alt
        >
      </template>
      <template #title>
        <div class="name">{{productItem.productName}}*{{productItem.weight}}KG</div>
      </template>
      <template #tags>
        <div class="tags">
          <div class="price">${{parseFloat(productItem.price).toFixed(2)}}</div>
          <div class="step">
            <van-stepper
              @change="updateNumCart"
              v-model="goodsInfo.number"
              button-size="20px"
              theme="round"
            />
          </div>
        </div>
      </template>
    </van-card>
    <!-- <template #right>
      <van-button @click="removeItem" square text="Remove" type="danger" class="delete-button"/>
    </template>
  </van-swipe-cell> -->
  </div>
</template>
<script>
import { queryProductsById, updateCart, removeCart } from "@/api/mall";
export default {
  name: "CartList",
  data() {
    return {
      productItem: {}
    };
  },
  methods: {
    viewDetail() {
      this.$router.push({
        name: "goodsDetail",
        params: { goodsId: this.goodsInfo.productId }
      });
    },
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    //details
    getProductDetails() {
      queryProductsById({ productId: this.goodsInfo.productId })
        .then(res => {
          if (res.data.length !== 0) {
            this.productItem = res.data[0];
          }
        })
        .catch(e => {
          this.productItem = {};
          console.log(e);
        });
    },
    //update
    updateNumCart(val) {
      if(val == ""){
        val = 1
      }
      let params = {
        username: this.goodsInfo.username,
        productId: this.goodsInfo.productId,
        count: val
      };
      updateCart(params)
        .then(res => {
          this.$emit("calculate");
        })
        .catch(e => {
          console.log(e);
        });
    },

    //remove
    removeItem() {
      let params = {
        username: this.goodsInfo.username,
        productId: this.goodsInfo.productId
      };
      removeCart(params)
        .then(res => {
          this.$emit("refresh");
        })
        .catch(e => {
          this.$emit("refresh");
          console.log(e);
        });
    }
  },
  props: {
    goodsInfo: {
      type: Object,
      default: () => {}
    }
  },
  mounted() {
    this.getProductDetails();
  }
};
</script>
<style lang="less" scoped>
/deep/ .van-card {
  background-color: #fff;
  .van-card__thumb {
    width: 1.2rem;
    height: 1.2rem;
  }
}
/deep/ .van-stepper__plus {
  background-color: #00b459;
  border-color: #00b459;
  color: #fff;
}
/deep/ .van-stepper__minus {
  background-color: #00b459;
  border-color: #00b459;
  color: #fff;
}
.avatar {
  width: 1.2rem;
  height: 1.2rem;
  border-radius: 10%;
  object-fit: cover;
}
.name {
  font-family: Montserrat-Regular;
  font-size: 0.32rem;
  letter-spacing: 0;
  line-height: 0.32rem;
  margin-top: 0.2rem;
}
.tags {
  display: flex;
  .price {
    font-size: 0.26rem;
    font-family: Montserrat-Regular;
    color: #00b459;
    letter-spacing: 0;
    line-height: 0.32rem;
    margin: 0.2rem 0;
  }
  .step {
    margin: 0.3rem 0 0 0;
    display: flex;
    justify-content: flex-end;
    flex: 1;
  }
}
.delete-button {
  height: 100%;
  width: 2rem;
}
</style>
