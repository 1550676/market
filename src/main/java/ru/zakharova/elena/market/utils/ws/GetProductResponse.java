//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.08.22 at 03:11:11 PM MSK 
//


package ru.zakharova.elena.market.utils.ws;

import org.springframework.stereotype.Component;
import ru.zakharova.elena.market.entities.dtos.ProductDTO;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "productsDTOs"
})
@XmlRootElement(name = "getProductResponse")
@Component
public class GetProductResponse {

    @XmlElement(required = true)
    protected List<ProductDTO> productsDTOs;

    public List<ProductDTO> getProductsDTOs() {
        if (productsDTOs == null) {
            productsDTOs = new ArrayList<>();
        }
        return this.productsDTOs;
    }

}
