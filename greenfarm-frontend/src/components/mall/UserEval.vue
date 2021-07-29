<template>
  <div class="comment eval">
    <van-card>
      <template #thumb>
        <img class="avatar" :src="iconurl((userInfo.iconUrl) ? userInfo.iconUrl: '')" alt>
      </template>
      <template #title>
        <span class="user">{{userInfo.username}}</span>
      </template>
      <template #tags>
        <div class="rate">
          <span class="star">{{userInfo.commentContent}}</span>
          <span class="date">{{commentDate}}</span>
        </div>
      </template>
    </van-card>
  </div>
</template>
<script>
export default {
  name: "UserEval",
  data() {
    return {
      commentDate: ""
    };
  },
  props: {
    userInfo: {
      type: Object,
      default: () => {}
    }
  },
  methods: {
    iconurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    }
  },
  mounted() {
    if (this.userInfo.createTime !== null) {
      this.commentDate = this.userInfo.createTime.substr(0, 10);
    }
  }
};
</script>
<style lang="less" scoped>
.comment {
  border-bottom: 0.1px solid #f6f6f6;
  padding: 0.3rem 0.3rem 0.3rem 0;
  background-color: #fff;
}
.avatar {
  width: 0.84rem;
  height: 0.84rem;
  border-radius: 50%;
}
.user {
  color: #383f45;
  text-align: left;
  font-family: Montserrat-Regular;
  font-size: 0.32rem;
}
.rate {
  padding-top: 0.2rem;
  .star {
    font-family: Montserrat-Regular;
    font-size: 0.24rem;
    color: #666666;
    text-align: left;
    display: block;
  }
  .date {
    margin-top: 0.2rem;
    font-family: Montserrat-Light;
    font-size: 0.24rem;
    color: #909090;
    text-align: left;
    display: block;
  }
}
/deep/ .van-card {
  background-color: #fff;
  padding: 0rem;
  .van-card__thumb {
    width: 1rem;
    height: 1rem;
  }
}
</style>

