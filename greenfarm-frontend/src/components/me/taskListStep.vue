<template>
  <div>
    <van-step class="van">
      <div class="step">
        <div class="time">
          <h3>{{dataObj.beginTime.split("T")[0]}}</h3>
        </div>
        <div :class="plant_step">
          <div :class="www">{{dataObj.plantStep}}</div>
        </div>
      </div>
    </van-step>
  </div>
</template>

<script>
export default {
  data() {
    return {
      plant_step: "plant-step",
      www: "www bc-0"
    };
  },
  props: {
    dataObj: {
      default: () => {},
      type: Object
    }
  },
  mounted() {
    this.plantStep();
  },
  methods: {
    plantStep() {
      let temp = this.dataObj.stepId % 10;
      switch (temp) {
        case 0:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0 ppp";
          } else {
            this.www = "www bc-5 ppp";
          }
          break;
        case 1:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0";
          } else {
            this.www = "www bc-1";
          }
          break;
        case 2:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0 ppp";
          } else {
            this.www = "www bc-2 ppp";
          }
          break;
        case 3:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0";
          } else {
            this.www = "www bc-3";
          }
          break;
        case 4:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0 ppp";
          } else {
            this.www = "www bc-4 ppp";
          }
          break;
        case 5:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0";
          } else {
            this.www = "www bc-5";
          }
          break;
        case 6:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0 ppp";
          } else {
            this.www = "www bc-1 ppp";
          }
          break;
        case 7:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0";
          } else {
            this.www = "www bc-2";
          }
          break;
        case 8:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0 ppp";
          } else {
            this.www = "www bc-3 ppp";
          }
          break;
        case 9:
          if (this.dataObj.status == "0") {
            this.www = "www bc-0";
          } else {
            this.www = "www bc-4";
          }
          break;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.van {
  margin-top: 0.4rem;
}
.step {
  display: flex;
  padding-right: 1rem;
  height: 1rem;
  margin-top: -0.4rem;
  .time {
    flex: 1;
    color: #c0c3ca;
    letter-spacing: -0.01rem;
    padding: 0.1rem;
    h3 {
      font-family: Montserrat-Light;
      font-size: 0.24rem;
      height: 1.3rem;
      line-height: 1rem;
    }
  }
  .plant-step {
    flex: 2;
    border-radius: 8px;
    height: 1.2rem;
    vertical-align: middle;
    .www {
      height: 100%;
      line-height: 1.2rem;
      font-size: 0.32rem;
      padding-left: 0.3rem;
      box-shadow: 0 4px 16px 0 rgba(190, 190, 190, 0.44);
      border-radius: 8px;
      color: #ffffff;
    }
  }
}
.bc-0 {
  background-color: #e5e5e5;
}
.bc-1 {
  background-color: #ffb72b;
}
.bc-2 {
  background-color: #865fc1;
}
.bc-3 {
  background-color: #eb4985;
}
.bc-4 {
  background-color: #ff8737;
}
.bc-5 {
  background-color: #45c4f3;
}
.ppp {
  width: 80%;
}
</style>
