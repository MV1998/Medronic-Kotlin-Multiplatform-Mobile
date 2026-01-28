package com.mv1998.medronic.viewmodels.fruit_view_model

import com.mv1998.medronic.domain.usecases.SearchFruitUseCase
import dev.icerock.moko.mvvm.flow.CMutableStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

data class UIState(
    val isLoading : Boolean = true,
    val list : List<String>
)

class FruitViewModel(
    private val fruitUseCase: SearchFruitUseCase
) : ViewModel() {
    private val _fruitState : CMutableStateFlow<UIState> = MutableStateFlow(UIState(list = emptyList(), isLoading = true)).cMutableStateFlow()
    val fruitState = _fruitState.cStateFlow()

    private val searchQuery = MutableStateFlow("")


    init {
       loadFruitList()
    }

    @OptIn(FlowPreview::class)
    private fun loadFruitList() {
        viewModelScope.launch {
            combine(
                fruitUseCase.execute(),
                searchQuery
                    .debounce(300)
                    .distinctUntilChanged()
            ) { fruits, query ->
                val filtered = if (query.isBlank()) {
                    fruits
                } else {
                    fruits.filter {
                        it.contains(query, ignoreCase = true)
                    }
                }

                UIState(
                    isLoading = false,
                    list = filtered
                )
            }.collect {
                _fruitState.value = it
            }
        }
    }


    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnQuery -> {
                searchQuery.value = uiAction.query
            }
        }
    }

}

sealed class UiAction {
    data class OnQuery(val query : String) : UiAction()
}