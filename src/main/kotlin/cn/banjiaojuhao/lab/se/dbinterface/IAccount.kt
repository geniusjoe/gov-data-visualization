package cn.banjiaojuhao.lab.se.dbinterface

import cn.banjiaojuhao.lab.se.account.User
import cn.banjiaojuhao.lab.se.account.UserProfile

interface IAccount {
    /**
     * query a record whose username == username in param
     *
     * @param username
     * @return the user or null if no one meet requires
     */
    fun queryUserByName(username: String): User?

    /**
     * query a record whose uid == uid in param
     *
     * @param uid
     * @return the user or null if no one meet requires
     */
    fun queryUserByUid(uid: Int): User?

    /**
     * change password of user
     *
     * @param uid          user id
     * @param new_password
     */
    fun changePassword(uid: Int, new_password: String)

    /**
     * update part of tb_user
     *
     * @param new_profile
     */
    fun updateUserProfile(new_profile: UserProfile)

    /**
     * query user profile by uid
     *
     * @param uid
     * @return
     */
    fun queryUserProfile(uid: Int): UserProfile?
}