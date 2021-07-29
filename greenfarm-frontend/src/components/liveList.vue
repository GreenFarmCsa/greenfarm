<template>
  <div class="live-list">
    <div class="title">Live</div>
    <div class="list">
      <div class="list-item" v-for="(item,index) in liveList" :key="index">
        <div v-if="item.status == '1'" @click="watchLive(item)">
          <img class="profile img" :src="require('@/assets/images/live profile.png')" alt>
          <img class="avatar img" :src="imgurl(item.iconUrl)" alt>
        </div>
      </div>
      <div class="list-item" v-for="i in 6" :key="i + 'a'"></div>
    </div>
  </div>
</template>
<script>
import { queryLiveByFarmId } from "@/api/live";
export default {
  name: "LiveList",
  data() {
    return {
      liveList: []
    };
  },
  mounted() {
    this.getLive();
  },
  props: {
    location: {
      default: "Beijing",
      type: String
    }
  },
  methods: {
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    watchLive(live) {
      window.UMJSBridge.callHandler(
        "liveAPI",
        {
          index: "1",
          url: process.env.VUE_APP_BASE_LIVE + live.liveId,
          username: live.username,
          imgurl:
            process.env.VUE_APP_BASE_API + "/file/download?url=" + live.iconUrl,
          location: this.location
        },
        function(response) {}
      );
    },
    getLive() {
      queryLiveByFarmId({ farmId: this.$route.params.id })
        .then(res => {
          this.liveList = res.data;
        })
        .catch(e => {
          console.log("error", e);
        });
    }
  }
};
</script>
<style lang="scss" scoped>
.live-list {
  background: #fff;
  margin-top: 0.26rem;
  padding: 0.4rem 0.38rem;
  .title {
    font-family: Montserrat-SemiBold;
    font-size: 0.32rem;
    color: #333333;
    letter-spacing: 0;
    line-height: 0.32rem;
  }
  .list {
    padding-top: 0.4rem;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .list-item {
      position: relative;
      margin-right: 0.2rem;
      width: 1.12rem;
      .img {
      }
      .profile {
        width: 1.12rem;
        height: 1rem;
        position: relative;
      }
      .avatar {
        position: absolute;
        top: 0.12rem;
        left: 0.1rem;
        width: 0.8rem;
        height: 0.8rem;
        border-radius: 50%;
      }
    }
  }
}
</style>

