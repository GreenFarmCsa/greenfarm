package com.callforcode.greenfarm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GFBoxConfirmInfo {

    private GFUser user;

    private GFFarm farm;

}
