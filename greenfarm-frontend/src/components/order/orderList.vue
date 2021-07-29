<template>
  <div>
    <div v-for="(item,$index) in dataObj.detail" :key="$index" :item="item">
      <orderTab :dataObj="item"/>
    </div>
    <div class="order-info">Total: ${{parseFloat(dataObj.money).toFixed(2)}}+{{dataObj.carbonCredit}}Credits</div>
  </div>
</template>

<script>
import { myPurchasedProduct, mySoldProduct } from "@/api/order";
import orderTab from "@/components/order/orderTab";

export default {
  components: { orderTab },
  data() {
    return {
      pullLoading: false,
      listLoading: false,
      finishedList: false,
      cardList: [],
      PurchasedList: [],
      SoldList: []
    };
  },
  props: {
    dataObj: {
      default: () => {},
      type: Object
    }
  },
  computed: {
    productType() {
      return this.$store.state.user.rolename;
    }
  },
  mounted() {},
  methods: {}
};
</script>

<style lang="scss" scoped>
.order {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  .order-item {
    .order-info {
      margin: 0.44rem 0.3rem 0.36rem 0.3rem;
      font-family: Montserrat-Regular;
      font-size: 0.32rem;
      color: #333333;
      letter-spacing: 0;
      text-align: right;
      line-height: 0.32rem;
    }
  }
}
</style>
