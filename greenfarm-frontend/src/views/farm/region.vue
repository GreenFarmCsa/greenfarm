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
import RecommendFarm from "@/components/recommendFarm";
import TagListItem from "@/components/farm/TagListItem";
import { queryFarmByLocation } from "@/api/farm";
export default {
  name: "HomePage",
  components: { Header, TagListItem, RecommendFarm },
  data() {
    return {
      dataList: [
        {
          createTime: "2021-06-21T02:09:39.917Z",
          farmId: 1546526312321,
          farmName: "Hot Spring Planting Center",
          iconUrl: "string",
          idleArea: 0,
          total_area: 100,
          imageUrl: "string",
          introduction:
            "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
          location: "Beijing,China",
          modifyTime: "2021-06-21T02:09:39.917Z",
          remark: "string",
          rentPeriod: "string",
          suitedCrops: "string",
          totalArea: 0,
          username: "string"
        },
        {
          createTime: "2021-06-21T02:09:39.917Z",
          farmId: 8565262,
          farmName: "Hot Spring Planting Center",
          iconUrl: "string",
          total_area: 200,
          idleArea: 0,
          imageUrl: "string",
          introduction:
            "The farm supports growing vegetables such as corn, potatoes, tomatoes, cucumbers and Onions.",
          location: "Beijing,China",
          modifyTime: "2021-06-21T02:09:39.917Z",
          remark: "string",
          rentPeriod: "string",
          suitedCrops: "string",
          totalArea: 0,
          username: "string"
        }
      ],
      tagList: [
        {
          value: "CN",
          name: "CN"
        },
        {
          value: "US",
          name: "US"
        },
        {
          value: "DE",
          name: "DE"
        },
        {
          value: "CA",
          name: "CA"
        },
        {
          value: "AUS",
          name: "AUS"
        }
      ],
      curTag: "CN"
    };
  },
  computed: {},
  mounted() {
    this.getData();
  },
  methods: {
    getData(keyword) {
      let params = {
        location: keyword || this.curTag
      };
      queryFarmByLocation(params).then(res => {
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
  padding: 1.14rem 0.3rem 0.3rem 0.3rem;
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




