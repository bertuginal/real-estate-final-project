package com.example.patika.dto.request;

import com.example.patika.model.enums.AdvertStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertStatusRequest {

    private int id;
    private AdvertStatus advertStatus;
}
