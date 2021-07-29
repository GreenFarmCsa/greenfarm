<template>
  <div class="taskList">
    <div class="title">
      <div class="left">{{location}}</div>
      <div class="right">
        <svg-icon iconClass="task-detail" style="font-size: 0.4rem;" @click="goOverview"></svg-icon>
      </div>
    </div>

    <div class="body">
      <div class="type">{{type}}</div>
      <div class="steps">
        <div class="dot">
          <img src="@/assets/images/taskList/dot-line-grey.png" alt="">
        </div>
        <div
          class="step-item"
          v-for="(item,index) in plantTaskSteps"
          :key="item.stepId"
          @click="goDetail(item,index)"
        >
          <div class="num">
            <img
              :src="require('@/assets/images/taskList/'+(index+1)+(item.status=='0'?'-gray.png':'-light.png'))"
              alt
            >
            <div class="border"></div>
          </div>
          <div class="credit">
            <img
              :src="require('@/assets/images/taskList/'+(item.carbonCredit)+(index>=active?'-gray.png':'-light.png'))"
              alt
            >
          </div>
          <div class="step-icon">
            <img
              :src="require('@/assets/images/taskList/'+(iconList[index])+
            (index==active?'-now.png':(index>active?'-gray.png':'-finish.png')))"
              alt
            >
          </div>
          <div class="info">
            <div class="name">{{item.plantStep}}</div>
            <div class="tip">Suggested time</div>
            <div class="time">
              <span class="time-str">{{getTime(item.beginTime)}}</span>
              <span class="min">To</span>
              <span class="time-str">{{getTime(item.endTime)}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { querySteps } from "@/api/greenplant";

export default {
  data() {
    return {
      location: "Planting Tips from FAO",
      type: "",
      active: 0,
      plantTaskSteps: [],
      iconList: [
        "seed",
        "irrigate",
        "fertilize",
        "pest control",
        "weed",
        "harvest",
        "sell"
      ]
    };
  },
  mounted() {
    this.querysteps();
  },
  methods: {
    getTime(time) {
      if (time) {
        return time.substring(0, 10);
      } else {
        return "";
      }
    },
    //status "0"have not started  "2"done
    querysteps() {
      this.type = this.$route.query.seedName;
      querySteps({
        id: this.$route.query.taskId ? this.$route.query.taskId : "1"
      })
        .then(res => {
          // res.data[6].plantStep = "Box";
          // res.data[6].status = "0"; 
          if (res.data[6].plantStep == "Box") {
            this.iconList[6] = "box";
          }
          this.plantTaskSteps = res.data;
          let i = 0;
          let j = 0;
          while (i < res.data.length) {
            if (res.data[i].status == 2) j++;
            i++;
          }
          this.active = j;
          this.active = j;
        })
        .catch(e => {
          console.log(e);
        });
    },
    goDetail(item, index) {
      item.farmId = this.$route.query.farmId;
      if (item.plantStep == "Sell") {
        if (index <= this.active) {
          if (item.status == "0") {
            this.$router.push({
              name: "goodsEdit",
              query: {
                taskId: this.$route.query.taskId,
                farmId: this.$route.query.farmId,
                landId: this.$route.query.landId,
                stepId: item.stepId
              }
            });
          } else {
            this.$toast({
              message: "This product has been registered."
            });
          }
        }
      } else if (item.plantStep == "Box") {
        if (index <= this.active) {
          if (item.status == "0") {
            this.$router.push({
              name: "goodsBox",
              query: {
                taskId: this.$route.query.taskId,
                farmId: this.$route.query.farmId,
                landId: this.$route.query.landId,
                stepId: item.stepId
              }
            });
          } else {
            this.$toast({
              message: "This product has been Boxed."
            });
          }
        }
      } else {
        if (index <= this.active) {
          this.$router.push({ name: "taskDetail", query: item });
        }
      }
    },
    goOverview() {
      this.$router.push({
        path: "/me/plant/overview",
        query: {
          seedId: this.$route.query.seedId
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.taskList {
  .title {
    background: #f4f5f7;
    padding: 0.36rem 0.3rem;
    font-size: 0.28rem;
    display: flex;
    align-items: center;
    .left {
      font-family: Montserrat-Regular;
      color: #666666;
    }
    .right {
      display: flex;
      justify-content: flex-end;
      flex: 1;
    }
  }
  .body {
    .type {
      padding: 0.4rem 0.38rem;
      font-family: Montserrat-Medium;
      font-size: 0.32rem;
      color: #333333;
    }
    .steps {
      padding-bottom: 0.3rem;
      position: relative;
      .dot {
        z-index: -1;
        position: absolute;
        left: 0.77rem;
        top: 0.7rem;
        img {
          height: 11rem;
        }
      }
      .step-item {
        padding: 0.3rem 0.4rem 0.3rem 0.54rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .num {
          width: 0.6rem;
          height: 0.6rem;
          img {
            width: 0.6rem;
            height: 0.6rem;
          }
        }
        .credit {
          margin-top: -0.16rem;
          img {
            width: 0.65rem;
          }
        }
        .step-icon {
          img {
            width: 1.2rem;
          }
        }
        .info {
          .name {
            font-family: Montserrat-Regular;
            font-size: 0.28rem;
            color: #333333;
            letter-spacing: 0;
          }
          .tip {
            margin: 0.16rem 0;
            font-family: Montserrat-Light;
            font-size: 0.24rem;
            color: #666666;
            letter-spacing: 0;
          }
          .time {
            span {
              font-family: Montserrat-Regular;
              font-size: 0.24rem;
              letter-spacing: 0;
            }
            .time-str {
              color: #67d29c;
            }
            .min {
              color: #666666;
              margin: 0 0.1rem;
            }
          }
        }
        // &:not(:last-child) .num .border {
        //   border-left: 0.08rem dotted #d4d4d4;
        //   height: 1.3rem;
        //   margin-left: 0.26rem;
        // }
      }
    }
  }
  .step {
    text-align: center;
    padding: 0.2rem 0.1rem;
  }
}
</style>
