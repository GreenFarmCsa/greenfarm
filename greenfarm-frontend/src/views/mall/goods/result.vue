<template>
  <div>
    <!--Search-->
    <Header></Header>
    <van-divider/>
    <!--show-->
    <div class="goodsList">
      <van-pull-refresh
        class="pullrefresh"
        loading-text="Loading..."
        pulling-text="Pull to refresh..."
        loosing-text="Release to refresh..."
        v-model="refreshing"
        @refresh="onRefresh"
      >
        <van-list
          v-model="loading"
          :finished="finished"
          finished-text="End"
          @load="getProductsList"
        >
          <GoodsList
            @click.native="$router.push('/mall/goods/detail/' + item.productId)"
            v-for="(item,index) in goodsList"
            :key="index"
            :item="item"
          ></GoodsList>
        </van-list>
      </van-pull-refresh>
    </div>
  </div>
</template>
<script>
import Header from "@/components/mall/GoodsHeader";
import GoodsList from "@/components/mall/GoodsList";
import { queryProducts, queryProductByFarmId } from "@/api/mall";
export default {
  name: "ResultList",
  components: { Header, GoodsList },
  data() {
    return {
      loading: false,
      finished: false,
      refreshing: false,
      goodsList: [],
      productName: "",
      farmId: ""
    };
  },
  mounted() {
    this.productName = this.$route.query.productName;
    this.farmId = this.$route.query.farmId;
  },
  methods: {
    //query
    getProductsList() {
      if (this.refreshing) {
        this.goodsList = [];
        this.refreshing = false;
      }
      if (this.farmId == undefined || this.farmId == "") {
        queryProducts({ searchText: this.productName })
          .then(res => {
            this.goodsList = res.data;
            this.loading = false;
            this.finished = true;
          })
          .catch(e => {
            this.goodsList = [];
            this.loading = false;
            this.finished = true;
            console.log(e);
          });
      } else {
        queryProductByFarmId({ farmId: this.farmId })
          .then(res => {
            this.goodsList = res.data;
            this.loading = false;
            this.finished = true;
          })
          .catch(e => {
            this.goodsList = [];
            this.loading = false;
            this.finished = true;
            console.log(e);
          });
      }
    },
    //refresh
    onRefresh() {
      this.finished = false;
      this.loading = true;
      this.getProductsList();
    }
  }
};
</script>
<style lang="less" scoped>
/deep/ .van-divider {
  margin: 0;
}
/deep/ .van-tabs {
  .van-tab {
    font-size: 0.2rem;
    font-family: Montserrat-Regular;
  }
}
.goodsList {
  background: #fdfdfd;
  padding: 1.14rem 0.3rem 0 0.3rem;
  overflow: true;
  .card {
    padding: 0.3rem;
    margin: 0.3rem 0;
    box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
  }
  .pullrefresh {
    overflow: true;
  }
}
</style>
