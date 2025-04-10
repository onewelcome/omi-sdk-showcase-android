package com.onewelcome.showcaseapp.omisdk

import android.content.Context
import com.onegini.mobile.sdk.android.client.OneginiClient
import com.onegini.mobile.sdk.android.client.OneginiClientBuilder
import com.onewelcome.showcaseapp.OneginiConfigModel
import com.onewelcome.showcaseapp.entity.OmiSdkInitializationSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class OmiSdkEngine @Inject constructor(
  @ApplicationContext private val context: Context,
  private val createPinRequestHandler: CreatePinRequestHandler,
  private val pinAuthenticationRequestHandler: PinAuthenticationRequestHandler,
) {
  val omiSdk
    get() = OneginiClient.instance ?: init(null)

  fun init(settings: OmiSdkInitializationSettings?): OneginiClient {
    return OneginiClientBuilder(context, createPinRequestHandler, pinAuthenticationRequestHandler)
      .setConfigModel(OneginiConfigModel())
      .apply {
        settings?.shouldStoreCookies?.let { shouldStoreCookies(it) }
        settings?.httpConnectTimeout?.let { setHttpConnectTimeout(it) }
        settings?.httpReadTimeout?.let { setHttpReadTimeout(it) }
        settings?.deviceConfigCacheDuration?.let { setDeviceConfigCacheDurationSeconds(it) }
      }.build()
  }
}
