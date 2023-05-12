package br.com.java.datacalculatefreight.application.orders.resources;

import br.com.java.datacalculatefreight.application.company.resources.CompanyResponse;
import br.com.java.datacalculatefreight.application.orders.OrdersService;
import br.com.java.datacalculatefreight.utils.DefaultLog;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersController {

    @Autowired
    private DefaultLog defaultLog;

    @Autowired
    private OrdersService ordersService;

    private static final String API_V1 = "/orders/v1/";

    @ApiOperation("Pedido por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<OrdersResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        return ResponseEntity.ok(ordersService.getById(id));
    }

    @ApiOperation("Pedido por numero e empresa")
    @GetMapping(value = "/v1/order/{orderNumber}/company/{companyId}")
    public ResponseEntity<OrdersResponse> getByDocument(@Valid @PathVariable("orderNumber") String orderNumber, @Valid @PathVariable("companyId") Long companyId) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1 + "document/");
        return ResponseEntity.ok(ordersService.getByOrder(orderNumber, companyId));
    }

    @ApiOperation("Pedidos listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<OrdersResponse>> getAll(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String sortBy,
                                                        @RequestParam(required = false) String sortDirection) {
        return new ResponseEntity<>(ordersService.getAll(page, size, sortBy, sortDirection), HttpStatus.OK);
    }
}
