package com.onewelcome.core.di

import com.onewelcome.core.facade.OmiSdkEngine
import com.onewelcome.core.facade.OmiSdkFacade
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FacadeModule {

  @Binds
  fun bindOmiSdkFacade(omiSdkEngine: OmiSdkEngine): OmiSdkFacade
}

