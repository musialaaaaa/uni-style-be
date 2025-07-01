package org.example.uni_style_be.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ShoppingCart")
@RequiredArgsConstructor
@Tag(name = "gio hang")
public class ShoppingCartController {
//    private final ShoppingCartService shoppingCartService;
//    @PostMapping
//    @Operation(summary = "them moi")
//    public ShoppingCartResponse create(@RequestBody @Valid ShoppingCartRequest rq) {
//        return shoppingCartService.create(rq);
//    }
//    @DeleteMapping("/{id}")
//    @Operation(summary = "delete")
//    public void delete(@PathVariable Long id) {
//        shoppingCartService.delete(id);
//    }
//    @PutMapping("/{id}")
//    @Operation(summary = "update")
//    public ShoppingCartRequest update(
//            @PathVariable Long id, @Valid @RequestBody ShoppingCartRequest shoppingCartRequest) throws JsonMappingException {
//        return shoppingCartService.update(id, shoppingCartRequest);
//    }
}
