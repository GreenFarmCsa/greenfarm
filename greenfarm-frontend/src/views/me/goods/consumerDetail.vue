<template>
  <div>
    <div class="card">
      <div class="product-card">
        <div v-for="(item,$index) in order.detail" :key="$index">
          <orderTab :dataObj="item"></orderTab>
        </div>
      </div>
      <div class="money-card">
        <div class="tab">
          <div class="key">Price</div>
          <div class="value">$ {{parseFloat(order.money).toFixed(2)}}</div>
        </div>
        <div class="tab">
          <div class="key">Carbon credit</div>
          <div class="value">{{order.carbonCredit}} Credits</div>
        </div>
      </div>
      <div class="total-card">Total: ${{parseFloat(order.money).toFixed(2)}}+{{order.carbonCredit}}Credits</div>
    </div>
    <div class="card">
      <div class="addr-card">
        <div class="top">
          <div class="icon">
            <svg-icon iconClass="location-mark" style="font-size: 0.32rem;"></svg-icon>
          </div>
          <div class="block">
            <div class="addr">{{order.address}}</div>
            <div class="info">{{order.username}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {{order.phone}}</div>
          </div>
        </div>
        <div class="banner">
          <img :src="require('@/assets/images/mall-banner.png')">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import orderTab from "@/components/order/orderTab";

export default {
  components: { orderTab },
  data() {
    return {
      order: []
    };
  },
  mounted() {
    this.order = JSON.parse(decodeURIComponent(this.$route.query.param));
  },
  methods: {}
};
</script>

<style lang="scss" scoped>
.card {
  margin: 0 0 0.26rem 0;
  box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
}
.product-card {
  padding: 0.08rem 0 0.34rem 0;
  border-bottom: 0.02rem solid #eeeff1;
}
.money-card {
  padding: 0.3rem 0.3rem 0 0.3rem;
  border-bottom: 0.02rem solid #eeeff1;
  .tab {
    display: flex;
    margin-bottom: 0.32rem;
    .key {
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.28rem;
    }
    .value {
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #00b459;
      letter-spacing: 0;
      text-align: right;
      line-height: 0.28rem;
      margin-left: auto;
    }
  }
}
.total-card {
  padding: 0.3rem;
  font-family: Montserrat-Regular;
  font-size: 0.32rem;
  color: #333333;
  letter-spacing: 0;
  text-align: right;
  line-height: 0.32rem;
}
.addr-card {
  .top {
    display: flex;
    padding: 0.3rem;
    .block {
      margin-left: 0.2rem;
      .addr {
        font-family: Montserrat-Medium;
        font-size: 0.28rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 0.32rem;
      }
      .info {
        font-family: Montserrat-Light;
        font-size: 0.28rem;
        color: #666666;
        line-height: 0.28rem;
        padding-top: 0.16rem;
      }
    }
  }
  .banner {
    height: 0.2rem;
    img {
      width: 100%;
    }
  }
}
</style>
