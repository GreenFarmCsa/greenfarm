<template>
  <div class="goods-edit">
    <van-form validate-first @failed="onFailed" @submit="submit">
      <van-cell-group>
        <van-field
          :value="owner"
          readonly
          input-align="right"
          label="Owner"
          placeholder="Please enter"
        />
        <van-field
          :value="origin"
          readonly
          input-align="right"
          label="Origin"
          placeholder="Beijing"
        />
      </van-cell-group>
      <van-cell-group style="margin-top: 0.26rem;">
        <van-field
          v-model="productName"
          input-align="right"
          label="Name"
          placeholder="Enter plant name"
          required
          :rules="[{ required: true, message: 'please input' }]"
        />
        <van-field input-align="right" label="Type" placeholder="input">
          <template #input @click="chooseType=true">
            <van-dropdown-menu active-color="#00B459" style="padding-right:0.2rem;">
              <van-dropdown-item v-model="type" :options="typeOptions"/>
            </van-dropdown-menu>
          </template>
        </van-field>
        <van-field
          v-model="price"
          class="price"
          input-align="right"
          label="Price"
          type="number"
          placeholder="0"
          required
          :rules="[{ required: true, message: 'please input' }]"
        />
        <van-field
          v-model="weight"
          class="weight"
          input-align="right"
          label="Weight"
          type="number"
          placeholder="0"
          required
          :rules="[{ required: true, message: 'please input' }]"
        />
        <van-field
          v-model="quantity"
          input-align="right"
          label="Quantity"
          type="number"
          placeholder="Enter the number"
          required
          :rules="[{ required: true, message: 'please input' }]"
        />
        <van-field
          v-model="certification"
          input-align="right"
          label="Certification"
          placeholder="Enter award"
        />
        <van-field
          v-model="carbonEmission"
          class="weight"
          input-align="right"
          label="Carbon emissions"
          type="number"
          placeholder="0"
        />
        <van-field
          v-model="integral"
          input-align="right"
          label="Consumable integral"
          type="number"
          placeholder="0"
        />
        <van-field
          v-model="contributions"
          class="price"
          input-align="right"
          label="Contributions"
          type="number"
          placeholder="0"
        />
        <div class="introduction">
          <div class="title">Introduction</div>
          <van-field
            v-model="advice"
            autosize
            rows="5"
            type="textarea"
            clearable
            maxlength="100"
            show-word-limit
            placeholder="Please enter produce detail."
          />
        </div>
      </van-cell-group>
      <van-popup position="bottom" v-show="chooseType">
        <van-picker
          title="picker"
          show-toolbar
          :columns="['VEGETABLES','FRUIT','SEEDS','FERTILIZER']"
        />
      </van-popup>
      <div class="box">
        <div class="boxtitle">
          <span class="required">*</span>
          Upload Images or Videos
        </div>
        <div class="boxcontent">
          <div
            class="fileload"
            v-for="item in videoList"
            :key="item"
            @click="videoPopupShow=true;videoUrl=item.video;"
          >
            <img :src="imgurl(item.pic)">
            <div class="videoIcon">
              <svg-icon iconClass="begin" style="font-size: 0.4rem;"></svg-icon>
            </div>
          </div>
          <div class="fileload" v-for="(item, $index) in picList" :key="$index">
            <img :src="imgurl(item)" @click="viewImg($index)">
          </div>
          <div class="fileload" v-if="uploadLoading" style="display:flex;align-items:center;justify-content:center;">
            <van-loading />
          </div>
          <div class="fileload" @click="upload()" v-else>
            <svg-icon iconClass="camera-blue-add" style="font-size: 1.4rem;"></svg-icon>
          </div>
          <div class="fileload" v-for="i in 4" :key="i"></div>
        </div>
      </div>
      <div class="commitBtn" native-type="submit">
        <van-button round block type="info" native-type="submit">Upload Now</van-button>
      </div>
    </van-form>
    <div
      v-if="videoPopupShow"
      style="position:fixed;z-index:2000;top:0;left:0;right:0;bottom:0;background:#000"
    >
      <div
        style="width:100%;background:#fff;padding:0.2rem 0.3rem;font-size:0.4rem"
        @click="videoPopupShow=false"
      >
        <van-icon name="arrow-left" color="#333333" size="0.4rem" style="padding-left: 0.06rem;"/>
      </div>
      <div id="videoContainer" style="height:calc(100% - 0.9rem);position:relative;">
        <video
          style="width:100vw;height:100%;background-color:#000"
          :src="videourl(videoUrl)"
          controls="controls"
          autoplay
        ></video>
      </div>
    </div>
  </div>
</template>

<script>
import { ImagePreview } from "vant";
import { productAdd } from "@/api/mall";
import { queryFarmById } from "@/api/farm";
export default {
  components: { [ImagePreview.Component.name]: ImagePreview.Component },
  data() {
    return {
      chooseType: false,
      owner: this.$store.state.user.username,
      origin: "",
      videoList: [],
      picList: [],
      advice: "",
      certification: "",
      quantity: "",
      weight: "",
      type: "fruit",
      price: "",
      productName: "",
      carbonEmission: "",
      integral: "",
      contributions: "",
      typeOptions: [
        { text: "vegetables", value: "vegetables" },
        { text: "fruit", value: "fruit" },
        { text: "seeds", value: "seeds" },
        { text: "fertilizer", value: "fertilizer" }
      ],
      videoPopupShow: false,
      videoUrl: "",
      uploadLoading: false
    };
  },
  watch: {
    videoPopupShow(n, o) {
      if (n) {
        let scrollTop =
          document.body.scrollTop || document.documentElement.scrollTop;
        document.body.style.cssText +=
          "position:fixed;width:100%;top:-" + scrollTop + "px;";
      } else {
        let body = document.body;
        body.style.position = "";
        let top = body.style.top;
        document.body.scrollTop = document.documentElement.scrollTop = -parseInt(
          top
        );
        body.style.top = "";
      }
    }
  },
  mounted() {
    this.queryFarm();
  },
  methods: {
    queryFarm() {
      queryFarmById({ id: this.$route.query.farmId }).then(res => {
        if (res.data) {
          this.origin = res.data.location;
          this.owner = res.data.username;
        }
      }).catch(e => {
        console.log(e);
      });
    },
    submit() {
      if (this.picList.length == 0) {
        this.$toast({position:'bottom',message:'Please Upload Images'});
        return
      }
      let videoList = [];
      this.videoList.forEach(item => {
        videoList.push(item.pic + "," + item.video);
      });
      productAdd({
        taskId: this.$route.query.taskId,
        farmId: this.$route.query.farmId,
        stepId: this.$route.query.stepId,
        landId: this.$route.query.landId,
        carbonCredit: this.integral,
        carbonEmission: this.carbonEmission,
        category: this.type,
        createTime: "2021-06-30T07:22:14.973Z",
        donateAmount: this.contributions,
        identifications: this.certification,
        introduction: this.introduction,
        likeNumber: 0,
        modifyTime: "2021-06-30T07:22:14.973Z",
        number: this.quantity,
        price: this.price,
        productId: 0,
        productName: this.productName,
        remark: "string",
        saleNumber: 0,
        username: this.$store.state.user.username,
        imageUrl: this.picList,
        vedioUrl: videoList,
        weight: this.weight
      })
        .then(res => {
          this.$router.replace({ name: "goodsResult" });
        })
        .catch(e => {
          console.log(e);
        });
    },
    upload() {
      this.uploadLoading = true;
      let _this = this;
      window.UMJSBridge.callHandler(
        "jumpCamera",
        {
          type: 0,
          maxSelectable: 9,
          mimeType: 2,
          maxRecordTime: 30,
          capture: true
        },
        function(response) {}
      );
      window.UMJSBridge.registerHandler("jumpCamera", data => {
        this.uploadLoading = false;
        if (data) {
          if (data[0].type == "video") {
            if (data[0].success) {
              this.videoList.push({
                video: data[0].uFileId,
                pic: data[1].uFileId
              });
              this.but = "but-2";
              this.hidding = "hidding";
            } else {
              _this.$toast({ message: "upload failed", position: "bottom" });
            }
          } else if (data[0].type == "picture") {
            data.forEach(item => {
              if (item.success) {
                this.picList.push(item.uFileId);
                this.but = "but-2";
                this.hidding = "hidding";
              }
            });
          } else {
            _this.$toast({ message: "upload failed", position: "bottom" });
          }
        } else {
          // _this.$toast({ message: "upload failed", position: "bottom" });
        }
      });
    },
    imgurl(url) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    videourl(url) {
      return process.env.VUE_APP_BASE_API + "/file/play?url=" + url;
    },
    viewImg(val) {
      let showlist = [];
      this.picList.forEach(item => {
        showlist.push(
          process.env.VUE_APP_BASE_API + "/file/download?url=" + item
        );
      });
      ImagePreview({ images: showlist, startPosition: val });
    },
    onFailed(errorInfo) {
      console.log("failed", errorInfo);
    }
  }
};
</script>

<style lang="scss" scoped>
.goods-edit {
  padding-top: 0.26rem;
  background: #f6f6f6;
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
      .required {
        color: red;
      }
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

  .introduction {
    .title {
      padding: 0.2rem 0.32rem 0 0.32rem;
      font-size: 0.28rem;
      line-height: 0.48rem;
      font-family: Montserrat-Regular;
    }
    ::v-deep .van-cell__value.van-cell__value--alone.van-field__value {
      border: 0.02rem solid #dcdee0;
      border-radius: 0.08rem;
      padding: 0.24rem;
    }
  }
  .commitBtn {
    padding: 0 0.32rem 0.32rem 0.32rem;
    background: #ffffff;
    button {
      background: #00b459;
      border: 0;
      border-radius: 0.4rem;
      height: 0.8rem;
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
