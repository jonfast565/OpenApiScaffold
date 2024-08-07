package com.jfast
package utilities

import java.security.cert.X509Certificate
import javax.net.ssl._

object DisableSslValidation {
  def disableSslVerification(): Unit = {
    try {
      val trustAllCerts: Array[TrustManager] = Array(new X509TrustManager {
        override def getAcceptedIssuers: Array[X509Certificate] = null

        override def checkClientTrusted(certs: Array[X509Certificate], authType: String): Unit = {}

        override def checkServerTrusted(certs: Array[X509Certificate], authType: String): Unit = {}
      })

      val sc: SSLContext = SSLContext.getInstance("TLS")
      sc.init(null, trustAllCerts, new java.security.SecureRandom())
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory)

      val allHostsValid: HostnameVerifier = (_: String, _: SSLSession) => true
      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}

