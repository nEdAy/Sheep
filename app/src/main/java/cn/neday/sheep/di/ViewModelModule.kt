package cn.neday.sheep.di

import cn.neday.sheep.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CreditHistoryViewModel() }

    viewModel { GoodsListViewModel() }

    viewModel { IndexViewModel() }

    viewModel { LoginViewModel() }

    viewModel { RankingListViewModel() }

    viewModel { SearchResultViewModel() }

    viewModel { SearchViewModel() }
}