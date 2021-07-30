<template>
  <div class="farmaddbody">
    <div class="body">
      <div class="body-content">
        <div class="form-title">
          <span>Registration Of Farm</span>
        </div>
        <div class="form-body">
          <div class="farmname">
            <Label :title="farmName.farmName" :must="farmName.must"/>
            <FormInput
              :error="farmName.error"
              :placeholder="farmName.placeholder"
              :inputval="formData.farmName"
              v-model="formData.farmName"
            />
          </div>
          <div class="farmLogo">
            <Label :title="farmImages.farmName" :must="farmImages.must"/>
            <div class="logo" v-if="loading1">
              <van-loading />
            </div>
            <div class="logo" v-else>
              <div :class="imgslogoclass">
                <svg-icon iconClass="add-farm" style="font-size: 1.4rem" @click="uploadimgs"></svg-icon>
              </div>
              <div
                :class="imgslogohid"
                @click="uploadimgs"
                v-for="(itemaa,index) in formData.imageUrls"
                :key="index"
              >
                <img :src="getImg(itemaa)" alt>
              </div>
              <div :class="imgsclass">
                <span>{{farmImages.placeholder}}</span>
              </div>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmLogo.farmName" :must="farmLogo.must"/>
            <div class="logo" v-if="loading2">
              <van-loading />
            </div>
            <div class="logo" v-else>
              <div :class="imglogoclass">
                <svg-icon iconClass="add-farm" style="font-size: 1.4rem" @click="uploadimg"></svg-icon>
              </div>
              <div :class="imglogohid" @click="uploadimg">
                <img :src="getImg(formData.iconUrl)" alt>
              </div>
              <div :class="imgclass" @click="uploadimg">
                <span>{{farmLogo.placeholder}}</span>
              </div>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmVr.farmName" :must="farmVr.must"/>
            <div class="logo" v-if="loading3">
              <van-loading />
            </div>
            <div class="logo" v-else>
              <div :class="vrlogoclass">
                <svg-icon iconClass="add-vr" style="font-size: 1.4rem" @click="uploadvr"></svg-icon>
              </div>
              <div :class="vrlogohid" @click="uploadvr">
                <img :src="getImg(formData.vrUrl)" alt>
              </div>
              <div :class="vrclass" @click="uploadvr">
                <span>{{farmVr.placeholder}}</span>
              </div>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmArea.farmName" :must="farmArea.must"/>
            <div class="farmCrops">
              <FormInput
                :error="farmArea.error"
                :placeholder="farmArea.placeholder"
                :inputval="formData.totalArea"
                v-model="formData.totalArea"
              />
              <span>m²</span>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmAddress.farmName" :must="farmAddress.must"/>
            <div class="farmLogos">
              <textarea
                name
                id
                rows="3"
                :placeholder="farmAddress.placeholder"
                v-model="formData.location"
              ></textarea>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmPeriod.farmName" :must="farmPeriod.must"/>
            <FormInput
              :error="farmPeriod.error"
              :placeholder="farmPeriod.placeholder"
              :disabled="disabled"
              :inputval="formData.rentPeriod"
              v-model="formData.rentPeriod"
            />
          </div>
          <div class="farmLogo">
            <div style="display:flex;justify-content:center;align-items:center">
              <Label :title="farmLongitude.farmName" :must="farmLongitude.must"/>
              <div @click="getLocation()">
                <img style="width:1.2rem" src="@/assets/images/map2.png" alt>
              </div>
            </div>
            <FormInput
              :placeholder="farmLongitude.placeholder"
              :disabled="disabled"
              v-model="longitude"
            />
          </div>
          <div class="farmLogo">
            <Label :title="farmLatitude.farmName" :must="farmLatitude.must"/>
            <FormInput
              :placeholder="farmLatitude.placeholder"
              :disabled="disabled"
              v-model="latitude"
            />
          </div>
          <div class="farmLogo">
            <Label :title="farmCrops.farmName" :must="farmCrops.must"/>
            <div class="farmCrops">
              <FormInput
                :error="farmCrops.error"
                :placeholder="farmCrops.placeholder"
                v-model="inputval"
              />
              <div class="add-button">
                <button @click="addCrop">Add</button>
              </div>
            </div>
            <div class="tab-button" v-for="(item,index) in cropsList" :key="index">
              <button>{{item}}</button>
              <div class="close" @click="close(index)">
                <svg-icon iconClass="close" style="font-size:.24rem"></svg-icon>
              </div>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmNote.farmName" :must="farmNote.must"/>
            <div class="farmLogos">
              <textarea
                name
                id
                rows="3"
                :placeholder="farmNote.placeholder"
                v-model="formData.introduction"
              ></textarea>
            </div>
          </div>
          <div class="rigistrationOfLand">
            <div class="roltitle">
              <div class="roltext">Registration Of Land</div>
              <div class="addTheLand">
                <button @click="toaddland">Add the Land</button>
              </div>
            </div>
            <div :class="rolbody">
              <div class="bodyimg">
                <img :src="require('@/assets/images/add-one-placeholder.jpg')">
              </div>
              <div class="bodytext">
                <div>Land that have not been leased,</div>
                <div>Please create land!</div>
              </div>
            </div>
            <div :class="myland">
              <div class="my-land" v-for="(item,index) in landList" :key="index">
                <div class="myland-top">
                  <img src="@/assets/images/editframland.png" alt>
                  <div :class="item.like" style="background: #ff9f00;">
                    <span>Available</span>
                  </div>
                  <div :class="item.dislike" style="background: #ABABAB;">
                    <span>Unavailable</span>
                  </div>
                </div>
                <div class="myland-body">
                  <div class="myland-left">
                    <span>Land ID</span>
                  </div>
                  <div class="myland-right">
                    <span>{{item.landId}}</span>
                  </div>
                </div>
                <div class="myland-body">
                  <div class="myland-left">
                    <span>Suited crops</span>
                  </div>
                  <div class="myland-right">
                    <span>{{item.suitedCrops}}</span>
                  </div>
                </div>
                <div class="myland-body">
                  <div class="myland-left">
                    <span>Area</span>
                  </div>
                  <div class="myland-right">
                    <span>{{item.area}}m²</span>
                  </div>
                </div>
                <div class="myland-body">
                  <div class="myland-left">
                    <span>Lease price</span>
                  </div>
                  <div class="myland-right">
                    <span>${{parseFloat(item.price).toFixed(2)}}</span>
                  </div>
                </div>
                <div class="myland-body">
                  <div class="myland-left">
                    <span>Creation time</span>
                  </div>
                  <div class="myland-right">
                    <span>{{item.createTime}}</span>
                  </div>
                </div>
                <div class="myland-body">
                  <div class="myland-left">
                    <span>Modification time</span>
                  </div>
                  <div class="myland-right">
                    <span>{{item.modifyTime}}</span>
                  </div>
                </div>
                <div class="myland-footer">
                  <div class="svg">
                    <svg-icon iconClass="todaaa" style="font-size: .4rem" @click="toedit(item)"></svg-icon>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="bottom-button">
            <div class="cancel">
              <button @click="cancel">Cancel</button>
            </div>
            <div class="confirm" @click="confirm()">
              <button>Confirm</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="divs"></div>
  </div>
</template>

<script>
import FormInput from "@/components/FormInput";
import Label from "@/components/Label";
import { reviseFarm, queryFarmById } from "@/api/farm";
import { queryLandByFarmId } from "@/api/land";
export default {
  components: { FormInput, Label },
  data() {
    return {
      imgsclass: "logo-right",
      imgslogoclass: "logo-left",
      imgslogohid: "hidden",
      imgclass: "logo-right",
      imglogoclass: "logo-left",
      imglogohid: "hidden",
      vrclass: "logo-right",
      vrlogoclass: "logo-left",
      vrlogohid: "hidden",
      disabled: true,
      city: "",
      country: "",
      myland: "hidden",
      rolbody: "rolbody",
      formData: {
        username: this.$store.state.user.username
          ? this.$store.state.user.username
          : "fred"
      },
      landList: [],
      farmName: {
        farmName: "Name of the farm",
        error: "Name of the farm is not allowed null",
        placeholder: "Please enter the farmName",
        must: "must"
      },
      farmImages: {
        farmName: "Farm Images",
        error: "Images of the farm is not allowed null",
        placeholder: "The image size is limited to 800K.",
        must: "must"
      },
      farmLogo: {
        farmName: "Farm Icon",
        error: "Icon of the farm is not allowed null",
        placeholder: "The image size is limited to 800K.",
        must: "must"
      },
      farmVr: {
        farmName: "VR Photo",
        error: "VR Photo of the farm is not allowed null",
        placeholder: "The image size is limited to 2M.",
        must: "must"
      },
      farmArea: {
        farmName: "Area of Farm",
        error: "Area of the farm is not allowed null",
        placeholder: "Please enter the…",
        must: "must"
      },
      farmAddress: {
        farmName: "Farm address",
        error: "address of the farm is not allowed null",
        placeholder: "Please enter the address",
        must: "must"
      },
      farmPeriod: {
        farmName: "The lease period",
        error: "period of the farm is not allowed null",
        placeholder: "Please enter the…",
        must: "must"
      },
      farmLongitude: {
        farmName: "Longitude",
        error: "longitude of the farm is not allowed null",
        placeholder: "Please enter the longitude",
        must: "must"
      },
      farmLatitude: {
        farmName: "Latitude",
        error: "latitude of the farm is not allowed null",
        placeholder: "Please enter the latitude",
        must: "must"
      },
      farmCrops: {
        farmName: "Suited crops",
        placeholder: "Please enter the crops",
        must: "must"
      },
      farmNote: {
        farmName: "Introduction",
        error: "",
        placeholder: "Please enter the Note",
        must: "hidden"
      },
      cropsList: [],
      longitude: "",
      latitude: "",
      location: "",
      inputval: "",
      addressList: [
        {
          Country: "China",
          city: [
            {
              name: "Beijing"
            },
            {
              name: "Tianjin"
            },
            {
              name: "Shanghai"
            },
            {
              name: "Shenzhen"
            }
          ]
        },
        {
          Country: "USA",
          city: [
            {
              name: "New York"
            }
          ]
        }
      ],
      cityList: [
        {
          name: "Beijing"
        },
        {
          name: "Tianjin"
        },
        {
          name: "Shanghai"
        },
        {
          name: "Shenzhen"
        }
      ],
      loading1: false,
      loading2: false,
      loading3: false,
    };
  },
  created() {
    this.getData();
  },
  mounted() {
    window.scrollTo(0, 1);
    window.scrollTo(0, 0);
  },
  methods: {
    getLocation() {
      window.UMJSBridge.callHandler("jumpMapSelect", {}, response => {
        if (response.loc && response.loc.length == 2) {
          this.longitude = response.loc[0];
          this.latitude = response.loc[1];
        } else {
          this.$toast({ message: "selete failed!", position: "bottom" });
        }
      });
    },
    toaddland() {
      this.$router.push({
        name: "leaseLandAdd",
        query: { id: this.$route.query.id }
      });
    },
    cancel() {
      this.$router.back();
    },
    getData() {
      let id = this.$route.query.id;
      this.formData.farmId = id;
      queryFarmById({ id }).then(res => {
        let data = res.data;
        if (!data.imageUrls) data.imageUrls = ["111"];
        else {
          data.imageUrl = data.imageUrls;
          this.imgclass = "hidden";
          this.imglogoclass = "hidden";
          this.imglogohid = "imgs";
        }
        if (data.iconUrl) {
          this.imgsclass = "hidden";
          this.imgslogoclass = "hidden";
          this.imgslogohid = "imgs";
        }
        if (data.vrUrl) {
          this.vrclass = "hidden";
          this.vrlogoclass = "hidden";
          this.vrlogohid = "imgs";
        }
        if (data.latitudeLongitude) {
          this.longitude = data.latitudeLongitude.split(",")[0];
          this.latitude = data.latitudeLongitude.split(",")[1];
        }
        data.totalArea = data.totalArea + "";
        this.formData = data;
        this.cropsList = res.data.suitedCrops.split(",");
        if (this.cropsList) {
          for (let i = 0; i < this.cropsList.length; i++) {
            if (this.inputval) this.inputval += " ; " + this.cropsList[i];
            else this.inputval += this.cropsList[i];
          }
        }
      }).catch(e => {
        console.log(e);
      });
      queryLandByFarmId({ id }).then(res => {
        let data = [];
        for (let i = 0; i < res.data.length; i++) {
          data[i] = res.data[i];
          let createTime = this.getTime(res.data[i].createTime);
          let modifyTime = this.getTime(res.data[i].modifyTime);
          let like = res.data[i].isRent == true ? "hidden" : "signs";
          let dislike = res.data[i].isRent == true ? "signs" : "hidden";
          this.$set(data[i], "like", like);
          this.$set(data[i], "dislike", dislike);
          this.$set(data[i], "createTime", createTime);
          this.$set(data[i], "modifyTime", modifyTime);
        }
        this.landList = data;
        if (this.landList.length > 0) {
          this.myland = "myland";
          this.rolbody = "hidden";
        }
      }).catch(e => {
        console.log(e);
      })
    },
    getTime(time) {
      if (time)
        time =
          time.split(".")[0].split("T")[0] +
          " " +
          time.split(".")[0].split("T")[1]
            ? time.split(".")[0].split("T")[0] +
              " " +
              time.split(".")[0].split("T")[1]
            : time;
      return time;
    },
    toedit(val) {
      this.$router.push({
        name: "leaseLandEdit",
        query: { id: JSON.stringify(val) }
      });
    },
    confirm() {
      let cropsList = this.cropsList;
      this.formData.suitedCrops = "";
      if (cropsList) {
        for (let i = 0; i < cropsList.length; i++) {
          if (this.formData.suitedCrops)
            this.formData.suitedCrops += "," + cropsList[i];
          else this.formData.suitedCrops += this.cropsList[i];
        }
      }
      if (!this.formData.farmName) {
        this.$toast({ message: this.farmName.error, position: "bottom" });
        return;
      }
      if (!this.formData.imageUrls) {
        this.$toast({ message: this.farmLogo.error, position: "bottom" });
        return;
      }
      if (!this.formData.vrUrl) {
        this.$toast({ message: this.farmVr.error, position: "bottom" });
        return;
      }
      if (!this.formData.totalArea) {
        this.$toast({ message: this.farmArea.error, position: "bottom" });
        return;
      }
      this.formData.totalArea = parseInt(this.formData.totalArea);
      if (!this.formData.totalArea) {
        this.$toast({ message: "area must number", position: "bottom" });
        return;
      }
      if (!this.formData.location) {
        this.$toast({ message: this.farmAddress.error, position: "bottom" });
        return;
      }
      if (!this.formData.rentPeriod) {
        this.$toast({ message: this.farmPeriod.error, position: "bottom" });
        return;
      }
      if (!this.formData.iconUrl) {
        this.$toast({
          message: "IconUrl of the icon is not allowed null",
          position: "bottom"
        });
        return;
      }
      if (!this.formData.suitedCrops) {
        this.$toast({
          message: "Crops of the farm is not allowed null",
          position: "bottom"
        });
        return;
      }
      if (!this.longitude) {
        this.$toast({
          message: "Longitude of the farm is not allowed null",
          position: "bottom"
        });
        return;
      }
      if (!this.latitude) {
        this.$toast({
          message: "Latitude of the farm is not allowed null",
          position: "bottom"
        });
        return;
      }
      if (this.formData.suitedCrops.toString().length > 100) {
        this.$toast({message: "suitedCrops length exceeds 100",position: "bottom"});
        return;
      }
      if (this.formData.introduction && this.formData.introduction.toString().length > 1000) {
        this.$toast({message: "introduction length exceeds 1000",position: "bottom"});
        return;
      }
      if (this.formData.location.toString().length > 100) {
        this.$toast({message: "location length exceeds 100",position: "bottom"});
        return;
      }
      this.formData.latitudeLongitude = this.longitude + "," + this.latitude;
      reviseFarm({ ...this.formData }).then(res => {
        this.$router.back();
      }).catch(e => {
        console.log(e);
      })
    },
    uploadimgs() {
      this.uploads();
    },
    uploadimg() {
      this.upload("img");
    },
    uploadvr() {
      this.uploadVR();
    },
    uploads() {
      this.loading1 = true;
      window.UMJSBridge.callHandler(
        "jumpCamera",
        {
          type: 0,
          maxSelectable: 5,
          mimeType: 0,
          maxRecordTime: 30,
          capture: true
        },
        function(response) {}
      );
      window.UMJSBridge.registerHandler("jumpCamera", data => {
        this.loading1 = false;
        if (data) {
          let arr = [];
          for (let i = 0; i < data.length; i++) {
            if (data[i].success && data[i].type == "picture") {
              arr.push(data[i].uFileId);
            }
          }
          this.formData.imageUrls = arr;
          this.imgsclass = "hidden";
          this.imgslogoclass = "hidden";
          this.imgslogohid = "imgs";
          this.formData = Object.assign({}, this.formData);
        } else {
          // this.$toast({ message: "fail", position: "bottom" });
        }
      });
    },
    upload(result) {
      this.loading2 = true;
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
        this.loading2 = false;
        if (data) {
          if (data[0].success && data[0].type == "picture") {
            if (result == "img") {
              this.formData.iconUrl = data[0].uFileId;
              this.imgclass = "hidden";
              this.imglogoclass = "hidden";
              this.imglogohid = "imgs";
            } else {
              this.formData.vrUrl = data[0].uFileId;
              this.vrclass = "hidden";
              this.vrlogoclass = "hidden";
              this.vrlogohid = "imgs";
            }
            this.formData = Object.assign({}, this.formData);
          } else {
            this.$toast({ message: "fail", position: "bottom" });
          }
        }
      });
    },
    uploadVR() {
      this.loading3 = true;
      window.UMJSBridge.callHandler("jumpVRCamera", {}, response => {});
      window.UMJSBridge.registerHandler("jumpVRCamera", data => {
        this.loading3 = false;
        if (data && data[0].success && data[0].type == "picture") {
          this.formData.vrUrl = data[0].uFileId;
          this.vrclass = "hidden";
          this.vrlogoclass = "hidden";
          this.vrlogohid = "imgs";

          this.formData = Object.assign({}, this.formData);
        } else {
          // this.$toast({ message: "Upload Failed", position: "bottom" });
        }
      });
    },
    getImg(val) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + val;
    },
    addCrop() {
      if (this.inputval) {
        this.cropsList = this.inputval.split(";");
      }
    },
    close(val) {
      this.cropsList.splice(val, 1);
      this.inputval = "";
      if (this.cropsList) {
        for (let i = 0; i < this.cropsList.length; i++) {
          if (this.inputval) this.inputval += " ; " + this.cropsList[i];
          else this.inputval += cropsList[i];
        }
      }
    },
    setCity(val) {
      this.cityList = this.addressList[val.target.value].city;
    }
  }
};
</script>

<style lang="scss" scoped>
.farmaddbody {
  width: 100%;
  background: #f6f6f6;
  padding-top: 0.02rem;
  .body {
    width: 100%;
    margin-top: 0.2rem;
    background-color: #fff;
    .body-content {
      width: 90%;
      margin: 0.2rem auto 0 auto;
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
        .farmLogo {
          .location {
            display: flex;
            .location-provice {
              flex: 2;
              select {
                height: 0.7rem;
                margin-top: 0.25rem;
                width: 95%;
                background: #ffffff;
                border-radius: 0.08rem;
                border: 0.02rem solid #dcdee0;
              }
            }
            .location-city {
              flex: 3;
              select {
                height: 0.7rem;
                margin-top: 0.25rem;
                background: #ffffff;
                width: 100%;
                border-radius: 0.08rem;
                border: 0.02rem solid #dcdee0;
              }
            }
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
            align-items: center;
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
              margin-left: 0.2rem;
              word-wrap: break-word;
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
      }
      .rigistrationOfLand {
        margin-top: 0.5rem;
        .roltitle {
          display: flex;
          .roltext {
            flex: 7;
            font-family: Montserrat-Medium;
            font-size: 0.32rem;
            color: #333333;
            text-align: left;
            height: 0.6rem;
            margin-top: 0.15rem;
          }
          .addTheLand {
            flex: 3;
            button {
              border: 0.02rem solid #00b459;
              border-radius: 0.4rem;
              color: #fff;
              background-color: #00b459;
              height: 0.6rem;
              width: 95%;
              font-family: Montserrat-Regular;
              font-size: 0.24rem;
              color: #fcfcfc;
              letter-spacing: 0;
              text-align: center;
              line-height: 0.28rem;
            }
          }
        }
        .rolbody {
          .bodyimg {
            text-align: center;
            padding: 0.5rem 0.5rem;
            position: relative;
            img {
              width: 100%;
              height: 5rem;
              object-fit: cover;
            }
          }
          .bodytext {
            div {
              text-align: center;
              font-family: Montserrat-Regular;
              font-size: 0.32rem;
              color: #333333;
              letter-spacing: 0;
              line-height: 0.44rem;
            }
          }
        }
        .hidden {
          display: none;
        }
        .myland {
          .my-land {
            width: 100%;
            margin-top: 0.3rem;
            background: #ffffff;
            box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
            border-radius: 0.16rem;
            .myland-top {
              height: 2rem;
              width: 100%;
              position: relative;
              margin-bottom: 0.2rem;
              img {
                width: 100%;
                height: 100%;
              }
              .sign {
                position: absolute;
                top: 0;
                right: 0;
                line-height: 0.6rem;
                height: 0.6rem;
                border-radius: 0 0.16rem 0 0.16rem;
                background: #ababab;
                padding: 0 0.2rem;
                span {
                  font-family: PingFangSC-Regular;
                  font-size: 0.24rem;
                  color: #ffffff;
                  line-height: 0.24rem;
                }
              }
              .signs {
                position: absolute;
                top: 0;
                right: 0;
                line-height: 0.6rem;
                height: 0.6rem;
                border-radius: 0 0.16rem 0 0.16rem;
                // background: #ff9f00;
                padding: 0 0.2rem;
                span {
                  font-family: PingFangSC-Regular;
                  font-size: 0.24rem;
                  color: #ffffff;
                  line-height: 0.24rem;
                }
              }
            }
            .myland-body {
              width: 90%;
              margin: auto;
              display: flex;
              .myland-left {
                flex: 1;
                span {
                  font-family: Montserrat-Regular;
                  font-size: 0.28rem;
                  color: #666666;
                  line-height: 0.6rem;
                }
              }
              .myland-right {
                word-wrap: break-word;
                height: auto;
                min-width: 50%;
                flex: 1;
                text-align: right;
                span {
                  font-family: Montserrat-Regular;
                  color: #00b459;
                  font-size: 0.28rem;
                  line-height: 0.6rem;
                }
              }
            }
            .myland-footer {
              border-radius: 0 0 0.16rem 0.16rem;
              margin-top: 0.2rem;
              height: 1rem;
              text-align: right;
              background: #00b459;
              padding-top: 0.02rem;
              .svg {
                margin: 0.3rem;
              }
            }
          }
        }
      }
      .bottom-button {
        height: 2rem;
        display: flex;
        .cancel {
          flex: 1;
          height: 0.8rem;
          button {
            margin-top: 0.5rem;
            border: 0.02rem solid #00b459;
            border-radius: 0.4rem;
            color: #00b459;
            background-color: #fff;
            height: 0.8rem;
            width: 95%;
          }
        }
        .confirm {
          flex: 1;
          height: 0.8rem;
          text-align: right;
          button {
            margin-top: 0.5rem;
            border: 0.02rem solid #00b459;
            border-radius: 0.4rem;
            color: #fff;
            background-color: #00b459;
            height: 0.8rem;
            width: 95%;
            font-family: Montserrat-Light;
          }
        }
      }
    }
  }
}
</style>
