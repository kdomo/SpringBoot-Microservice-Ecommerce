package com.domo.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.domo.orderservice.dto.OrderLineItemsDto;
import com.domo.orderservice.dto.OrderRequest;
import com.domo.orderservice.model.Order;
import com.domo.orderservice.model.OrderLineItems;
import com.domo.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	public void placeOrder(OrderRequest orderRequest) {
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
				.map(this::mapToDto)
				.toList();

		Order order = Order.builder()
				.orderNumber(UUID.randomUUID().toString())
				.orderLineItemsList(orderLineItems)
				.build();

		orderRepository.save(order);
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		return OrderLineItems.builder()
				.price(orderLineItemsDto.getPrice())
				.quantity(orderLineItemsDto.getQuantity())
				.skuCode(orderLineItemsDto.getSkuCode())
				.build();
	}
}
