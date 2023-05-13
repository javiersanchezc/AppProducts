package com.app.product.AppProducts.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.ProblemDetail;

@JsonIgnoreProperties(value = {
        "type",
        "instance"
})
public class ProblemDetailBase extends ProblemDetail {
}
