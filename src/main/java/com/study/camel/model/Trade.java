package com.study.camel.model;

import java.util.Objects;

public class Trade {
    private int id;
    private String symbol;
    private int shares;

    public Trade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return id == trade.id && shares == trade.shares && Objects.equals(symbol, trade.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol, shares);
    }

    public void incrementShares() {
        shares++;
    }
}
