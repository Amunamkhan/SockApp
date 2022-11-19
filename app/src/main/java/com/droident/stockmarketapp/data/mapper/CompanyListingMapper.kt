package com.droident.stockmarketapp.data.mapper

import com.droident.stockmarketapp.data.local.CompanyListingEntity
import com.droident.stockmarketapp.domain.model.CompanyListing


fun CompanyListingEntity.toCompanyListing():CompanyListing{
    return  CompanyListing(
             name=name,
             symbol=symbol,
             exchange=exchange
    )
}

fun CompanyListing.toCompanyListingEntity():CompanyListingEntity{
    return  CompanyListingEntity(
        name=name,
        symbol=symbol,
        exchange=exchange
    )
}