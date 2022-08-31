<template>
<div>
  <el-radio-group v-model="labelPosition" size="small">
    <el-radio-button label="left">左对齐</el-radio-button>
    <el-radio-button label="right">右对齐</el-radio-button>
    <el-radio-button label="top">顶部对齐</el-radio-button>
  </el-radio-group>
  <div style="margin: 20px;"></div>
  <el-form label-suffix=":" :label-position="labelPosition" label-width="80px" :model="userForm" :rules="rules" ref="uForm">
    <el-form-item label="用户名称">
      <el-input v-model="userForm.account" :readonly="true"></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickname">
      <el-input v-model="userForm.nickname" maxlength="20"
                :show-word-limit="true" :clearable="true"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="userForm.password" type="password" :show-password="true" :clearable="true"></el-input>
    </el-form-item>
    <el-form-item>
      <el-popconfirm
        title="确定要修改您的用户信息吗？"
        @confirm="onSubmit('uForm')"
      >
      <el-button type="primary" slot="reference">立即修改</el-button>
      </el-popconfirm>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</div>
</template>

<script>
export default {
  name: "PersonalCenter",
  data() {
    return {
      labelPosition: 'right',
      userForm: {
        account: this.$store.state.account,
        nickname: this.$store.state.name,
        password: ''
      },
      rules: {
        nickname: [
          {required: true, message: '请输入用户昵称', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入用户密码', trigger: 'blur'},
          {max: 15, message: '不能大于15个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    onSubmit(uForm) {
      let that=this;
      this.$refs[uForm].validate((valid) => {
        if (valid) {
          that.$store.dispatch('updateInfo',that.userForm).then((res)=>{
              that.$message({
                message: '成功修改用户信息',
                type: 'success'
              });
            //进行一下刷新页面，待完成
            that.userForm.nickname=that.$store.state.name;
            that.userForm.account=that.$store.state.account;
          }).catch((error)=>{that.$message.error("修改失败！");})
        } else {
          that.$message.error('数据不合法!!');
          return false;
        }
      })
    },
  }
}
</script>

<style scoped>
.photo{
  width: 300px;
}

</style>
