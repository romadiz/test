package com.text.demo.controller;

import com.text.demo.model.TextAnalist;
import com.text.demo.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/betvictor")
public class Controller {

    @Autowired
    private TextService textService;

    @RequestMapping(value = "/text/p_start/{p_start}/p_end/{p_end}/w_count_min/{w_count_min}/w_count_max/{w_count_max}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TextAnalist getTextAnalyst(@PathVariable Integer p_start, @PathVariable Integer p_end, @PathVariable Integer w_count_min,
                                      @PathVariable Integer w_count_max){
        return textService.getTextAnalyst(p_start, p_end, w_count_min, w_count_max);
    }
}
