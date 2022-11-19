package com.droident.stockmarketapp.presentation.company_listing

import com.droident.stockmarketapp.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean=false,
    val isRefreshing: Boolean=false,
    val searchQuery: String=""
)
