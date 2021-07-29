<template>
  <div>
    <div class="seed">
      <div class="seed-top">
        <div class="seed-top-left">
          <span>Seed</span>
        </div>
        <div class="seed-top-right">
          <button @click="toseed">select seeds</button>
        </div>
      </div>
      <div :class="chosed">
        <div class="seed-body">
          <div class="seed-body-left">
            <div class="seed-img">
              <img :src="normalSeed.seedImageUrl" alt>
            </div>
          </div>
          <div class="seed-body-right">
            <div class="seed-body-right-title">
              <div class="seed-body-right-title-left">
                <span>{{normalSeed.seedName}}</span>
                <button>x{{normalSeed.number}}</button>
              </div>
              <div class="seed-body-right-title-right">
                <span>${{parseFloat(normalSeed.seedPrice).toFixed(2)}}</span>
              </div>
            </div>
            <div class="seed-body-right-info">
              <div class="seed-body-right-info-left">Lowest planting:</div>
              <div class="seed-body-right-info-right">5m²</div>
            </div>
            <div class="seed-body-right-info">
              <div class="seed-body-right-info-left">production:</div>
              <div class="seed-body-right-info-right">{{normalSeed.production}}kg/m²</div>
            </div>
          </div>
        </div>
        <div class="price">
          <span>Payment ${{parseFloat(seedprice).toFixed(2)}}</span>
        </div>
        <div class="seed-footer">
          <div class="seed-footer-left">
            <div class="seed-footer-left-img">
              <svg-icon iconClass="ferterlizer-2" style="font-size: 0.38rem"></svg-icon>
            </div>
            <div class="seed-footer-left-title">Organic Fertilizer</div>
          </div>
          <div class="seed-footer-right">{{normalSeed.fertilizerName}}</div>
        </div>
      </div>
    </div>
    <van-popup v-model="seed" class="popup-seed" closeable :close="closePopup()">
      <div class="popup-title">Select Seeds</div>
      <div class="popup-body">
        <van-radio-group v-model="seedChose">
          <div v-for="(item,index) in seedList" :key="index">
            <van-radio
              :name="index"
              checked-color="#07c160"
              class="choes-seeds"
              @click="choseSeed(index)"
            >
              <div class="seed-body">
                <div class="seed-body-left">
                  <div class="seed-img">
                    <img :src="item.seedImageUrl" alt>
                  </div>
                </div>
                <div class="seed-body-right">
                  <div class="seed-body-right-title">
                    <div class="seed-body-right-title-left">
                      <span>{{item.seedName}}</span>
                    </div>
                    <div class="seed-body-right-title-right">
                      <span>${{parseFloat(item.seedPrice).toFixed(2)}}</span>
                    </div>
                  </div>
                  <div class="seed-body-right-info">
                    <div class="seed-body-right-info-left">Lowest planting:</div>
                    <div class="seed-body-right-info-right">5m²</div>
                  </div>
                  <div class="seed-body-right-info">
                    <div class="seed-body-right-info-left">production:</div>
                    <div class="seed-body-right-info-right">{{item.production}}kg/m²</div>
                  </div>
                </div>
              </div>
            </van-radio>
            <div class="price">
              <svg-icon iconClass="remove" style="font-size: 0.38rem" @click="remove(index)"></svg-icon>
              <span>{{item.number}}</span>
              <svg-icon iconClass="add" style="font-size: 0.38rem" @click="add(index)"></svg-icon>
            </div>
            <div class="seed-footer">
              <div class="seed-footer-left">
                <div class="seed-footer-left-img">
                  <svg-icon iconClass="ferterlizer-2" style="font-size: 0.38rem"></svg-icon>
                </div>
                <div class="seed-footer-left-title">Organic Fertilizer</div>
              </div>
              <div class="seed-footer-right">{{item.fertilizerName}}</div>
            </div>
          </div>
        </van-radio-group>
      </div>
    </van-popup>
  </div>
</template>
<script>
import { querySeed } from "@/api/farm";
export default {
  name: "SeedCard",
  data() {
    return {
      seed: false,
      chosed: "hidden",
      normalSeed: {},
      seedList: [],
      radio: 1,
      seedprice: 0,
      fertilizerprice: 0,
      totalprice: 0,
      seedChose: "s",
      topayData: {
        area: 0,
        confirmCrops: "",
        createTime: "",
        farmId: 0,
        landId: 0,
        modifyTime: "",
        remark: "string",
        rentEndTime: "",
        rentPrice: 0,
        rentStartTime: "",
        seedId: 0,
        username: ""
      }

    };
  },
  props: {
  },
  mounted() {
    this.getInitData()
  },
  methods: {
    getInitData() {
      querySeed({}).then(res => {
        this.seedList = res.data;
        for (let i = 0; i < this.seedList.length; i++) {
          this.$set(this.seedList[i], "number", 1);
          let id =
            this.seedList[i].seedId % 5 == 0
              ? 5
              : this.seedList[i].seedId % 5;
          this.$set(
            this.seedList[i],
            "seedImageUrl",
            require("@/assets/images/seed-" + id + ".png")
          );
        }
      })
      .catch(e => {
        console.log(e);
      });
    },
    toseed() {
      this.seed = true;
    },
    choseSeed(val) {
      this.normalSeed = this.seedList[val];
      this.chosed = "chosed";
      this.radio = val;
      this.seedprice = this.seedList[val].number * this.seedList[val].seedPrice;
      this.fertilizerprice = this.seedList[val].fertilizerPrice;
      this.topayData.seedId = this.seedList[val].seedId;
      this.$emit("seedComponentValue",{
        "seedprice": this.seedprice,
        "fertilizerprice": this.fertilizerprice,
        "seedId": this.topayData.seedId,
      })
    },
    closePopup() {
    },
    add(val) {
      this.seedList[val].number += 1;
      this.seedprice = this.seedList[val].number * this.seedList[val].seedPrice;
      this.$emit("seedComponentValue",{
        "seedprice": this.seedprice,
        "fertilizerprice": this.fertilizerprice,
        "seedId": this.topayData.seedId,
      })    
    },
    remove(val) {
      if (this.seedList[val].number > 1) {
        this.seedList[val].number -= 1;
      }
      this.seedprice = this.seedList[val].number * this.seedList[val].seedPrice;
      this.$emit("seedComponentValue",{
        "seedprice": this.seedprice,
        "fertilizerprice": this.fertilizerprice,
        "seedId": this.topayData.seedId,
      })
    },
  }
};
</script>
<style lang="scss" scoped>
.seed {
  width: 100%;
  padding: 0.24rem 0;
  height: 3.3rem;
  .seed-top {
    margin: auto;
    width: 90%;
    display: flex;
    line-height: 0.3rem;
    .seed-top-left {
      margin-top: 0.02rem;
      flex: 4;
      line-height: 0.5rem;
      span {
        font-family: Montserrat-SemiBold;
        font-size: 0.32rem;
        color: #333;
      }
    }
    .seed-top-right {
      margin-top: 0.02rem;
      flex: 4;
      line-height: 0.5rem;
      text-align: right;
      button {
        font-family: Montserrat-Light;
        color: #ffffff;
        letter-spacing: 0;
        text-align: center;
        border-radius: 0.25rem;
        border: 0;
        padding: 0 0.3rem;
        background-color: #00b459;
        font-size: 0.24rem;
        box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
      }
    }
  }
  .chosed {
    .seed-body {
      margin: 0.1rem auto;
      width: 90%;
      display: flex;
      line-height: 0.3rem;
      height: 1.5rem;
      display: flex;
      .seed-body-left {
        margin-top: 0.02rem;
        font-family: Montserrat-SemiBold;
        flex: 2;
        font-weight: 700;
        line-height: 0.5rem;
        font-size: 0.32rem;
        color: #333;
        .seed-img {
          height: 100%;
          width: 80%;
          img {
            border-radius: 0.16rem;
            width: 100%;
            height: 100%;
          }
        }
      }
      .seed-body-right {
        margin-top: 0.02rem;
        font-family: Montserrat-Regular;
        flex: 5;
        line-height: 0.5rem;
        color: #fff;
        text-align: right;
        .seed-body-right-title {
          text-align: left;
          margin-top: 0.02rem;
          line-height: 0.5rem;
          display: flex;
          .seed-body-right-title-left {
            text-align: left;
            line-height: 0.5rem;
            flex: 2;
            span {
              font-family: Montserrat-Medium;
              font-size: 0.28rem;
              color: #333333;
            }
            button {
              margin-left: 0.2rem;
              border: 0;
              background: rgba(247, 181, 0, 0.2);
              border-radius: 0.22rem;
              font-size: 0.24rem;
              color: #f7b500;
              height: 0.36rem;
              line-height: 0.24rem;
            }
          }
          .seed-body-right-title-right {
            line-height: 0.5rem;
            font-size: 0.28rem;
            text-align: right;
            flex: 1;
            span {
              font-size: 0.28rem;
              font-family: Montserrat-Regular;
              color: #00b459;
            }
          }
        }
        .seed-body-right-info {
          display: flex;
          line-height: 0.5rem;
          .seed-body-right-info-left {
            font-family: Montserrat-Light;
            color: #6c7b8a;
            font-size: 0.28rem;
            text-align: left;
            line-height: 0.5rem;
            flex: 2;
          }
          .seed-body-right-info-right {
            font-family: Montserrat-Regular;
            color: #00b459;
            line-height: 0.5rem;
            font-size: 0.28rem;
            font-weight: 600;
            flex: 1;
          }
        }
      }
    }
    .price {
      width: 90%;
      margin: auto;
      margin-top: 0.12rem;
      text-align: right;
      span {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #333333;
        line-height: 0.28rem;
      }
    }
    .seed-footer {
      margin: 0.1rem auto;
      width: 90%;
      display: flex;
      line-height: 0.5rem;
      height: 0.6rem;
      display: flex;
      .seed-footer-left {
        flex: 15;
        display: flex;
        .seed-footer-left-img {
          flex: 1;
          width: 0.4rem;
          height: 0.5rem;
          line-height: 0.6rem;
        }
        .seed-footer-left-title {
          font-family: Montserrat-Regular;
          font-weight: 600;
          font-size: 0.28rem;
          line-height: 0.5rem;
          flex: 8;
          margin-left: 0.1rem;
        }
      }
      .seed-footer-right {
        font-family: Montserrat-Light;
        flex: 9;
        font-size: 0.28rem;
        line-height: 0.5rem;
        color: #6c7b8a;
        text-align: right;
      }
    }
  }
  .hidden {
    display: none;
  }
}
.popup-seed {
  width: 90%;
  border-radius: 0.2rem;
  height: 9rem;
  padding-bottom: 0.2rem;
  .popup-title {
    text-align: center;
    height: 1.1rem;
    line-height: 1.1rem;
    font-size: 0.32rem;
    color: #333;
    font-family: Montserrat-Medium;
    font-weight: 550;
  }
  .popup-body {
    width: 90%;
    margin: auto;
    display: flex;
    .van-radio-group {
      width: 100%;
      .choes-seeds {
        .van-radio__label {
          width: 100% !important;
          .seed-body {
            margin: 0.1rem auto;
            width: 100%;
            display: flex;
            line-height: 0.3rem;
            height: 1.5rem;
            display: flex;
            .seed-body-left {
              margin-top: 0.02rem;
              font-family: Montserrat-SemiBold;
              flex: 6;
              font-weight: 700;
              line-height: 0.5rem;
              font-size: 0.32rem;
              color: #333;
              .seed-img {
                height: 100%;
                width: 80%;
                img {
                  width: 100%;
                  height: 100%;
                  border-radius: 0.2rem;
                  object-fit: cover;
                }
              }
            }
            .seed-body-right {
              margin-top: 0.02rem;
              font-family: Montserrat-Regular;
              flex: 12;
              line-height: 0.5rem;
              color: #fff;
              text-align: right;
              .seed-body-right-title {
                text-align: left;
                margin-top: 0.02rem;
                line-height: 0.5rem;
                display: flex;
                .seed-body-right-title-left {
                  text-align: left;
                  line-height: 0.5rem;
                  flex: 2;
                  span {
                    font-family: Montserrat-Medium;
                    font-size: 0.28rem;
                    color: #333333;
                  }
                }
                .seed-body-right-title-right {
                  line-height: 0.5rem;
                  font-size: 0.28rem;
                  text-align: right;
                  flex: 1;
                  span {
                    font-size: 0.28rem;
                    font-family: Montserrat-Regular;
                    color: #00b459;
                  }
                }
              }
              .seed-body-right-info {
                display: flex;
                line-height: 0.5rem;
                .seed-body-right-info-left {
                  color: #6c7b8a;
                  font-size: 0.28rem;
                  text-align: left;
                  line-height: 0.5rem;
                  flex: 2;
                }
                .seed-body-right-info-right {
                  color: #00b459;
                  line-height: 0.5rem;
                  font-size: 0.28rem;
                  font-weight: 600;
                  flex: 1;
                }
              }
            }
          }
        }
      }
      .price {
        width: 100%;
        margin: auto;
        text-align: right;
        height: 0.5rem;
        line-height: 0.6rem;
        span {
          font-size: 0.24rem;
          color: #333333;
          line-height: 0.6rem;
          margin: 0 0.2rem;
          font-family: Montserrat-Regular;
        }
      }
      .seed-footer {
        margin: 0.1rem auto;
        width: 100%;
        display: flex;
        line-height: 0.5rem;
        height: 0.6rem;
        display: flex;
        border-bottom: 0.01rem solid #eeeff1;
        .seed-footer-left {
          flex: 15;
          display: flex;
          .seed-footer-left-img {
            flex: 1;
            width: 0.4rem;
            height: 0.5rem;
            line-height: 0.6rem;
          }
          .seed-footer-left-title {
            font-family: Montserrat-Regular;
            font-weight: 600;
            font-size: 0.28rem;
            line-height: 0.5rem;
            flex: 8;
            margin-left: 0.1rem;
          }
        }
        .seed-footer-right {
          flex: 9;
          font-size: 0.28rem;
          line-height: 0.5rem;
          color: #6c7b8a;
          text-align: right;
        }
      }
    }
  }
}
</style>

