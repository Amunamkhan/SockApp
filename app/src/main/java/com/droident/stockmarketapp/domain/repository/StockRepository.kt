package com.droident.stockmarketapp.domain.repository

import com.droident.stockmarketapp.domain.model.CompanyListing
import com.droident.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow


interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>



}