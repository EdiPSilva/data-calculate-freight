package br.com.java.datacalculatefreight.application.orders.builders;

import br.com.java.datacalculatefreight.application.calculationFreight.builders.CalculationFreightEntityBuilder;
import br.com.java.datacalculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.orders.persistence.OrdersEntity;
import br.com.java.datacalculatefreight.utils.Fuctions;

public class OrdersEntityBuilder {

    private OrdersEntity ordersEntity;

    private OrdersEntityBuilder() {

    }

    public static OrdersEntityBuilder getInstance() {
        final OrdersEntityBuilder builder = new OrdersEntityBuilder();
        builder.ordersEntity = OrdersEntity.builder()
                .id(1L)
                .companyEntity(CompanyEntityBuilder.getInstance().getCompanyEntity())
                .calculationFreightEntity(CalculationFreightEntityBuilder.getInstance(1L).getCalculationFreightEntity())
                .orderNumber("123")
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public OrdersEntity getOrdersEntity() {
        return this.ordersEntity;
    }
}
