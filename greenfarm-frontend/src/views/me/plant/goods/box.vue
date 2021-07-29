<template>
  <div class="goods-box">
    <van-form validate-first @failed="onFailed" @submit="submit">
      <van-cell-group>
        <van-field
          :value="address"
          readonly
          input-align="right"
          label="Home address"
          placeholder="Please enter"
          rows="1"
          autosize
          type="textarea"
        />
        <van-field
          :value="receiver"
          readonly
          input-align="right"
          label="Receiver"
          placeholder="Please enter"
        />
        <van-field
          :value="phone"
          readonly
          input-align="right"
          label="Phone"
          placeholder="Please enter"
        />
      </van-cell-group>
      <van-cell-group style="margin-top: 0.26rem;">
        <van-field
          :value="owner"
          readonly
          input-align="right"
          label="Owner"
          placeholder="Please enter"
        />
        <van-field
          clearable
          :value="origin"
          readonly
          input-align="right"
          label="Origin"
          placeholder="Please enter"
        />
        <van-field
          clearable
          v-model="productName"
          input-align="right"
          label="Produce Name"
          placeholder="Please enter"
        />
        <van-field input-align="right" label="Type" placeholder="input">
          <template #input @click="chooseType=true">
            <van-dropdown-menu active-color="#00B459" style="padding-right:0.2rem;">
              <van-dropdown-item v-model="type" :options="typeOptions"/>
            </van-dropdown-menu>
          </template>
        </van-field>
        <van-field
          v-model="weight"
          class="weight"
          input-align="right"
          label="Weight"
          type="number"
          placeholder="0"
          :rules="[{ required: true, message: 'please input' }]"
        />
      </van-cell-group>
      <van-popup position="bottom" v-show="chooseType">
        <van-picker
          title="picker"
          show-toolbar
          :columns="['VEGETABLES','FRUIT','SEEDS','FERTILIZER']"
        />
      </van-popup>

      <div class="commitBtn">
        <van-button
          :class="{'commitBtnActive': activeCommit}"
          round
          block
          type="info"
          native-type="submit"
        >Confirm</van-button>
        <!-- <van-button round block class="commitBtnD" >Confirm</van-button> -->
      </div>
    </van-form>
  </div>
</template>

<script>
import { productBox } from "@/api/mall";
import { querySubscriber } from "@/api/lease";
export default {
  data() {
    return {
      activeCommit: false,
      address: "address",
      receiver: "receiver",
      phone: "phone",
      chooseType: false,
      owner: this.$store.state.user.username,
      origin: "origin",

      productName: "",
      type: "fruit",
      weight: "",
      typeOptions: [
        { text: "vegetables", value: "vegetables" },
        { text: "fruit", value: "fruit" },
        { text: "seeds", value: "seeds" },
        { text: "fertilizer", value: "fertilizer" }
      ]
    };
  },
  watch: {
    productName(n, o) {
      if (!n) {
        this.activeCommit = false;
      } else {
        if (!o && this.weight) {
          this.activeCommit = true;
        }
      }
    },
    weight(n, o) {
      if (!n) {
        this.activeCommit = false;
      } else {
        if (!o && this.productName) {
          this.activeCommit = true;
        }
      }
    }
  },
  mounted() {
    this.querySubscriber();
  },
  methods: {
    querySubscriber() {
      querySubscriber({taskId: this.$route.query.taskId}).then(res => {
        res.data.location && (this.address = res.data.location);
        res.data.username && (this.receiver = res.data.username);
        res.data.phone && (this.phone = res.data.phone);
        res.data.farmOwner && (this.owner = res.data.farmOwner);
        res.data.farmLocation && (this.origin = res.data.farmLocation);
      }).catch(e => {
        console.log(e);
      })
    },
    submit() {
      console.log({
        category: this.type,
        farmId: this.$route.query.farmId,
        landId: this.$route.query.landId,
        productName: this.productName, 
        stepId: this.$route.query.stepId,
        taskId: this.$route.query.taskId,
        username: this.$store.state.user.username,
        weight: this.weight
      });

      productBox({
        category: this.type,
        farmId: this.$route.query.farmId,
        landId: this.$route.query.landId,
        productName: this.productName, //
        stepId: this.$route.query.stepId,
        taskId: this.$route.query.taskId,
        username: this.$store.state.user.username,
        weight: this.weight
      })
        .then(res => {
          this.$router.back();
        })
        .catch(e => {
          console.log(e);
        });
    },
    onFailed(errorInfo) {
      console.log("failed", errorInfo);
    }
  }
};
</script>

<style lang="scss" scoped>
.goods-box {
  padding-top: 0.26rem;
  background: #f6f6f6;
  ::v-deep.van-cell__value.van-field__value .van-field__body textarea {
    text-align: justify;
  }
  ::v-deep.van-cell::after {
    bottom: 1px;
  }
  ::v-deep.van-cell__title.van-field__label {
    width: 3rem;
    span {
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #333333;
      letter-spacing: 0;
      line-height: 0.28rem;
    }
  }
  ::v-deep.van-field__control.van-field__control--right {
    font-family: Montserrat-Regular;
    color: #00b459;
  }
  ::v-deep.van-field__error-message {
    text-align: right;
    font-size: 0.24rem;
  }
  ::v-deep.van-cell::after {
    border-bottom: 0.01rem solid #dcdee0;
  }
  ::v-deep.van-dropdown-menu__bar {
    box-shadow: 0 0 0 rgba(100, 101, 102, 0);
    height: 0.48rem;
  }
  ::v-deep.van-ellipsis {
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    color: #00b459;
    letter-spacing: 0;
  }
  ::v-deep.van-cell__title {
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    letter-spacing: 0;
  }
  .weight {
    ::v-deep.van-field__value {
      ::after {
        content: "KG";
        color: #00b459;
        padding-left: 0.1rem;
      }
    }
  }
  .price {
    ::v-deep.van-field__value {
      ::after {
        content: "$";
        color: #00b459;
        padding-left: 0.1rem;
      }
    }
  }
  .box {
    background-color: #fff;
    margin-top: 0.26rem;
    padding: 0.4rem;
    .boxtitle {
      font-family: Montserrat-Medium;
      font-size: 0.32rem;
      color: #333333;
      letter-spacing: 0;
      line-height: 0.32rem;
      margin-bottom: 0.4rem;
    }
    .boxcontent {
      display: flex;
      align-content: center;
      justify-content: space-between;
      flex-wrap: wrap;
      width: 100%;
      height: auto;
      .fileload {
        position: relative;
        width: 1.4rem;
        img {
          margin-bottom: 0.1rem;
          width: 1.4rem;
          height: 1.4rem;
          border-radius: 0.08rem;
          object-fit: cover;
        }
        .videoIcon {
          position: absolute;
          left: 50%;
          top: 50%;
          transform: translate(-50%, -65%);
        }
      }
    }
    .box-top {
      display: flex;
      .boxtitle {
        flex: 6;
        padding-bottom: 0.35rem;
        font-family: Montserrat-Medium;
        font-size: 0.32rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 0.72rem;
        font-weight: 550;
      }
    }
  }

  .commitBtn {
    margin-top: 0.26rem;
    padding: 0.48rem 0.32rem 0.32rem 0.32rem;
    background: #ffffff;
    button {
      background: #b4b6b5;
      border: 0;
      border-radius: 0.4rem;
      height: 0.8rem;
    }
    .commitBtnActive {
      background: #00b459;
    }
    ::v-deep .van-button__text {
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #fcfcfc;
      text-align: center;
    }
  }
}
</style>
