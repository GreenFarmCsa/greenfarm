<template>
  <div>
    <div class="checkout-submit-box">
      <div class="price">
        <span class="label">Price:</span>
        <span class="num">${{parseFloat(price).toFixed(2)}} </span>
      </div>
      <div class="btn" @click="onCheck">
        Check Out
      </div>
    </div>
    <van-popup v-model="show" class="credit-card-wrapper" closeable>
      <CreditCard @confirm="commitOrder()"></CreditCard>
    </van-popup>
  </div>
</template>
<script>
import CreditCard from "@/components/mall/CreditCard";
import { mapState } from "vuex";
import { removeCart, createOrder, queryCart } from "@/api/mall";
export default {
  name: "CheckoutBar",
  components: { CreditCard },
  data() {
    return {
      show: false
    };
  },
  methods: {
    //clear
    clearCart() {
      return new Promise(resolve => {
        let result = true;
        this.productIdList.forEach(item => {
          let clearVO = {
            username: this.username,
            productId: item
          };
          removeCart(clearVO)
            .then(res => {
            })
            .catch(e => {
              result = false;
              console.log(e);
            });
        });
        resolve(result);
      });
    },
    //commit
    async commitOrder() {
      let usPrice = (this.price).toFixed(2);
      let OrderVO = {
        address: this.address,
        carbonCredit: this.carbonCredit,
        username: this.username,
        money: usPrice,
        detail: []
      };
      if (this.productIdList.length > 0) {
        // cart
        try {
          let resCart = await queryCart({ username: this.username });
          let cartList = resCart.data;
          cartList.forEach(item => {
            if (this.productIdList.indexOf(item.productId) > -1) {
              let singleObj = {
                count: item.number,
                imageUrl: item.imageUrl,
                productId: item.productId,
                productName: item.productName
              };
              OrderVO.detail.push(singleObj);
            }
          });
        } catch (err) {
          console.log(err);
        }
      } else {
        //buy it now
        let singleObj = {
          count: this.quickGoods.quickNum,
          imageUrl: this.quickGoods.imageUrl,
          productId: this.quickGoods.productId,
          productName: this.quickGoods.productName
        };
        OrderVO.detail.push(singleObj);
      }
      try {
        let resOrder = await createOrder(OrderVO);
        let resCart = await this.clearCart();
        if (resCart) {
          this.$router.push({ name: "orderResult" });
        }
      } catch (err) {
        console.log(err);
      }
    },
    onCheck() {
      //add order
      if (this.isGCC) {
        this.show = true;
      } else {
        this.commitOrder();
      }
    }
  },
  computed: {
    ...mapState({
      price: state => state.cartGoods.totalPrice,
      isGCC: state => state.cartGoods.isChooseGCC,
      username: state => state.user.username,
      address: state => state.user.location,
      productIdList: state => state.cartGoods.goodsList,
      carbonCredit: state => state.cartGoods.carbonCredit,
      quickGoods: state => state.cartGoods.quickGoods
    })
  },
  mounted() {}
};
</script>
<style lang="less" scoped>
.checkout-submit-box {
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
  .price {
    .label {
      font-family: Montserrat-Light;
      font-size: 0.28rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.28rem;
    }
    .num {
      margin-left: 0.1rem;
      font-family: Montserrat-Regular;
      font-size: 0.32rem;
      color: #08C25E;
      letter-spacing: 0;
      line-height: 0.32rem;
    }
  }
  .btn {
    background: #00B459;
    border-radius: 0.44rem;
    height: 0.7rem;
    width: 2.28rem;
    text-align: center;
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    color: #FCFCFC;
    letter-spacing: 0;
    line-height: 0.7rem;
  }
}
.text {
  font-family: Montserrat-Regular;
  font-size: 0.2rem;
  color: #050505;
}
.credit-card-wrapper {
  width: calc(100% - 0.72rem);
  height: calc(100% - 4.2rem);
  border-radius: 0.32rem;
  ::v-deep .van-popup__close-icon {
    color: #333333;
  }
}
</style>
