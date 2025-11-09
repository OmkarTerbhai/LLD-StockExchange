package com.lld.stockexchange.services;

import com.lld.stockexchange.entities.Stock;

public interface StockService {

    Stock getStockBySymbol(String stockSymbol);
}
