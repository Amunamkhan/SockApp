package com.droident.stockmarketapp.data.repository

import com.droident.stockmarketapp.data.csv.CSVParser
import com.droident.stockmarketapp.data.local.StockDatabase
import com.droident.stockmarketapp.data.mapper.toCompanyListing
import com.droident.stockmarketapp.data.mapper.toCompanyListingEntity
import com.droident.stockmarketapp.data.remote.StockApi
import com.droident.stockmarketapp.domain.model.CompanyListing
import com.droident.stockmarketapp.domain.repository.StockRepository
import com.droident.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingsParser: CSVParser<CompanyListing>,
) : StockRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {

        return flow {
            emit(Resource.Loading(true))

            val localListings = dao.searchCompanyListing(query)
            api
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isEmpty()

            val shouldNotLoadRemote = !isDbEmpty && !fetchFromRemote

            if (shouldNotLoadRemote) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListing()
                companyListingsParser.parse(response.byteStream());



            } catch (e: IOException) {
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->

                dao.clearCompanyListings()

                dao.insertCompanyListing(listings.map { companyListing ->
                    companyListing.toCompanyListingEntity()
                })


                emit(Resource.Success(
                    data = dao.searchCompanyListing("").map { it ->
                        it.toCompanyListing()
                    }
                ))

                emit(Resource.Loading(false))


            }


        }

    }
}