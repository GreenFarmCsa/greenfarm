<template>
  <div class="homepage">
    <div class="user-message-wrapper">
      <div class="user-img">
        <img :src="dataObj.iconUrl" alt>
      </div>
      <div class="user-message">
        <div class="user-name">{{dataObj.username}}</div>
        <div class="user-time">{{dataObj.createTime}}</div>
      </div>
    </div>
    <div class="img-wrapper">
      <img :src="dataObj.topicImageUrl" alt>
    </div>
    <div class="like-wrapper">
      <svg-icon
        iconClass="heart-enabled"
        style="font-size: 0.38rem;vertical-align: middle;"
        v-if="dataObj.hasLiked"
        @click.stop="onLike(dataObj)"
      ></svg-icon>
      <svg-icon
        iconClass="heart-disabled"
        style="font-size: 0.38rem;vertical-align: middle;"
        v-else
        @click.stop="onLike(dataObj)"
      ></svg-icon>
    </div>
    <div class="comments-wrapper">
      <div class="title">{{dataObj.topicName}}</div>
      <div class="content" v-html="dataObj.topicContent"></div>
      <div class="total">{{commentsList.length}} comments in total</div>
      <div class="comments-list">
        <div class="comments-list-item" v-for="item in commentsList" :key="item.commentId">
          <div class="name">{{item.username}}</div>
          <div class="content" v-html="item.commentContent"></div>
        </div>
      </div>
    </div>
    <div class="input-wrapper">
      <van-cell-group>
        <van-field
          v-model="newComments"
          placeholder="Leave a comment here…"
          @click="inputFocuse"
          :disabled="inputShow"
        />
      </van-cell-group>
    </div>
    <van-action-sheet v-model="inputShow">
      <div class="action-sheet-wrapper">
        <div class="action-sheet-input-wrapper">
          <van-cell-group>
            <van-field
              ref="comment"
              v-model="newCommentsSheet"
              placeholder="Leave a comment here…"
            />
          </van-cell-group>
        </div>
        <van-button type="primary" @click="addTopicComment">
          <svg-icon iconClass="send-comment" style="font-size: 0.38rem"></svg-icon>
        </van-button>
      </div>
    </van-action-sheet>
  </div>
</template>
<script>
import {
  queryTopicComment,
  addTopicComment,
  queryTopicByByTopicId,
  topicLike
} from "@/api/topic";
import { timeHandle } from "@/utils/index";
export default {
  name: "HomePage",
  data() {
    return {
      dataObj: {},
      commentsList: [],
      newComments: "",
      newCommentsSheet: "",
      inputShow: false
    };
  },
  computed: {},
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      let params = {
        topicId: this.$route.params.id,
        username: this.$store.state.user.username
      };
      queryTopicByByTopicId(params).then(res => {
        let tmp = res.data;
        if(tmp.topicContent){
          tmp.topicContent = tmp.topicContent.replace(/\n/g, "<br/>");
        }
        this.dataObj = tmp;
        this.dataObj.createTime = timeHandle(this.dataObj.createTime);
        this.dataObj.topicImageUrl =
          process.env.VUE_APP_BASE_API +
          "/file/download?url=" +
          this.dataObj.topicImageUrl;
        this.dataObj.iconUrl =
          process.env.VUE_APP_BASE_API +
          "/file/download?url=" +
          this.dataObj.iconUrl;
        this.$set(this.dataObj, "flag", true);
      });
      this.getTopicComment();
    },
    getTopicComment() {
      let params = {
        topicId: this.$route.params.id
      };
      queryTopicComment(params).then(res => {
        let tmp = res.data;
        for (let i = 0, len = tmp.length; i < len; i++) {
          if(tmp[i].commentContent){
            tmp[i].commentContent = tmp[i].commentContent.replace(/\n/g, "<br/>");
          }
        }
        this.commentsList = tmp;
      });
    },
    inputFocuse() {
      this.inputShow = true;
      this.$nextTick(() => {
        this.$refs.comment.focus();
      });
    },
    addTopicComment() {
      let params = {
        commentContent: this.newCommentsSheet,
        username: this.$store.state.user.username || "jack",
        topicId: this.$route.params.id
      };
      addTopicComment(params).then(res => {
        this.getTopicComment();
        this.inputShow = false;
        this.newCommentsSheet = "";
      }).catch(e => {
        console.log(e);
      });
    },
    onLike(item) {
      let params = {
        topicId: item.topicId,
        username: this.$store.state.user.username || "jack",
        isLike: !item.hasLiked
      };

      topicLike(params).then(res => {
        this.getData();
      }).catch(e => {
        console.log(e);
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.homepage {
  background: #fdfdfd;
  .user-message-wrapper {
    display: flex;
    padding: 0.16rem 0.3rem;
    box-sizing: border-box;
    .user-img {
      flex: 0 0 0.72rem;
      margin-right: 0.24rem;
      img {
        width: 0.72rem;
        height: 0.72rem;
      }
    }
    .user-message {
      display: flex;
      flex-direction: column;
      flex: 1;
      .user-name {
        margin-bottom: 0.15rem;
        font-family: "Montserrat-Medium";
        font-size: 0.28rem;
        color: #333333;
        letter-spacing: 0;
        line-height: 0.35rem;
      }
      .user-time {
        font-family: "Montserrat-Light";
        font-size: 0.24rem;
        color: #999999;
        letter-spacing: 0;
      }
    }
  }
  .img-wrapper {
    width: 100%;
    height: 4.8rem;
    img {
      width: 100%;
      height: 4.8rem;
      object-fit: cover;
    }
  }
  .like-wrapper {
    margin: 0.3rem 0.24rem;
    .like-icon {
      width: 0.5rem;
      height: 0.5rem;
      background: red;
    }
    .like-num {
      display: inline-block;
      margin-left: 0.08rem;
      vertical-align: middle;
      font-family: "Montserrat-Light";
      font-size: 0.28rem;
      color: #000;
      letter-spacing: 0;
      text-align: center;
      line-height: 0.56rem;
    }
  }
  .comments-wrapper {
    margin: 0.3rem 0.24rem;
    .title {
      margin-bottom: 0.3rem;
      font-family: "Montserrat-Regular";
      font-size: 0.28rem;
      color: #333333;
      letter-spacing: 0;
    }
    .content {
      font-family: "Montserrat-Light";
      font-size: 0.28rem;
      color: #333333;
      letter-spacing: 0;
      line-height: 0.36rem;
    }
    .total {
      margin-top: 0.1rem;
      font-family: "Montserrat-Light";
      font-size: 0.24rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.48rem;
    }
    .comments-list {
      padding-top: 0.1rem;
      .comments-list-item {
        &:not(:last-child) {
          margin-bottom: 0.13rem;
        }
        display: flex;
        .name {
          margin-right: 0.28rem;
          font-family: "Montserrat-Regular";
          font-size: 0.28rem;
          color: #333333;
          letter-spacing: 0;
          line-height: 0.36rem;
        }
        .content {
          font-family: "Montserrat-Light";
          font-size: 0.28rem;
          color: #666666;
          letter-spacing: 0;
          line-height: 0.36rem;
          word-break: break-all;
        }
      }
    }
  }
  .input-wrapper {
    width: 6.88rem;
    height: 0.76rem;
    padding: 0 0.3rem 0.5rem 0.3rem;
    ::v-deep .van-field__body {
      input::-webkit-input-placeholder {
        font-family: Montserrat-Light;
        font-size: 0.28rem;
        letter-spacing: 0;
        text-align: left;
        line-height: 0.36rem;
        color: #9A9A9A;
      }
    }
  }
  .action-sheet-wrapper {
    padding: 0.2rem;
    .action-sheet-input-wrapper {
      display: inline-block;
      width: 5.8rem;
      height: 0.76rem;
      padding: 0 0.3rem;
      ::v-deep .van-field__body {
        input::-webkit-input-placeholder {
          font-family: Montserrat-Light;
          font-size: 0.28rem;
          letter-spacing: 0;
          text-align: left;
          line-height: 0.36rem;
          color: #9A9A9A;
        }
      }
    }
  }
  .footer {
    margin: 1rem 0;
  }
}
</style>
<style lang="scss">
.input-wrapper {
  .van-cell {
    background: #f3f4f5;
    border-radius: 0.8rem;
  }
}
.action-sheet-wrapper {
  .action-sheet-input-wrapper {
    .van-cell {
      background: #f3f4f5;
      border-radius: 0.8rem;
    }
  }
  .van-button {
    width: 0.64rem;
    height: 0.64rem;
    vertical-align: middle;
    border-radius: 50%;
  }
}
</style>



