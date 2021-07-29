<template>
  <div>
    <div
      class="financial-products-list-item"
      :class="[dataObj.orgName=='Agriculture Bank of China'?'abc':dataObj.orgName=='CHASE Bank of China'?'chase':'financial']"
    >
      <div class="products-name">{{dataObj.financeProductName}}</div>
      <div class="card-no">{{dataObj.financeProductNo}}</div>
      <div class="limit">{{handleNum(dataObj.financeLimit)}}</div>
      <div class="expires">Expires&nbsp;{{dataObj.validPeriod}}</div>
    </div>
  </div>
</template>
<script>
export default {
  name: "FarmListItem",
  data() {
    return {};
  },
  props: {
    dataObj: {
      default: () => {},
      type: Object
    }
  },
  methods: {
    handleNum(value) {
      if(value == undefined || value == ""){
        return "$0.00" 
      }
      let arr = [];
      if (value.includes('$')) {
        arr = value.slice(1).split('.');
      } else {
        arr = value.split('.')
      }
      let digital = arr.length > 1 ? arr[1] : '';
      let num = arr[0];
      let count = 0;
      let result = '';
      for (let i = num.length - 1; i >= 0; i--) {
        count ++;
        result = num[i] + result;
        if(!(count % 3) && i != 0) {
          result = ',' + result;
        }
      }
      return "$" + result + (digital ? ( '.' + digital) : '');
    }
  }
};
</script>
<style lang="scss">
.financial-products-list {
  .financial-products-list-item {
    width: 7.28rem;
    height: 4.16rem;
    margin: 0 auto 0.32rem auto;
    padding: 0.64rem 0.48rem;
    box-sizing: border-box;
    border-radius: 0.16rem;
    &.financial {
      background: url("~@/assets/images/barclays-background.png") center;
      background-size: 100%;
    }
    &.abc {
      background: url("~@/assets/images/abc-background-1.png") center;
      background-size: 100%;
    }
    &.chase {
      background: url("~@/assets/images/chase-bank-background-1.png") center;
      background-size: 100%;
    }
    .products-name {
      height: 1rem;
      font-family: "Montserrat-Regular";
      font-size: 0.32rem;
      color: #ffffff;
      letter-spacing: 0;
      line-height: 0.4rem;
    }
    .card-no {
      margin: 0.1rem 0 0 0.34rem;
      font-family: "Montserrat-Regular";
      font-size: 0.4rem;
      color: #ffffff;
      letter-spacing: 0.04rem;
      line-height: 0.4rem;
    }
    .limit {
      float: right;
      right: 0.54rem;
      margin: 0.24rem 0.22rem 0 0;
      font-family: "Montserrat-Regular";
      font-size: 0.44rem;
      color: #ffffff;
      letter-spacing: 0;
    }
    .expires {
      clear: both;
      float: right;
      margin: 0.38rem 0.22rem 0 0;
      opacity: 0.73;
      font-family: "Montserrat-Regular";
      font-size: 0.24rem;
      color: #ffffff;
      letter-spacing: 0;
    }
    .logo {
      float: left;
      width: 0.5rem;
      height: 0.5rem;
      margin: 0.24rem 0;
      background: #fff;
    }
  }
}
</style>

