package com.example.citycyclerentals;


public class Item {
        private String name;
        private String location;
        private double hourlyPrice;
        private double dailyPrice;
        private double monthlyPrice;
        private boolean inCart;

        public Item(String name, String location, double hourlyPrice, double dailyPrice, double monthlyPrice ) {
            this.name = name;
            this.location = location;
            this.hourlyPrice = hourlyPrice;
            this.dailyPrice = dailyPrice;
            this.monthlyPrice = monthlyPrice;

        }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

}

