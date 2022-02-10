package spring.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class DetailsErrors {

    private Date timeMark;
    private String message;
    private String details;
}
