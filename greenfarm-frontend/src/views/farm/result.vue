<template>
  <div>
    <Header @onSearch="onSearch"></Header>
    <div class="homepage">
      <div class="recommend-wrapper">
        <div class="recommend-list">
          <RecommendFarm
            v-for="(item,index) in dataList"
            :key="index"
            :item="item"
            @click.native="goDetail(item)"
          ></RecommendFarm>
        </div>
      </div>
    </div>
  </div>
</template> 
<script>
import Header from "@/components/farm/FarmSearchHeader";
import RecommendFarm from "@/components/recommendFarm";
import { queryFarm } from "@/api/farm";
export default {
  name: "HomePage",
  components: { Header, RecommendFarm },
  data() {
    return {
      dataList: [],
      keyword: ""
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      this.keyword = this.$route.params.keyword;
      let params = {
        searchText: this.keyword
      };
      queryFarm(params).then(res => {
        this.dataList = res.data;
      });
    },
    goDetail(item) {
      this.$router.push({
        path: "/farm/detail/" + item.farmId
      });
    },
    onSearch(keyword) {
      this.$router.push({
        path: "/farm/result/" + keyword
      });
      this.getData();
    }
  }
};
</script>

<style lang="scss" scoped>
.homepage {
  background: #fdfdfd;
  padding: 1.14rem 0.3rem 0.3rem 0.3rem;
  .card {
    padding: 0.3rem;
    box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
  }
  .kind-card {
    margin: 0.15rem 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    height: 1.16rem;
    .tab {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      width: 33%;
      .img-wrapper {
        width: 0.48rem;
        height: 0.48rem;
        border: 0.02rem solid #dedede;
        border-radius: 1.06rem;
        border-radius: 0.16rem;
        box-sizing: border-box;
        img {
          width: 0.48rem;
        }
      }
      p {
        font-size: 0.28rem;
        margin-top: 0.14rem;
        font-weight: bold;
        font-family: "Montserrat-Regular";
      }
    }
  }
  .recommend-wrapper {
    .recommend-title {
      display: inline-block;
      margin: 0.23rem 0;
      line-height: 0.32rem;
      font-family: "Montserrat-Medium";
      font-size: 0.32rem;
      color: #333333;
      letter-spacing: 0;
      font-weight: bold;
    }
  }
  .footer {
    margin: 1rem 0;
  }
}
</style>




