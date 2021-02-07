package org.models;

import lombok.Data;

@Data
public class Prices {

    private static Integer GARAN = 10;
    private static Integer THYAO = 12;
    private static Integer TOASO = 38;
    private static Integer EREGL = 14;
    private static Integer ARCLK = 35;

    public static void setPrice(String stockName, Integer price) {
        switch (stockName) {
            case "GARAN":
                Prices.GARAN = price;
                break;
            case "TOASO":
                Prices.TOASO = price;
                break;
            case "THYAO":
                Prices.THYAO = price;
                break;
            case "ARCLK":
                Prices.ARCLK = price;
                break;
            case "EREGL":
                Prices.EREGL = price;
                break;

            default:
                break;
        }
        System.out.println("TOASO price: " + getPrice("TOASO"));
        System.out.println("GARAN price: " + getPrice("GARAN"));
        System.out.println("THYAO price: " + getPrice("THYAO"));
        System.out.println("ARCLK price: " + getPrice("ARCLK"));
        System.out.println("EREGL price: " + getPrice("EREGL"));
    }

    public static Integer getPrice(String stockName) {
        switch (stockName) {
            case "GARAN":
                return Prices.GARAN;
            case "TOASO":
                return Prices.TOASO;
            case "THYAO":
                return Prices.THYAO;
            case "ARCLK":
                return Prices.ARCLK;
            case "EREGL":
                return Prices.EREGL;

            default:
                return -1;
        }
    }

    public static boolean isBuyValid(Integer price, String stockName) {
        return price >= getPrice(stockName);
    }

    public static boolean isSellValid(Integer price, String stockName) {
        return price <= getPrice(stockName);
    }
}