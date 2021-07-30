<template>
  <div>
    <div class="canvas-button">
      <div class="icon" @click="enlarge" style="border-bottom: 0.02rem solid #33C37A;">
        <img v-if="maxsize" :src="require('@/assets/images/cross_white_2x.png')">
        <img v-else :src="require('@/assets/images/cross_green_2x.png')">
      </div>
      <div class="icon" @click="shrink">
        <img v-if="minsize" :src="require('@/assets/images/minus_white_2x.png')">
        <img v-else :src="require('@/assets/images/minus_green_2x.png')">
      </div>
    </div>
    <canvas
      ref="bargraphCanvas"
      :width="originWidth"
      :height="originHeight"
      :style="'width:'+canvasWidth+'px;height:'+canvasHeight+'px;position: absolute;padding-bottom: 1.48rem'"
    ></canvas>
    <div ref="floatmsg" id="floatmsg" class="floatmsg" v-show="ifshowmsg">{{msg}}</div>
  </div>
</template>

<script>
import { leaseFarmsJack } from "@/api/lease";

export default {
  name: "HonorWall",
  components: {},
  data() {
    return {
      leaseFarms: [],
      msg: "",
      ifshowmsg: false,
      originWidth: 2926,
      originHeight: 2714,
      maxsize: true,
      minsize: true,
      canvasWidth: 798.6,
      canvasHeight: 740.3,
      bgImg: {
        url: require("@/assets/images/carbon_map_background.png"),
        x: 0,
        y: 0,
        width: 2926,
        height: 2714
      },
      ImgList: [],
      cropsImgList: [],
      myCanvas: null,
      ctx: null,
      imgObject: [],
      imgX: 0,
      imgY: 0,
      imgScale: 1
    };
  },
  mounted() {
    this.myCanvas = this.$refs.bargraphCanvas;
    this.ctx = this.myCanvas.getContext("2d");
    this.getleaseFarms();
    this.canvasEventsInit();
  },
  methods: {
    async getleaseFarms() {
      try {
        const res = await leaseFarmsJack({
          username: this.$store.state.user.username
        });
        this.leaseFarms = res.data;
        for (let i = 0, len = this.leaseFarms.length; i < len; i++) {
          let crops = this.leaseFarms[i].suitedCrops.split(",");
          if (this.leaseFarms[i].farmName == "Fred Farm") {
            this.ImgList.push({
              url: require("@/assets/images/fred_farm(na)_2x.png"),
              x: 185,
              y: 425,
              width: 364,
              height: 268,
              cx: 185,
              cy: 425,
              cw: 280,
              ch: 268,
              suitedCrops: this.leaseFarms[i].suitedCrops
            });
            for (let j = 0, len = crops.length; j < len; j++) {
              this.pushCrops(crops[j], j, 3, 200, 490);
            }
          }
          if (this.leaseFarms[i].farmName == "Natural Farm") {
            this.ImgList.push({
              url: require("@/assets/images/natural_farm(af) _2x.png"),
              x: 660,
              y: 1380,
              width: 268,
              height: 404,
              cx: 660,
              cy: 1420,
              cw: 268,
              ch: 240,
              suitedCrops: this.leaseFarms[i].suitedCrops
            });
            for (let j = 0, len = crops.length; j < len; j++) {
              this.pushCrops(crops[j], j, 3, 673, 1545);
            }
          }
          if (this.leaseFarms[i].farmName == "Fairytale Farm") {
            this.ImgList.push({
              url: require("@/assets/images/fairytale_farm(sa)_2x.png"),
              x: 38,
              y: 2185,
              width: 560,
              height: 312,
              cx: 140,
              cy: 2120,
              cw: 460,
              ch: 250,
              suitedCrops: this.leaseFarms[i].suitedCrops
            });
            for (let j = 0, len = crops.length; j < len; j++) {
              this.pushCrops(crops[j], j, 3, 240, 2290);
            }
          }
          if (this.leaseFarms[i].farmName == "Uphall Farm") {
            this.ImgList.push({
              url: require("@/assets/images/uphall_farm(eu)_2x.png"),
              x: 868,
              y: 171,
              width: 368,
              height: 398,
              cx: 868,
              cy: 171,
              cw: 368,
              ch: 230,
              suitedCrops: this.leaseFarms[i].suitedCrops
            });
            for (let j = 0, len = crops.length; j < len; j++) {
              this.pushCrops(crops[j], j, 3, 910, 245);
            }
          }
          if (this.leaseFarms[i].farmName == "Arizona Organic Farm") {
            this.ImgList.push({
              url: require("@/assets/images/arizona_organic farm(na)_2x.png"),
              x: 1406,
              y: 589,
              width: 464,
              height: 326,
              cx: 1406,
              cy: 589,
              cw: 450,
              ch: 190,
              suitedCrops: this.leaseFarms[i].suitedCrops
            });
            for (let j = 0, len = crops.length; j < len; j++) {
              this.pushCrops(crops[j], j, 5, 1430, 670);
            }
          }
          if (this.leaseFarms[i].farmName == "Sunny Green Farm") {
            this.ImgList.push({
              url: require("@/assets/images/sunny_green_farm(na)_2x.png"),
              x: 1227,
              y: 1222,
              width: 410,
              height: 406,
              cx: 1227,
              cy: 1222,
              cw: 410,
              ch: 200,
              suitedCrops: this.leaseFarms[i].suitedCrops
            });
            for (let j = 0, len = crops.length; j < len; j++) {
              this.pushCrops(crops[j], j, 4, 1270, 1300);
            }
          }
          if (this.leaseFarms[i].farmName == "Phoenix Organic Farm") {
            this.ImgList.push({
              url: require("@/assets/images/phoenix _organic_farm(as)_2x.png"),
              x: 2167,
              y: 603,
              width: 544,
              height: 298,
              cx: 2167,
              cy: 603,
              cw: 460,
              ch: 250,
              suitedCrops: this.leaseFarms[i].suitedCrops
            });
            for (let j = 0, len = crops.length; j < len; j++) {
              this.pushCrops(crops[j], j, 4, 2200, 680);
            }
          }
        }
        this.loadImg();
      } catch (err) {
        console.log(err);
        this.loadImg();
      }
    },
    pushCrops(type, order, num, x, y) {
      let line = parseInt(order / num);
      let column = order % num;
      if (type == "Corn") {
        this.cropsImgList.push({
          url: require("@/assets/images/corn_2x.png"),
          x: x + column * 85,
          y: y + line * 85,
          width: 71,
          height: 71
        });
      }
      if (type == "Tomato") {
        this.cropsImgList.push({
          url: require("@/assets/images/tomato_2x.png"),
          x: x + column * 85,
          y: y + line * 85,
          width: 71,
          height: 71
        });
      }
      if (type == "Onion") {
        this.cropsImgList.push({
          url: require("@/assets/images/onion_2x.png"),
          x: x + column * 85,
          y: y + line * 85,
          width: 71,
          height: 71
        });
      }
      if (type == "Spinach") {
        this.cropsImgList.push({
          url: require("@/assets/images/spinach_2x.png"),
          x: x + column * 85,
          y: y + line * 85,
          width: 71,
          height: 71
        });
      }
      if (type == "Cucumber") {
        this.cropsImgList.push({
          url: require("@/assets/images/cucumber_2x.png"),
          x: x + column * 85,
          y: y + line * 85,
          width: 71,
          height: 71
        });
      }
    },
    loadImg() {
      var _this = this;
      let ImgList = _this.ImgList.concat(_this.cropsImgList);
      let length = ImgList.length;
      var imageList = [];
      let count = 0;
      //background image
      var isBgLoaded = false;
      var img = new Image();
      var bgImg = this.bgImg;
      img.src = bgImg.url;
      img.onload = async () => {
        imageList.push({
          img: img,
          x: bgImg.x,
          y: bgImg.y,
          width: bgImg.width,
          height: bgImg.height
        });
        ++count;
        if (length > 0) {
          //other image
          for (let key = 0; key < length; key++) {
            let item = ImgList[key];
            let extarImg = new Image();
            extarImg.src = item.url;
            let a = await _this.loadImage(extarImg);
            imageList.push({
              img: extarImg,
              x: item.x,
              y: item.y,
              width: item.width,
              height: item.height
            });
            if (++count >= length) {
              _this.imgObject = imageList;
              _this.drawImage(imageList);
            }
          }
        } else {
          _this.imgObject = imageList;
          _this.drawImage(imageList);
        }
      };
    },

    drawImage(imgList) {
      var _this = this;
      _this.ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);
      for (let i = 0; i < imgList.length; i++) {
        _this.ctx.drawImage(
          imgList[i].img, //image
          _this.imgX + imgList[i].x * _this.imgScale,
          _this.imgY + imgList[i].y * _this.imgScale, //image coordinates
          imgList[i].width * _this.imgScale,
          imgList[i].height * _this.imgScale //height and width of the image
        );
      }
    },

    loadImage(extarImg) {
      return new Promise((resolve, reject) => {
        extarImg.onload = () => resolve();
      });
    },

    // vanvas event
    canvasEventsInit() {
      var _this = this;
      var canvas = _this.myCanvas;
      window.scrollTo(140, 0);
      canvas.addEventListener(
        "click",
        function(e) {
          let rect = canvas.getBoundingClientRect();
          let x = ((e.clientX - rect.left) / rect.width) * _this.originWidth;
          let y = ((e.clientY - rect.top) / rect.height) * _this.originHeight;
          let flag = false;
          for (let i = 0, len = _this.ImgList.length; i < len; i++) {
            if (
              y > _this.ImgList[i].cy &&
              y < _this.ImgList[i].cy + _this.ImgList[i].ch &&
              x > _this.ImgList[i].cx &&
              x < _this.ImgList[i].cx + _this.ImgList[i].cw
            ) {
              var floatmsg = _this.$refs.floatmsg;
              _this.msg = _this.ImgList[i].suitedCrops;
              floatmsg.style.top = e.clientY - rect.top + 65 + "px";
              floatmsg.style.left = e.clientX - rect.left + "px";
              _this.ifshowmsg = true;
              flag = true;
            }
          }
          if (!flag) {
            _this.ifshowmsg = false;
          }
        },
        false
      );
    },
    enlarge() {
      this.ifshowmsg = false;
      let tmp = this.canvasHeight * 1.1;
      if (tmp < 3284) {
        this.minsize = true;
        this.canvasWidth *= 1.1;
        this.canvasHeight *= 1.1;
      } else {
        this.maxsize = false;
        this.canvasWidth = 3540;
        this.canvasHeight = 3284;
      }
    },
    shrink() {
      this.ifshowmsg = false;
      let tmp = this.canvasHeight * 0.9;
      if (tmp >= 670) {
        this.maxsize = true;
        this.canvasWidth *= 0.9;
        this.canvasHeight *= 0.9;
      } else {
        this.minsize = false;
        this.canvasWidth = 726;
        this.canvasHeight = 673;
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.canvas-button {
  margin: 0.4rem;
  z-index: 10;
  border: 0.02rem solid #33c37a;
  font-size: 0.6rem;
  position: fixed;
  right: 0.4rem;
  bottom: 2rem;
  background: #00b459;
  width: 1rem;
  height: 2rem;
  border-radius: 0.1rem;
  .icon {
    width: 1rem;
    height: 1rem;
    img {
      padding: 0.3rem;
      width: 0.42rem;
      height: 0.42rem;
    }
  }
}
.floatmsg {
  z-index: 250;
  position: absolute;
  min-width: 1.6rem;
  max-width: 2.4rem;
  font-family: Montserrat-Light;
  padding: 0.18rem;
  border-radius: 0.16rem;
  word-break: break-all;
  line-height: 0.28rem;
  font-size: 0.24rem;
  background-color: rgba(50, 50, 50, 0.701961);
  border-width: 0px;
  border-color: #333333;
  color: #ffffff;
}
</style>