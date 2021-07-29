<template>
  <div class="dashboard">
    <div class="user">
      <div class="left">
        <div class="name">{{$store.state.user.username}}</div>
        <div class="text">{{greetings()}}</div>
      </div>
      <div class="right">
        <!-- <img class="avatar" :src="imgurl($store.state.user.iconUrl)" alt> -->
        <van-image  
          round
          width="0.84rem"
          height="0.84rem" 
          :src="imgurl($store.state.user.iconUrl)"
        >
          <template v-slot:loading>
            <van-loading type="spinner" size="20" />
          </template>
        </van-image>
      </div>
    </div>
    <div class="block">
      <div class="block-title">
        <svg-icon iconClass="My plant" style="font-size: 0.4rem;"></svg-icon>
        <span>My Plant</span>
      </div>
      <PlantTask></PlantTask>
    </div>
    <div class="block">
      <div class="block-title">
        <svg-icon iconClass="carbon_credits" style="font-size: 0.4rem"></svg-icon>
        <span>My Carbon Credit</span>
      </div>
      <div class="card chart-card">
        <div class="chart" id="chart" style="width:100%;height:3rem"></div>
      </div>
      <div class="card footprint-card">
        <div class="title">Carbon Footprint Detail</div>
        <FootPrint v-for="(item,index) in carbonFootprintListShow" :key="index" :item="item"></FootPrint>
        <div class="arrowDown" v-if="ifFoldFootprint" @click="showMoreFootprint"><van-icon name="arrow-down" /></div>
      </div>
    </div>
    <div class="block">
      <div class="block-title">
        <svg-icon iconClass="recommend farm" style="font-size: 0.4rem"></svg-icon>
        <span>Recommended Farm</span>
      </div>
      <RecommendFarm
        v-for="(item,index) in recommendFarmsList"
        :key="index"
        :item="item"
        @click.native="$router.push('/farm/detail/' + item.farmId)"
      ></RecommendFarm>
    </div>
    <div class="block">
      <div class="block-title">
        <svg-icon iconClass="recommend goods" style="font-size: 0.4rem"></svg-icon>
        <span>Recommended Produce</span>
      </div>
      <RecommendGoods
        v-for="(item,index) in recommendProductsList"
        :key="index"
        :item="item"
        @click.native="$router.push('/mall/goods/detail/' + item.productId)"
      ></RecommendGoods>
    </div>
  </div>
</template>

<script>
import {
  echartsFootprint,
  carbonFootprint,
  recommendFarms,
  recommendProducts
} from "@/api/dashboard";
import { type } from "os";
import { request } from "http";
import FootPrint from "@/components/footPrint";
import RecommendFarm from "@/components/recommendFarm";
import RecommendGoods from "@/components/recommendGoods";
import PlantTask from "@/components/plantTask";
import { mkdir } from "fs";
export default {
  components: { FootPrint, RecommendFarm, RecommendGoods, PlantTask },
  data() {
    return {
      carbonFootprintList: [],
      carbonFootprintListShow: [],
      recommendFarmsList: [],
      recommendProductsList: [],
      echartsDate: ["06-21", "06-22", "06-23", "06-24", "06-25"],
      echartsActual: ["201", "105", "168", "300", "100"],
      echartsTarget: ["305", "405", "300", "389", "350"],
      ifFoldFootprint: true,
    };
  },
  mounted() {
    this.getEchartsFootprint();
    this.getCarbonFootprint();
    this.getRecommendFarms();
    this.getRecommendProducts();
  },
  methods: {
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    greetings() {
      let date = new Date();
      let hours = date.getHours();
      if (hours < 12) {
        return "Good Morning!";
      } else if (hours >= 12 && hours < 18) {
        return "Good Afternoon!";
      } else {
        return "Good Evening!";
      }
    },
    getEchartsFootprint() {
      echartsFootprint({ username: this.$store.state.user.username })
        .then(res => {
            this.echartsDate = [];
            this.echartsActual = [];
            this.echartsTarget = [];
            res.data.targetFootprint.forEach(item => {
              this.echartsDate.push(item.date.substring(5, 10));
              this.echartsTarget.push(item.value);
            });
            res.data.actualFootprint.forEach(item => {
              this.echartsActual.push(item.value);
            });
            this.getEchart();
        })
        .catch(e => {
          console.log(e);
        });
    },
    getCarbonFootprint() {
      carbonFootprint({ username: this.$store.state.user.username })
        .then(res => {
          this.carbonFootprintList = res.data;
          if( res.data.length <= 5 ){
            this.carbonFootprintListShow = this.carbonFootprintList
            this.ifFoldFootprint = false
          }else{
            this.carbonFootprintListShow = this.carbonFootprintList.slice(0,5)
            this.ifFoldFootprint = true
          }
        })
        .catch(e => {
          console.log(e);
        });
    },
    showMoreFootprint(){
      this.carbonFootprintListShow = this.carbonFootprintList
      this.ifFoldFootprint = false
    },
    getRecommendFarms() {
      recommendFarms({ username: this.$store.state.user.username })
        .then(res => {
          this.recommendFarmsList = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    getRecommendProducts() {
      recommendProducts({ username: this.$store.state.user.username })
        .then(res => {
          this.recommendProductsList = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    getEchart() {
      let myChart = this.$echarts.init(document.getElementById("chart"));
      let data = {
        time: this.echartsDate,
        actual: this.echartsActual,
        target: this.echartsTarget
      };
      let option = {
        legend: {
          top: "0",
          left: "0",
          itemWidth: 10,
          itemHeight: 10,
          data: [
            { icon: "circle", name: "actual", color: "blue" },
            { icon: "circle", name: "target" }
          ],
          textStyle: {
            color: "#999"
          }
        },
        grid: {
          top: "25%",
          left: "2%",
          right: "6%",
          bottom: "0%",
          containLabel: true
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLine: {
              show: false,
              lineStyle: {
                color: "#092b5d"
              }
            },
            axisLabel: {
              // interval: this.echartsDate.length > 9 ? 2 : 0,
              interval: parseInt(this.echartsDate.length / 10),
              rotate: 45,
              textStyle: {
                fontFamily: "Montserrat-Light",
                color: "#A0A8B0",
                fontSize: "12px"
              },
              formatter: data => {
                return data;
              }
            },
            axisTick: {
              show: false
            },
            data: data.time
          }
        ],
        yAxis: [
          {
            splitNumber: 1,
            splitLine: {
              show: true,
              lineStyle: {
                color: "#D0D3D5",
                opacity: 0.2
              }
            },
            axisLine: {
              show: false,
              lineStyle: {
                color: "#092b5d"
              }
            },
            axisLabel: {
              show: true,
              textStyle: {
                fontFamily: "PingFangSC-Regular",
                color: "#BDCADB",
                fontSize: "12px"
              }
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            color: "#12E7BD",
            name: "actual",
            type: "line",
            symbol: "none",
            showAllSymbol: false,
            symbolSize: 10,
            lineStyle: {
              normal: {
                color: "#12E7BD"
              }
            },
            itemStyle: {},
            markPoint: {
              show: false,
              symbol:
                // "image://" + require("@/assets/images/echarts-marker.svg"),
                "image://" + require("@/assets/images/echarts-marker-mid.svg"),
              itemStyle: {
                color: {}
              },
              symbolSize: [40, 32],
              // symbolOffset: [20, -25],
              symbolOffset: [0, -20],
              data: [{ type: "max", name: "max" }],
              label: {
                offset: [0, -3],
                color: "#ffffff",
                fontFamily: "Montserrat-Regular",
                fontSize: "0.24rem"
              }
            },
            label: {
              normal: {
                show: false,
                position: "top",
                formatter: [" {a|{c}}"].join(","),
                rich: {
                  a: {
                    color: "#333",
                    align: "center"
                  }
                }
              }
            },
            smooth: true,
            areaStyle: {
              normal: {
                color: new this.$echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "#12E7BD4D"
                    },
                    {
                      offset: 1,
                      color: "#12E7BD00"
                    }
                  ],
                  false
                )
              }
            },
            data: data.actual
          },
          {
            color: "#FFCA60",
            name: "target",
            type: "line",
            symbol: "none",
            showAllSymbol: false,
            symbolSize: 10,
            lineStyle: {
              normal: {
                color: "#FFC450"
              }
            },
            itemStyle: {},
            label: {
              normal: {
                show: false,
                position: "top",
                formatter: [" {a|{c}}"].join(","),
                rich: {
                  a: {
                    color: "#333",
                    align: "center"
                  }
                }
              }
            },
            smooth: true,
            data: data.target
          }
        ]
      };
      myChart.setOption(option, true);
      window.addEventListener("resize", () => {
        myChart.resize();
      });
    }
  }
};
</script>

<style lang="scss" scoped>
@import "@/styles/commonC.scss";
.dashboard {
  background: #fdfdfd;
  padding: 0.4rem 0.3rem;
  padding-top: 1.3rem !important;
  color: #333;
  font-family: Montserrat-Medium;
  .user {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 0.4rem;
    .left {
      .name {
        font-size: 0.52rem;
        font-weight: bold;
        margin-bottom: 0.3rem;
        font-family: Montserrat-SemiBold;
      }
      .text {
        font-size: 0.32rem;
        font-weight: 100;
        font-family: Montserrat-Regular;
      }
    }
    .right {
      .avatar {
        width: 0.84rem;
        height: 0.84rem;
        border-radius: 50%;
      }
    }
  }
  .block {
    .block-title {
      margin: 0.5rem 0;
      span {
        margin-left: 0.16rem;
        font-size: 0.32rem;
        font-weight: bold;
        font-family: Montserrat-Medium;
      }
    }
    .footprint-card {
      .title {
        font-family: Montserrat-Regular;
        font-size: 0.32rem;
        color: #140f26;
        letter-spacing: 0;
        line-height: 0.32rem;
      }
      .arrowDown {
        display: flex;
        padding-top: 0.2rem;
        .van-icon {
          margin: 0 auto;
        }
      }
    }
  }
}
</style>
