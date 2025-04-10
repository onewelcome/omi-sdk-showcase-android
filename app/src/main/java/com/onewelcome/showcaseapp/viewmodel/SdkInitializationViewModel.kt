package com.onewelcome.showcaseapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.onewelcome.showcaseapp.entity.OmiSdkInitializationSettings
import com.onewelcome.showcaseapp.usecase.OmiSdkInitializationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SdkInitializationViewModel @Inject constructor(
  private val omiSdkInitializationUseCase: OmiSdkInitializationUseCase
) : ViewModel() {

  var uiState by mutableStateOf(State())
    private set

  fun onEvent(event: UiEvent) {
    when (event) {
      is UiEvent.ChangeHttpConnectTimeoutValue -> uiState = uiState.copy(httpConnectTimeout = event.value)
      is UiEvent.ChangeHttpReadTimeoutValue -> uiState = uiState.copy(httpReadTimeout = event.value)
      is UiEvent.ChangeShouldStoreCookiesValue -> uiState = uiState.copy(shouldStoreCookies = event.value)
      is UiEvent.ChangeDeviceConfigCacheDurationValue -> uiState = uiState.copy(deviceConfigCacheDurationSeconds = event.value)
      is UiEvent.InitializeOneginiSdk -> initializeOmiSdk()
    }
  }

  private fun initializeOmiSdk() {
    val settings = OmiSdkInitializationSettings(
      shouldStoreCookies = uiState.shouldStoreCookies,
      httpConnectTimeout = uiState.httpConnectTimeout,
      httpReadTimeout = uiState.httpReadTimeout,
      deviceConfigCacheDuration = uiState.deviceConfigCacheDurationSeconds
    )
    omiSdkInitializationUseCase.initialize(settings)
  }

  data class State(
    val shouldStoreCookies: Boolean = true,
    val httpConnectTimeout: Int? = null,
    val httpReadTimeout: Int? = null,
    val deviceConfigCacheDurationSeconds: Int? = null
  )

  sealed interface UiEvent {
    data class ChangeShouldStoreCookiesValue(val value: Boolean) : UiEvent
    data class ChangeHttpConnectTimeoutValue(val value: Int?) : UiEvent
    data class ChangeHttpReadTimeoutValue(val value: Int?) : UiEvent
    data class ChangeDeviceConfigCacheDurationValue(val value: Int?) : UiEvent
    data object InitializeOneginiSdk : UiEvent
  }
}
