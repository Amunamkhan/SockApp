package com.droident.stockmarketapp.di

import com.droident.stockmarketapp.data.csv.CSVParser
import com.droident.stockmarketapp.data.csv.CompanyListingsParser
import com.droident.stockmarketapp.data.repository.StockRepositoryImpl
import com.droident.stockmarketapp.domain.model.CompanyListing
import com.droident.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingsParser: CompanyListingsParser):CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl):
            StockRepository
}