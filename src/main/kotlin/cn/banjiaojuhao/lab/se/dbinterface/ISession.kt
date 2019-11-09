package cn.banjiaojuhao.lab.se.dbinterface

interface ISession {
    /**
     * generate a unique sid
     * insert a record(sid, uid) into db_session
     * return sid.
     *
     * @param uid userId
     * @return sid sessionId
     */
    fun createSession(uid: Int): String

    /**
     * query middleware whose sid == sid in params
     *
     * @param sid sid of middleware to query
     * @return uid of that middleware or null if not exists
     */
    fun querySession(sid: String): Int?

    /**
     * delete middleware whose sid == sid in params
     *
     * @param sid sid of middleware to delete
     */
    fun deleteSession(sid: String)
}