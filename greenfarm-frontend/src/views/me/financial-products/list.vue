<template>
  <div>
    <div class="background"></div>
    <div class="financial-products-list">
      <FinancialProductsListItem
        :dataObj="item"
        v-for="item in dataList"
        :key="item.name"
        @click.native="goDetail(item)"
      ></FinancialProductsListItem>
    </div>
  </div>
</template>

<script>
import FinancialProductsListItem from "@/components/financialProducts/FinancialProductsListItem";
import { queryFinanceProductWithSignByUserName } from "@/api/financeProduct";
export default {
  name: "HomePage",
  components: { FinancialProductsListItem },
  data() {
    return {
      dataList: []
    };
  },
  computed: {},
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      let params = {
        username: this.$store.state.user.username || "jack"
      };
      queryFinanceProductWithSignByUserName(params).then(res => {
        this.dataList = res.data;
      });
    },
    goDetail(item) {
      this.$router.push({
        path:
          "/me/financial-products/detail/" +
          item.financeProductId +
          "/" +
          item.signed
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.background {
  width: 100%;
  height: 2rem;
  margin-top: -0.5rem;
  background-color: #ffffff;
  background-image: url("~@/assets/images/financial-products-background-1.png");
  background-repeat: no-repeat;
  background-size: 100% auto;
}
</style>
