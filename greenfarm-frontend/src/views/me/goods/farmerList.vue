<template>
  <div>
    <div class="top">
      <div class="verticalBar">
        <div class="line1"/>
      </div>
      <div class="tabs">
        <van-tabs
          v-model="productType"
          color="#00B459"
          title-active-color="#00B459"
          @click="clickTab"
        >
          <van-tab title="Order"></van-tab>
          <van-tab title="Inventory"></van-tab>
        </van-tabs>
      </div>
    </div>
    <div class="card" v-if="productType=='0'">
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
            <OrderList :key="$index" @click.native="showDetail(item)" :dataObj="item"></OrderList>
          </div>
        </div>
      </van-pull-refresh>
    </div>
    <div v-else class="card" v-for="(item,$index) in inventoryList" :key="$index">
      <InventoryList :key="$index" :item="item"/>
    </div>
  </div>
</template>

<script>
import OrderList from "@/components/order/orderList";
import InventoryList from "@/components/order/inventoryList";
import { myPurchasedProduct, mySoldProduct, queryPlant } from "@/api/order";
export default {
  components: { OrderList, InventoryList },
  data() {
    return {
      pullLoading: false,
      listLoading: false,
      finishedList: false,
      productType: 0, // 0 Order  1 Inventory
      cardList: [],
      inventoryList: []
    };
  },
  mounted() {
    this.getPeoductList();
    this.queryPlant();
  },
  methods: {
    async getPeoductList() {
      try {
        let res = await mySoldProduct({
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

    async queryPlant() {
      try {
        let res = await queryPlant({
          username: this.$store.state.user.username
        });
        this.inventoryList = res.data;
      } catch (err) {
        console.log(err);
      } finally {
      }
    },

    clickTab(val) {
      this.productType = val;
      if (val == "0") {
        this.getPeoductList();
      } else {
        this.queryPlant();
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
    }
  }
};
</script>

<style lang="scss" scoped>
.top {
  .verticalBar {
    padding-top: 0.4rem;
    .line1 {
      width: 0.02rem;
      height: 0.26rem;
      background: #999999;
      margin-left: 3.6rem;
    }
  }
  .tabs {
    margin-top: -0.26rem;
    padding: 0 1rem;
    background-color: rgba(0, 0, 0, 0);
    ::v-deep .van-tab {
      height: 0.5rem;
      background-color: rgba(0, 0, 0, 0);
    }
    ::v-deep .van-tab__text {
      font-family: Montserrat-Regular;
      padding-bottom: 0.18rem;
    }
    ::v-deep .van-tabs__line {
      height: 0.02rem;
      width: 1.18rem;
    }
    ::v-deep .van-tabs--line {
      background-color: rgba(0, 0, 0, 0);
      height: 0.5rem;
    }
    ::v-deep .van-tabs__wrap {
      background-color: rgba(0, 0, 0, 0);
      height: 0.5rem;
    }
    ::v-deep .van-tabs__nav.van-tabs__nav--line {
      background-color: rgba(0, 0, 0, 0);
      height: 0.5rem;
    }
  }
}
.order {
  padding: 0 0.3rem;
  .order-item {
    margin: 0.3rem 0;
    background: #ffffff;
    box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
    border: 0.02rem solid #eeeff1;
  }
}
</style>
