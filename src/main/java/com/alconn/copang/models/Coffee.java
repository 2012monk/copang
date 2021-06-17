package com.alconn.copang.models;

import com.alconn.copang.common.WeUser;
import com.alconn.copang.security.CoffeeListener;
import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(CoffeeListener.class)
@Builder @Getter
@Entity
public class Coffee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private WeUser user;


}
