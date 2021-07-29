<template>
  <div>
    <van-pull-refresh
      v-model="pullLoading"
      loading-text="Loading..."
      pulling-text="Pull to refresh..."
      loosing-text="Release to refresh..."
      @refresh="onPullRefresh"
      success-text="refresh succeeded"
    >
      <div class="order">
        <div class="order-item" v-for="(item,$index) in cardList" :key="$index">
          <OrderList :key="$index" :item="item" @click.native="showDetail(item)" :dataObj="item"></OrderList>
          <div class="order-button">
            <div class="button" @click="toEvaluation(item)">Review</div>
          </div>
        </div>
      </div>
    </van-pull-refresh>
  </div>
</template>

<script>
import OrderList from "@/components/order/orderList";
import { myPurchasedProduct, mySoldProduct } from "@/api/order";
export default {
  components: { OrderList },
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
  computed: {
    productType() {
      return this.$store.state.user.rolename;
    }
  },
  mounted() {
    this.getPeoductList(1);
  },
  methods: {
    async getPeoductList(flag) {
      try {
        let res = await myPurchasedProduct({
          username: this.$store.state.user.username
        });
        this.cardList = res.data;
      } catch (err) {
        console.log(err);
      } finally {
        this.listLoading = false;
        this.pullLoading = false;
        this.finishedList = true;
      }
    },
    onPullRefresh() {
      this.cardList = [];
      this.finishedList = false;
      this.getPeoductList();
    },
    showDetail(val) {
      this.$router.push({
        path: "/me/goods/consumer-detail",
        query: {
          param: encodeURIComponent(JSON.stringify(val))
        }
      });
    },
    toEvaluation(val) {
      this.$router.push({
        path: "/me/goods/evaluation",
        query: {
          param: encodeURIComponent(JSON.stringify(val.detail))
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.order {
  padding: 0 0.3rem;
  .order-item {
    margin: 0.3rem 0;
    background: #ffffff;
    box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
    border: 0.02rem solid #eeeff1;
  }
  .order-button {
    height: 0.72rem;
    margin: 0.32rem;
    .button {
      width: 2.02rem;
      height: 0.72rem;
      line-height: 0.72rem;
      font-family: Montserrat-Light;
      font-size: 0.28rem;
      color: #00b459;
      background: #ffffff;
      border: 0.02rem solid #00b459;
      border-radius: 0.44rem;
      letter-spacing: 0;
      text-align: center;
      vertical-align: middle;
      float: right;
    }
  }
}
</style>
