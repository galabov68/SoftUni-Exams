package alararestaurant.service;

import alararestaurant.domain.dtos.OrderItemDto;
import alararestaurant.domain.dtos.OrderRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final String ORDER_XML_FILE_PATH = "C:\\Users\\galab\\Downloads\\AlaraRestaurant\\src\\main\\resources\\files\\orders.xml";

    private final OrderRepository orderRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, EmployeeRepository employeeRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository) throws JAXBException {
        this.orderRepository = orderRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return fileUtil.readFile(ORDER_XML_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(OrderRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        OrderRootDto orderRootDto = (OrderRootDto) unmarshaller.unmarshal(new File(ORDER_XML_FILE_PATH));

        orderRootDto.getOrders()
                .forEach(orderDto -> {
                    Order order = modelMapper.map(orderDto, Order.class);

                    Employee employee = employeeRepository.findByName(orderDto.getEmployee());

                    if (!validationUtil.isValid(order) || employee == null) {
                        sb.append("Invalid data format.").append(System.lineSeparator());
                        return;
                    }
                    order.setEmployee(employee);
                    order.setOrderItems(null);
                    orderRepository.saveAndFlush(order);

                    for (OrderItemDto orderItemDto : orderDto.getItems().getItems()) {
                        Item item = itemRepository.findByName(orderItemDto.getName());
                        if (item == null) {
                            continue;
                        }
                        OrderItem orderItem = modelMapper.map(orderItemDto, OrderItem.class);
                        orderItem.setOrder(order);
                        orderItem.setItem(item);
                        if (!validationUtil.isValid(orderItem)) {
                            sb.append("Invalid data format.");
                            continue;
                        }
                        orderItemRepository.saveAndFlush(orderItem);
                    }
                    sb.append(String.format("Order for %s on %s added%n", order.getCustomer(), order.getDateTime()));
                });
        return sb.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder sb = new StringBuilder();
        orderRepository.findAllOrderFinishedByBurgerFlippers()
                .forEach(order -> {
                    sb.append(String.format("Name: %s%n", order.getEmployee().getName()));
                    sb.append(String.format("Orders:%n"));
                    sb.append(String.format("   Customer: %s%n", order.getCustomer()));
                    sb.append(String.format("   Items:%n"));
                    order.getOrderItems()
                            .forEach(item -> {
                                sb.append(String.format("       Name:%s%n", item.getItem().getName()));
                                sb.append(String.format("       Price:%s%n", item.getItem().getPrice()));
                                sb.append(String.format("       Quantity: %s%n%n", item.getQuantity()));
                            });
                });
        return sb.toString();
    }
}
