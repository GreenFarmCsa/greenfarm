<template>
  <div>
    <div class="financial-products-list">
      <FinancialProductsListItem :dataObj="dataObj"></FinancialProductsListItem>
    </div>
    <div class="introduction-wrapper">
      <div class="title">Introduction</div>
      <div class="content">{{dataObj.financeProductDesc}}</div>
    </div>
    <div class="button-wrapper">
      <van-button type="primary" round @click="onSign" v-if="!signed">Sign</van-button>
      <van-button type="primary" round @click="onBreak" class="break-btn" v-else>Break</van-button>
    </div>
  </div>
</template>

<script>
import FinancialProductsListItem from "@/components/financialProducts/FinancialProductsListItem";
import {
  queryFinanceProductById,
  applyFinanceProduct,
  breakFinanceProduct
} from "@/api/financeProduct";
export default {
  name: "HomePage",
  components: { FinancialProductsListItem },
  data() {
    return {
      dataObj: {},
      signed: false
    };
  },
  mounted() {
    this.getData();
    this.signed = this.$route.query.signed == "false" ? false : true;
  },
  methods: {
    getData() {
      let params = {
        id: this.$route.params.id
      };
      queryFinanceProductById(params).then(res => {
        this.dataObj = res.data;
      });
    },
    onBreak() {
      let params = {
        username: this.$store.state.user.username,
        financeProductId: this.$route.params.id
      };
      breakFinanceProduct(params).then(res => {
        this.signed = !this.signed;
        this.$toast({ position: "bottom", message: "success!" });
      }).catch(e => {
        console.log(e);
      });
    },
    onSign() {
      let params = {
        username: this.$store.state.user.username,
        financeProductId: this.$route.params.id
      };
      applyFinanceProduct(params).then(res => {
        this.signed = !this.signed;
        this.$toast({ position: "bottom", message: "success!" });
      }).catch(e => {
        console.log(e);
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.introduction-wrapper {
  padding: 0 0.44rem;
  .title {
    font-family: "Montserrat-Medium";
    font-size: 0.3rem;
    color: #333333;
    letter-spacing: 0;
    line-height: 0.3rem;
  }
  .content {
    font-family: "Montserrat-Light";
    font-size: 0.28rem;
    color: #666666;
    letter-spacing: 0;
    text-align: justify;
    line-height: 0.4rem;
  }
}
.button-wrapper {
  margin: 0.6rem 0.44rem;
}
</style>
<style lang="scss">
.button-wrapper {
  .van-button {
    width: 100%;
    height: 0.8rem;
  }
  .break-btn.van-button {
    color: #ff9f00;
    background-color: rgba(255, 159, 0, 0.21);
    border: 0.02rem solid #ff9f00;
  }
}
</style>
