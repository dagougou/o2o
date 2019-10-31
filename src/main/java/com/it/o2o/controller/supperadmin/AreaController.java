package com.it.o2o.controller.supperadmin;

import com.it.o2o.entity.Area;
import com.it.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjh
 * @create 2019-05-30-17:48
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {
     Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @ResponseBody
    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    public Map<String,Object> listArea() {
        List<Area> areas;
        Map<String, Object> map = new HashMap<>();
        try {
            areas = areaService.getAreas();
            map.put("rows", areas);
            map.put("total", areas.size());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", e.getMessage());
        }
        return map;
    }
}
