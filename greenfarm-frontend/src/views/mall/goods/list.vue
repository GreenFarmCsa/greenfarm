<template>
  <div>
    <!--Search-->
    <Header></Header>
    <div class="listHome">
      <van-divider/>
      <!--classification-->
      <div class="tag-list">
        <TagListItem
          :dataObj="item"
          v-for="item in tagList"
          :key="item.value"
          @click.native="changeList(item)"
          :class="[activeName===item.value?'active':'']"
        ></TagListItem>
      </div>
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
            loading-text="Loading..."
            pulling-text="Pull to refresh..."
            loosing-text="Release to refresh..."
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
  </div>
</template>
<script>
import Header from "@/components/mall/GoodsHeader";
import GoodsList from "@/components/mall/GoodsList";
import TagListItem from "@/components/farm/TagListItem";
import { queryProductsByCategory } from "@/api/mall";
export default {
  name: "MallList",
  components: { Header, GoodsList, TagListItem },
  data() {
    return {
      activeName: "",
      loading: false,
      finished: false,
      refreshing: false,
      goodsList: [],
      tagList: [
        {
          value: "vegetables",
          name: "Vegetables"
        },
        {
          value: "fruit",
          name: "Fruit"
        },
        {
          value: "seeds",
          name: "Seed"
        },
        {
          value: "fertilizer",
          name: "Fertilizer"
        }
      ]
    };
  },
  mounted() {
    this.activeName = this.$route.params.id;
  },
  methods: {
    changeList(item) {
      this.activeName = item.value;
      this.getProductsList();
    },
    //category
    getProductsList() {
      if (this.refreshing) {
        this.goodsList = [];
        this.refreshing = false;
      }
      queryProductsByCategory({ category: this.activeName })
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
  margin: 0 0 0.2rem;
}
/deep/ .van-pull-refresh {
  overflow: visible;
}
.listHome {
  padding: 1.14rem 0 0 0;
}
.tag-list {
  padding: 0rem 0.3rem;
}
.goodsList {
  background: #fdfdfd;
  padding: 0rem 0.3rem;
  .card {
    padding: 0.3rem;
    margin: 0.3rem 0;
    box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
  }
}
</style>
