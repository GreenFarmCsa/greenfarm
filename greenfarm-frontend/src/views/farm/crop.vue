<template>
  <div>
    <Header @onSearch="onSearch" ref="header"></Header>
    <div class="homepage">
      <div class="tag-list">
        <TagListItem
          :dataObj="item"
          v-for="item in tagList"
          :key="item.value"
          @click.native="changeClassification(item)"
          :class="[curTag===item.name?'active':'']"
        ></TagListItem>
      </div>
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
import TagListItem from "@/components/farm/TagListItem";
import RecommendFarm from "@/components/recommendFarm";
import { queryFarmByProduct } from "@/api/farm";
export default {
  name: "HomePage",
  components: { Header, TagListItem, RecommendFarm },
  data() {
    return {
      dataList: [],
      tagList: [
        {
          value: "Corn",
          name: "Corn"
        },
        {
          value: "Tomato",
          name: "Tomato"
        },
        {
          value: "Onion",
          name: "Onion"
        },
        {
          value: "Spinach",
          name: "Spinach"
        },
        {
          value: "Cucumber",
          name: "Cucumber"
        }
      ],
      curTag: "Corn"
    };
  },
  computed: {},
  mounted() {
    this.getData();
  },
  methods: {
    getData(keyword) {
      let params = {
        productName: keyword || this.curTag
      };
      queryFarmByProduct(params).then(res => {
        this.dataList = res.data;
      });
    },
    goDetail(item) {
      this.$router.push({
        path: "/farm/detail/" + item.farmId
      });
    },
    changeClassification(item) {
      this.curTag = item.name;
      this.getData();
      this.$refs.header.keyword = "";
    },
    onSearch(keyword) {
      this.getData(keyword);
      this.curTag = "";
    }
  }
};
</script>

<style lang="scss" scoped>
.homepage {
  background: #fdfdfd;
  padding: 1.28rem 0.3rem 0.3rem 0.3rem;
  .card {
    padding: 0.3rem;
    box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
  }
  .footer {
    margin: 1rem 0;
  }
}
</style>




