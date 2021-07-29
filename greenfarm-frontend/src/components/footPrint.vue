<template>
  <div class="item">
    <div class="left">
      <div class="img">
        <img :src="getImg(item.footprintName)" alt>
      </div>
      <div class="content">
        <div class="name">{{item.footprintName}}</div>
        <div class="time">{{timeHandle(item.createTime)}}</div>
      </div>
    </div>
    <div class="credits">
      <span v-if="item.footprintCategory == 'c'">-</span>
      <span v-if="item.footprintCategory == 'p'">+</span>
      <span>{{item.carbonCredit}}</span>
    </div>
  </div>
</template>
<script>
import { timeHandle } from "@/utils/index";
export default {
  name: "FootPrint",
  data() {
    return {};
  },
  props: {
    item: {
      type: Object,
      default: () => {}
    }
  },
  methods: {
    getImg(name) {
      if (name.includes("Seed")) {
        return require("@/assets/images/seeding-1.png");
      } else if (name.includes("Water")) {
        return require("@/assets/images/irrigation-1.png");
      } else if (name.includes("Fertilize")) {
        return require("@/assets/images/picking-vegetables-1.png");
      } else if (name.includes("Weed")) {
        return require("@/assets/images/weeding-1.png");
      } else if (name.includes("Harvest")) {
        return require("@/assets/images/harvest-6.png");
      } else if (name.includes("Add")) {
        return require("@/assets/images/farm-big-4.png");
      } else if (name.includes("Irrigate")) {
        return require("@/assets/images/irrigation-1.png");
      } else if (name.includes("Pest Control")) {
        return require("@/assets/images/pest-control.png");
      } else {
        return require("@/assets/images/farm-big-4.png");
      }
    },
    timeHandle(time) {
      return timeHandle(time);
    }
  }
};
</script>
<style lang="scss" scoped>
.item {
  margin: 0 -0.3rem;
  padding: 0.4rem 0.3rem 0.3rem;

  display: flex;
  justify-content: space-between;
  align-items: center;
  &:not(:last-child) {
    border-bottom: 1px solid #eeeff1;
  }
  .left {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .img {
      img {
        width: 0.72rem;
        height: 0.72rem;
        border-radius: 50%;
        object-fit: cover;
      }
    }
    .content {
      margin: 0 0.3rem;
      .name {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 0.28rem;
      }
      .time {
        margin-top: 0.14rem;
        font-family: Montserrat-Light;
        font-size: 0.24rem;
        color: #999999;
        letter-spacing: 0;
        line-height: 0.24rem;
      }
    }
  }
  .credits {
    span {
      font-family: Montserrat-Medium;
      font-size: 0.32rem;
      color: #00b459;
      letter-spacing: 0;
      line-height: 0.32rem;
    }
  }
}
</style>

