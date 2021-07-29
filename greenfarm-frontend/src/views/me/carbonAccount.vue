<template>
  <div>
    <div class="background"></div>
    <div class="part firstpart">
      <div class="title">My Produce</div>
      <div class="credit">
        <div class="carbon-credit">
          <div class="pic"/>
          <div class="font">{{carbonCredit}}</div>
        </div>
      </div>
    </div>
    <div class="part">
      <div class="title">My Carbon Behavior</div>
      <div class="behavior">
        <div class="verticalBar">
          <div class="line1"/>
          <div class="line2"/>
        </div>
        <div class="tabs">
          <van-tabs
            v-model="behaviorType"
            @click="clickTab"
            color="#00B459"
            title-active-color="#00B459"
          >
            <van-tab title="All"></van-tab>
            <van-tab title="Consume"></van-tab>
            <van-tab title="Plant"></van-tab>
          </van-tabs>
        </div>
        <div class="content content-border">
          <FootPrint v-for="(item,index) in carbonFootprintList" :key="index" :item="item"></FootPrint>
        </div>
      </div>
    </div>
    <div class="part">
      <div class="title">Account Authorization</div>
      <div class="info">
        “Green Farm” would like permission to share your carbon credit account data with institutions below, while we keep your data on our end using privacy-enhancing technology.
        Your data will be used to train the federated learning network and deliver personalized financial support to you.
      </div>
      <div class="content content-border">
        <van-list>
          <div class="authorization" v-for="(item, $index) in authorizationList" :key="$index">
            <div class="img">
              <img :src="item.imgUrl">
            </div>
            <div class="key">{{item.key}}</div>
            <div class="switch">
              <van-switch
                v-model="item.authorization"
                active-color="#00B459"
                inactive-color="#999999"
                size="0.45rem"
                @click="changeAuthorization"
              />
            </div>
          </div>
        </van-list>
      </div>
    </div>
  </div>
</template>

<script>
import { queryUserInfo } from "@/api/user";
import { carbonFootprint } from "@/api/dashboard";
import FootPrint from "@/components/footPrint";

export default {
  components: { FootPrint },
  data() {
    return {
      behaviorType: 0,
      carbonCredit: "",
      carbonFootprintList: [],
      carbonFootprintListAll: [],
      carbonFootprintListConsume: [],
      carbonFootprintListPlant: [],
      authorizationList: [
        {
          key: "Agricultural Bank of China",
          imgUrl: require("@/assets/images/abc-logo-2.png"),
          authorization: true
        },
        {
          key: "Citibank",
          imgUrl: require("@/assets/images/citibank-logo.png"),
          authorization: true
        },
        {
          key: "Standard Chartered Bank",
          imgUrl: require("@/assets/images/sc-bank-logo.png"),
          authorization: true
        }
      ]
    };
  },
  mounted() {
    this.queryUserInfo();
    this.getCarbonFootprint(); // carbon-footprint
  },
  methods: {
    async queryUserInfo() {
      try {
        const res = await queryUserInfo({
          username: this.$store.state.user.username
        });
        this.carbonCredit = res.data.carbonCredit;
      } catch (err) {
        console.log(err);
      } finally {
      }
    },

    async getCarbonFootprint() {
      const res = await carbonFootprint({
        username: this.$store.state.user.username
      });
      try {
        this.carbonFootprintList = res.data;
        this.carbonFootprintListAll = res.data;
        this.classificationFootprint(res.data);
      } catch (err) {
        console.log(err);
      } finally {
      }
    },
    //classification according to field footprintCategory  The key words are c(Consume) & p(Plant)
    classificationFootprint(list) {
      for (let i = 0; i < list.length; i++) {
        if (list[i].footprintCategory == "c") {
          this.carbonFootprintListConsume.push(list[i]);
        } else if (list[i].footprintCategory == "p") {
          this.carbonFootprintListPlant.push(list[i]);
        }
      }
    },
    clickTab(val) {
      this.behaviorType = val;
      let queryval = "";
      switch (val) {
        case 0:
          queryval = "All";
          this.carbonFootprintList = this.carbonFootprintListAll;
          break;
        case 1:
          queryval = "Consume";
          this.carbonFootprintList = this.carbonFootprintListConsume;
          break;
        case 2:
          queryval = "Plant";
          this.carbonFootprintList = this.carbonFootprintListPlant;
          break;
      }
    },
    changeAuthorization() {
      // this.$toast({message:"change authorization"})
    }
  }
};
</script>

<style lang="scss" scoped>
.background {
  width: 100%;
  height: 5rem;
  margin-top: -1.1rem;
  background-color: #ffffff;
  background-image: url("../../assets/images/carbon-account-background-2.png");
  background-repeat: no-repeat;
  background-size: 100% auto;
}
.firstpart {
  margin-top: -3.9rem;
}
.part {
  padding: 0.3rem;
  .title {
    font-size: 0.32rem;
    font-weight: 550;
    margin-bottom: 0.2rem;
    font-family: Montserrat-Medium;
  }
  .info {
    padding: 0 0.15rem 0.2rem 0.15rem;
    margin-top: 0.2rem;
    font-family: Montserrat-Light;
    font-size: 0.28rem;
    color: #666666;
    letter-spacing: 0;
    text-align: justify;
    line-height: 0.4rem;
  }
  .credit {
    .carbon-credit {
      height: 1.98rem;
      border-radius: 0.6rem 0.6rem 0 0;
      text-align: center;
      background-color: #ffffff;
      .pic {
        background-image: url("../../assets/images/bank-bakcground-1.png");
        background-repeat: no-repeat;
        background-size: 100% auto;
        height: 1.98rem;
        margin-top: 0;
      }
      .font {
        margin: 0 auto;
        margin-top: -1.64rem;
        color: #12a48b;
        font-family: Montserrat-Medium;
        font-size: 0.8rem;
      }
    }
  }
  .content-border {
    box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
    border: 0.02rem solid #eeeff1;
  }
  .content {
    padding: 0 0.3rem;
    .authorization {
      display: flex;
      align-items: center;
      margin: 0 -0.3rem;
      padding: 0.4rem 0.24rem;
      &:not(:last-child) {
        border-bottom: 0.02rem solid #eeeff1;
      }
      .img {
        padding-left: 0.04rem;
        img {
          width: 1.1rem;
          height: 0.8rem;
        }
      }
      .key {
        margin: 0 0.2rem;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 0.28rem;
      }
      .switch {
        display: flex;
        justify-content: flex-end;
        flex: 1;
      }
    }
  }
  .behavior {
    .verticalBar {
      .line1 {
        width: 0.02rem;
        height: 0.26rem;
        background: #e9e9e9;
        margin-left: 2.2rem;
      }
      .line2 {
        margin-top: -0.26rem;
        width: 0.02rem;
        height: 0.26rem;
        background: #e9e9e9;
        margin-left: 4.7rem;
      }
    }
    .tabs {
      margin-top: -0.26rem;
      padding-bottom: 0.3rem;
      background-color: rgba(0, 0, 0, 0);
      ::v-deep .van-tab {
        background-color: rgba(0, 0, 0, 0);
      }
      ::v-deep .van-tab__text {
        font-family: Montserrat-Medium;
        padding-bottom: 0.1rem;
      }
      ::v-deep .van-tabs__line {
        height: 0.04rem;
        width: 1.18rem;
      }
      ::v-deep .van-tabs--line {
        background-color: rgba(0, 0, 0, 0);
      }
      ::v-deep .van-tabs__wrap {
        background-color: rgba(0, 0, 0, 0);
      }
      ::v-deep .van-tabs__nav.van-tabs__nav--line {
        background-color: rgba(0, 0, 0, 0);
      }
    }
  }
}
.productCard {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  .img {
    img {
      width: 2rem;
      height: 2rem;
    }
  }
  .detail {
    .title {
      font-size: 0.32rem;
      font-weight: 550;
    }
    .description {
      margin-top: 0.1rem;
    }
    .tag {
      margin-top: 0.1rem;
      height: 0.3rem;
    }
  }
  .count {
    display: flex;
    justify-content: flex-end;
    flex: 1;
  }
}
</style>
