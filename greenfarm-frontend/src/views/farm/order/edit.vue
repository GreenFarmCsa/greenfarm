<template>
  <div class="farmorderbody">
    <div class="split"/>
    <div class="header">
      <div>
        <div class="header-address">
          <div class="leftaddress">
            <span>Address</span>
          </div>
          <div class="rightaddress">
            <span>{{farm.location }}</span>
          </div>
        </div>
        <div v-if="pageType=='order'" class="header-address">
          <div class="leftperiod">
            <span>The lease period</span>
          </div>
          <div class="rightperiod">
            <span>365 days</span>
          </div>
        </div>
      </div>
    </div>
    <div class="split"/>
    <SeedCard @seedComponentValue="changeSeedValue($event)"/>
    <div class="split"/>
    <UserInformation :title="pageType=='order' ? 'Lessee Information' : 'Subscribe Information'"/>
    <div class="split"/>
    <PaymentMethod/>
    <div class="payment">
      <div class="payment-content">
        <div class="pay-total">
          Total:
          <span>${{parseFloat(totalprice).toFixed(2)}}</span>
        </div>
        <div class="pay-detail">
          <button @click="todetail">Detail</button>
        </div>
        <div class="pay-submit">
          <button @click="topay">Payment</button>
        </div>
      </div>
    </div>
    <div class="div"></div>
    <van-popup v-model="detail" class="popup-bottom" position="bottom">
      <div class="detail-body">
        <div class="detail-top">Particulars</div>
        <div class="detail-list">
          <div class="detail-list-left">Land lease cost</div>
          <div class="detail-list-right">$ {{parseFloat(landprice).toFixed(2)}}</div>
        </div>
        <div class="detail-list">
          <div class="detail-list-left">Seed costs</div>
          <div class="detail-list-right">$ {{parseFloat(seedprice).toFixed(2)}}</div>
        </div>
        <div class="detail-list">
          <div class="detail-list-left">Organic fertilizer cost</div>
          <div class="detail-list-right">$ {{parseFloat(fertilizerprice).toFixed(2)}}</div>
        </div>
        <div class="detail-list">
          <div class="detail-list-left">Statistical</div>
          <div class="detail-list-right">$ {{parseFloat(totalprice).toFixed(2)}}</div>
        </div>
      </div>
    </van-popup>
  </div>
</template>
<script>
import { queryFarmById, querySeed } from "@/api/farm";
import { addRent } from "@/api/lease";
import { queryLandByFarmId } from "@/api/land";
import PaymentMethod from "@/components/farm/PaymentMethod"
import UserInformation from "@/components/farm/UserInformation"
import SeedCard from "@/components/farm/SeedCard"
export default {
  name: "FarmOrderEdit",
  components: { PaymentMethod, UserInformation, SeedCard },
  data() {
    return {
      chosed: "hidden",
      number: 1,
      normalSeed: {},
      seedprice: 0,
      landprice: 0,
      fertilizerprice: 0,
      totalprice: 0,
      seedList: [],
      radio: 1,
      farm: {},
      baseAddress: "Hot Spring Center, Beijing",
      time: "365 days",
      seed: false,
      detail: false,
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
        username: "jack"
      },
      pageType: ""
    };
  },
  created() {
    this.getInitData();
    this.pageType = this.$route.query.type;
  },
  methods: {
    changeSeedValue(val) {
      this.seedprice = val.seedprice
      this.fertilizerprice = val.fertilizerprice
      this.totalprice = this.seedprice + this.fertilizerprice + this.landprice;
      this.topayData.seedId = val.seedId;
    },
    topay() {
      if (!this.topayData.seedId) {
        this.$toast({ message: "Please choes seed", position: "bottom" });
        return;
      }
      this.topayData.rentPrice = this.totalprice;
      this.topayData.remark = this.pageType=='order' ? 'string' : 'subscribe'
      addRent({ ...this.topayData }).then(res => {
        this.$router.push({ path: "/farm/order/result" });
        return;
      }).catch(e => {
        console.log(e);
      });
    },
    todetail() {
      this.detail = true;
    },
    getInitData() {
      this.farm.farmId = this.$route.query.farmId;
      queryFarmById({ id: 1 }).then(res => {
        this.farm = res.data;
      }).catch(e => {
        console.log(e);
      });
      let land = JSON.parse(this.$route.query.land);
      this.topayData.area = land.area;
      this.landprice = land.price ? land.price : 0;
      this.topayData.confirmCrops = land.suitedCrops;
      this.topayData.farmId = land.farmId;
      this.topayData.landId = land.landId;
      this.topayData.username = this.$store.state.user.username
        ? this.$store.state.user.username
        : "jack";
      this.totalprice = this.landprice;
    }
  }
};
</script>
<style lang="scss">
.farmorderbody {
  .popup-bottom {
    height: 5rem;
    border-radius: 0.2rem 0.2rem 0 0;
    .detail-body {
      width: 90%;
      margin: 0.2rem auto 0 auto;
      .detail-top {
        height: 1rem;
        font-family: Montserrat-SemiBold;
        font-size: 0.32rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 1rem;
        font-weight: 700;
      }
      .detail-list {
        display: flex;
        .detail-list-left {
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #7a7f83;
          text-align: left;
          line-height: 0.8rem;
          flex: 1;
        }
        .detail-list-right {
          text-align: right;
          flex: 1;
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #14ac69;
          letter-spacing: 0;
          line-height: 0.8rem;
        }
      }
    }
  }
  .div {
    width: 100%;
  }
  .header {
    width: 100%;
    background: white;
    padding: 0.2rem 0;
  }
  .header-address {
    margin: auto;
    width: 90%;
    display: flex;
    .leftaddress {
      flex: 2;
      margin-top: 0.02rem;
      line-height: 0.5rem;
      span {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
      }
    }
    .rightaddress {
      flex: 6;
      line-height: 0.5rem;
      text-align: right;
      span {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #14ac69;
      }
    }
    .leftperiod {
      flex: 1;
      margin-top: 0.02rem;
      line-height: 0.5rem;
      span {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666666;
      }
    }
    .rightperiod {
      flex: 1;
      line-height: 0.5rem;
      text-align: right;
      span {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #14ac69;
      }
    }
  }
  .payment {
    box-shadow: 0 0 0.2rem 0 rgba(0,0,0,0.08);
    width: 100%;
    background-color: #fff;
    .payment-content {
      width: 90%;
      height: 1.1rem;
      margin: 0 auto;
      display: flex;
      align-items: center;
      .pay-total {
        font-family: Montserrat-Light;
        font-size: 0.28rem;
        color: #666666;
        line-height: 1rem;
        span {
          color: #08c25e;
          font-family: Montserrat-Regular;
          font-size: 0.32rem;
        }
      }
      .pay-detail {
        padding-left: 0.32rem;
        button {
          font-family: Montserrat-Regular;
          border: 0;
          background: rgba(0,180,89,0.10);
          color: #00b459;
          font-size: 0.24rem;
          padding: 0.1rem 0.2rem;
          border-radius: 0.26rem;
        }
      }
      .pay-submit {
        // text-align: right;
        margin-left: auto;
        button {
          font-family: Montserrat-Regular;
          border-radius: 0.44rem;
          border: 0;
          background-color: #00b459;
          padding: 0.26rem 0.64rem;
          color: #FCFCFC;
          font-size: 0.28rem;
        }
      }
    }
  }
  .split {
    height: 0.26rem;
    background-color: #f6f6f6;
  }
}
</style>


