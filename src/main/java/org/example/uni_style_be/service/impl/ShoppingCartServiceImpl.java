package org.example.uni_style_be.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.entities.Order;
import org.example.uni_style_be.entities.ShoppingCart;
import org.example.uni_style_be.model.request.OrderRequest;
import org.example.uni_style_be.model.request.ShoppingCartRequest;
import org.example.uni_style_be.model.response.ShoppingCartResponse;
import org.example.uni_style_be.repositories.ShoppingCartReposotory;
import org.example.uni_style_be.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final String PREFIX_CODE = "SP";
    ShoppingCartReposotory shoppingCartReposotory;
    private final ObjectMapper objectMapper;

    @Override
    public ShoppingCartResponse create(ShoppingCartRequest rq) {
// tu sinh ma gio hang
        Long nextValue = shoppingCartReposotory.getNextSeq();
        String code =  PREFIX_CODE+nextValue;

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .name(rq.getName())
                .status(ShoppingCart.Status.valueOf(rq.getStatus().toUpperCase()))
                .isDeleted(false)
                .build();
        ShoppingCart shoppingCartResult=shoppingCartReposotory.save(shoppingCart);
        return mapToCreateResponse(shoppingCartResult);
    }

    private ShoppingCartResponse mapToCreateResponse(ShoppingCart shoppingCartResult) {
        return ShoppingCartResponse.builder()
                .id(shoppingCartResult.getId())
                .name(shoppingCartResult.getName())
                .status(shoppingCartResult.getStatus().toString())
                .isDeleted(shoppingCartResult.getIsDeleted())
                .build();
    }

    @Override
    public void delete(Long id) {
        ShoppingCart shoppingCart = shoppingCartReposotory.findById(id).orElseThrow();
        shoppingCart.setIsDeleted(true);
        shoppingCartReposotory.save(shoppingCart);
    }

    @Override
    public ShoppingCartRequest update(Long id, ShoppingCartRequest shoppingCartRequest) throws JsonMappingException {
        ShoppingCart shoppingCart = shoppingCartReposotory.findById(id).orElseThrow();
        objectMapper.updateValue(shoppingCart, shoppingCartRequest);
        return objectMapper.convertValue(shoppingCartReposotory.save(shoppingCart), ShoppingCartRequest.class);
    }


}
