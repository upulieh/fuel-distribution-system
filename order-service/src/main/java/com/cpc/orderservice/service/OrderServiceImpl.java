package com.cpc.orderservice.service;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpc.orderservice.OrderServiceApplication;
import com.cpc.orderservice.models.FuelType;
import com.cpc.orderservice.models.Order;
import com.cpc.orderservice.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Override
	public Order submitOrder(String stationId, Order order) {
//		setting values for the order
		String createdRefId = createId(stationId, order.getFuelType());
		order.setId(createdRefId);
		order.setReservedTime(LocalDateTime.now());
		order.setReserved(true);
		return orderRepository.save(order);
	}

	private String createId(String stationId, FuelType fuelType) {
		int uniqueId = (int) ((new Date().getTime() / 10000000L) % Integer.MAX_VALUE); // creating a unique value of 8
																						// numbers

		return stationId.substring(0, 4) + stationId.charAt(stationId.length() - 1) + uniqueId + getFId(fuelType);
	}

	private String getFId(FuelType fId) {
		switch (fId) {
		case OCTANE92:
			return "92";
		case OCTANE95:
			return "95";
		case REGULAR_DIESEL:
			return "RD";
		case SUPER_DIESEL:
			return "SD";
		default:
			System.out.println("No such FuelType. Can't create fId");
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String checkOrderStatus(String id) {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			return getStatus(order.get());
		} else {
			return "Please check the reference number and try again!";
		}
	}

	private String getStatus(Order o) {
		if (o.isDelivered()) {
			// is delivered
			LocalDateTime deliveredDateTime = o.getDeliveredTime();
			return "Delivery completed on " + (DateTimeFormatter.ISO_LOCAL_DATE).format(deliveredDateTime) + " at "
					+ (DateTimeFormatter.ISO_LOCAL_TIME).format(deliveredDateTime) + ". Thank you!";
		} else if (o.isDispatched()) {
			// is dispatched
			LocalDateTime dispatchedDateTime = o.getDispatchedTime();
			return "Dispatching completed on " + (DateTimeFormatter.ISO_LOCAL_DATE).format(dispatchedDateTime) + " at "
					+ (DateTimeFormatter.ISO_LOCAL_TIME).format(dispatchedDateTime) + ". Await delivery!";
		} else if (o.isScheduled()) {
			// is scheduled
			LocalDateTime scheduleDateTime = o.getScheduledTime();
			return "Scheduling completed on " + (DateTimeFormatter.ISO_LOCAL_DATE).format(scheduleDateTime) + " at "
					+ (DateTimeFormatter.ISO_LOCAL_TIME).format(scheduleDateTime) + ". Await dispatch!";
		} else if (o.isAllocated()) {
			// is allocated
			LocalDateTime allocatedDateTime = o.getAllocatedTime();
			return "Allocation completed on " + (DateTimeFormatter.ISO_LOCAL_DATE).format(allocatedDateTime) + " at "
					+ (DateTimeFormatter.ISO_LOCAL_TIME).format(allocatedDateTime) + ". Await scheduling!";
		} else if (o.isReserved()) {
			// is reserved
			LocalDateTime reservedDateTime = o.getReservedTime();
			return "Reservation completed on " + (DateTimeFormatter.ISO_LOCAL_DATE).format(reservedDateTime) + " at "
					+ (DateTimeFormatter.ISO_LOCAL_TIME).format(reservedDateTime) + ". Await allocation!";
		} else {
			// none of the fields is true
			return "Something went wrong...";
		}
	}

	@Override
	public String confirmOrderReceival(String id) {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			return updateDeliveredField(order.get());
		} else {
			return "Please check the reference number and try again!";
		}
	}

	private String updateDeliveredField(Order order) {
		// check for any db exceptions
		order.setDelivered(true);
		order.setDeliveredTime(LocalDateTime.now());
		orderRepository.save(order);
		return "Receival is confirmed. Thank you!";
	}

	@Override
	public List<Order> fetchAllOrders() {
		List<Order> orders = (List<Order>) orderRepository.findAll();
		// any all order related editing can be done here
		return orders;
	}

	// emitted from inventory service
	@Override
	public Order updateOrderAllocation(String id) {
		Optional<Order> order = orderRepository.findById(id);

		if (order.isPresent()) {
			return updateAllocatedField(order.get());
		} else {
			return null; // handle null in controller
		}
	}

	private Order updateAllocatedField(Order order) {
		order.setAllocated(true);
		order.setAllocatedTime(LocalDateTime.now());
		Order o = orderRepository.save(order);
		return o;
	}

	// emitted from schedule service
	@Override
	public Order updateOrderSchedule(String id, LocalDateTime scheduledDateTime) {
		Optional<Order> order = orderRepository.findById(id);

		if (order.isPresent()) {
			return updateScheduleField(order.get(), scheduledDateTime);
		} else {
			return null; // todo - handle null in controller
		}
	}

	private Order updateScheduleField(Order order, LocalDateTime scheduledDateTime) {
		order.setScheduled(true);
		order.setScheduledTime(scheduledDateTime);
		Order o = orderRepository.save(order);
		return o;
	}

	@Override
	public Order updateOrderDispatch(String id, LocalDateTime dispatchedDateTime) {
		Optional<Order> order = orderRepository.findById(id);

		if (order.isPresent()) {
			return updateDispatchField(order.get(), dispatchedDateTime);
		} else {
			return null; // todo - handle null in controller
		}
	}

	private Order updateDispatchField(Order order, LocalDateTime dispatchedDateTime) {
		order.setDispatched(true);
		order.setDispatchedTime(dispatchedDateTime);
		Order o = orderRepository.save(order);
		return o;
	}
}
