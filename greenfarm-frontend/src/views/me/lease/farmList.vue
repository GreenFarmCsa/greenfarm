<template>
  <div class="mylease">
    <div
      @click="viewFarmDetail(item)"
      v-for="(item,index) in leaseFarmsJackList"
      :key="index"
      :item="item"
    >
      <RecommendFarm :item="item"></RecommendFarm>
    </div>
  </div>
</template>

<script>
import RecommendFarm from "@/components/recommendFarm";
import { leaseFarmsJack } from "@/api/lease";
import { type } from "os";
import { request } from "http";
export default {
  components: { RecommendFarm },
  data() {
    return {
      leaseFarmsJackList: []
    };
  },
  mounted() {
    this.getleaseFarms();
  },
  methods: {
    getleaseFarms() {
      leaseFarmsJack({ username: this.$store.state.user.username })
        .then(res => {
          this.leaseFarmsJackList = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    viewFarmDetail(item) {
      this.$router.push({
        path: "farmShow",
        query: { leaseFarmsJackList: item }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/styles/commonC.scss";
.mylease {
  background: #fdfdfd;
  padding: 0.1rem 0.3rem;
  color: #333;
  font-family: Montserrat-Medium;
}
</style>
