package com.jwt.util;

import java.util.ArrayList;
import java.util.List;

public class StaticVariables {

    public final static List<String> All_PERMISSIONS = new ArrayList<String>(){{
        add("/login");
        add("/test-authentication");
    }};

}
