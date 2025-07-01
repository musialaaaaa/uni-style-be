package org.example.uni_style_be.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.uni_style_be.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingCartServiceImpl implements ShoppingCartService {
//    private final String PREFIX_CODE = "SP";
//    ShoppingCartReposotory shoppingCartReposotory;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public ShoppingCartResponse create(ShoppingCartRequest rq) {
//// tu sinh ma gio hang
//        Long nextValue = shoppingCartReposotory.getNextSeq();
//        String code =  PREFIX_CODE+nextValue;
//
//        Cart shoppingCart = Cart.builder()
//                .name(rq.getName())
//                .status(Cart.Status.valueOf(rq.getStatus().toUpperCase()))
//                .isDeleted(false)
//                .build();
//        Cart shoppingCartResult=shoppingCartReposotory.save(shoppingCart);
//        return mapToCreateResponse(shoppingCartResult);
//    }
//
//    private ShoppingCartResponse mapToCreateResponse(Cart shoppingCartResult) {
//        return ShoppingCartResponse.builder()
//                .id(shoppingCartResult.getId())
//                .name(shoppingCartResult.getName())
//                .status(shoppingCartResult.getStatus().toString())
//                .isDeleted(shoppingCartResult.getIsDeleted())
//                .build();
//    }
//
//    @Override
//    public void delete(Long id) {
//        Cart shoppingCart = shoppingCartReposotory.findById(id).orElseThrow();
//        shoppingCart.setIsDeleted(true);
//        shoppingCartReposotory.save(shoppingCart);
//    }
//
//    @Override
//    public ShoppingCartRequest update(Long id, ShoppingCartRequest shoppingCartRequest) throws JsonMappingException {
//        Cart shoppingCart = shoppingCartReposotory.findById(id).orElseThrow();
//        objectMapper.updateValue(shoppingCart, shoppingCartRequest);
//        return objectMapper.convertValue(shoppingCartReposotory.save(shoppingCart), ShoppingCartRequest.class);
//    }
//

}
