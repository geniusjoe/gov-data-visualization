package cn.banjiaojuhao.lab.se.captcha

import com.alibaba.fastjson.annotation.JSONField
import com.wf.captcha.SpecCaptcha
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

data class CaptchaImage(
        @JSONField(name = "id") val id: Int,
        @JSONField(name = "captcha_img") val image: String)

class Captcha {
    private val resultLocker = Mutex(false)
    private val resultMap = HashMap<Int, String>()
    private val random =
            Random(System.currentTimeMillis())

    suspend fun createCaptcha(): CaptchaImage {
        val captcha = SpecCaptcha()
        val resultText = captcha.text()
        val image = captcha.toBase64()
        var captchaId = 0
        resultLocker.withLock {
            while (resultMap.containsKey(captchaId)) {
                captchaId = random.nextInt()
            }
            resultMap[captchaId] = resultText
        }
        return CaptchaImage(captchaId, image)
    }

    suspend fun verify(id: Int, resultText: String): Boolean {
        resultLocker.withLock {
            return if (resultMap.containsKey(id)) {
                val correct = resultMap[id] == resultText
                resultMap.remove(id)
                correct
            } else {
                false
            }
        }
    }
}