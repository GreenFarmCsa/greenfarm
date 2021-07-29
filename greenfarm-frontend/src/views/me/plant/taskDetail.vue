<template>
  <div class="taskDetail">
    <div class="hint"></div>
    <div class="box">
      <div class="box-top">
        <div class="boxtitle">{{plantStep}} Method</div>
      </div>
      <div class="boxcontent">
        <p>{{(plantTaskDetail&&plantTaskDetail.remark)?plantTaskDetail.remark:""}}</p>
      </div>
    </div>
    <div class="box">
      <div class="box-top">
        <div class="boxtitle">Records Of {{plantStep}}</div>
        <div :class="but">
          <button class="btn" @click="approved" v-if="but=='but-2'">Submit</button>
          <button class="btn" v-else>Submit</button>
        </div>
      </div>
      <div class="approved" v-if="ifapproved">
        <div class="dian"></div>
        <span>Approved</span>
      </div>
      <div class="unapproved" v-else>
        <div class="dian"></div>
        <span>Unapproved</span>
      </div>
      <div class="boxcontent">
        <div class="left">
          <div v-if="plantTaskDetail&&plantTaskDetail.operRecord" style="float:left">
            <div
              class="fileload"
              v-for="(item,index) in imgRecordList"
              :key="index" >
              <img :src="imgurl(item.url)" @click="viewImg(index,'1')">
            </div>
            <div
              class="fileload"
              v-for="(item,index) in videoRcordList"
              :key="index" >
              <div
                @click="videoPlay(videourl(item.url.split(',')[1]),'normal')"
                class="videoIcon"
              >
                <svg-icon iconClass="begin" style="font-size: 0.4rem;"></svg-icon>
              </div>
              <img
                @click="videoPlay(videourl(item.url.split(',')[1]),'normal')"
                :src="(item.url&&item.url.split(',').length>0)?imgurl(item.url.split(',')[0]):''"
              >
            </div>
          </div>
          <div
            class="fileload"
            v-for="item in videoList"
            :key="item"
            @click="videoPlay(videourl(item.video),'normal')"
          >
            <div class="videoIcon">
              <svg-icon iconClass="begin" style="font-size: 0.4rem;"></svg-icon>
            </div>
            <img :src="imgurl(item.pic)">
          </div>
          <div class="fileload" v-for="item in picList" :key="item">
            <img :src="imgurl(item)" @click="viewImg(index,'2')">
          </div>
          <div class="fileload" v-if="uploadLoading" style="display:flex;align-items:center;justify-content:center;width:1.4rem;height:1.4rem">
            <van-loading />
          </div>
          <div class="fileload" @click="upload()" v-if="showAddImg&&!uploadLoading">
            <svg-icon iconClass="camera-blue-add" style="font-size: 1.4rem;"></svg-icon>
          </div>
        </div>
      </div>
    </div>
    <div class="box">
      <div class="box-top">
        <div class="boxtitle">Live</div>
        <div class="video-start-center" @click="startLive()">
          <div class="img">
            <img src="@/assets/images/zhibobofangshexiangjiguankanmianxing-copy.png" alt>
          </div>
          <span>Start</span>
        </div>
      </div>
      <div class="boxcontent">
        <div class="live-record-container" v-if="liveRecordList && liveRecordList.length > 0">
          <div
            class="record-item"
            v-for="(item,index) in liveRecordList"
            :key="index"
            @click="videoPlay(videourl(item),'normal')"
          >
            <img src="@/assets/images/live-record.png" alt>
          </div>
          <div class="record-item" v-for="i in 5" :key="i+'a'"></div>
        </div>
        <div class="live-record-kong" v-else>
          <img src="@/assets/images/live-kong.png" alt>
          <div class="tip">There is no recording yet</div>
        </div>
      </div>
    </div>
    <div class="box">
      <div class="box-top">
        <div class="boxtitle">Planting Tutorial</div>
      </div>
      <div class="boxcontent">
        <div class="left">
          <div
            class="fileload"
            @click="videoPlay(item.split(',')[0],'youtube')"
            v-for="(item,index) in plantTaskDetail.videoUrl"
            :key="index"
          >
            <div class="videoIcon">
              <svg-icon iconClass="begin" style="font-size: 0.4rem;"></svg-icon>
            </div>
            <img :src="imgurl(item.split(',')[1])" alt>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="videoPopupShow"
      style="position:fixed;z-index:2000;top:0;left:0;right:0;bottom:0;background:#000"
    >
      <div
        style="width:100%;background:#fff;padding:0.4rem 0.16rem;font-size:0.4rem"
        @click="videoPopupShow=false"
      >
        <van-icon name="arrow-left" color="#333333" size="0.4rem" style="padding-left: 0.06rem;"/>
      </div>
      <div
        id="videoContainer"
        style="height:calc(100% - 1.3rem);position:relative;display:flex;align-items:center;"
      >
        <video
          v-if="videoType == 'normal'"
          style="width:100vw;height:100%;background-color:#000"
          :src="videoUrl"
          controls="controls"
          autoplay
        ></video>
        <iframe
          v-else
          style="width:100vw"
          :src="videoUrl"
          title="YouTube video player"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
        ></iframe>
      </div>
    </div>
  </div>
</template>
<script>
import { ImagePreview } from "vant";
import { queryTaskDetail, taskRevise } from "@/api/greenplant";
import { addLive, reviseLive } from "@/api/live";
import { json } from "body-parser";

export default {
  components: { [ImagePreview.Component.name]: ImagePreview.Component },
  data() {
    return {
      ifapproved: false,
      showAddImg: true,
      but: "but",
      hidding: "right",
      toVideos: "fileload",
      toPhotos: "fileload",
      plantStep: "",
      picList: [],
      videoList: [],
      videoPopupShow: false,
      videoUrl: "",
      videoType: "normal",
      plantTaskDetail: [],
      taskRevise: {},
      liveRecordList: [],
      imgRecordList: [],
      videoRcordList: [],
      uploadLoading: false,
    };
  },
  mounted() {
    this.querytaskdetail();
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
  methods: {
    videoPlay(url, type) {
      this.videoType = type;
      this.videoUrl = url;
      this.videoPopupShow = true;
    },
    viewImg(val, flag) {
      let tmp = [];
      if (
        flag == "1" &&
        this.plantTaskDetail &&
        this.plantTaskDetail.operRecord
      ) {
        let showlist = [];
        let databaseImg = JSON.parse(this.plantTaskDetail.operRecord);
        databaseImg.forEach(item => {
          if (item.type == "image") {
            tmp.push(
              process.env.VUE_APP_BASE_API + "/file/download?url=" + item.url
            );
          }
        });
      } else if (flag == "2") {
        this.picList.forEach(item => {
          tmp.push(process.env.VUE_APP_BASE_API + "/file/download?url=" + item);
        });
      } else if (flag == "3") {
        tmp.push(require("@/assets/images/grapes-1.png"));
      }
      ImagePreview({ images: tmp, startPosition: val });
    },
    taskrevise() {
      let operRecord = [];
      if (
        this.plantTaskDetail.operRecord &&
        this.plantTaskDetail.operRecord.length > 0
      ) {
        operRecord = JSON.parse(this.plantTaskDetail.operRecord);
      }
      this.picList.forEach(item => {
        operRecord.push({
          type: "image",
          url: item
        });
      });
      this.videoList.forEach(item => {
        operRecord.push({
          type: "video",
          url: item.pic + "," + item.video
        });
      });
      taskRevise({
        operRecord: operRecord,
        stepId: this.$route.query.stepId
      })
        .then(res => {
          this.but = "but";
          this.showAddImg = false;
          this.$toast({ message: "success", position: "bottom" });
          this.$router.back();
        })
        .catch(e => {
          console.log(e);
        });
    },
    querytaskdetail() {
      if (this.$route.query.status == 2) {
        this.showAddImg = false;
      }
      this.plantStep = this.$route.query.plantStep;
      queryTaskDetail({ id: this.$route.query.stepId })
        .then(res => {
          this.plantTaskDetail = res.data;
          if (this.plantTaskDetail && this.plantTaskDetail.operRecord) {
            this.ifapproved = true;
            JSON.parse(this.plantTaskDetail.operRecord).forEach(item => {
              if (item.type == "live") {
                this.liveRecordList.push(item.url);
              }
              else if (item.type == "image") {
                this.imgRecordList.push(item);
              }
              else if (item.type == "video") {
                this.videoRecordList.push(item);
              }
            });
          }
        })
        .catch(e => {
          console.log(e);
        });
    },
    approved() {
      if (this.$route.query.status != 2) {
        this.taskrevise();
      }
    },
    imgurl(id) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + id;
    },
    videourl(id) {
      return process.env.VUE_APP_BASE_API + "/file/play?url=" + id;
    },
    upload() {
      // this.uploadLoading = true;
      let mimeType = 2;
      window.UMJSBridge.callHandler(
        "jumpCamera",
        {
          type: 0,
          maxSelectable: 9,
          mimeType: mimeType,
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
              this.ifapproved = true;
            } else {
              this.$toast({ message: "upload failed", position: "bottom" });
            }
          } else if (data[0].type == "picture") {
            data.forEach(item => {
              if (item.success) {
                this.picList.push(item.uFileId);
                this.but = "but-2";
                this.ifapproved = true;
              }
            });
          } else {
            this.$toast({ message: "upload failed", position: "bottom" });
          }
        } else {
          this.$toast({ message: "upload failed", position: "bottom" });
        }
      });
    },
    startLive() {
      addLive({
        farmId: this.$route.query.farmId,
        status: "1",
        stepId: this.$route.query.stepId,
        username: this.$store.state.user.username
      })
        .then(res => {
          if (res.data && res.data.liveId) {
            window.UMJSBridge.callHandler(
              "liveAPI",
              {
                index: "0",
                url: process.env.VUE_APP_BASE_LIVE + res.data.liveId
              },
              response => {
                if (response == "live close") {
                  this.$toast({ message: "live exit~", position: "bottom" });
                  reviseLive({
                    farmId: this.$route.query.farmId,
                    liveId: res.data.liveId,
                    status: "0",
                    username: this.$store.state.user.username
                  })
                    .then(res => {})
                    .catch(e => {});
                } else {
                  this.$toast({
                    message: "start live failed!",
                    position: "bottom"
                  });
                }
              }
            );
          } else {
            this.$toast({ message: "start live failed!", position: "bottom" });
          }
        })
        .catch(e => {
          console.log("error",e)
        });
    }
  },
  destroyed() {}
};
</script>

<style lang="scss" scoped>
.unapproved {
  margin-bottom: 0.3rem;
  display: flex;
  align-items: center;
  .dian {
    margin: 0.12rem 0;
    background-color: #bfbfbf;
    width: 0.16rem;
    height: 0.16rem;
    border-radius: 0.08rem;
    float: left;
  }
  span {
    font-family: Montserrat-Light;
    font-size: 0.24rem;
    color: #999999;
    line-height: 0.24rem;
    margin-left: 0.1rem;
  }
}
.approved {
  margin-bottom: 0.3rem;
  display: flex;
  align-items: center;
  .dian {
    margin: 0.12rem;
    background-color: #00b459;
    width: 0.16rem;
    height: 0.16rem;
    border-radius: 0.08rem;
    float: left;
  }
  span {
    font-family: Montserrat-Light;
    font-size: 0.24rem;
    color: #00b459;
    line-height: 0.24rem;
    margin-left: 0.1rem;
  }
}
.taskDetail {
  background-color: #f6f6f6;
}
.hint {
  height: 0.2rem;
  background-color: #f6f6f6;
}
.box {
  background-color: #fdfdfd;
  box-shadow: 0 -1px 0 0 #f1f0f5;
  margin-bottom: 0.2rem;
  padding: 0.4rem;
  .box-top {
    display: flex;
    justify-content: space-between;
    .boxtitle {
      font-family: Montserrat-Medium;
      font-size: 0.32rem;
      color: #333333;
      letter-spacing: 0;
      line-height: 0.72rem;
      font-weight: 550;
    }
    .video-start-center {
      width: 2.4rem;
      border-radius: 0.6rem;
      background-color: #00b459;
      height: 0.7rem;
      line-height: 0.7rem;
      text-align: center;
      display: flex;
      .img {
        flex: 1;
        text-align: right;
        img {
          line-height: 0.7rem;
          margin-top: 0.15rem;
          margin-right: 0.15rem;
          width: 0.4rem;
          height: 0.4rem;
        }
      }
      span {
        flex: 1;
        line-height: 0.7rem;
        vertical-align: center;
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #fcfcfc;
        letter-spacing: 0;
        text-align: left;
        margin-left: 0.15rem;
      }
    }
    .but {
      text-align: right;
      button {
        font-family: Montserrat-Light;
        font-size: 0.28rem;
        line-height: 0.28rem; 
        color: #999999;
        background-color: #d6d6d6;
        border-radius: 53.5px;
        border-radius: 53.5px;
        border: 0;
        height: 0.64rem;
        width: 1.82rem;
      }
    }
    .but-2 {
      flex: 5;
      text-align: right;
      button {
        font-family: Montserrat-Light;
        font-size: 0.28rem;
        line-height: 0.28rem; 
        color: #fff;
        background-color: #00b459;
        border-radius: 53.5px;
        border-radius: 53.5px;
        border: 0;
        padding: 0.2rem;
      }
    }
  }
  .boxcontent {
    width: 100%;
    height: auto;
    word-wrap: break-word;
    word-break: break-all;
    overflow: hidden;
    p {
      font-family: Montserrat-Light;
      font-size: 0.28rem;
      color: #666666;
      letter-spacing: 0;
      text-align: justify;
      line-height: 0.4rem;
    }
    .left {
      float: left;
      .fileload {
        float: left;
        margin-right: 0.1rem;
        margin-bottom: 0.1rem;
        background-color: #f5f5f5;
        position: relative;
        img {
          width: 1.4rem;
          height: 1.4rem;
          border-radius: 0.08rem;
        }
        .videoIcon {
          position: absolute;
          left: 50%;
          top: 50%;
          transform: translate(-50%, -50%);
        }
      }
    }
    .right {
      float: left;
      font-family: Montserrat-Light;
      font-size: 0.28rem;
      color: #999999;
      letter-spacing: 0;
      line-height: 0.36rem;
      padding: 0.34rem 0.15rem;
      width: 3rem;
    }
    .live-record-kong {
      margin-top: 0.5rem;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      img {
        width: 1.4rem;
      }
      .tip {
        margin-left: 0.2rem;
        font-family: Montserrat-Light;
        font-size: 0.28rem;
        color: #999999;
        letter-spacing: 0;
        line-height: 0.36rem;
      }
    }
    .live-record-container {
      margin-top: 0.5rem;
      display: flex;
      justify-content: space-between;
      flex-wrap: wrap;
      .record-item {
        width: 1.4rem;
        img {
          width: 1.4rem;
        }
      }
    }
  }
}
</style>
