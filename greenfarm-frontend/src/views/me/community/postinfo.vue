<template>
  <div class="postinfobody">
    <div class="postinfo-img">
      <img :src="getImg(com.communityImageUrl)" alt>
    </div>
    <div class="list-body">
      <div class="list" v-for="(item,index) in dataList" :key="index">
        <div class="list-left">
          <img :src="getImg(item.topicImageUrl)" alt>
        </div>
        <div class="list-right">
          <div
            class="list-right-title"
            @click="$router.push('/community/post-detail/'+item.topicId)"
            v-html="item.topicContent"
          ></div>
          <div class="list-right-bottom">
            <div class="list-right-bottom-left">
              <div class="list-right-bottom-left-img">
                <svg-icon iconClass="time" style="font-size: 0.24rem"></svg-icon>
              </div>
              <div class="list-right-bottom-left-time">{{item.time}}</div>
            </div>
            <div class="list-right-bottom-right">
              <svg-icon
                :iconClass="item.hasLiked==false?'heart-disabled':'heart-enabled'"
                style="font-size: 0.38rem"
                @click="like(index,item)"
              ></svg-icon>
              <div class="like-num" @click="like(index,item)">{{item.likeSum}}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="div"></div>
  </div>
</template>
<script>
import { queryTopicByCommunityId, topicLike } from "@/api/topic";
import { timeHandle } from "@/utils/index";
export default {
  name: "HomePage",
  data() {
    return {
      dataList: [],
      com: {}
    };
  },
  created() {
    this.getData();
  },
  methods: {
    toInfo(val) {
      this.$router.push({ path: "/postinfo" });
    },
    getImg(val) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + val;
    },
    getData() {
      this.com = JSON.parse(this.$route.query.id);
      queryTopicByCommunityId({
        id: this.com.communityId,
        username: this.$store.state.user.username
      }).then(res => {
        let data = [];
        for (let i = 0; i < res.data.length; i++) {
          let tmp = res.data[i];
          if(tmp.topicContent){
            tmp.topicContent = tmp.topicContent.replace(/\n/g, "<br/>");
          }
          data[i] = tmp;
          // let time = this.getTime(res.data[i].createTime);
          let time = timeHandle(res.data[i].createTime);
          data[i].topicImageUrl = res.data[i].topicImageUrl;
          this.$set(data[i], "time", time);
        }
        this.dataList = data;
      }).catch(e => {
        console.log(e);
      });
    },
    getTime(time) {
      if (
        time.split(".")[0].split("T")[0] +
        " " +
        time.split(".")[0].split("T")[1]
      )
        return (
          time.split(".")[0].split("T")[0] +
          " " +
          time.split(".")[0].split("T")[1]
        );
      return time;
    },
    like(index, item) {
      let params = {
        topicId: item.topicId,
        username: this.$store.state.user.username,
        isLike: !item.hasLiked
      };
      topicLike(params).then(res => {
        item.hasLiked = !item.hasLiked;
        if (item.hasLiked) {
          item.likeSum = item.likeSum + 1;
        } else {
          item.likeSum = item.likeSum - 1;
        }
      }).catch(e => {
        console.log(e);
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.postinfobody {
  padding-bottom: 0.3rem;
  .postinfo-img {
    width: 100%;
    height: 4.4rem;
    img {
      height: 100%;
      width: 100%;
    }
  }
  .list-body {
    width: 90%;
    margin: auto;
    .list {
      padding: 0.36rem 0.32rem 0.32rem 0.18rem;
      margin-top: 0.3rem;
      min-height: 1rem;
      box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
      display: flex;
      .list-left {
        width: 0.72rem;
        img {
          width: 0.72rem;
          height: 0.72rem;
          border-radius: 50%;
        }
      }
      .list-right {
        padding-left: 0.16rem;
        width: 5.27rem;
        .list-right-title {
          word-wrap: break-word;
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #333333;
          letter-spacing: 0;
          line-height: 0.36rem;
        }
        .list-right-bottom {
          margin-top: 0.3rem;
          display: flex;
          .list-right-bottom-left {
            height: 0.24rem;
            display: flex;
            padding-bottom: 0.24rem;
            .list-right-bottom-left-img {
              line-height: 0.24rem;
              font-size: 0.24rem;
            }
            .list-right-bottom-left-time {
              margin-left: 0.12rem;
              font-family: Montserrat-Light;
              font-size: 0.24rem;
              color: #6c7b8a;
              letter-spacing: 0;
              line-height: 0.24rem;
            }
          }
          .list-right-bottom-right {
            flex: 2;
            text-align: right;
            padding-top: 0.1rem;
            .like-num {
              display: inline-block;
              margin-top: -0.1rem;
              margin-left: 0.08rem;
              vertical-align: middle;
              font-family: "Montserrat-Light";
              font-size: 0.28rem;
              color: #666;
              letter-spacing: 0;
              text-align: center;
              line-height: 0.56rem;
            }
          }
        }
      }
    }
  }
}
</style>


