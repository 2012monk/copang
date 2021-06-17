package com.alconn.copang.controller;

import com.alconn.copang.annotations.CheckIdentity;
import com.alconn.copang.common.ResponseMessage;
import com.alconn.copang.models.Coffee;
import com.alconn.copang.repo.CoffeeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CoffeeController {

    private final CoffeeRepo repo;

    @Secured("ROLE_CLIENT")
    @PostMapping("/coffee")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage<Coffee> makeOne(@RequestBody Coffee coffee) {
        return ResponseMessage.<Coffee>builder()
                .message("created")
                .code("101")
                .data(repo.save(coffee))
                .build();
    }

    @Secured("ROLE_CLIENT")
    @CheckIdentity
    // TODO how to figure out repository exceptions
    @GetMapping("/coffee/{id}")
    public ResponseMessage<Coffee> getOne(@PathVariable Long id){
        return ResponseMessage.<Coffee>builder()
                .data(repo.getById(id))
                .build();
    }

    @GetMapping("/coffee/all")
    public ResponseMessage<List<Coffee>> getList(){
        return ResponseMessage.<List<Coffee>>builder()
                .data(repo.findAll())
                .build();
    }

    @PutMapping("/coffee")
    public ResponseMessage<Coffee> updateCoffee(@RequestBody Coffee coffee) {
        return ResponseMessage.<Coffee>builder()
                .data(repo.save(coffee))
                .message("success")
                .code("update")
                .build();
    }

    @DeleteMapping("/coffee/{id}")
    public ResponseMessage<String> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseMessage.<String>builder().data(id.toString()).message("success").build();
    }
}
