<template>
  <div class="pay">
    <div class="pay-top">Method Of Payment</div>
    <div class="pay-body">
      <van-radio-group v-model="radio">
        <div class="pay-list" v-for="(item,index) in payList" :key="index">
          <div class="pay-body-img">
            <img :src="item.url" alt>
          </div>
          <div class="pay-body-title">{{item.title}}</div>
          <div class="pay-body-chose">
            <van-radio :name="item.title" checked-color="#07c160" @click="chosePayway(item.title)"></van-radio>
          </div>
        </div>
      </van-radio-group>
    </div>
  </div>
</template>
<script>
export default {
  name: "PaymentList",
  data() {
    return {
      radio: "Visa",
      payList: [
        {
          url: require("@/assets/images/visa.png"),
          title: "Visa"
        },
        {
          url: require("@/assets/images/master.png"),
          title: "Master"
        },
        {
          url: require("@/assets/images/abc-logo.png"),
          title: "Green Credit Card"
        }
      ]
    };
  },
  methods: {
    chosePayway(val) {
      if (val == "Green Credit Card") {
        this.$store.dispatch("cartGoods/setGreenCreditCard", true);
      } else {
        this.$store.dispatch("cartGoods/setGreenCreditCard", false);
      }
    }
  },
  mounted() {
    this.$store.dispatch("cartGoods/setGreenCreditCard", false);
  }
};
</script>
<style lang="scss" scoped>
.pay {
  width: 100%;
  margin-top: 0.2rem;
  background: white;
  padding-top: 0.2rem;
  .pay-top {
    width: 90%;
    margin: 0.1rem auto;
    text-align: left;
    margin-top: 0.02rem;
    font-family: Montserrat-Medium;
    font-weight: 550;
    line-height: 0.5rem;
    font-size: 0.3rem;
    color: #333;
  }

  .pay-body {
    width: 90%;
    margin: 0.1rem auto;
    display: flex;
    .van-radio-group {
      width: 100%;
      .pay-list {
        display: flex;
        width: 100%;
        line-height: 0.6rem;
        &:not(:first-child) {
          margin-top: 0.28rem;
        }
        .pay-body-img {
          margin-top: 0.05rem;
          line-height: 0.6rem;
          width: 0.5rem;
          height: 0.5rem;
          flex: 1;
          img {
            width: 0.5rem;
          }
        }
        .pay-body-title {
          line-height: 0.6rem;
          font-weight: 500;
          font-size: 0.28rem;
          color: #666;
          flex: 8;
        }
        .pay-body-chose {
          line-height: 0.6rem;
          text-align: right;
          flex: 1;
        }
      }
    }
  }
}
</style>
