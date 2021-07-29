<template>
  <div>
    <div class="card plant-card" v-for="(key,index) in plantTaskMap.keys()" :key="index">
      <div class="head">
        <div class="img">
          <img
            :src="imgurl(plantTaskMap.get(key)[0].iconUrl,require('@/assets/images/carbon-credits-1.png'))"
            alt
          >
        </div>
        <div class="title">{{plantTaskMap.get(key)[0].farmName}}</div>
      </div>
      <div
        class="item"
        v-for="(item,index1) in plantTaskMap.get(key)"
        :key="index1+'a'"
        @click="goTaskList(item)"
      >
        <div class="title">
          <div class="text">{{item.seedName?item.seedName:'Seed'}}</div>
          <div class="step">{{item.status?item.status:'Status'}}</div>
        </div>
        <div class="progress">
          <!-- <div class="progress-number">{{item.percentage}}</div> -->
          <div class="progress-number">{{Math.ceil(parseFloat(item.percentage))}}%</div>
          <div class="progress-container">
            <div class="done" :style="'width:' + item.percentage"></div>
            <div class="point" :style="'left:' + item.percentage">
              <svg-icon iconClass="progressbar-carrot" style="font-size: 0.44rem;"></svg-icon>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { plantTask } from "@/api/dashboard";
export default {
  name: "PlantTask",
  data() {
    return {
      plantTaskMap: new Map()
    };
  },
  mounted() {
    this.getPlantTask();
  },
  props: {},
  methods: {
    getNum(num) {

    },
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    //taskId to taskList
    goTaskList(item) {
      this.$router.push({
        path: "/me/plant/task-list/",
        query: {
          taskId: item.taskId,
          seedName: item.seedName,
          farmId: item.farmId,
          landId: item.landId,
          seedId: item.seedId
        }
      });
    },
    //input:username: 'jack'   output:plantTask
    getPlantTask() {
      plantTask({ username: this.$store.state.user.username })
        .then(res => {
          let data = res.data;
          let map = new Map();
          data.forEach((item, index) => {
            if (item.farmId) {
              if (map.get(item.farmId) == undefined) {
                let a = [];
                a.push(item);
                map.set(item.farmId, a);
              } else {
                let a = map.get(item.farmId);
                a.push(item);
                map.set(item.farmId, a);
              }
            }
          });
          this.plantTaskMap = map;
        })
        .catch(e => {
          console.log(e);
          // this.$toast({
          //   message: "get plantTask failed!",
          //   position: "bottom"
          // });
        });
    }
  }
};
</script>
<style lang="scss" scoped>
@import "@/styles/commonC.scss";
.plant-card {
  padding-left: 0.48rem;
  padding-right: 0.48rem;
  .head {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .img {
      margin-right: 0.4rem;
      img {
        width: 0.92rem;
        height: 0.92rem;
        border-radius: 0.2rem;
        object-fit: cover;
      }
    }
    .title {
      font-family: Montserrat-Medium;
      font-size: 0.32rem;
      letter-spacing: -0.006rem;
      line-height: 0.32rem;
    }
  }
  .title {
    font-family: Montserrat-Medium;
    font-size: 0.32rem;
    letter-spacing: -0.006rem;
    line-height: 0.32rem;
  }

  .item {
    padding: 0.4rem 0 0.2rem;
    .title {
      display: flex;
      justify-content: space-between;
      align-items: center;
      .text {
        font-size: 0.28rem;
        font-family: Montserrat-Medium;
        letter-spacing: 0;
        line-height: 0.28rem;
      }
      .step {
        font-family: Montserrat-Regular;
        letter-spacing: 0;
        line-height: 0.4rem;
        height: 0.4rem;
        font-size: 0.22rem;
        color: #ff3030;
        background: #ffe5e5;
        padding: 0 0.2rem;
        border-radius: 0.04rem;
      }
    }
    .progress {
      margin: 0.3rem 0;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .progress-number {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        letter-spacing: 0;
        line-height: 0.28rem;
        width: 0.85rem;
      }
      .progress-container {
        position: relative;
        width: 68vw;
        height: 0.2rem;
        background: #e6e6e6;
        border-radius: 0.1rem;
        .done {
          position: absolute;
          left: 0;
          top: 0;
          height: 0.2rem;
          background: linear-gradient(113deg, #06e36c 0%, #76e8ea 89%);
          border-radius: 0.1rem 0 0 0.1rem;
        }
        .point {
          position: absolute;
          bottom: 0;
          width: 0.5rem;
          height: 0.5rem;
          transform: translate(-30%, 7%);
        }
      }
    }
  }
}
</style>

