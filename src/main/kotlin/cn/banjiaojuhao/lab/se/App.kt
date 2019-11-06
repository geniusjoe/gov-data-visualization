package cn.banjiaojuhao.lab.se

import cn.banjiaojuhao.lab.se.account.User
import cn.banjiaojuhao.lab.se.account.UserProfile
import cn.banjiaojuhao.lab.se.captcha.Captcha
import cn.banjiaojuhao.lab.se.db.account.DbAccount
import cn.banjiaojuhao.lab.se.db.account.DbSession
import cn.banjiaojuhao.lab.se.db.event.DbEvent
import cn.banjiaojuhao.lab.se.event.Event
import cn.banjiaojuhao.lab.se.event.Overview
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import io.vertx.core.Vertx
import io.vertx.ext.web.Route
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.core.closeAwait
import io.vertx.kotlin.core.http.closeAwait
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sun.misc.Signal
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class OverallResponse(
        @JSONField(name = "code") val code: Int,
        @JSONField(name = "message") val message: String,
        @JSONField(name = "data") val data: Any? = null)

data class LoginData(
        @JSONField(name = "token") val token: String,
        @JSONField(name = "user_type") val userType: Int)

class InvalidParameter(val paramName: String) : Exception()
class InvalidToken : Exception()
class ConcreteException(val code: Int, val msg: String) : Exception()

val vertx = Vertx.vertx()!!
val dbAccount = DbAccount()
val dbSession = DbSession()
val dbEvent = DbEvent()

fun main() = runBlocking {
    val httpServer = vertx.createHttpServer()
    val router = Router.router(vertx)
    val captcha = Captcha()

    router.get("/api/captcha").handler(this) {
        return@handler captcha.createCaptcha()
    }

    router.post("/api/user:login").handler(this) {
        val params = it.request().params()
        val username = params["username"] ?: throw InvalidParameter("username")
        val password = params["password"] ?: throw InvalidParameter("password")
        val captchaId = params["captcha_id"]?.toIntOrNull() ?: throw InvalidParameter("captcha_id")
        val captchaText = params["captcha_text"] ?: throw InvalidParameter("captcha_text")
        if (!captcha.verify(captchaId, captchaText)) {
            throw ConcreteException(1, "wrong captcha")
        }
        val user = dbAccount.queryUserByName(username) ?: throw ConcreteException(2, "user not exists")
        if (user.password != password) {
            throw ConcreteException(3, "wrong password")
        }
        val sid = dbSession.createSession(user.uid)
        return@handler LoginData(sid, user.userType)
    }


    router.post("/api/user:logout").handler(this) {
        val params = it.request().params()
        val sid = params["token"] ?: throw InvalidParameter("token")
        dbSession.deleteSession(sid)
        return@handler null
    }


    router.get("/api/events").handlerWithAuth(this) { routingContext, user ->
        val params = routingContext.request().params()
        when (user.userType) {
            0 -> {
                // keyboarder
                return@handlerWithAuth dbEvent.queryAppeal(user.uid)
            }
            1 -> {
                // official
                val startTime = params["start_time"]?.toLongOrNull() ?: throw InvalidParameter("start_time")
                val endTime = params["end_time"]?.toLongOrNull() ?: throw InvalidParameter("end_time")
                return@handlerWithAuth Overview(
                        dbEvent.queryOverall(startTime, endTime),
                        dbEvent.queryProperty(startTime, endTime),
                        dbEvent.queryStreetSubtype(startTime, endTime),
                        dbEvent.queryHotCommunity(startTime, endTime),
                        dbEvent.queryArchiveEvent(startTime, endTime),
                        dbEvent.queryWordCloud(startTime, endTime),
                        dbEvent.queryEventSrc(startTime, endTime)
                )
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }


    router.post("/api/events").handlerWithAuth(this) { routingContext, user ->
        val params = routingContext.request().params()
        when (user.userType) {
            0 -> {
                // keyboarder
                val appealText = params["appeal"] ?: throw InvalidParameter("appeal")
                val event = JSON.parseObject(appealText, Event::class.java)
                dbEvent.addAppeal(event)
                return@handlerWithAuth null
            }
            1 -> {
                // official
                throw InvalidToken()
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.put("/api/events").handlerWithAuth(this) { routingContext, user ->
        val params = routingContext.request().params()
        when (user.userType) {
            0 -> {
                // keyboarder
                val appealText = params["appeal"] ?: throw InvalidParameter("appeal")
                val event = JSON.parseObject(appealText, Event::class.java)
                dbEvent.updateAppeal(event)
                return@handlerWithAuth null
            }
            1 -> {
                // official
                throw InvalidToken()
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.delete("/api/events").handlerWithAuth(this) { routingContext, user ->
        val params = routingContext.request().params()
        when (user.userType) {
            0 -> {
                // keyboarder
                val recId = params["rec_id"]?.toIntOrNull() ?: throw InvalidParameter("rec_id")
                dbEvent.deleteAppeal(recId)
                return@handlerWithAuth null
            }
            1 -> {
                // official
                throw InvalidToken()
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.get("/api/events/undisposed/dynamic").handlerWithAuth(this) { routingContext, user ->
        val params = routingContext.request().params()
        when (user.userType) {
            0 -> {
                // keyboarder
                throw InvalidToken()
            }
            1 -> {
                // official
                val timeAfter = params["time_after"]?.toLongOrNull() ?: throw InvalidParameter("time_after")
                return@handlerWithAuth dbEvent.queryShortEvent(timeAfter)
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.get("/api/events/time_pass").handlerWithAuth(this) { routingContext, user ->
        when (user.userType) {
            0 -> {
                // keyboarder
                throw InvalidToken()
            }
            1 -> {
                // official
                return@handlerWithAuth dbEvent.queryTimePassed()
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.get("/api/events/undisposed").handlerWithAuth(this) { routingContext, user ->
        when (user.userType) {
            0 -> {
                // keyboarder
                throw InvalidToken()
            }
            1 -> {
                // official
                return@handlerWithAuth dbEvent.queryUndisposedAppeal()
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.get("/api/kpi").handlerWithAuth(this) { routingContext, user ->
        when (user.userType) {
            0 -> {
                // keyboarder
                throw InvalidToken()
            }
            1 -> {
                // official
                return@handlerWithAuth dbEvent.queryDepartmentKpi()
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.get("/api/user/profile").handlerWithAuth(this) { routingContext, user ->
        when (user.userType) {
            0, 1 -> {
                return@handlerWithAuth dbAccount.queryUserProfile(user.uid)
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.put("/api/user/profile").handlerWithAuth(this) { routingContext, user ->
        when (user.userType) {
            0, 1 -> {
                val params = routingContext.request().params()
                val profileText = params["profile"] ?: throw InvalidParameter("profile")
                val profile = JSON.parseObject(profileText, UserProfile::class.java)
                profile.uid = user.uid
                dbAccount.updateUserProfile(profile)
                return@handlerWithAuth null
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }

    router.put("/api/user/password").handlerWithAuth(this) { routingContext, user ->
        when (user.userType) {
            0, 1 -> {
                val params = routingContext.request().params()
                val oldPassword = params["old_password"] ?: throw InvalidParameter("old_password")
                val newPassword = params["new_password"] ?: throw InvalidParameter("new_password")
                if (user.password != oldPassword) {
                    throw ConcreteException(1, "wrong old_password")
                }
                dbAccount.changePassword(user.uid, newPassword)
                return@handlerWithAuth null
            }
            else -> {
                throw IllegalStateException("wrong user type from database ${user.userType}")
            }
        }
    }



    router.route("/*").handler(StaticHandler.create())

    httpServer.requestHandler(router).listenAwait(8080)
    suspendCoroutine<Unit> { con ->
        Signal.handle(Signal("INT")) {
            con.resume(Unit)
        }
    }
    httpServer.closeAwait()
    vertx.closeAwait()
    this.cancel()
}

/**
 * handler wrapper with exception catch
 */
fun Route.handler(coroutineScope: CoroutineScope, handler: suspend (RoutingContext) -> Any?) {
    this.handler { routingContext: RoutingContext ->
        coroutineScope.launch(vertx.dispatcher()) {
            try {
                val result = OverallResponse(0, "", handler(routingContext))
                routingContext.response().putHeader("Content-Type", "application/json")
                routingContext.response().end(JSON.toJSONString(result))
            } catch (e: InvalidToken) {
                val result = OverallResponse(101, "illegal token")
                routingContext.response().putHeader("Content-Type", "application/json")
                routingContext.response().end(JSON.toJSONString(result))
            } catch (e: InvalidParameter) {
                val result = OverallResponse(102, "illegal params ${e.paramName}")
                routingContext.response().putHeader("Content-Type", "application/json")
                routingContext.response().end(JSON.toJSONString(result))
            } catch (e: ConcreteException) {
                val result = OverallResponse(e.code, e.msg)
                routingContext.response().putHeader("Content-Type", "application/json")
                routingContext.response().end(JSON.toJSONString(result))
            } catch (e: Exception) {
                val result = OverallResponse(103, "unknown error $e")
                routingContext.response().putHeader("Content-Type", "application/json")
                routingContext.response().end(JSON.toJSONString(result))
            }
        }
    }
}

fun Route.handlerWithAuth(coroutineScope: CoroutineScope, handler: suspend (RoutingContext, User) -> Any?) {
    this.handler(coroutineScope) { routingContext: RoutingContext ->
        val params = routingContext.request().params()
        val token = params["token"] ?: throw InvalidParameter("token")
        val uid = dbSession.querySession(token) ?: throw InvalidToken()
        val user = dbAccount.queryUserByUid(uid) ?: throw InvalidToken()
        return@handler handler(routingContext, user)
    }
}
