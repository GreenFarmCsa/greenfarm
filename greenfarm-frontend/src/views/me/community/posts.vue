<template>
  <div class="postbody">
    <div class="postbody-body">
      <div class="postbody-list" v-for="(item,index) in dataList" :key="index">
        <div class="post-list-top" @click="toInfo(item)">
          <div class="post-list-top-left">
            <svg-icon iconClass="house-1" style="font-size:.4rem"></svg-icon>
          </div>
          <div class="post-list-top-title">{{item.communityName}}</div>
        </div>
        <div class="post-list-body">
          <div class="post-list-img" @click="toInfo(item)">
            <img :src="getImg(item.communityImageUrl)" alt @click="toInfo(item)">
          </div>
          <div class="post-list-community">
            <ul>
              <li
                v-for="(it,ins) in item.topic"
                :key="ins"
                @click="$router.push('/community/post-detail/'+it.topicId)"
              >{{it.topicName}}</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="div"></div>
  </div>
</template>
<script>
import {
  queryCommunityByFarmId,
  joinCommunity,
  queryCommunityByUserName
} from "@/api/community";
import { queryTopicByCommunityId } from "@/api/topic";
export default {
  name: "HomePage",
  data() {
    return {
      dataList: [],
      topicList: []
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    toInfo(val) {
      this.$store.dispatch("title/setTitle", val.communityName);
      this.$router.push({
        path: "/me/community/postinfo",
        query: { id: JSON.stringify(val) }
      });
    },
    getImg(val) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + val;
    },
    getData() {
      let params = {
        farmId: this.$route.params.farmId
      };
      let name = this.$store.state.user.username || "jack";
      queryCommunityByUserName({ name }).then(res => {
        this.dataList = res.data;
        this.getTopic();
      }).catch(e => {
        console.log(e);
      });
    },

    getTopic() {
      if (this.dataList) {
        for (let i = 0; i < this.dataList.length; i++) {
          let params = {
            username: this.$store.state.user.username,
            id: this.dataList[i].communityId
          };
          queryTopicByCommunityId(params).then(res => {
            this.$set(this.dataList[i], "topic", res.data);
          }).catch(e => {
            console.log(e);
          });
        }
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.postbody {
  width: 100%;
  background-color: #fff;
  .postbody-body {
    width: 90%;
    margin: 0.1rem auto;
    .postbody-list {
      width: 100%;
      margin-top: 0.2rem;
      border-radius: 0 0 0.18rem 0.18rem;
      box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
      .post-list-top {
        display: flex;
        border-radius: 0.18rem 0.18rem 0 0;
        height: 0.8rem;
        background-color: #00b459;
        .post-list-top-left {
          flex: 1;
          height: 0.8rem;
          padding-top: 0.2rem;
          padding-left: 0.3rem;
          box-sizing: border-box;
        }
        .post-list-top-title {
          margin-left: 0.1rem;
          flex: 12;
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #ffffff;
          line-height: 0.8rem;
        }
      }
      .post-list-body {
        width: 100%;
        .post-list-img {
          height: 2.24rem;
          width: 100%;
          img {
            width: 100%;
            height: 100%;
          }
        }
        .post-list-community {
          width: 100%;
          ul {
            width: 100%;
            padding: 0 0.3rem;
            box-sizing: border-box;
            li {
              width: 100%;
              word-wrap: break-word;
              font-family: Montserrat-Regular;
              font-size: 0.28rem;
              background-color: #fff;
              color: #666666;
              letter-spacing: 0;
              line-height: 1rem;
              margin-bottom: 0.01rem;
              border-bottom: 0.01rem solid #eeeff1;
            }
          }
        }
      }
    }
  }
}
</style>


