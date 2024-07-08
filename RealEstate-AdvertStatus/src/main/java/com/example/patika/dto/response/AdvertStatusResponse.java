package com.example.patika.dto.response;

import com.example.patika.model.enums.AdvertStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertStatusResponse {

    private int id;
    private AdvertStatus advertStatus;
}
