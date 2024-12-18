package com.backend.pro.http.model.pat;

import lombok.Data;

import java.util.List;

@Data
public class BusinessMessageData {
    List<OrderData> Data;
}