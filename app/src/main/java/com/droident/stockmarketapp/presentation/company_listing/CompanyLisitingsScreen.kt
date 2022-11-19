package com.droident.stockmarketapp.presentation.company_listing


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start = true)
@Composable
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingViewModel= hiltViewModel()
){
  val swipeRefreshState= SwipeRefreshState(
      isRefreshing = viewModel.state.isRefreshing
  )
    val state= viewModel.state

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    CompanyListingsEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            textStyle = TextStyle(color = Color.White
                , fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Monospace
            ),
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.onEvent(CompanyListingsEvent.Refresh)
        }) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ){
                items ( state.companies.size){i->
                      val company=state.companies[i]
                      CompanyItem(
                          company = company,
                          modifier = Modifier
                              .fillMaxWidth()
                              .clickable {

                              }
                      )
                    if(i<state.companies.size)
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 14.dp
                            )
                        )
                }
            }
            
        }


    }
}