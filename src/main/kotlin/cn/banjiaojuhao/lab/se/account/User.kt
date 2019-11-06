package cn.banjiaojuhao.lab.se.account

import com.alibaba.fastjson.annotation.JSONField

enum class UserType(val value: Int) { KeyBoarder(0), Official(1) }

data class User(
        val uid: Int,
        val nickName: String,
        val username: String,
        val password: String,
        val userType: Int,
        val department: String,
        val position: String,
        val phone: String,
        val email: String)

data class UserProfile(
        @JSONField(name = "uid") var uid: Int,
        @JSONField(name = "nick_name") val nickName: String,
        @JSONField(name = "department") val department: String,
        @JSONField(name = "position") val position: String,
        @JSONField(name = "phone") val phone: String,
        @JSONField(name = "email") val email: String
)