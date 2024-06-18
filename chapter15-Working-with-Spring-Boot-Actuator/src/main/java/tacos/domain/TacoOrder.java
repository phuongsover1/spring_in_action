package tacos.domain;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    public static TacoOrder empty = new TacoOrder("null",null,null,null,null,null,null,null,null,null,null);
    @Id
    private String id;

    private Date placedAt;

    @NotBlank(message = "Delivery name is required!!")
    private String deliveryName;

    @NotBlank(message = "Street is required!!")
    private String deliveryStreet;

    @NotBlank(message = "City is required!!")
    private String deliveryCity;

    @NotBlank(message = "State is required!!")
    private String deliveryState;

    @NotBlank(message = "Zip code is required!!")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card number!!")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }
}