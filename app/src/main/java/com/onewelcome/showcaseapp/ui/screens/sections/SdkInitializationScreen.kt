package com.onewelcome.showcaseapp.ui.screens.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onewelcome.showcaseapp.Constants
import com.onewelcome.showcaseapp.R
import com.onewelcome.showcaseapp.ui.components.ExpandableCard
import com.onewelcome.showcaseapp.ui.components.NumberSettingTextField
import com.onewelcome.showcaseapp.ui.components.SdkFeatureScreen
import com.onewelcome.showcaseapp.ui.components.SettingCheckbox
import com.onewelcome.showcaseapp.ui.theme.Dimensions
import com.onewelcome.showcaseapp.viewmodel.SdkInitializationViewModel
import com.onewelcome.showcaseapp.viewmodel.SdkInitializationViewModel.State
import com.onewelcome.showcaseapp.viewmodel.SdkInitializationViewModel.UiEvent

@Composable
fun SdkInitializationScreen(
  navController: NavController,
  viewModel: SdkInitializationViewModel = hiltViewModel()
) {
  SdkInitializationScreenContent(
    uiState = viewModel.uiState,
    onNavigateBack = { navController.popBackStack() },
    onEvent = { viewModel.onEvent(it) })
}

@Composable
private fun SdkInitializationScreenContent(
  uiState: State,
  onNavigateBack: () -> Unit,
  onEvent: (UiEvent) -> Unit
) {
  SdkFeatureScreen(
    title = stringResource(R.string.section_title_sdk_initialization),
    onNavigateBack = onNavigateBack,
    description = { FeatureDescription() },
    settings = { SettingsSection(uiState = uiState, onEvent = onEvent) },
    result = { InitializationResult() },
    action = {
      Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onEvent(UiEvent.InitializeOneginiSdk) }
      ) {
        Text(stringResource(R.string.button_initialize_sdk))
      }
    }
  )
}

@Composable
private fun FeatureDescription() {
  Column {
    Text(
      style = MaterialTheme.typography.bodyLarge,
      text = stringResource(R.string.sdk_initialization_description)
    )
    Text(
      style = MaterialTheme.typography.bodyLarge,
      text = buildAnnotatedString {
        append(stringResource(R.string.read_more) + " ")
        withLink(
          LinkAnnotation.Url(
            Constants.DOCUMENTATION_SK_INITIALIZATION,
            TextLinkStyles(style = SpanStyle(textDecoration = TextDecoration.Underline, color = MaterialTheme.colorScheme.primary))
          )
        ) {
          append(stringResource(R.string.here))
        }
      })
  }
}

@Composable
private fun SettingsSection(uiState: State, onEvent: (UiEvent) -> Unit) {
  Column(verticalArrangement = Arrangement.spacedBy(Dimensions.verticalSpacing)) {
    Text(
      text = stringResource(R.string.required),
      style = MaterialTheme.typography.titleSmall
    )
    ExpandableCard(title = stringResource(R.string.label_title_handlers)) {} //TODO To be done in scope of EXAMPLEAND-153
    Text(
      text = stringResource(R.string.optional),
      style = MaterialTheme.typography.titleSmall
    )
    ExpandableCard(
      title = stringResource(R.string.label_sdk_settings)
    ) { SdkSettings(uiState, onEvent) }
    ExpandableCard(
      title = stringResource(R.string.label_http_settings)
    ) { HttpSettings(uiState, onEvent) }
    ExpandableCard(
      title = stringResource(R.string.label_custom_authenticators)
    ) { } //TODO To be done in scope of EXAMPLEAND-155
    ExpandableCard(
      title = stringResource(R.string.label_custom_identity_providers)
    ) { } //TODO To be done in scope of EXAMPLEAND-156
  }
}

@Composable
fun SdkSettings(uiState: State, onEvent: (UiEvent) -> Unit) {
  Column(
    modifier = Modifier.padding(Dimensions.standardPadding)
  ) {
    NumberSettingTextField(
      modifier = Modifier.fillMaxWidth(),
      value = uiState.deviceConfigCacheDurationSeconds,
      onValueChange = { onEvent(UiEvent.ChangeDeviceConfigCacheDurationValue(it)) },
      label = { Text(stringResource(R.string.option_set_device_config_cache_duration)) }
    )
  }
}

@Composable
private fun HttpSettings(uiState: State, onEvent: (UiEvent) -> Unit) {
  Column(
    modifier = Modifier.padding(Dimensions.standardPadding),
    verticalArrangement = Arrangement.spacedBy(Dimensions.verticalSpacing)
  ) {
    SettingCheckbox(
      text = stringResource(R.string.option_should_store_cookies),
      checked = uiState.shouldStoreCookies
    ) { onEvent(UiEvent.ChangeShouldStoreCookiesValue(it)) }
    NumberSettingTextField(
      modifier = Modifier.fillMaxWidth(),
      value = uiState.httpConnectTimeout,
      onValueChange = { onEvent(UiEvent.ChangeHttpConnectTimeoutValue(it)) },
      label = { Text(stringResource(R.string.option_set_http_connect_timeout)) }
    )
    NumberSettingTextField(
      modifier = Modifier.fillMaxWidth(),
      value = uiState.httpReadTimeout,
      onValueChange = { onEvent(UiEvent.ChangeHttpReadTimeoutValue(it)) },
      label = { Text(stringResource(R.string.option_set_http_read_timeout)) }
    )
  }
}

@Composable
private fun InitializationResult() {
  Column {

  }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
  SdkInitializationScreenContent(
    uiState = State(),
    onNavigateBack = {},
    onEvent = {})
}
