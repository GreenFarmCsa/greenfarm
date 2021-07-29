<template>
  <div class="pay">
    <div class="pay-top">
      <span>Method Of Payment</span>
    </div>
    <div class="pay-body">
      <van-radio-group v-model="radio">
        <div class="pay-list" v-for="(item,index) in payList" :key="index">
          <div class="pay-body-img">
            <img :src="item.url" alt>
          </div>
          <div class="pay-body-title">{{item.title}}</div>
          <div class="pay-body-chose">
            <van-radio :name="item.title" checked-color="#07c160" icon-size="0.36rem" @click="chosePayway(item.title)"></van-radio>
          </div>
        </div>
      </van-radio-group>
    </div>
  </div>
</template>
<script>
export default {
  name: "PaymentMethod",
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
  props: {},
  methods: {
    chosePayway(val) {}
  }
};
</script>
<style lang="scss" scoped>
.pay {
  width: 100%;
  padding: 0.24rem 0;
  .pay-top {
    width: 90%;
    margin: 0.1rem auto;
    text-align: left;
    margin-top: 0.02rem;
    span {
      font-family: Montserrat-Medium;
      font-size: 0.32rem;
      color: #333;
    }
    line-height: 0.5rem;
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
          .van-radio{
            justify-content: center;
            margin: 0.1rem 0;
          }
          ::v-deep .van-radio__icon .van-icon {
            border: 0.01rem solid #08C25E
          }
        }
      }
    }
  }
}
</style>

