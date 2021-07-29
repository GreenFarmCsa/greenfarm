<template>
  <div class="farmaddbody">
    <div class="split"/>
    <div class="body">
      <div class="body-content">
        <div class="form-title">
          <span></span>
        </div>
        <div class="form-body">
          <div class="farmLogo">
            <Label :title="farmName.farmName" :must="farmName.must"/>
            <div class="location">
              <PopupPicker 
                :columns="farmList"
                valueKey="farmName"
                realValueKey="communityId"
                showValueKey="communityName"
                @onConfirm="onConfirm($event)"
                backVal="value"/>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="topicName.farmName" :must="topicName.must"/>
            <div class="farmCrops">
              <FormInput
                :error="topicName.error"
                :placeholder="topicName.placeholder"
                :inputval="formData.topicName"
                v-model="formData.topicName"
                class="formInput"
              />
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="topicContent.farmName" :must="topicContent.must"/>
            <div class="farmLogos">
              <textarea
                name
                id
                rows="5"
                :placeholder="topicContent.placeholder"
                v-model="formData.topicContent"
                @blur="onblur"
              ></textarea>
              <div :class="errors">{{topicContent.error}}</div>
            </div>
          </div>

          <div class="farmLogo">
            <Label :title="topicLogo.farmName" :must="topicLogo.must"/>
            <div class="logo" v-if="uploadLoading">
              <van-loading />
            </div>
            <div class="logo" v-else>
              <div :class="imglogoclass" @click="uploadimg">
                <svg-icon iconClass="camera-blue-add" style="font-size: 1.4rem"></svg-icon>
              </div>
              <div :class="imglogohid" @click="uploadimg">
                <img :src="getImg(formData.topicImageUrl)" alt>
              </div>
              <div :class="imgclass"></div>
            </div>
          </div>
          <div class="bottom-button">
            <div class="confirm" @click="confirm()">
              <button :class="[confirmOK?'ok':'']">Post</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
import PopupPicker from "@/components/PopupPicker";
import FormInput from "@/components/FormInput";
import Label from "@/components/Label";
import { addFarm, queryFarmByUserName } from "@/api/farm";
import { addTopic } from "@/api/topic";
import {
  queryCommunityByFarmId,
  queryCommunityByUserName
} from "@/api/community";
import { Toast } from "vant";
export default {
  components: { FormInput, Label, PopupPicker },
  data() {
    return {
      showPicker: false,
      imgclass: "logo-right",
      imglogoclass: "logo-left",
      imglogohid: "hidden",
      showCommunityId: "",
      formData: {
        communityId: "",
        topicName: "",
        topicContent: "",
        topicImageUrl: ""
      },
      farmName: {
        farmName: "Please select a farm",
        error: "Name of the farm is not allowed null",
        placeholder: "Please select the…",
        must: "must"
      },
      topicName: {
        farmName: "Title",
        error: "Title of the community is not allowed null",
        placeholder: "Leave a title here…",
        must: "must"
      },
      topicLogo: {
        farmName: "Select a picture",
        error: "picture of the community is not allowed null",
        placeholder: "The image size is limited to 800K.",
        must: "must"
      },
      topicContent: {
        farmName: "Content",
        error: "Content of the community is not allowed null",
        placeholder: "Leave a content here…",
        must: "must"
      },
      farmList: [],
      errors: "hidden",
      uploadLoading: false,
    };
  },
  mounted() {
    this.getData();
  },
  computed: {
    confirmOK() {
      return (
        this.formData.farmId !== "" &&
        this.formData.topicImageUrl !== "" &&
        this.formData.topicName !== "" &&
        this.formData.topicContent !== ""
      );
    }
  },
  methods: {
    onConfirm(value) {
      this.formData.communityId = value;
    },
    getData() {
      let params = {
        name: this.$store.state.user.username || "jack"
      };
      queryCommunityByUserName(params).then(res => {
        this.farmList = res.data;
      });
    },
    confirm() {
      if (!this.confirmOK) {
        return;
      }
      let params = {
        username: this.$store.state.user.username || "fred",
        communityId: this.formData.communityId,
        topicName: this.formData.topicName,
        topicContent: this.formData.topicContent,
        topicImageUrl: this.formData.topicImageUrl
      };
      addTopic(params).then(res => {
        if (res.data && res.data.topicId) {
          this.$router.replace({
            path: "/community/post-detail/" + res.data.topicId
          });
          Toast({
            message: "success",
            icon: "passed"
          });
        }
      })
      .catch(e => {
        console.log(error,e)
      });
    },
    getImg(val) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + val;
    },
    uploadimg() {
      this.upload();
      if (this.formData.topicImageUrl) {
        this.imgclass = "hidden";
        this.imglogoclass = "hidden";
        this.imglogohid = "imgs";
      }
    },
    upload() {
      // this.uploadLoading = true;
      window.UMJSBridge.callHandler(
        "jumpCamera",
        {
          type: 0,
          maxSelectable: 1,
          mimeType: 0,
          maxRecordTime: 30,
          capture: true
        },
        function(response) {}
      );
      window.UMJSBridge.registerHandler("jumpCamera", data => {
        this.uploadLoading = false;
        if (data) {
          if (data[0].success && data[0].type == "picture") {
            this.formData.topicImageUrl = data[0].uFileId;
            this.formData = Object.assign({}, this.formData);
            if (this.formData.topicImageUrl) {
              this.imgclass = "hidden";
              this.imglogoclass = "hidden";
              this.imglogohid = "imgs";
            }
          } else {
            this.$toast({ message: "upload error", position: "bottom" });
          }
        }
      });
    },
    onblur() {
      if (!this.formData.topicContent) {
        this.errors = "error";
      } else {
        this.errors = "hidden";
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.farmaddbody {
  .divs {
    height: 1.4rem;
    width: 100%;
  }
  width: 100%;
  background: #f6f6f6;
  padding-top: 0.02rem;
  .split {
    height: 0.26rem;
    background-color: #f6f6f6;
  }
  .body {
    width: 100%;
    background-color: #fff;
    .body-content {
      width: 90%;
      margin: 0 auto;
      padding-top: 0.02rem;
      .form-title {
        margin-top: 0.1rem;
        span {
          font-family: Montserrat-Medium;
          font-size: 0.32rem;
          color: #333333;
          text-align: left;
          line-height: 0.9rem;
        }
      }
      .form-body {
        .farmname {
          margin-top: 0.4rem;
        }
        .farmLogo {
          margin-top: 0.4rem;
          .location {
            display: flex;
          }
          overflow: hidden;
          margin-top: 0.3rem;
          .tab-button {
            float: left;
            position: relative;
            button {
              background: rgba(0, 180, 89, 0.14);
              padding: 0.1rem 0.2rem;
              border-radius: 0.26rem;
              border: 0;
              font-size: 0.24rem;
              color: #00b459;
              margin-right: 0.2rem;
              margin-top: 0.3rem;
            }
            .close {
              position: absolute;
              right: 0.15rem;
              top: 0.2rem;
            }
          }
          .farmCrops {
            display: flex;
            span {
              flex: 1;
              margin-top: 0.25rem;
              font-family: Montserrat-Regular;
              font-size: 0.28rem;
              color: #887d7d;
              letter-spacing: 0;
              line-height: 0.7rem;
            }
            .add-button {
              flex: 2;
              margin-top: 0.25rem;
              button {
                padding: 0.2rem 0.4rem;
                background: #00b459;
                border: 0.02rem solid #dcdee0;
                border-radius: 0 0.08rem 0.08rem 0;
                font-family: Montserrat-Regular;
                font-size: 0.28rem;
                color: #ffffff;
                letter-spacing: 0;
                line-height: 0.28rem;
              }
            }
          }

          .farmLogos {
            textarea {
              margin-top: 0.25rem;
              width: 100%;
              border: 0.02rem solid #dcdee0;
              border-radius: 0.08rem;
              padding: 0.28rem;
              box-sizing: border-box;
              font-size: 0.28rem;
            }
          }
          .logo {
            width: 100%;
            margin-top: 0.25rem;
            display: flex;
            .logo-left {
              flex: 2;
              height: 1.4rem;
              width: 1.4rem;
            }
            .imgs {
              height: 1.4rem;
              width: 1.4rem;
              img {
                width: 100%;
                height: 100%;
              }
            }
            .hidden {
              display: none;
            }
            .logo-right {
              flex: 7;
              height: 1.4rem;
              width: 1.4rem;
              margin-top: 0.4rem;
              span {
                font-family: Montserrat-Light;
                font-size: 0.28rem;
                color: #999999;
                letter-spacing: 0;
                line-height: 0.36rem;
              }
            }
          }
        }
        .hidden {
          display: none;
        }
        .error {
          color: red;
          font-size: 0.26rem;
        }
      }
      .bottom-button {
        display: flex;
        .cancel {
          flex: 1;
          height: 0.8rem;
          button {
            margin-top: 0.5rem;
            border: 0.02rem solid #00b459;
            border-radius: 0.4rem;
            color: #999;
            background-color: #fff;
            height: 0.8rem;
            width: 95%;
          }
        }
        .confirm {
          flex: 1;
          height: 0.8rem;
          text-align: right;
          margin-top: 0.3rem;
          button {
            border: 0.02rem solid #999;
            border-radius: 0.4rem;
            color: #fff;
            background-color: #999;
            height: 0.8rem;
            width: 95%;
            &.ok {
              background-color: #00b459;
              border: 0.02rem solid #00b459;
            }
          }
        }
      }
    }
  }
}
</style>